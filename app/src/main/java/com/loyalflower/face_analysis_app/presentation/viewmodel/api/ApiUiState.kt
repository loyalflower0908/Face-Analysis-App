package com.loyalflower.face_analysis_app.presentation.viewmodel.api

import com.loyalflower.face_analysis_app.domain.model.UserAttribute

/**
 * **인증 상태 [ApiUiState]**
 * - [ApiViewModel]에서 Api 통신 상태를 나타내는 `sealed class`
 * - UI에서 통신 상태에 따라 적절한 화면을 표시할 때 사용됨
 */
sealed class ApiUiState {

    /**
     * **초기 상태**
     * - 인증 작업이 시작되지 않은 상태
     */
    object Initial : ApiUiState()

    /**
     * **로딩 상태**
     * - 통신 과정이 진행 중인 상태 (예: 로그인 요청, 사용자 데이터 조회 등)
     */
    object Loading : ApiUiState()

    /**
     * **통신 성공 상태**
     * - 성공적으로 사용자 속성 데이터를 가져온 경우
     *
     * @param userAttribute 서버에서 가져온 사용자 속성 리스트
     */
    data class Success(val userAttribute: List<UserAttribute>) : ApiUiState()

    /**
     * **통신 실패 상태**
     * - 통신 과정에서 오류가 발생한 경우
     *
     * @param message 오류 메시지 (예: 네트워크 오류, 잘못된 이미지 등)
     */
    data class Error(val message: String) : ApiUiState()
}
