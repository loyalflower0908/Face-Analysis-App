package com.loyalflower.face_analysis_app.data.repository

import com.loyalflower.face_analysis_app.data.mapper.toDomain
import com.loyalflower.face_analysis_app.data.network.ApiService
import com.loyalflower.face_analysis_app.domain.model.LoginResult
import com.loyalflower.face_analysis_app.domain.repository.LoginRepository
import timber.log.Timber
import javax.inject.Inject


/**
 * LoginRepository의 구현체
 *
 * - API 호출을 통해 로그인 로직을 처리한다.
 * - API 응답을 `toDomain()`을 사용하여 도메인 모델로 변환한다.
 * - Timber를 사용하여 요청 및 응답 정보를 로깅한다.
 */
class LoginRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : LoginRepository {

    /**
     * 로그인 함수
     *
     * @param userId 유저 아이디
     * @param password 비밀번호
     * @return [LoginResult] (성공 시 토큰, 실패 시 에러 메시지 포함)
     */
    override suspend fun login(userId: String, password: String): LoginResult {
        return try {
            // 서버로 로그인 요청
            val response = apiService.login(mapOf(
                "user_id" to userId,
                "password" to password
            ))

            // API 응답 로깅
            Timber.tag("LoginRepositoryImpl").d("Raw Response: ${response.raw()}")
            Timber.tag("LoginRepositoryImpl").d("Response Body: ${response.body()}")

            if (response.isSuccessful) {
                response.body()?.toDomain() ?: LoginResult.Error("Login failed") // 도메인 모델 변환
            } else {
                Timber.tag("LoginRepositoryImpl").e("Error Body: ${response.errorBody()?.string()}")
                LoginResult.Error("Login failed: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Timber.tag("LoginRepositoryImpl").e("Login failed: ${e.message}")
            LoginResult.Error("Exception: ${e.message}")
        }
    }
}