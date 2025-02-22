package com.loyalflower.face_analysis_app.domain.usecase

import com.loyalflower.face_analysis_app.domain.model.LoginResult
import com.loyalflower.face_analysis_app.domain.repository.LoginRepository
import javax.inject.Inject

/**
 * **사용자 로그인 유스케이스 [LoginUseCase]**
 * - 사용자의 로그인 요청을 처리하는 유스케이스
 *
 * @property loginRepository 로그인 API 기능을 담당하는 레포지토리
 */
class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    /**
     * **로그인 요청 실행**
     * - 사용자 이름과 비밀번호를 받아 로그인 요청을 수행
     *
     * @param userId 사용자 아이디
     * @param password 비밀번호
     * @return `LoginResult.Success` (로그인 성공 시 토큰 포함)
     *         또는 `LoginResult.Error` (실패 시 에러 메시지 포함)
     */
    suspend fun execute(userId: String, password: String): LoginResult {
        return loginRepository.login(userId, password) // ✅ 로그인 요청 실행
    }
}