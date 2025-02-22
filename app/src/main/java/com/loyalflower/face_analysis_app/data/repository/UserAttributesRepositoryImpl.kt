package com.loyalflower.face_analysis_app.data.repository

import com.loyalflower.face_analysis_app.data.mapper.toDomain
import com.loyalflower.face_analysis_app.data.network.ApiService
import com.loyalflower.face_analysis_app.domain.model.UserAttributeResult
import com.loyalflower.face_analysis_app.domain.repository.UserAttributesRepository
import timber.log.Timber
import javax.inject.Inject

/**
 * UserAttributesRepository의 구현체
 *
 * - API 호출을 통해 사용자 속성 조회를 수행한다.
 * - API 응답을 `toDomain()`을 사용하여 도메인 모델로 변환한다.
 * - Timber를 사용하여 요청 및 응답 정보를 로깅한다.
 */
class UserAttributesRepositoryImpl @Inject constructor(
    private val apiService: ApiService
):UserAttributesRepository {

    /**
     * 사용자 속성(사진 분석 값)을 받아 오는 함수
     *
     * @param token 인증 토큰
     * @param userId 사용자 아이디
     * @return [UserAttributeResult] (성공 시 사용자 속성 리스트, 실패 시 에러 메시지 포함)
     */
    override suspend fun fetchUserAttributes(token: String, userId: String): UserAttributeResult {
        return try {
            // 서버로 사용자 속성 요청
            val response = apiService.getUserAttributes("Bearer $token", userId)

            // API 응답 로깅
            Timber.tag("UserAttributesRepositoryImpl").d("Raw Response: ${response.raw()}")
            Timber.tag("UserAttributesRepositoryImpl").d("Response Body: ${response.body()}")

            if (response.isSuccessful) {
                response.body()?.toDomain() ?: UserAttributeResult.Error("Fetch user attributes failed") // 도메인 모델 변환
            } else {
                Timber.tag("UserAttributesRepositoryImpl").e("Error Body: ${response.errorBody()?.string()}")
                UserAttributeResult.Error("Fetch user attributes failed: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Timber.tag("UserAttributesRepositoryImpl").e("Fetch user attributes failed: ${e.message}")
            UserAttributeResult.Error("Fetch user attributes failed: ${e.message}")
        }
    }
}