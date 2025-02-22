# Face Analysis App 📸✨

**Face Analysis App**은 사용자의 얼굴 이미지를 촬영하여 서버([Face Analysis Sever](https://github.com/loyalflower0908/Face-Analysis-Server))에 업로드하고,

사진 분석 결과(성별, 나이, 감정, 인종 등)를 실시간으로 확인할 수 있는 Android 애플리케이션입니다.

Jetpack Compose를 기반으로 UI를 구성하였으며,

MVVM 패턴과 클린 아키텍처를 적용해 유지보수와 확장성이 뛰어난 구조로 설계되었습니다.

---

## 앱 설명 🔍

Face Analysis App은 사용자가 손쉽게 자신의 얼굴을 촬영하고,

해당 이미지를 서버에 전송하여 분석된 결과를 받아볼 수 있도록 설계되었습니다.

촬영된 이미지가 서버에 업로드되면, 서버는 간단한 인증을 거치고 얼굴 분석 데이터를 제공합니다

이를 통해 사용자는 자신의 얼굴 분석 결과(예: 성별, 나이, 감정, 인종)를 직관적인 UI에서 확인할 수 있습니다.

---

## 주요 기능 🚀

- **MVVM 및 클린 아키텍처**
  - Domain, Data, Presentation 계층으로 명확하게 분리되어 있으며, 각 계층간 역할과 책임을 철저하게 구분
  - Repository와 UseCase를 통해 비즈니스 로직을 깔끔하게 관리

- **카메라 및 이미지 촬영**
  - **CameraX**를 이용해 전면 카메라 미리보기 및 사진 촬영
  - 촬영된 이미지를 Bitmap으로 변환, 자동 회전 처리 및 파일로 저장

- **이미지 업로드 및 파일 관리**
  - 촬영한 이미지를 JPEG 형식으로 앱 내부 캐시(cache) 디렉토리에 저장
  - 파일 관리 기능을 통해 임시 이미지 파일을 정리하여 저장 공간 최적화

- **서버 통신 및 인증**
  - **Retrofit**과 **OkHttp**를 이용한 API 호출로 이미지 업로드, 사용자 인증 및 얼굴 분석 데이터 조회
  - 서버로부터 반환된 사용자 아이디, 비밀번호, 인증 토큰 및 분석 결과를 도메인 모델로 매핑

- **네비게이션 및 UI 전환**
  - **Compose Navigation**을 활용해 카메라 화면과 결과 화면 간 데이터 전달 및 원활한 전환 구현
  - 직렬화된 데이터를 안전하게 전달하여 UI 간 상태 유지

- **의존성 주입 및 로깅**
  - **Dagger Hilt**를 통한 의존성 주입으로 모듈화 및 테스트 용이성 확보
  - **Timber** 라이브러리를 사용해 디버깅 및 로그 관리

---

## 기술 스택 🧰

- **UI 및 개발 프레임워크:**  
  - [Jetpack Compose](https://developer.android.com/jetpack/compose)  
  - [CameraX](https://developer.android.com/training/camerax)

- **네트워크 및 데이터 통신:**  
  - [Retrofit](https://square.github.io/retrofit/)  
  - [OkHttp](https://square.github.io/okhttp/)  
  - [Gson Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson)

- **아키텍처 및 상태 관리:**  
  - MVVM 패턴, 클린 아키텍처  

- **의존성 주입:**  
  - [Dagger Hilt](https://dagger.dev/hilt/)

- **로깅:**  
  - [Timber](https://github.com/JakeWharton/timber)

- **네비게이션 및 데이터 직렬화:**  
  - Compose Navigation, Kotlinx Serialization

 ---

## 구조

```kotlin
FaceAnalysisApp/
├── data
│   ├── manager
│   │   ├── CameraManager.kt         // 카메라 초기화, 미리보기, 이미지 촬영 관리
│   │   └── FileManager.kt           // Bitmap 파일 저장 및 임시 파일 정리
│   ├── mapper
│   │   ├── ImageUploadResponseMapper.kt  // ImageUploadResponse → ImageUploadResult 변환
│   │   ├── LoginResponseMapper.kt        // LoginResponse → LoginResult 변환
│   │   └── UserAttributeResponseMapper.kt// UserAttributeResponse → UserAttributeResult 변환
│   ├── network
│   │   ├── ApiService.kt            // 이미지 업로드, 로그인, 사용자 속성 조회 API 인터페이스
│   │   └── dto                      // 서버 응답 DTO (ImageUploadResponse.kt, LoginResponse.kt, UserAttributeResponse.kt)
│   └── repository
│       ├── ImageUploadRepositoryImpl.kt
│       ├── LoginRepositoryImpl.kt
│       └── UserAttributesRepositoryImpl.kt
├── domain
│   ├── model
│   │   ├── ImageUploadResult.kt     // 이미지 업로드 결과 (Success, Error)
│   │   ├── LoginResult.kt           // 로그인 결과 (Success, Error)
│   │   ├── UserAttribute.kt         // 사용자 속성 데이터 (lastUpdateTs, key, value / gender, age, emotion, race 등에 사용)
│   │   └── UserAttributeResult.kt   // 사용자 속성 조회 결과 (Success, Error)
│   ├── repository
│   │   ├── ImageUploadRepository.kt // 이미지 업로드 관련 인터페이스
│   │   ├── LoginRepository.kt       // 로그인 관련 인터페이스
│   │   └── UserAttributesRepository.kt // 사용자 속성 조회 관련 인터페이스
│   └── usecase
│       ├── ImageUploadUseCase.kt    // 이미지 업로드 유스케이스
│       ├── LoginUseCase.kt          // 로그인 유스케이스
│       └── FetchUserAttributesUseCase.kt // 사용자 속성 조회 유스케이스
├── di
│   └── AppModule.kt                 // Dagger Hilt 의존성 주입 모듈 (Retrofit, ApiService, Repository, Manager, EventBus 등 제공)
├── presentation
│   ├── bus
│   │   ├── EventBus.kt              // ViewModel 간 이벤트 전달 인터페이스
│   │   ├── EventBusImpl.kt          // EventBus 기본 구현체 (MutableSharedFlow 활용)
│   │   └── SharedEvent.kt           // 공유 이벤트 (예: 이미지 촬영 이벤트)
│   ├── view
│   │   ├── camera
│   │   │   ├── CameraScreen.kt      // 카메라 화면 UI (미리보기, 촬영, 가이드 오버레이)
│   │   │   └── ui                   // 카메라 화면에 사용되는 UI 컴포넌트 (CameraGuideOverlay.kt, CameraPreview.kt, CameraShutterButton.kt, RequestCameraPermission.kt)
│   │   ├── result
│   │   │   ├── ResultScreen.kt      // 결과 화면 UI (분석 결과 표시 및 뒤로가기 버튼)
│   │   │   └── ui                   // 결과 화면 UI 컴포넌트 (TitleBox.kt, Section.kt, BackButton.kt)
│   │   ├── navigation
│   │   │   ├── NavGraph.kt          // Compose Navigation 그래프 (CameraScreen, ResultScreen 간 네비게이션)
│   │   │   ├── NavigationUtil.kt    // 확장 함수 (예: List<UserAttribute>.toResultRoute())
│   │   │   └── Routes.kt            // 네비게이션 경로 모음
│   │   └── theme
│   │       ├── Color.kt                 // 앱 전반에 사용되는 색상 정의
│   │       ├── Dimens.kt                // 사이즈, 패딩 등 Material Design 가이드에 따른 치수 정의
│   │       └── Typography.kt            // 텍스트 스타일 정의 (폰트, 스타일, 크기 등)
│   └── viewmodel
│       ├── api
│       │   ├── ApiViewModel.kt      // API 통신 및 인증 관련 로직 관리
│       │   └── ApiUiState.kt        // API 통신 상태 정의 (Initial, Loading, Success, Error)
│       └── camera
│           ├── CameraViewModel.kt   // 카메라 제어 및 이미지 촬영 관련 로직 관리
│           └── CameraUiState.kt     // 카메라 UI 상태 정의 (Initial, Preview, Error)
├── MyApp.kt                         // Hilt 적용 Application 클래스 (Timber 초기화 포함)
└── MainActivity.kt                  // 앱 시작 Activity (Edge-to-Edge 모드, Navigation 실행)

```

---

## 스크린 샷 🖼️

| <img src="https://github.com/user-attachments/assets/370e55ce-7825-4d7b-a611-5e7ae310e6f5" width="200">   | <img src="https://github.com/user-attachments/assets/52dcf7b2-cf6a-414b-abe7-aaf4db5199b6" width="200"> | <img src="https://github.com/user-attachments/assets/79b41446-5228-4f72-84e4-bf422c9412e6" width="200"> |  
| :----------------------------------------------:  | :----------------------------------------------: | :----------------------------------------------: |  
|                 촬영 가이드 화면                  |         촬영 화면(AI 생성 30대 남성 사진)         |                  분석 결과 화면                   |  


| <img src="https://github.com/user-attachments/assets/a4f592b9-a936-4934-bb75-e2a1d892a151" width="200">   | <img src="https://github.com/user-attachments/assets/07449ec3-6169-4965-a5a7-a2c4e0363cfd" width="200"> | <img src="https://github.com/user-attachments/assets/e9944b3e-610c-475c-8454-9af2a53861fb" width="400"> |  
| :----------------------------------------------:  | :----------------------------------------------: | :----------------------------------------------: |  
|                   촬영 화면                       |                   분석 결과 화면                  |       사진 출처: GPT에서 DALL·E 3 모델           |  

---

## 제작 기간 ⏱️

- **개발 기간:** 2/12 ~ 2/22

---

## 저작권 및 라이선스 ⚖️

- **Guide 화면 아이콘:**  
  - 이 앱의 Guide 화면 아이콘은 **Grand Iconic**이 제작하였습니다.  
  - 아이콘의 저작권은 해당 아이콘 제작자에게 있으며, 사용 조건은 제작자의 라이선스 정책을 따릅니다.

- **Pretendard 폰트:**  
  - 본 프로젝트에서 사용된 Pretendard 폰트는 **SIL Open Font License 1.1**에 따라 배포됩니다.  
  - Pretendard 폰트의 원 저작권은 [Pretendard GitHub 리포지터리](https://github.com/orioncactus/pretendard)를 참고하시기 바랍니다.



