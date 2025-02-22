package com.loyalflower.face_analysis_app.di

import android.content.Context
import com.loyalflower.face_analysis_app.data.manager.CameraManager
import com.loyalflower.face_analysis_app.data.network.ApiService
import com.loyalflower.face_analysis_app.data.repository.ImageUploadRepositoryImpl
import com.loyalflower.face_analysis_app.data.repository.LoginRepositoryImpl
import com.loyalflower.face_analysis_app.data.repository.UserAttributesRepositoryImpl
import com.loyalflower.face_analysis_app.domain.repository.ImageUploadRepository
import com.loyalflower.face_analysis_app.domain.repository.LoginRepository
import com.loyalflower.face_analysis_app.domain.repository.UserAttributesRepository
import com.loyalflower.face_analysis_app.presentation.bus.EventBus
import com.loyalflower.face_analysis_app.presentation.bus.EventBusImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * 네트워크 요청에 사용할 공통 OkHttpClient를 제공합니다.
     *
     * @return 다음 설정이 적용된 [OkHttpClient]:
     * - 연결 시도 제한 시간: 10초
     * - 읽기 제한 시간: 60초 (응답 대기)
     * - 업로드(쓰기) 제한 시간: 60초 (사진 크기와 인터넷 속도에 따른 지연 예상)
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    /**
     * 얼굴 분석 API를 위한 Retrofit 인스턴스를 제공합니다.
     * **Base URL**: 서버의 URL
     * @param client 네트워크 요청에 사용할 [OkHttpClient]
     */
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("BASE_URL")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * **얼굴 분석 API 서비스 제공**
     * - Retrofit을 사용하여 `ApiService` 생성
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    /**
     * ImageUploadRepository 의 구현체를 제공합니다.
     *
     * @param apiService API 서비스
     * @return [ImageUploadRepository]의 구현체 [ImageUploadRepositoryImpl]
     */
    @Provides
    @Singleton
    fun provideImageUploadRepository(apiService: ApiService): ImageUploadRepository {
        return ImageUploadRepositoryImpl(apiService)
    }

    /**
     * LoginRepository 의 구현체를 제공합니다.
     *
     * @param apiService API 서비스
     * @return [LoginRepository] 구현체 [LoginRepositoryImpl]
     */
    @Provides
    @Singleton
    fun provideLoginRepository(apiService: ApiService): LoginRepository {
        return LoginRepositoryImpl(apiService)
    }

    /**
     * UserAttributesRepository 의 구현체를 제공합니다.
     *
     * @param apiService API 서비스
     * @return [UserAttributesRepository] 구현체 [UserAttributesRepositoryImpl]
     */
    @Provides
    @Singleton
    fun provideUserAttributesRepository(apiService: ApiService): UserAttributesRepository {
        return UserAttributesRepositoryImpl(apiService)
    }

    /**
     * CameraManager 인스턴스를 제공합니다.
     *
     * @param context 애플리케이션 컨텍스트
     * @return [CameraManager] 인스턴스
     */
    @Provides
    @Singleton
    fun provideCameraManager(@ApplicationContext context: Context): CameraManager {
        return CameraManager(context)
    }

    /**
     * EventBus 인스턴스를 제공합니다.
     * 공유 이벤트 관리를 위한 구현체 제공
     * @return [EventBus] 구현체 [EventBusImpl]
     */
    @Provides
    @Singleton
    fun provideEventBus(): EventBus {
        return EventBusImpl()
    }
}