package com.loyalflower.face_analysis_app.presentation.viewmodel.camera

/**
 * **카메라 UI 상태 [CameraUiState]**
 *
 * - 카메라 화면의 상태를 관리하는 `sealed class`
 * - **ViewModel → UI** 상태 변화를 전달할 때 사용됨
 */
sealed class CameraUiState {

    /**
     * **초기 상태**
     * - 카메라가 아직 설정되지 않은 상태
     */
    object Initial : CameraUiState()

    /**
     * **미리보기 상태**
     * - 카메라가 설정되고, 미리보기가 활성화된 상태
     */
    object Preview : CameraUiState()

    /**
     * **오류 상태**
     * - 카메라 관련 오류가 발생한 경우
     *
     * @param message 오류 메시지
     */
    data class Error(val message: String) : CameraUiState()
}
