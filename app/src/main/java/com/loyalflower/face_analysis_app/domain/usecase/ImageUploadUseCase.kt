package com.loyalflower.face_analysis_app.domain.usecase

import com.loyalflower.face_analysis_app.domain.model.ImageUploadResult
import com.loyalflower.face_analysis_app.domain.repository.ImageUploadRepository
import java.io.File
import javax.inject.Inject

/**
 * **이미지 업로드 유스케이스 [ImageUploadUseCase]**
 * - 사용자가 촬영한 이미지를 서버에 업로드하는 유스케이스
 *
 * @property imageUploadRepository 이미지 업로드를 처리하는 레포지토리
 */
class ImageUploadUseCase @Inject constructor(
    private val imageUploadRepository: ImageUploadRepository
) {
    /**
     * **이미지 업로드 실행**
     * - 파일을 받아 업로드 요청을 수행하고 결과를 반환
     *
     * @param file 업로드할 이미지 파일
     * @return `ImageUploadResult.Success` (업로드 성공 시 사용자 정보 포함)
     *         또는 `ImageUploadResult.Error` (실패 시 에러 메시지 포함)
     */
    suspend fun execute(file: File): ImageUploadResult {
        return imageUploadRepository.uploadImage(file) // ✅ 업로드 요청 실행
    }
}