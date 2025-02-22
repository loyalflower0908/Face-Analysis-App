package com.loyalflower.face_analysis_app.presentation.view.theme

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.loyalflower.face_analysis_app.R

//기본으로 사용할 폰트
val PretendardFont = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_light, FontWeight.Light)
)

/**
 * **앱 전반에서 사용되는 텍스트 스타일 정의**
 * - `TextStyle` 및 `SpanStyle`을 활용하여 텍스트의 색상, 크기, 폰트 스타일 등을 일괄적으로 관리
 * - **`TextStyle`**: 일반적인 텍스트에 사용
 * - **`SpanStyle`**: 특정 텍스트 부분에 스타일을 적용할 때 사용 (예: `AnnotatedString`)
 */
object AppTypography {

    // 메인 타이틀 스타일
    val Title = TextStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        color = COLOR_TITLE
    )

    // 섹션 타이틀 스타일 (심박수, 혈압)
    val SectionTitle = SpanStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        color = COLOR_SECTION_TITLE
    )

    // 측정값 스타일
    val StatusValue = SpanStyle(
        fontFamily = PretendardFont,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp,
        color = COLOR_SECTION_VALUE
    )
}