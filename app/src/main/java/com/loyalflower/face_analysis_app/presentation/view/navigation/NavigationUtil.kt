package com.loyalflower.face_analysis_app.presentation.view.navigation

import com.loyalflower.face_analysis_app.domain.model.UserAttribute

/**
 * **사용자 속성[UserAttribute] 리스트를 [ResultRoute]로 변환하는 확장 함수**
 *
 * - 서버에서 받아온 사용자 속성 리스트 (`List<UserAttribute>`)를 `ResultRoute`로 변환하여 내비게이션에 활용
 * - 필요한 키 (`gender`, `age`, `emotion`, `race`) 값을 찾아 매핑하며, 없을 경우 `"알 수 없음"` 기본값을 사용
 *
 * @receiver List<UserAttribute> 변환할 사용자 속성 리스트
 * @return 변환된 `ResultRoute` 객체
 */
fun List<UserAttribute>.toResultRoute(): ResultRoute {
    return ResultRoute(
        gender = this.find { it.key == "gender" }?.value ?: "알 수 없음",
        age = this.find { it.key == "age" }?.value ?: "알 수 없음",
        emotion = this.find { it.key == "emotion" }?.value ?: "알 수 없음",
        race = this.find { it.key == "race" }?.value ?: "알 수 없음"
    )
}
