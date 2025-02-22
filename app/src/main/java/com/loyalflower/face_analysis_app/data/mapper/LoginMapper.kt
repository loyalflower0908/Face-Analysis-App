package com.loyalflower.face_analysis_app.data.mapper

import com.loyalflower.face_analysis_app.data.network.dto.LoginResponse
import com.loyalflower.face_analysis_app.domain.model.LoginResult

/**
 * - 서버에서 받은 로그인 응답[LoginResponse]을 도메인 모델[LoginResult]로 변환
 *
 * @receiver `LoginResponse` 서버 응답 모델
 * @return `LoginResult.Success` (액세스 토큰 포함)
 */
fun LoginResponse.toDomain(): LoginResult {
    return LoginResult.Success(
        token = this.token // ✅ 서버에서 반환한 토큰 정보
    )
}
