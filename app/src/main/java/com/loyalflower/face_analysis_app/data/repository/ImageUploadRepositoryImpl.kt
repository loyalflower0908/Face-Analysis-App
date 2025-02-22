package com.loyalflower.face_analysis_app.data.repository

import com.loyalflower.face_analysis_app.data.mapper.toDomain
import com.loyalflower.face_analysis_app.data.network.ApiService
import com.loyalflower.face_analysis_app.domain.model.ImageUploadResult
import com.loyalflower.face_analysis_app.domain.repository.ImageUploadRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import timber.log.Timber
import java.io.File
import javax.inject.Inject

/**
 * 이미지 업로드 리포지토리 구현체
 *
 * - `ImageUploadRepository`를 구현하여 실제 API 호출을 처리
 * - `ApiService`를 사용하여 이미지 업로드 요청을 수행
 *
 * @param apiService API 서비스
 */
class ImageUploadRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ImageUploadRepository {

    /**
     * 이미지 파일을 업로드하는 함수
     *
     * @param file 업로드할 이미지 파일
     * @return [ImageUploadResult] (성공 시 인증 정보(id, pw), 실패 시 에러 메시지 포함)
     */
    override suspend fun uploadImage(file: File): ImageUploadResult {
        // 이미지 파일을 HTTP 요청 바디로 변환
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        return try {
            // 서버로 이미지 업로드 요청
            val response = apiService.uploadImage(body)

            // 응답 로깅
            Timber.tag("ImageUploadRepositoryImpl").d("Raw Response: ${response.raw()}")
            Timber.tag("ImageUploadRepositoryImpl").d("Response Body: ${response.body()}")


            // 서버 응답이 성공적이면, 응답 데이터를 도메인 모델로 변환
            if(response.isSuccessful){
                response.body()
                    ?.toDomain()  // ✅ Data Layer 모델 → Domain Layer 모델 변환
                    ?: ImageUploadResult.Error("Upload failed") // 변환 실패 시 에러 반환
            } else {
                // 서버 응답이 400(BAD REQUEST)일 경우, 에러 메시지를 추출하여 반환
                val errorBody = response.errorBody()?.string()
                val errorMessage = extractErrorMessage(errorBody)
                ImageUploadResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            Timber.tag("ImageUploadRepositoryImpl").e("Upload failed: ${e.message}")
            ImageUploadResult.Error("Exception: ${e.message}")
        }
    }
}


/**
 * 서버에서 반환한 JSON 에러 메시지를 추출하는 함수
 */
private fun extractErrorMessage(errorBody: String?): String {
    return try {
        val json = JSONObject(errorBody ?: "{}")
        json.optString("error", "Unknown error")  // ✅ 바로 "error" 필드 접근 가능
    } catch (e: Exception) {
        "Unknown error"
    }
}
