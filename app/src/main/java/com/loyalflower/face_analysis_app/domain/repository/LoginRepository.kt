package com.loyalflower.face_analysis_app.domain.repository

import com.loyalflower.face_analysis_app.domain.model.LoginResult

/**
 * 로그인 요청을 위한 Repository 인터페이스
 *
 * - 로그인 기능을 제공
 */
interface LoginRepository {

    /**
     * 사용자 로그인 요청
     *
     * @param userId 사용자 아이디
     * @param password 비밀번호
     * @return `LoginResult.Success` (토큰 포함) 또는 `LoginResult.Error` (실패 시 에러 메시지 포함)
     */
    suspend fun login(userId: String, password: String): LoginResult
}
