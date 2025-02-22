package com.loyalflower.face_analysis_app.presentation.view.camera

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.loyalflower.face_analysis_app.domain.model.UserAttribute
import com.loyalflower.face_analysis_app.presentation.view.camera.ui.CameraGuideOverlay
import com.loyalflower.face_analysis_app.presentation.view.camera.ui.CameraPreview
import com.loyalflower.face_analysis_app.presentation.view.camera.ui.CameraShutterButton
import com.loyalflower.face_analysis_app.presentation.view.camera.ui.RequestCameraPermission
import com.loyalflower.face_analysis_app.presentation.view.theme.Dimens
import com.loyalflower.face_analysis_app.presentation.viewmodel.api.ApiUiState
import com.loyalflower.face_analysis_app.presentation.viewmodel.api.ApiViewModel
import com.loyalflower.face_analysis_app.presentation.viewmodel.camera.CameraUiState
import com.loyalflower.face_analysis_app.presentation.viewmodel.camera.CameraViewModel
import kotlinx.coroutines.delay
import timber.log.Timber

/**
 * **카메라 화면 [CameraScreen]**
 * - 카메라 미리보기 및 사진 촬영 기능 제공
 * - 통신 상태 [ApiUiState]에 따라 결과 화면으로 이동
 * - 권한 요청 및 에러 처리 포함
 *
 * @param cameraViewModel 카메라 관련 로직을 관리하는 [CameraViewModel]
 * @param apiViewModel API 통신 관련 로직을 관리하는 [ApiViewModel]
 * @param onNavigateToResult 통신 성공 시 결과 화면으로 이동하는 콜백
 */
@Composable
fun CameraScreen(
    cameraViewModel: CameraViewModel = hiltViewModel(),
    apiViewModel: ApiViewModel = hiltViewModel(),
    onNavigateToResult: (List<UserAttribute>) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // 카메라 및 API 통신 상태 관찰
    val cameraUiState by cameraViewModel.cameraState.collectAsStateWithLifecycle()
    val authUiState by apiViewModel.authState.collectAsStateWithLifecycle()

    // 촬영 안내 가이드 표시 여부
    var showGuide by remember { mutableStateOf(true) }

    /**
     * **촬영 가이드 자동 닫기 (5초 뒤)**
     * - 이미 showGuide가 false가 되면 중단
     */
    LaunchedEffect(showGuide) {
        if (showGuide) {
            delay(5000L) // 5초 뒤
            showGuide = false
        }
    }

    /**
     * **카메라 오류 메시지 처리**
     * - [CameraViewModel]에서 발생한 오류 메시지를 Toast로 표시
     */
    LaunchedEffect(Unit) {
        cameraViewModel.errorMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * **카메라 권한 요청**
     * - 권한이 허용되면 `setupCamera` 호출
     * - 거부되면 Toast 메시지 표시
     */
    RequestCameraPermission(
        onPermissionGranted = {
            cameraViewModel.setupCamera(lifecycleOwner)
        },
        onPermissionDenied = {
            Toast.makeText(context, "카메라 권한이 필요합니다", Toast.LENGTH_SHORT).show()
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        /**
         * **카메라 UI 상태 처리**
         * - `Initial` 또는 `Preview` 상태에서 카메라 미리보기를 표시하고 Preview 상태에 촬영 버튼 활성화
         * - `Error` 상태에서는 오류 메시지 출력
         */
        when (cameraUiState) {
            is CameraUiState.Initial, is CameraUiState.Preview -> {
                CameraPreview(
                    onPreviewCreated = { previewView ->
                        cameraViewModel.setupPreview(previewView)
                    }
                )
                CameraShutterButton(
                    onClick = { cameraViewModel.takePhoto() },
                    enabled = cameraUiState is CameraUiState.Preview,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = Dimens.CameraButtonBottomPadding)
                )
                /**
                * **카메라 가이드 라인**
                * - showGuide가 true일 때만 표시
                * - 클릭 시 종료 혹은 LaunchedEffect로 5초 뒤 자동 종료
                 */
                AnimatedVisibility(visible = showGuide, enter = fadeIn(), exit = fadeOut()) {
                    CameraGuideOverlay { showGuide = false }
                }
            }

            is CameraUiState.Error -> {
                Timber.tag("CameraScreen").e((cameraUiState as CameraUiState.Error).message)
                Text(
                    text = "카메라 오류 발생 : ${(cameraUiState as CameraUiState.Error).message}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        /**
         * **API 통신 상태 처리**
         * - `Success` : 통신 완료 시 결과 화면으로 이동
         * - `Loading` : 로딩 인디케이터 표시
         * - `Error` : 통신 실패 메시지 출력
         */
        when (authUiState) {
            is ApiUiState.Success -> {
                LaunchedEffect(Unit) {
                    onNavigateToResult((authUiState as ApiUiState.Success).userAttribute)
                    apiViewModel.resetAuthState() // ✅ 상태 초기화 (재진입 방지)
                }
            }

            is ApiUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(Dimens.LoadingIndicatorSize)
                )
            }

            is ApiUiState.Error -> {
                Timber.tag("ApiViewModel").e((authUiState as ApiUiState.Error).message)
                Toast.makeText(
                    context,
                    (authUiState as ApiUiState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {} // ❌ 필요하지 않은 상태는 무시(Initial)
        }
    }
}