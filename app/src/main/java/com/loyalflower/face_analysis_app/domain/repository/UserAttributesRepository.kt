package com.loyalflower.face_analysis_app.domain.repository

import com.loyalflower.face_analysis_app.domain.model.UserAttributeResult


/**
 * 사용자 속성 정보(사진 분석 정보)를 가져오는 Repository 인터페이스
 *
 * - 인증 정보를 통해 사용자 속성 정보를 요청하는 기능을 제공
 */
interface UserAttributesRepository {

    /**
     * 특정 사용자의 사진 분석 속성 정보를 조회하는 함수
     *
     * @param token 액세스 토큰 (Bearer 토큰)
     * @param userId 조회할 사용자 ID
     * @return `FetchUserAttributeResult.Success(List<UserAttribute>)` 또는 `FetchUserAttributeResult.Error(에러 메시지)`
     */
    suspend fun fetchUserAttributes(token: String, userId: String): UserAttributeResult
}