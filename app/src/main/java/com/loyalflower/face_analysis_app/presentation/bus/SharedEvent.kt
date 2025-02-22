package com.loyalflower.face_analysis_app.presentation.bus

import java.io.File

/**
 * **공유 이벤트 (SharedEvent)**
 * - `EventBus`를 통해 ViewModel 간 이벤트를 전달할 때 사용되는 이벤트 클래스
 * - `sealed class`로 선언하여 이벤트 종류를 제한하고 타입 안전성을 보장
 */
sealed class SharedEvent {

    /**
     * **사진 촬영 이벤트**
     * - 카메라에서 촬영된 이미지 파일을 포함하는 이벤트
     * - `EventBus`를 통해 다른 ViewModel로 전달됨
     *
     * @param file 촬영된 이미지 파일
     */
    data class ImageCaptured(val file: File) : SharedEvent()
}