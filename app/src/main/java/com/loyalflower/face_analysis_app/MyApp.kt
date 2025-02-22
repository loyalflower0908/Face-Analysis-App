package com.loyalflower.face_analysis_app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * **애플리케이션 클래스 (MyApp)**
 * - `Hilt`를 사용하기 위한 `@HiltAndroidApp` 애노테이션을 추가
 * - 애플리케이션 전역에서 사용할 수 있도록 `Timber`를 설정
 */
@HiltAndroidApp
class MyApp : Application() {

    /**
     * **앱이 시작될 때 실행되는 코드**
     * - Timber 로깅 라이브러리를 초기화하여 디버깅을 쉽게 만듦
     * - Timber.DebugTree()는 디버그 할 때만 사용해서 로그를 출력
     */
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}
