package com.loyalflower.face_analysis_app.domain.model

/**
 * **사용자 속성 [UserAttribute]**
 * UI 및 비즈니스 로직에서 사용되는 도메인 모델
 *
 * @param lastUpdateTs 마지막으로 업데이트된 시간 (타임스탬프)
 * @param key 속성 키 (예: "gender", "age", "emotion", "race")
 * @param value 속성 값 (서버에서 문자열 형태로 전달됨)
 */
data class UserAttribute(
    val lastUpdateTs: Long,
    val key: String,
    val value: String
)