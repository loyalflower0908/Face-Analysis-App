package com.loyalflower.face_analysis_app.presentation.view.result

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.loyalflower.face_analysis_app.presentation.view.result.ui.BackButton
import com.loyalflower.face_analysis_app.presentation.view.result.ui.Section
import com.loyalflower.face_analysis_app.presentation.view.result.ui.TitleBox
import com.loyalflower.face_analysis_app.presentation.view.theme.COLOR_RESULT_SCREEN_BACKGROUND
import com.loyalflower.face_analysis_app.presentation.view.theme.Dimens


/**
 * **결과 화면 [ResultScreen]**
 * - 사용자의 얼굴 분석 결과를 표시하는 화면
 * - 성별, 나이, 감정, 인종 결과를 보여주며, 뒤로 가기 버튼을 통해 카메라 화면으로 이동 가능
 *
 * @param gender 성별 값
 * @param age 나이 값
 * @param emotion 감정 값
 * @param race 인종 값
 * @param onNavigateToCamera 카메라 화면으로 이동하는 콜백 함수
 */
@Composable
fun ResultScreen(
    gender: String,
    age: String,
    emotion: String,
    race: String,
    onNavigateToCamera: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(COLOR_RESULT_SCREEN_BACKGROUND)
    ) {
        // 타이틀
        TitleBox()
        // 뒤로 가기 버튼
        BackButton(onNavigateToCamera)
    }
    // 분석 값 내용
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Section(sectionTitle = "성별", sectionValue = gender)
        Spacer(modifier = Modifier.height(Dimens.SectionSpacing))
        Section(sectionTitle = "나이", sectionValue = "${age}세")
        Spacer(modifier = Modifier.height(Dimens.SectionSpacing))
        Section(sectionTitle = "감정", sectionValue = emotion)
        Spacer(modifier = Modifier.height(Dimens.SectionSpacing))
        Section(sectionTitle = "인종", sectionValue = race)
    }

    // ✅ 뒤로가기 이벤트 처리 → 카메라 화면으로 이동
    BackHandler {
        onNavigateToCamera()
    }
}



