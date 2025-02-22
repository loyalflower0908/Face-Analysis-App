package com.loyalflower.face_analysis_app.presentation.view.camera.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.loyalflower.face_analysis_app.R
import com.loyalflower.face_analysis_app.presentation.view.theme.COLOR_GUIDE_BACKGROUND
import com.loyalflower.face_analysis_app.presentation.view.theme.Dimens


/**
 * **안내 가이드 오버레이**
 * - 얼굴 가이드 라인 및 간단한 설명 표시
 */
@Composable
fun CameraGuideOverlay(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(COLOR_GUIDE_BACKGROUND)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(R.drawable.guide),
            contentDescription = "guide",
            modifier = Modifier
                .size(
                    width = Dimens.GuideImageWidth,
                    height = Dimens.GuideImageHeight
                )
                .align(Alignment.Center)
        )
    }
}