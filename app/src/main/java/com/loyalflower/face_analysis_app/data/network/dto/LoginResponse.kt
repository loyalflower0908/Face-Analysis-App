package com.loyalflower.face_analysis_app.data.network.dto

/**
 * 서버로부터 로그인 응답을 받을 때 사용되는 데이터 모델
 *
 * @property token 인증을 위한 액세스 토큰
 */
data class LoginResponse(
    val token: String          // 액세스 토큰 (필수)
)