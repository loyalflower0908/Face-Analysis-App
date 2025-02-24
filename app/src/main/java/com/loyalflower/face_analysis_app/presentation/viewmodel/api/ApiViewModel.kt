package com.loyalflower.face_analysis_app.presentation.viewmodel.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loyalflower.face_analysis_app.data.manager.FileManager
import com.loyalflower.face_analysis_app.domain.model.ImageUploadResult
import com.loyalflower.face_analysis_app.domain.model.LoginResult
import com.loyalflower.face_analysis_app.domain.model.UserAttributeResult
import com.loyalflower.face_analysis_app.domain.usecase.FetchUserAttributesUseCase
import com.loyalflower.face_analysis_app.domain.usecase.ImageUploadUseCase
import com.loyalflower.face_analysis_app.domain.usecase.LoginUseCase
import com.loyalflower.face_analysis_app.presentation.bus.EventFlow
import com.loyalflower.face_analysis_app.presentation.bus.SharedEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

/**
 * **인증 관련 ViewModel [ApiViewModel]**
 * - 사용자 인증 및 데이터 조회를 관리하는 ViewModel
 * - [EventFlow]를 통해 'CameraViewModel' 과 통신하여 이미지를 받아 업로드 후 인증 프로세스를 진행
 * - 상태 관리를 위해 [ApiUiState]를 사용하여 UI와 연동
 */
@HiltViewModel
class ApiViewModel @Inject constructor(
    private val fileManager: FileManager,
    private val uploadImageUseCase: ImageUploadUseCase,
    private val loginUseCase: LoginUseCase,
    private val fetchUserAttributesUseCase: FetchUserAttributesUseCase,
    private val eventFlow: EventFlow
) : ViewModel() {

    /**
     * **인증 상태**
     * - UI에서 관찰할 수 있도록 `StateFlow` 형태로 제공
     */
    private val _authState = MutableStateFlow<ApiUiState>(ApiUiState.Initial)
    val authState: StateFlow<ApiUiState> = _authState.asStateFlow()

    init {
        // ✅ EventBus를 구독하여 이미지 촬영 이벤트를 감지하고 업로드 실행
        viewModelScope.launch {
            eventFlow.events.collect { event ->
                when (event) {
                    is SharedEvent.ImageCaptured -> uploadImage(event.file)
                }
            }
        }
    }

    /**
     * **이미지 업로드 요청**
     * - 촬영된 이미지 파일을 업로드하고, 성공하면 로그인 요청을 수행
     *
     * @param file 업로드할 이미지 파일
     */
    private fun uploadImage(file: File) {
        viewModelScope.launch {
            _authState.value = ApiUiState.Loading // ✅ 업로드 시작 → Loading 상태로 변경

            when (val uploadResult = uploadImageUseCase.execute(file)) {
                is ImageUploadResult.Success -> {
                    fileManager.clearTempFiles() // ✅ 이미지 임시 파일 삭제
                    login(uploadResult.userId, uploadResult.password) // ✅ 로그인 요청
                }
                is ImageUploadResult.Error -> _authState.value = ApiUiState.Error(uploadResult.message)
            }
        }
    }

    /**
     * **사용자 로그인 요청**
     * - 업로드된 이미지가 인증되면, 제공된 계정 정보를 통해 로그인 요청 수행
     *
     * @param userId 사용자 아이디
     * @param password 비밀번호
     */
    private suspend fun login(userId: String, password: String) {
        when (val authResult = loginUseCase.execute(userId, password)) {
            is LoginResult.Success -> fetchUserAttributes(authResult.token, userId) // ✅ 로그인 성공 → 사용자 속성 조회 요청
            is LoginResult.Error -> _authState.value = ApiUiState.Error(authResult.message)
        }
    }

    /**
     * **사용자 속성 조회 요청**
     * - 사용자 ID를 기반으로 분석 데이터 (나이, 감정 등) 조회
     *
     * @param token 인증 토큰 (Bearer 토큰)
     * @param userId 사용자 아이디
     */
    private suspend fun fetchUserAttributes(token: String, userId: String) {
        when(val userAttributesResult = fetchUserAttributesUseCase.execute(token, userId)){
            is UserAttributeResult.Success -> _authState.value = ApiUiState.Success(userAttributesResult.attributes) // ✅ 속성 조회 성공 → UI 업데이트
            is UserAttributeResult.Error -> _authState.value = ApiUiState.Error(userAttributesResult.message)
        }
    }

    /**
     * **인증 상태 초기화**
     * - 인증 상태를 초기 상태로 리셋
     */
    fun resetAuthState() {
        _authState.value = ApiUiState.Initial
    }
}