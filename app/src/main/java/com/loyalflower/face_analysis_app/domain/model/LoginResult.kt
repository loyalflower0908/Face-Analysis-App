package com.loyalflower.face_analysis_app.domain.model

/**
 * **로그인 결과 [LoginResult]**
 * - 로그인 요청 후 반환되는 결과를 나타내는 `sealed class`
 * - `Success`와 `Error` 두 가지 상태를 포함
 */
sealed class LoginResult {

    /**
     * **로그인 성공 결과**
     * - 로그인 성공 시 인증 토큰을 포함
     *
     * @param token 인증을 위한 액세스 토큰
     */
    data class Success(val token: String) : LoginResult()

    /**
     * **로그인 실패 결과**
     * - 로그인 요청 실패 시 발생하는 에러 메시지를 포함
     *
     * @param message 실패 원인을 설명하는 에러 메시지
     */
    data class Error(val message: String) : LoginResult()
}