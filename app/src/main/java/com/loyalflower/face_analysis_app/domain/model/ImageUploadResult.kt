package com.loyalflower.face_analysis_app.domain.model

/**
 * **이미지 업로드 결과 [ImageUploadResult]**
 * - 이미지 업로드 요청 후 반환되는 결과를 나타내는 `sealed class`
 * - `Success`와 `Error` 두 가지 상태를 포함
 */
sealed class ImageUploadResult {

    /**
     * **이미지 업로드 성공 결과**
     * - 업로드된 이미지에 대한 사용자 정보를 포함
     *
     * @param userId 업로드 성공 후 반환된 사용자 아이디
     * @param password 업로드 성공 후 반환된 비밀번호
     */
    data class Success(
        val userId: String,
        val password: String
    ) : ImageUploadResult()

    /**
     * **이미지 업로드 실패 결과**
     * - 업로드 요청 실패 시 발생하는 에러 메시지를 포함
     *
     * @param message 실패 원인을 설명하는 에러 메시지
     */
    data class Error(val message: String) : ImageUploadResult()
}
