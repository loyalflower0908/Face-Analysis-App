package com.loyalflower.face_analysis_app.presentation.bus

import kotlinx.coroutines.flow.SharedFlow

/**
 * ViewModel 간의 이벤트를 전달하기 위한 인터페이스
 *
 * [SharedFlow]를 사용하여 이벤트 스트림을 관리하며,
 * ViewModel 간의 느슨한 결합을 가능하게 합니다.
 */
interface EventBus {
    /**
     * 구독 가능한 이벤트 스트림
     *
     * 읽기 전용 [SharedFlow]로, 모든 구독자에게 이벤트를 전달합니다.
     */
    val events: SharedFlow<SharedEvent>

    /**
     * 새로운 이벤트를 스트림에 발행합니다.
     *
     * @param event 발행할 [SharedEvent]
     */
    suspend fun emit(event: SharedEvent)
}