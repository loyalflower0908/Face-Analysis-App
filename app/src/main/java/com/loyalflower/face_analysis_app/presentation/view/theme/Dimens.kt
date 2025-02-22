package com.loyalflower.face_analysis_app.presentation.view.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

//Size, Padding 은 Material Design Guide 에 따라 8.dp 단위로 ( 테두리 제외 )
object Dimens {

    // <Camera UI>

    // Camera Button
    val CameraButtonSize = 80.dp
    val CameraButtonBorderWidth = 2.dp
    val CameraButtonInnerSize = 64.dp
    val CameraButtonInnerBorderWidth = 2.dp
    val CameraButtonBottomPadding = 72.dp

    // Camera Guide Image
    val GuideImageWidth = 400.dp
    val GuideImageHeight = 472.dp

    // Loading Indicator
    val LoadingIndicatorSize = 64.dp


    // <Result UI>

    // Result Screen
    const val TITLE_BOX_HEIGHT_FRACTION = 0.15f
    val TitleBoxBottomCornerShape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp)
    val TitleBoxBottomPadding = 32.dp
    val SectionSpacing = 32.dp

    // Back Button
    val BackButtonSize = 80.dp
    val BackButtonIconSize = 32.dp
    val BackButtonBottomPadding = 64.dp
}
