package com.loyalflower.face_analysis_app.presentation.bus

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * EventBus의 기본 구현체
 *
 * [MutableSharedFlow]를 사용하여 이벤트를 관리하고,
 * 외부에는 읽기 전용 [SharedFlow]로 노출합니다.
 */
class EventFlowImpl : EventFlow {
    private val _events = MutableSharedFlow<SharedEvent>()
    override val events = _events.asSharedFlow()

    /**
     * 이벤트를 스트림에 발행합니다.
     *
     * @param event 발행할 [SharedEvent]
     */
    override suspend fun emit(event: SharedEvent) {
        _events.emit(event)
    }
}