package com.loyalflower.face_analysis_app.presentation.view.result.ui

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.loyalflower.face_analysis_app.presentation.view.theme.BACK_BUTTON_CONTAINER_COLOR
import com.loyalflower.face_analysis_app.presentation.view.theme.BACK_BUTTON_ICON_COLOR
import com.loyalflower.face_analysis_app.presentation.view.theme.Dimens

// 카메라 화면으로 다시 돌아가는 버튼 (FAB)
@Composable
fun BoxScope.BackButton(onNavigateToCamera: () -> Unit) {
    FloatingActionButton(
        onClick = { onNavigateToCamera() },
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = Dimens.BackButtonBottomPadding)
            .size(Dimens.BackButtonSize),
        containerColor = BACK_BUTTON_CONTAINER_COLOR,
        contentColor = BACK_BUTTON_ICON_COLOR
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.size(Dimens.BackButtonIconSize)
        )
    }
}
