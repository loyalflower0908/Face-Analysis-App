package com.loyalflower.face_analysis_app.data.network.dto

/**
 * 서버로 부터 사용자 속성 정보를 받을 때 사용하는 데이터 모델
 *
 * @property lastUpdateTs 속성의 마지막 업데이트 타임스탬프
 * @property key 속성 키 (예: "gender", "age", "emotion", "race")
 * @property value 속성 값 (문자열)
 */
data class UserAttributeResponse(
    val lastUpdateTs: Long, // 마지막 업데이트 시간
    val key: String, // 속성의 종류 (예: "gender", "age" 등)
    val value: String // 속성 값 (String 타입)
)