package com.loyalflower.face_analysis_app.domain.repository

import com.loyalflower.face_analysis_app.domain.model.ImageUploadResult
import java.io.File


/**
 * 이미지 업로드 관련 Repository 인터페이스
 *
 * - 이미지 파일을 업로드하는 기능을 제공
 */
interface ImageUploadRepository {

    /**
     * 이미지 파일을 업로드하는 함수
     *
     * @param file 업로드할 이미지 파일
     * @return 업로드 결과 (`ImageUploadResult`)
     */
    suspend fun uploadImage(file: File): ImageUploadResult
}