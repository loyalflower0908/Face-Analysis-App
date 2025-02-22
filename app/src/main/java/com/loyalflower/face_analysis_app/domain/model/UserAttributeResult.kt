package com.loyalflower.face_analysis_app.domain.model

/**
 * **사용자 속성 조회 결과 [UserAttributeResult]**
 * - 사용자 얼굴 분석 데이터 속성을 가져오는 API 요청의 결과를 나타내는 `sealed class`
 *   UI 및 비즈니스 로직에서 사용되는 도메인 모델
 * - `Success`와 `Error` 두 가지 상태를 포함
 */
sealed class UserAttributeResult {

    /**
     * **사용자 속성 조회 성공 결과**
     * - 요청이 성공하면 사용자 속성 리스트를 포함
     *
     * @param attributes 조회된 사용자 속성([UserAttribute]) 리스트
     */
    data class Success(val attributes: List<UserAttribute>) : UserAttributeResult()

    /**
     * **사용자 속성 조회 실패 결과**
     * - 요청이 실패하면 에러 메시지를 포함
     *
     * @param message 실패 원인을 설명하는 에러 메시지
     */
    data class Error(val message: String) : UserAttributeResult()
}