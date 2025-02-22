package com.loyalflower.face_analysis_app.presentation.view.camera.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat


/**
 * **카메라 권한 요청 컴포저블**
 *
 * - 사용자에게 **카메라 권한**을 요청하고, 결과에 따라 지정된 콜백을 실행
 * - 권한이 **이미 허용된 경우** 즉시 `onPermissionGranted()` 실행
 * - 권한이 없는 경우 `ActivityResultLauncher`를 통해 권한 요청
 *
 * @param onPermissionGranted 사용자가 카메라 권한을 허용했을 때 실행할 콜백 함수
 * @param onPermissionDenied 사용자가 카메라 권한을 거부했을 때 실행할 콜백 함수
 */
@Composable
fun RequestCameraPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    var hasCameraPermission by remember {
        mutableStateOf(false) // 🔹 카메라 권한 상태 (초기값: false)
    }

    val context = LocalContext.current

    // 🔹 권한 요청 런처 (ActivityResult API 활용)
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(), // ✅ 단일 권한 요청
        onResult = { granted ->
            hasCameraPermission = granted // 권한 결과 업데이트
            if (granted) {
                onPermissionGranted() // ✅ 권한 허용 시 실행
            } else {
                onPermissionDenied() // ❌ 권한 거부 시 실행
            }
        }
    )

    LaunchedEffect(Unit) { // ✅ 한 번만 실행됨 (Composition 시점에 실행)
        // 현재 카메라 권한 상태 확인
        val permissionCheckResult = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        )

        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            hasCameraPermission = true
            onPermissionGranted() // ✅ 이미 권한이 있는 경우 즉시 실행
        } else {
            // ❌ 권한이 없으면 사용자에게 요청
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
}
