# AnantApp - Modular Architecture Documentation

## Overview

AnantApp uses a **multi-module architecture** to organize 61+ screens into 6 feature modules. This document explains the structure, dependencies, and implementation guidelines.

---

## Table of Contents

1. [Module Structure](#module-structure)
2. [Module Details](#module-details)
3. [Gradle Configuration](#gradle-configuration)
4. [File Organization](#file-organization)
5. [Creating a Feature Module](#creating-a-feature-module)
6. [Inter-Module Communication](#inter-module-communication)
7. [Navigation](#navigation)
8. [Testing](#testing)
9. [Best Practices](#best-practices)

---

## Module Structure

### Overall Architecture

```
AnantApp/
├── app/                          ← Main app module (entry point)
│   ├── src/main/
│   │   ├── java/com/example/anantapp/
│   │   │   └── MainActivity.kt
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
│
├── core/                         ← Shared code (all modules depend)
│   ├── src/main/java/com/example/anantapp/core/
│   │   ├── ui/
│   │   │   ├── theme/
│   │   │   ├── components/
│   │   │   └── navigation/
│   │   ├── data/
│   │   │   ├── api/
│   │   │   ├── model/
│   │   │   └── local/
│   │   └── utils/
│   └── build.gradle.kts
│
└── feature/                      ← Feature modules (independent)
    ├── nominee/                  ← 10 screens
    ├── verify/                   ← 20 screens
    ├── location/                 ← 5 screens
    ├── fundraiser/               ← 6 screens
    ├── wallet/                   ← 10 screens
    └── qr/                       ← 7 screens
```

### Dependency Graph

```
                    app
                    │
            ┌───────┼───────┬────────┬──────────┐
            ▼       ▼       ▼        ▼          ▼
          nominee  verify location fundraiser wallet
            │       │       │        │          │
            └───────┴───────┴────────┴──────────┘
                    │
                    ▼
                  core
                    │
        ┌───────────┼───────────┐
        ▼           ▼           ▼
    android.x   compose      coroutines
```

**Rule**: No feature module depends on another feature module. All depend on `core`.

---

## Module Details

### 1. Core Module

**Purpose**: Shared code used by all feature modules

**Contents**:
```
core/
├── ui/
│   ├── theme/
│   │   ├── VerifyTheme.kt
│   │   ├── WalletTheme.kt
│   │   ├── NomineeTheme.kt
│   │   ├── ProfileTheme.kt
│   │   ├── ThemeManager.kt
│   │   ├── CompositionLocal.kt
│   │   └── ThemedComponents.kt
│   ├── components/
│   │   ├── customShadow.kt
│   │   ├── GradientButton.kt
│   │   ├── FrostedCard.kt
│   │   └── CommonComponents.kt
│   └── navigation/
│       └── NavigationEvents.kt
├── data/
│   ├── api/
│   │   ├── ApiClient.kt
│   │   ├── ApiService.kt
│   │   └── interceptors/
│   ├── model/
│   │   ├── ApiResponse.kt
│   │   └── CommonModels.kt
│   └── local/
│       ├── PreferencesDataStore.kt
│       └── LocalDatabase.kt
└── utils/
    ├── Constants.kt
    ├── Extensions.kt
    └── Validators.kt
```

---

### 2. Nominee Feature Module (10 screens)

**Screens**:
1. AddNomineeCardsScreen
2. NomineeDetailsScreen
3. NomineeOTPVerificationScreen
4. FamilyMemberDetailsScreen
5. ManageNomineeScreen
6. NomineeListScreen
7. NomineeEditScreen
8. NomineeApprovalScreen
9. NomineeHistoryScreen
10. NomineeSettingsScreen

**Structure**:
```
feature/nominee/
├── src/main/java/com/example/anantapp/feature/nominee/
│   ├── presentation/
│   │   ├── screen/
│   │   │   ├── AddNomineeCardsScreen.kt
│   │   │   ├── NomineeDetailsScreen.kt
│   │   │   ├── NomineeOTPVerificationScreen.kt
│   │   │   └── ... (10 screens)
│   │   ├── viewmodel/
│   │   │   ├── NomineeViewModel.kt
│   │   │   ├── AddNomineeViewModel.kt
│   │   │   └── ... (VMs for complex screens)
│   │   └── composable/
│   │       ├── NomineeCard.kt
│   │       ├── OTPInput.kt
│   │       └── NomineeForm.kt
│   ├── data/
│   │   ├── model/
│   │   │   ├── Nominee.kt
│   │   │   ├── FamilyMember.kt
│   │   │   └── NomineeRequest.kt
│   │   ├── api/
│   │   │   └── NomineeApiService.kt
│   │   └── repository/
│   │       ├── NomineeRepository.kt
│   │       └── NomineeLocalRepository.kt
│   └── di/
│       └── NomineeModule.kt (dependency injection)
└── build.gradle.kts
```

---

### 3. Verify Feature Module (20 screens)

**Screens**:
1. PhotoUploadScreen
2. VerifyScreen
3. VerifyBankScreen
4. VerifyAddressScreen
5. DocumentScanScreen
6. FaceRecognitionScreen
7. OTPVerificationScreen
8. DocumentPreviewScreen
9. VerificationStatusScreen
10. VerificationHistoryScreen
11. DocumentCategoryScreen
12. DocumentUploadScreen
13. DocumentReviewScreen
14. VerificationFailedScreen
15. VerificationSuccessScreen
16. RetryVerificationScreen
17. ScanDocumentScreen
18. CropDocumentScreen
19. VerifyPersonalInfoScreen
20. VerifyContactScreen

**Key Characteristics**:
- Heavy document handling
- Camera integration
- Image processing
- ML Kit integration (if used)

---

### 4. Location Feature Module (5 screens)

**Screens**:
1. ShareRealTimeLocationScreen
2. LocationSharedSuccessScreen
3. ManageFamilyMembersScreen
4. LiveLocationMapScreen
5. LocationHistoryScreen

**Key Characteristics**:
- GPS/Location services
- Real-time tracking
- Map integration
- Background services

---

### 5. Fundraiser Feature Module (6 screens)

**Screens**:
1. CreateFundraiserScreen
2. SelectFundraiserCategoryScreen
3. TargetAndPaymentsScreen
4. GovernmentFundraisersScreen
5. FundraiserDetailsScreen
6. FundraiserManagementScreen

**Key Characteristics**:
- Fundraiser creation
- Category management
- Payment targets
- Gov fundraiser listing

---

### 6. Wallet Feature Module (10 screens)

**Screens**:
1. BalanceScreen
2. AddBalanceScreen
3. TransactionScreen
4. DonationHistoryScreen
5. DonorScreen
6. PaymentMethodScreen
7. OrderSuccessScreen
8. OrderStatusScreen
9. ThankyouScreen
10. WalletSettingsScreen

**Key Characteristics**:
- Money management
- Transaction history
- Donation tracking
- Payment processing

---

### 7. QR Feature Module (7 screens)

**Screens**:
1. GenerateQRCodeScreen
2. GenerateQRCodeInfoScreen
3. ViewQRCodeScreen
4. QRCodeScannerScreen
5. QRCodeHistoryScreen
6. ShareQRCodeScreen
7. QRCodeSettingsScreen

**Key Characteristics**:
- QR code generation
- QR code scanning
- QR code sharing
- Camera integration

---

## Gradle Configuration

### Root `settings.gradle.kts`

```gradle
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AnantApp"

include(":app")
include(":core")
include(":feature:nominee")
include(":feature:verify")
include(":feature:location")
include(":feature:fundraiser")
include(":feature:wallet")
include(":feature:qr")
```

### Core Module `core/build.gradle.kts`

```gradle
plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.example.anantapp.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // AndroidX
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.10.1")

    // Compose
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material3:material3:1.0.0")
    implementation("androidx.compose.foundation:foundation:1.5.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.0")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Network (Retrofit + OkHttp)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // JSON
    implementation("com.google.code.gson:gson:2.10.1")

    // Dependency Injection (optional, if using Hilt)
    implementation("com.google.dagger:hilt-core:2.46")
    kapt("com.google.dagger:hilt-compiler:2.46")
}
```

### Feature Module `feature/nominee/build.gradle.kts`

```gradle
plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.example.anantapp.feature.nominee"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Core module
    implementation(project(":core"))

    // Compose
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material3:material3:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.0")
}
```

### App Module `app/build.gradle.kts`

```gradle
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.example.anantapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.anantapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
    }

    packagingOptions {
        exclude("META-INF/proguard/androidx-*.pro")
    }
}

dependencies {
    // Core module
    implementation(project(":core"))

    // Feature modules
    implementation(project(":feature:nominee"))
    implementation(project(":feature:verify"))
    implementation(project(":feature:location"))
    implementation(project(":feature:fundraiser"))
    implementation(project(":feature:wallet"))
    implementation(project(":feature:qr"))

    // AndroidX
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.10.1")

    // Compose
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material3:material3:1.0.0")
    implementation("androidx.activity:activity-compose:1.7.0")

    // Navigation (optional, if using Jetpack Navigation)
    implementation("androidx.navigation:navigation-compose:2.6.0")

    // Dependency Injection (Hilt)
    implementation("com.google.dagger:hilt-android:2.46")
    kapt("com.google.dagger:hilt-compiler:2.46")
}
```

---

## File Organization

### Within Each Feature Module

```
feature/nominee/src/main/java/com/example/anantapp/feature/nominee/

presentation/                      ← UI Layer (Screens + ViewModels)
├── screen/
│   ├── AddNomineeCardsScreen.kt
│   ├── NomineeDetailsScreen.kt
│   └── ... (all screens)
├── viewmodel/
│   ├── NomineeViewModel.kt        ← Shared ViewModel for multiple screens
│   ├── AddNomineeViewModel.kt
│   └── NomineeOTPViewModel.kt
├── composable/                     ← Reusable UI components specific to this feature
│   ├── NomineeCard.kt
│   ├── OTPInputField.kt
│   └── NomineeForm.kt
└── state/
    ├── NomineeState.kt
    └── NomineeEvent.kt

data/                              ← Data Layer (Models, API, Repository)
├── model/
│   ├── Nominee.kt
│   ├── FamilyMember.kt
│   ├── NomineeRequest.kt
│   ├── NomineeResponse.kt
│   └── NomineeStatus.kt
├── api/
│   ├── NomineeApiService.kt
│   └── NomineeApiModels.kt
├── repository/
│   ├── NomineeRepository.kt       ← Interface (contract)
│   └── NomineeRepositoryImpl.kt    ← Implementation
└── local/
    └── NomineeLocalDataSource.kt  ← Local caching (if needed)

di/                                ← Dependency Injection
└── NomineeModule.kt              ← Hilt module (if using Hilt)

navigation/
└── NomineeNavigation.kt           ← Navigation routes specific to this module
```

---

## Creating a Feature Module

### Step 1: Create Module Directory

In Android Studio:
1. **File → New → Module**
2. Select "Android Library"
3. Name: `feature_nominee` (Android Studio converts to `feature:nominee`)

Or manually create:
```bash
mkdir -p feature/nominee/src/main/java/com/example/anantapp/feature/nominee
mkdir -p feature/nominee/src/main/res/values
```

### Step 2: Create `build.gradle.kts`

Copy the template from "Feature Module Gradle Configuration" section above and adjust:
- Change `namespace` to match your module
- Change `project(":core")` dependency

### Step 3: Create Directory Structure

```bash
# From feature/nominee/ directory
mkdir -p src/main/java/com/example/anantapp/feature/nominee/{presentation,data,di,navigation}
mkdir -p src/main/java/com/example/anantapp/feature/nominee/presentation/{screen,viewmodel,composable,state}
mkdir -p src/main/java/com/example/anantapp/feature/nominee/data/{model,api,repository,local}
```

### Step 4: Create Initial Files

**presentation/screen/NomineeDetailsScreen.kt**:
```kotlin
package com.example.anantapp.feature.nominee.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.core.ui.theme.ThemedCard
import com.example.anantapp.core.ui.theme.ThemedTitle
import com.example.anantapp.core.ui.theme.ThemeViewModel
import com.example.anantapp.core.ui.theme.ScreenTheme
import com.example.anantapp.feature.nominee.presentation.viewmodel.NomineeViewModel

@Composable
fun NomineeDetailsScreen(
    onBackClick: () -> Unit = {},
    themeViewModel: ThemeViewModel = viewModel(),
    viewModel: NomineeViewModel = viewModel()
) {
    val themeState by themeViewModel.themeState.collectAsState()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        themeViewModel.setNomineeScreenTheme()
    }

    val theme = themeState.theme

    ThemedCard(theme = theme) {
        ThemedTitle("Nominee Details", theme = theme)
        // Screen content here
    }
}
```

**presentation/viewmodel/NomineeViewModel.kt**:
```kotlin
package com.example.anantapp.feature.nominee.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anantapp.feature.nominee.data.repository.NomineeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class NomineeScreenState(
    val nominees: List<Nominee> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class NomineeViewModel(
    private val repository: NomineeRepository = NomineeRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(NomineeScreenState())
    val state: StateFlow<NomineeScreenState> = _state.asStateFlow()

    fun loadNominees() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                val nominees = repository.getNominees()
                _state.value = _state.value.copy(
                    nominees = nominees,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = e.message,
                    isLoading = false
                )
            }
        }
    }
}
```

**data/model/Nominee.kt**:
```kotlin
package com.example.anantapp.feature.nominee.data.model

data class Nominee(
    val id: String,
    val name: String,
    val relation: String,
    val email: String,
    val phone: String,
    val status: NomineeStatus = NomineeStatus.PENDING
)

enum class NomineeStatus {
    ACTIVE,
    INACTIVE,
    PENDING,
    REJECTED
}
```

**data/repository/NomineeRepository.kt**:
```kotlin
package com.example.anantapp.feature.nominee.data.repository

import com.example.anantapp.feature.nominee.data.model.Nominee

class NomineeRepository {
    // TODO: Inject API service when ready
    
    suspend fun getNominees(): List<Nominee> {
        // TODO: Call API
        return emptyList()
    }
    
    suspend fun addNominee(nominee: Nominee) {
        // TODO: Call API
    }
    
    suspend fun updateNominee(nominee: Nominee) {
        // TODO: Call API
    }
    
    suspend fun deleteNominee(id: String) {
        // TODO: Call API
    }
}
```

### Step 5: Update `settings.gradle.kts`

Add to root:
```gradle
include(":feature:nominee")
```

### Step 6: Update `app/build.gradle.kts`

Add dependency:
```gradle
dependencies {
    implementation(project(":feature:nominee"))
}
```

---

## Inter-Module Communication

### 1. Through Core Module

**Shared Data Models** (`core/data/model/`):
```kotlin
// This goes in core so all modules can use it
data class User(
    val id: String,
    val name: String,
    val email: String
)
```

**Shared API Service** (`core/data/api/`):
```kotlin
// Core API client that all modules use
class ApiClient {
    fun getService() = retrofit.create(ApiService::class.java)
}
```

### 2. Through Events

**Shared Navigation Events** (`core/ui/navigation/`):
```kotlin
sealed class NavigationEvent {
    class ToNomineeDetails(val nomineeId: String) : NavigationEvent()
    class ToVerifyScreen : NavigationEvent()
    object Back : NavigationEvent()
}
```

### 3. Through Shared ViewModels

For features that need to share state:
```kotlin
// In core module
class SharedStateViewModel : ViewModel() {
    val selectedUser = MutableStateFlow<User?>(null)
}

// In feature modules
class NomineeViewModel(
    private val sharedState: SharedStateViewModel
) : ViewModel() {
    val user = sharedState.selectedUser
}
```

---

## Navigation

### Navigation Flow

```
MainActivity
    ↓
when (navState) {
    ScreenRoute.NOMINEE -> NomineeDetailsScreen()
    ScreenRoute.VERIFY -> VerifyScreen()
    ScreenRoute.LOCATION -> ShareLocationScreen()
    ...
}
```

### Each Module's Navigation Routes

**feature/nominee/navigation/NomineeNavigation.kt**:
```kotlin
package com.example.anantapp.feature.nominee.navigation

object NomineeRoutes {
    const val NOMINEE_DETAILS = "nominee_details"
    const val NOMINEE_ADD = "nominee_add"
    const val NOMINEE_OTP = "nominee_otp"
}
```

**feature/verify/navigation/VerifyNavigation.kt**:
```kotlin
package com.example.anantapp.feature.verify.navigation

object VerifyRoutes {
    const val VERIFY_SCREEN = "verify"
    const val PHOTO_UPLOAD = "photo_upload"
    const val DOCUMENT_SCAN = "document_scan"
}
```

**MainActivity.kt** collects all routes:
```kotlin
@Composable
fun MainNavigation() {
    val currentScreen = remember { mutableStateOf("home") }

    when (currentScreen.value) {
        // Nominee routes
        NomineeRoutes.NOMINEE_DETAILS -> NomineeDetailsScreen()
        
        // Verify routes
        VerifyRoutes.VERIFY_SCREEN -> VerifyScreen()
        
        // Location routes
        LocationRoutes.SHARE_LOCATION -> ShareLocationScreen()
        
        // Fundraiser routes
        FundraiserRoutes.CREATE -> CreateFundraiserScreen()
        
        // Wallet routes
        WalletRoutes.BALANCE -> BalanceScreen()
        
        // QR routes
        QRRoutes.GENERATE -> GenerateQRCodeScreen()
    }
}
```

---

## Testing

### Unit Tests (Within Each Module)

**feature/nominee/src/test/java/.../presentation/viewmodel/NomineeViewModelTest.kt**:
```kotlin
class NomineeViewModelTest {

    private lateinit var viewModel: NomineeViewModel
    private lateinit var repository: FakeNomineeRepository

    @Before
    fun setup() {
        repository = FakeNomineeRepository()
        viewModel = NomineeViewModel(repository)
    }

    @Test
    fun testLoadNominees_Success() = runTest {
        val nominees = listOf(
            Nominee("1", "John", "Son", "john@email.com", "1234567890")
        )
        repository.setNominees(nominees)

        viewModel.loadNominees()
        
        val state = viewModel.state.first()
        assertTrue(state.nominees.isNotEmpty())
        assertFalse(state.isLoading)
    }
}
```

### Integration Tests

**core/src/androidTest/java/.../data/NomineeApiIntegrationTest.kt**:
```kotlin
class NomineeApiIntegrationTest {
    
    @get:Rule
    val mockWebServer = MockWebServerRule()

    @Test
    fun testGetNomineesList() = runTest {
        val api = createApiService()
        val response = api.getNominees()
        
        assertNotNull(response)
    }
}
```

---

## Best Practices

### 1. **Module Independence**
- ✅ DO: Feature modules depend only on core
- ❌ DON'T: Feature module A depend on feature module B
- ✅ DO: Share code through core module

### 2. **Package Naming**
```
Feature Module: com.example.anantapp.feature.nominee
Core Module:    com.example.anantapp.core
App Module:     com.example.anantapp
```

### 3. **Layered Architecture**
Each module must have:
- **Presentation Layer**: Screens + ViewModels
- **Data Layer**: Models + API + Repository
- **Navigation**: Routes specific to module

### 4. **Dependency Direction**
```
Presentation → Data
    ↓
   Core
```
NOT:
```
Data → Presentation  ❌
```

### 5. **Naming Conventions**

| Type | Convention | Example |
|------|-----------|---------|
| Screen | `{Feature}Screen` | `NomineeDetailsScreen` |
| ViewModel | `{Feature}ViewModel` | `NomineeViewModel` |
| Repository | `{Feature}Repository` | `NomineeRepository` |
| API Service | `{Feature}ApiService` | `NomineeApiService` |
| Model | `{Entity}` | `Nominee`, `FamilyMember` |
| State | `{Feature}State` | `NomineeState` |
| Event | `{Feature}Event` | `NomineeEvent` |

### 6. **Build Performance**

Module separation provides:
- **Faster builds**: Only changed modules recompile
- **Parallel builds**: Multiple modules compile simultaneously
- **Better caching**: Gradle caches module outputs

Enable parallel builds in `gradle.properties`:
```properties
org.gradle.parallel=true
org.gradle.workers.max=8
```

### 7. **Code Reusability**

- Move common code to `core/`
- Create shared composables in `core/ui/components/`
- Use core repositories for shared API calls
- Extend core theme for module-specific styles

### 8. **Error Handling**

Each module should handle its own errors:
```kotlin
// In feature module repository
suspend fun getNominees(): Result<List<Nominee>> {
    return try {
        val nominees = api.getNominees()
        Result.success(nominees)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

// In viewmodel
viewModel.loadNominees().onFailure { error ->
    _state.value = _state.value.copy(
        errorMessage = error.message
    )
}
```

### 9. **Logging**

Use consistent logging across modules:
```kotlin
class NomineeRepository {
    private val tag = "NomineeRepository"
    
    suspend fun getNominees(): List<Nominee> {
        Log.d(tag, "Fetching nominees...")
        // ...
    }
}
```

### 10. **Version Control**

- Each module is independent Git history
- Easier code review (review one module at a time)
- Easier to track changes per feature

---

## Migration Strategy

### Phase 1: Create Core Module
1. Move shared code to `core/`
2. Move `ui/theme/` → `core/ui/theme/`
3. Move `ui/components/` → `core/ui/components/`
4. Update imports in existing screens

### Phase 2: Create Feature Modules
1. Create `feature/nominee/`
2. Move nominee-related screens
3. Create repository layer
4. Update imports

### Phase 3: Repeat Phase 2 for Other Modules
- `feature/verify/`
- `feature/location/`
- `feature/fundraiser/`
- `feature/wallet/`
- `feature/qr/`

### Phase 4: Update MainActivity
1. Import all feature modules
2. Create central navigation
3. Test all screen transitions

### Phase 5: API Integration
1. Create API models in each module's data layer
2. Implement API calls in repositories
3. Connect to backend services

---

## Summary

| Aspect | Details |
|--------|---------|
| **Modules** | 1 core + 6 features |
| **Total Screens** | 50+ screens |
| **Shared Code** | Core module |
| **Dependencies** | Features → Core |
| **Build Benefit** | Faster incremental builds |
| **Testing Benefit** | Test modules independently |
| **Maintenance Benefit** | Clear separation of concerns |

---

## Quick Reference

### Directory Tree
```
AnantApp/
├── app/
│   └── build.gradle.kts
├── core/
│   ├── ui/theme/
│   ├── ui/components/
│   ├── data/
│   └── build.gradle.kts
└── feature/
    ├── nominee/
    ├── verify/
    ├── location/
    ├── fundraiser/
    ├── wallet/
    └── qr/
```

### Gradle Dependencies
```
app → {all features} → core
```

### Module Communication
```
Through core module only
No direct feature-to-feature communication
```

---

## Next Steps

1. ✅ Create core module structure
2. ✅ Create feature module templates
3. ✅ Migrate existing screens
4. ✅ Set up navigation in MainActivity
5. ✅ Add API integration layer
6. ✅ Write unit tests for each module
7. ✅ Document API integration

---

**Last Updated**: March 30, 2026  
**Version**: 1.0
