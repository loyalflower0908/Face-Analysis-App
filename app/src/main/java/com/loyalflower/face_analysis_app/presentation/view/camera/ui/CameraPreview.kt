package com.loyalflower.face_analysis_app.presentation.view.camera.ui

import android.view.ViewGroup
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

/**
 * **카메라 미리보기를 표시하는 컴포저블 함수**
 *
 * - CameraX의 `PreviewView`를 `AndroidView`로 감싸서 Jetpack Compose에서 렌더링
 * - 카메라 프리뷰 UI를 표시하고, `onPreviewCreated` 콜백을 통해 `PreviewView`를 외부에서 접근 가능하게 함
 *
 * @param onPreviewCreated `PreviewView`가 생성된 후 호출되는 콜백 함수
 */
@Composable
fun CameraPreview(
    onPreviewCreated: (PreviewView) -> Unit
) {
    AndroidView(
        factory = { context ->
            PreviewView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, // 가로 전체 크기
                    ViewGroup.LayoutParams.MATCH_PARENT  // 세로 전체 크기
                )
                implementationMode = PreviewView.ImplementationMode.PERFORMANCE // 성능 최적화 모드
                scaleType = PreviewView.ScaleType.FILL_CENTER // 화면 중앙에 맞추고 여백이 생길 수 있음
                onPreviewCreated(this) // 생성된 PreviewView를 콜백으로 전달
            }
        },
        modifier = Modifier.fillMaxSize() // 화면 전체 크기로 설정
    )
}
