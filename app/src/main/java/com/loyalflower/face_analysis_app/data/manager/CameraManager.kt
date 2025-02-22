package com.loyalflower.face_analysis_app.data.manager

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.view.Surface
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject


/**
 * **CameraManager**
 * - CameraX를 활용하여 카메라 설정, 미리보기 및 사진 촬영을 관리하는 클래스
 * - 후면 카메라를 사용하며, 촬영된 이미지를 Bitmap으로 변환 후 콜백을 통해 제공
 *
 * @param context 애플리케이션 컨텍스트 (Hilt를 사용하여 주입)
 */
class CameraManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private var cameraProvider: ProcessCameraProvider? = null
    private var previewView: PreviewView? = null
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService

    /**
     * **카메라 초기화 및 바인딩**
     * - 카메라를 실행할 스레드 풀을 생성
     * - `ProcessCameraProvider`를 가져와서 카메라를 바인딩
     *
     * @param lifecycleOwner 카메라 생명주기를 관리할 [LifecycleOwner]
     */
    fun setupCamera(lifecycleOwner: LifecycleOwner) {
        cameraExecutor = Executors.newSingleThreadExecutor() // 별도 스레드에서 실행

        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            try {
                cameraProvider = cameraProviderFuture.get()
                bindPreview(lifecycleOwner) // 미리보기 바인딩
            } catch (exc: Exception) {
                Timber.tag("CameraManager").e(exc, "카메라 프로바이더 가져오기 실패")
            }
        }, ContextCompat.getMainExecutor(context))
    }

    /**
     * **카메라 미리보기 및 이미지 캡처 바인딩**
     * - 후면 카메라 사용
     * - `PreviewView`에 미리보기 설정
     * - 이미지 캡처 기능 활성화
     *
     * @param lifecycleOwner 카메라 생명주기를 관리할 [LifecycleOwner]
     */
    private fun bindPreview(lifecycleOwner: LifecycleOwner) {
        val preview = Preview.Builder()
            .setTargetRotation(Surface.ROTATION_0) // 기본 회전 설정
            .build()

        previewView?.let { previewView ->
            preview.surfaceProvider = previewView.surfaceProvider
        }

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK) // 전면 카메라 선택
            .build()

        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY) // 지연 시간 최소화
            .setTargetRotation(Surface.ROTATION_0)
            .build()

        try {
            cameraProvider?.let { provider ->
                provider.unbindAll() // 기존 바인딩 해제
                provider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            }
        } catch (exc: Exception) {
            Timber.tag("CameraManager").e(exc, "카메라 바인딩 실패")
        }
    }

    /**
     * **미리보기 화면을 설정**
     *
     * @param previewView 카메라 미리보기를 표시할 [PreviewView]
     */
    fun setupPreview(previewView: PreviewView) {
        this.previewView = previewView
    }

    /**
     * **사진 촬영 및 Bitmap 변환**
     * - 촬영된 이미지를 Bitmap으로 변환한 후 콜백을 통해 반환
     * - `ImageCapture`를 사용하여 사진 촬영
     * - `ImageProxy`를 Bitmap으로 변환 후 자동 회전 처리
     *
     * @param onPhotoTaken 촬영된 이미지를 전달할 콜백
     */
    fun takePhoto(onPhotoTaken: (Bitmap) -> Unit) {
        imageCapture.takePicture(
            cameraExecutor,
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onError(exc: ImageCaptureException) {
                    Timber.tag("CameraManager").e(exc, "사진 촬영 실패: ${exc.message}")
                }

                override fun onCaptureSuccess(image: ImageProxy) {
                    val rotationDegrees = image.imageInfo.rotationDegrees
                    val bitmap = image.toBitmap() // ImageProxy → Bitmap 변환
                    val rotatedBitmap = rotateBitmap(bitmap, rotationDegrees) // 올바른 방향으로 회전 적용
                    onPhotoTaken(rotatedBitmap)
                    image.close() // 메모리 해제
                }
            }
        )
    }

    /**
     * **Bitmap 이미지 회전**
     * - 카메라 센서 회전값을 고려하여 이미지 방향을 조정
     *
     * @param bitmap 원본 [Bitmap] 이미지
     * @param degrees 회전할 각도
     * @return 회전된 [Bitmap] 이미지
     */
    private fun rotateBitmap(bitmap: Bitmap, degrees: Int): Bitmap {
        val matrix = Matrix().apply {
            postRotate(degrees.toFloat())
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}