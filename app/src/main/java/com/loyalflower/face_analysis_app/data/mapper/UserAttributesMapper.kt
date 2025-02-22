package com.loyalflower.face_analysis_app.data.mapper

import com.loyalflower.face_analysis_app.data.network.dto.UserAttributeResponse
import com.loyalflower.face_analysis_app.domain.model.UserAttribute
import com.loyalflower.face_analysis_app.domain.model.UserAttributeResult

/**
 * - 서버에서 받은 사용자 속성[UserAttributeResponse] 리스트를 도메인 모델[UserAttributeResult]로 변환
 *
 * @receiver `List<UserAttributeResponse>` 서버 응답 모델 리스트
 * @return `UserAttributeResult.Success` (변환된 사용자 속성 리스트 포함)
 */
fun List<UserAttributeResponse>.toDomain(): UserAttributeResult {
    return UserAttributeResult.Success(
        attributes = this.map { response -> // ✅ 서버 모델을 도메인 모델로 매핑
            UserAttribute(
                lastUpdateTs = response.lastUpdateTs, // ✅ 최종 업데이트 시간
                key = response.key, // ✅ 속성 키 (예: "gender", "age", "emotion", "race")
                value = response.value // ✅ 속성 값 (문자열 형태)
            )
        }
    )
}
