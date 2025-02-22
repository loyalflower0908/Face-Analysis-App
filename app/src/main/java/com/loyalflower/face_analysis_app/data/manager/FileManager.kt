package com.loyalflower.face_analysis_app.data.manager

import android.content.Context
import android.graphics.Bitmap
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


/**
 * **FileManager**
 * - 앱 내부 저장소 (cache 디렉토리)에 이미지 파일을 저장하고 관리하는 유틸리티 클래스
 * - CameraX로 촬영한 Bitmap을 파일로 변환하여 임시 저장
 * - 임시 이미지 파일을 정리하는 기능 포함
 *
 * @property context 애플리케이션 컨텍스트 (Hilt를 사용하여 주입)
 */
@Singleton
class FileManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    /**
     * **Bitmap을 파일로 저장하는 함수**
     *
     * - 현재 시간을 기반으로 고유한 파일명을 생성하여 저장
     * - 저장된 파일은 `cacheDir`에 위치
     * - 파일은 JPEG 포맷으로 100% 품질로 저장됨
     *
     * @param bitmap 저장할 이미지(Bitmap)
     * @return 저장된 파일 객체 (`File`)
     */
    fun saveBitmapToFile(bitmap: Bitmap): File {
        val fileName = "IMG_${System.currentTimeMillis()}.jpg" // 현재 시간 기반 파일명 생성
        val file = File(context.cacheDir, fileName) // 앱의 캐시 디렉토리에 파일 생성

        file.outputStream().use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream) // JPEG 형식으로 압축 저장
        }

        return file
    }

    /**
     * **임시 이미지 파일을 정리하는 함수**
     *
     * - 앱의 `cacheDir`에 저장된 이미지 중 `IMG_`로 시작하는 파일을 모두 삭제
     * - 업로드 후 불필요한 이미지 파일을 제거하여 저장 공간을 절약
     */
    fun clearTempFiles() {
        context.cacheDir.listFiles()?.forEach { file ->
            if (file.name.startsWith("IMG_")) {
                file.delete() // 파일 삭제
            }
        }
    }
}