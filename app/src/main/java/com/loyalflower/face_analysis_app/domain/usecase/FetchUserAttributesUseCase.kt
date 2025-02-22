package com.loyalflower.face_analysis_app.domain.usecase

import com.loyalflower.face_analysis_app.domain.model.UserAttributeResult
import com.loyalflower.face_analysis_app.domain.repository.UserAttributesRepository
import javax.inject.Inject

/**
 * **사용자 속성 조회 유스케이스 [FetchUserAttributesUseCase]**
 * - 특정 사용자의 얼굴 분석 속성 정보를 조회하는 유스케이스
 *
 * @property userAttributesRepository 사용자 속성 조회 API 기능을 담당하는 레포지토리
 */
class FetchUserAttributesUseCase @Inject constructor(
    private val userAttributesRepository: UserAttributesRepository
) {
    /**
     * **사용자 속성 조회 실행**
     * - 서버에서 특정 사용자의 얼굴 분석 정보를 조회하여 반환
     *
     * @param token 인증을 위한 액세스 토큰
     * @param userId 조회할 사용자 ID
     * @return `UserAttributeResult.Success` (조회 성공 시 사용자 속성 리스트 포함)
     *         또는 `UserAttributeResult.Error` (실패 시 에러 메시지 포함)
     */
    suspend fun execute(token: String, userId: String): UserAttributeResult {
        return userAttributesRepository.fetchUserAttributes(token, userId) // ✅ 사용자 속성 조회 실행
    }
}