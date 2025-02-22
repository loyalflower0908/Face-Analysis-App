package com.loyalflower.face_analysis_app.presentation.view.navigation

import kotlinx.serialization.Serializable

/**
 * **네비게이션 경로: 카메라 화면 [CameraRoute]**
 * - `@Serializable`을 사용하여 직렬화 가능하도록 설정
 * - `object`로 선언하여 싱글톤으로 사용 (경로에 추가 데이터가 필요하지 않음)
 */
@Serializable
object CameraRoute

/**
 * **네비게이션 경로: 결과 화면 [ResultRoute]**
 * - 카메라 촬영 후 결과 데이터를 전달하기 위한 경로
 * - `@Serializable`을 사용하여 직렬화 가능하도록 설정 (Navigation 라이브러리에서 안전하게 데이터 전달)
 *
 * @param gender 성별 정보 (예: "남성", "여성")
 * @param age 나이 정보 (예: "23")
 * @param emotion 감정 정보 (예: "행복")
 * @param race 인종 정보 (예: "아시아인")
 */
@Serializable
data class ResultRoute(
    val gender: String,
    val age: String,
    val emotion: String,
    val race: String
)
