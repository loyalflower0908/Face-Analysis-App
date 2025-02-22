package com.loyalflower.face_analysis_app.presentation.viewmodel.camera

import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loyalflower.face_analysis_app.data.manager.CameraManager
import com.loyalflower.face_analysis_app.data.manager.FileManager
import com.loyalflower.face_analysis_app.presentation.bus.EventBus
import com.loyalflower.face_analysis_app.presentation.bus.SharedEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * **카메라 관련 로직을 관리하는 ViewModel [CameraViewModel]**
 *
 * - 카메라 설정, 미리보기, 사진 촬영 등의 기능을 담당
 * - UI에서 카메라 상태를 관찰할 수 있도록 [StateFlow] 제공
 * - 오류 메시지는 [SharedFlow]를 통해 비동기적으로 전달
 *
 * @param cameraManager 카메라 제어를 담당하는 [CameraManager]
 * @param fileManager 촬영된 이미지를 저장하는 [FileManager]
 * @param eventBus 이벤트 공유를 위한 [EventBus] (사진 촬영 완료 이벤트 전송)
 */
@HiltViewModel
class CameraViewModel @Inject constructor(
    private val cameraManager: CameraManager,
    private val fileManager: FileManager,
    private val eventBus: EventBus
) : ViewModel() {

    /**
     * **카메라 UI 상태를 관리하는 StateFlow**
     * - 기본값: `CameraUiState.Initial`
     */
    private val _cameraState = MutableStateFlow<CameraUiState>(CameraUiState.Initial)
    val cameraState: StateFlow<CameraUiState> = _cameraState.asStateFlow()

    /**
     * **카메라 오류 메시지를 관리하는 SharedFlow**
     * - UI에서 오류 발생 시 토스트 메시지 등으로 표시 가능
     */
    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    /**
     * **카메라를 설정하고 미리보기를 활성화**
     *
     * @param lifecycleOwner 카메라를 바인딩할 [LifecycleOwner]
     */
    fun setupCamera(lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch {
            try {
                cameraManager.setupCamera(lifecycleOwner) // 카메라 설정
                _cameraState.value = CameraUiState.Preview // 미리보기 상태로 변경
            } catch (e: Exception) {
                _cameraState.value = CameraUiState.Error("카메라 설정 실패: ${e.localizedMessage}")
            }
        }
    }

    /**
     * **PreviewView를 설정하여 미리보기를 활성화**
     *
     * @param previewView 미리보기를 표시할 [PreviewView]
     */
    fun setupPreview(previewView: PreviewView) {
        try {
            cameraManager.setupPreview(previewView) // 카메라 프리뷰 설정
        } catch (e: Exception) {
            viewModelScope.launch {
                _errorMessage.emit("프리뷰 설정 실패: ${e.localizedMessage}") // 오류 메시지 전송
            }
        }
    }

    /**
     * **사진을 촬영하고 저장**
     * - 촬영 후 파일로 변환하여 [EventBus]를 통해 전송
     */
    fun takePhoto() {
        viewModelScope.launch {
            try {
                cameraManager.takePhoto { bitmap ->
                    viewModelScope.launch(Dispatchers.IO) {
                        try {
                            val file = fileManager.saveBitmapToFile(bitmap) // 촬영된 이미지 저장
                            eventBus.emit(SharedEvent.ImageCaptured(file)) // 저장된 파일을 EventBus로 전달
                        } catch (e: Exception) {
                            _cameraState.value = CameraUiState.Error("파일 저장 실패: ${e.localizedMessage}")
                        }
                    }
                }
            } catch (e: Exception) {
                _cameraState.value = CameraUiState.Error("사진 촬영 실패: ${e.localizedMessage}")
            }
        }
    }
}