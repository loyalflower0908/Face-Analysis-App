package com.loyalflower.face_analysis_app.data.mapper

import com.loyalflower.face_analysis_app.data.network.dto.ImageUploadResponse
import com.loyalflower.face_analysis_app.domain.model.ImageUploadResult

/**
 * - 서버에서 받은 이미지 업로드 응답[ImageUploadResponse]을 도메인 모델[ImageUploadResult]로 변환
 *
 * @receiver `ImageUploadRespons' 서버 응답 모델
 * @return `ImageUploadResult.Success` (사용자 인증 정보 포함)
 */
fun ImageUploadResponse.toDomain(): ImageUploadResult {
    return ImageUploadResult.Success(
        userId = this.id, // ✅ 서버에서 반환한 사용자 아이디
        password = this.pw // ✅ 서버에서 반환한 사용자 비밀번호
    )
}
