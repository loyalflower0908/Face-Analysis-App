package com.loyalflower.face_analysis_app.data.network

import com.loyalflower.face_analysis_app.data.network.dto.ImageUploadResponse
import com.loyalflower.face_analysis_app.data.network.dto.LoginResponse
import com.loyalflower.face_analysis_app.data.network.dto.UserAttributeResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


/**
 * 얼굴 분석 API 서비스 인터페이스
 *
 * - 사용자 인증 및 분석 정보 조회 기능을 제공
 */
interface ApiService {

    /**
     * 이미지를 서버에 업로드하는 API
     *
     * @param image 업로드할 이미지 파일 (멀티파트 형식)
     * @return 업로드 결과인 사용자 인증 정보를 포함한 `Response<UploadImageResponse>`
     */
    @Multipart
    @POST("/upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<ImageUploadResponse>

    /**
     * 사용자 로그인 API
     *
     * @param credentials 사용자 로그인 정보 (user_id, password)
     * @return 로그인 결과를 포함한 `Response<LoginResponse>`
     */
    @POST("/login")
    suspend fun login(
        @Body credentials: Map<String, String>
    ): Response<LoginResponse>

    /**
     * 특정 사용자의 속성 정보 조회 API (사진 분석 값)
     *
     * @param authHeader 인증 토큰 (예: "Bearer {token}")
     * @param userId 조회할 사용자의 ID
     * @return 사용자의 속성 정보 목록을 포함한 `Response<List<UserAttributeResponse>>`
     */
    @GET("/{user_id}/attributes")
    suspend fun getUserAttributes(
        @Header("Authorization") authHeader: String,
        @Path("user_id") userId: String
    ): Response<List<UserAttributeResponse>>
}
