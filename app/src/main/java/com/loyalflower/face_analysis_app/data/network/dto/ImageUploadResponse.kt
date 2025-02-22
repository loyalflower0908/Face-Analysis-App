package com.loyalflower.face_analysis_app.data.network.dto

/**
 * 서버로 이미지 업로드 후 반환되는 응답 데이터 모델
 *
 * @property id 업로드 후 반환되는 사용자 아이디 (로그인에 사용)
 * @property pw 업로드 후 반환되는 사용자 비밀번호 (로그인에 사용)
 */
data class ImageUploadResponse(
    val id: String,   // 서버에서 제공하는 사용자 아이디 정보
    val pw: String       // 서버에서 제공하는 사용자 비밀번호 정보
)