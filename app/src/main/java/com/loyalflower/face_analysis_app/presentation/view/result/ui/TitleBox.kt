package com.loyalflower.face_analysis_app.presentation.view.result.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.loyalflower.face_analysis_app.R
import com.loyalflower.face_analysis_app.presentation.view.theme.AppTypography
import com.loyalflower.face_analysis_app.presentation.view.theme.Dimens

// 타이틀
@Composable
fun TitleBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(Dimens.TITLE_BOX_HEIGHT_FRACTION)
    ) {
        Image(
            painter = painterResource(R.drawable.titlebox),
            contentDescription = "TitleBox",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .clip(Dimens.TitleBoxBottomCornerShape)
        )
        Text(
            text = "얼굴 분석 결과",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = Dimens.TitleBoxBottomPadding),
            style = AppTypography.Title
        )
    }
}
