package com.loyalflower.face_analysis_app.presentation.view.result.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.loyalflower.face_analysis_app.presentation.view.theme.AppTypography

// 얼굴 분석 값을 표시하는 Text Composable
@Composable
fun Section(sectionTitle:String, sectionValue:String) {
    Text(text = buildAnnotatedString {
        withStyle(AppTypography.SectionTitle) {
            append("${sectionTitle}: ")
        }
        withStyle(AppTypography.StatusValue) {
            append(sectionValue)
        }
    })
}