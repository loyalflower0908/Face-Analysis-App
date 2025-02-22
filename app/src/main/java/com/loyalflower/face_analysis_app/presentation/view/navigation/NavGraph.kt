package com.loyalflower.face_analysis_app.presentation.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.loyalflower.face_analysis_app.presentation.view.camera.CameraScreen
import com.loyalflower.face_analysis_app.presentation.view.result.ResultScreen

/**
 * **네비게이션 그래프 정의 [NavGraph]**
 * - [CameraScreen]과 [ResultScreen] 간의 네비게이션을 설정하는 함수
 * - [NavHost]를 사용하여 Compose Navigation을 구성
 */
@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CameraRoute // 시작 화면: CameraScreen
    ) {
        /**
         * **카메라 화면 [CameraScreen]**
         * - `onNavigateToResult`를 통해 측정 결과 데이터를 `ResultScreen`으로 전달
         */
        composable<CameraRoute> {
            CameraScreen(
                onNavigateToResult = { attributes ->
                    val resultRoute = attributes.toResultRoute() // 유저 속성을 `ResultRoute`로 변환
                    navController.navigate(resultRoute) // 결과 화면으로 이동
                }
            )
        }

        /**
         * **결과 화면 [ResultScreen]**
         * - `toRoute<ResultRoute>()`를 통해 전달된 데이터를 추출하여 사용
         * - 뒤로 가기 버튼을 누르면 `CameraScreen`으로 이동
         */
        composable<ResultRoute> {
            val args = it.toRoute<ResultRoute>() // 네비게이션 인자로부터 데이터를 가져옴
            ResultScreen(
                gender = args.gender,
                age = args.age,
                emotion = args.emotion,
                race = args.race,
                onNavigateToCamera = {
                    navController.navigateUp() // 이전 화면 (CameraScreen)으로 이동
                })
        }
    }
}