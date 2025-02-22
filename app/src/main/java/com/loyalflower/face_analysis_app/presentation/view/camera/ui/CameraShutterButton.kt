package com.loyalflower.face_analysis_app.presentation.view.camera.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.loyalflower.face_analysis_app.presentation.view.theme.CAMERA_BUTTON_BORDER_COLOR
import com.loyalflower.face_analysis_app.presentation.view.theme.CAMERA_BUTTON_CONTAINER_COLOR
import com.loyalflower.face_analysis_app.presentation.view.theme.CAMERA_BUTTON_INNER_BORDER_COLOR
import com.loyalflower.face_analysis_app.presentation.view.theme.CAMERA_BUTTON_INNER_CIRCLE_COLOR
import com.loyalflower.face_analysis_app.presentation.view.theme.Dimens

/**
 * 카메라 셔터 버튼을 생성하는 컴포저블 함수입니다.
 *
 * - 원형 UI 디자인을 가진 카메라 촬영 버튼을 렌더링
 * - 버튼을 클릭하면 `onClick` 콜백이 실행됨
 * - `enabled` 상태에 따라 버튼 활성화 여부 조절 가능
 *
 * @param onClick 버튼 클릭 시 실행할 콜백 함수
 * @param modifier 외부에서 전달 가능한 `Modifier` (기본값: `Modifier`)
 * @param enabled 버튼 활성화 여부 (`true`일 때 클릭 가능)
 */
@Composable
fun CameraShutterButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean
) {
    Box(
        modifier = modifier
            .size(Dimens.CameraButtonSize) // 버튼 크기 설정
            .clip(CircleShape) // 원형 버튼
            .background(CAMERA_BUTTON_CONTAINER_COLOR) // 바깥쪽 원 배경색
            .border(
                width = Dimens.CameraButtonBorderWidth,
                shape = CircleShape,
                color = CAMERA_BUTTON_BORDER_COLOR
            )
            .clickable(
                onClick = onClick,
                enabled = enabled // 활성화 여부
            ),
        contentAlignment = Alignment.Center // 내부 요소 중앙 정렬
    ) {
        // 내부 원 (셔터 버튼의 안쪽 원 테두리)
        Box(
            modifier = Modifier
                .size(Dimens.CameraButtonInnerSize) // 안쪽 원 크기
                .clip(CircleShape) // 원형 모양
                .background(CAMERA_BUTTON_INNER_CIRCLE_COLOR) // 내부 투명 처리
                .border(
                    width = Dimens.CameraButtonInnerBorderWidth, // 테두리 두께
                    shape = CircleShape, // 테두리 모양 (원형)
                    color = CAMERA_BUTTON_INNER_BORDER_COLOR // 테두리 색상
                )
        )
    }
}