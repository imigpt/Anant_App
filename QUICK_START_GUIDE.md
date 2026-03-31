# AnantApp - Modular Architecture Quick Start Guide

## 5-Minute Module Creation Guide

### Quick Setup Steps

#### 1. Create Module Directory
```bash
# From AnantApp root directory
mkdir -p feature/nominee/src/main/java/com/example/anantapp/feature/nominee
mkdir -p feature/nominee/src/main/res/values
```

#### 2. Create `feature/nominee/build.gradle.kts`
```gradle
plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.example.anantapp.feature.nominee"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
        targetSdk = 34
    }
}

dependencies {
    implementation(project(":core"))
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material3:material3:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
}
```

#### 3. Update `settings.gradle.kts`
Add line:
```gradle
include(":feature:nominee")
```

#### 4. Update `app/build.gradle.kts`
Add dependency:
```gradle
dependencies {
    implementation(project(":feature:nominee"))
}
```

#### 5. Create Basic Files

**`feature/nominee/src/main/java/com/example/anantapp/feature/nominee/presentation/screen/NomineeDetailsScreen.kt`**:
```kotlin
package com.example.anantapp.feature.nominee.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.core.ui.theme.ThemedCard
import com.example.anantapp.feature.nominee.presentation.viewmodel.NomineeViewModel

@Composable
fun NomineeDetailsScreen(
    onBackClick: () -> Unit = {},
    viewModel: NomineeViewModel = viewModel()
) {
    ThemedCard {
        // Screen content
    }
}
```

**`feature/nominee/src/main/java/com/example/anantapp/feature/nominee/presentation/viewmodel/NomineeViewModel.kt`**:
```kotlin
package com.example.anantapp.feature.nominee.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class NomineeState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class NomineeViewModel : ViewModel() {
    private val _state = MutableStateFlow(NomineeState())
    val state: StateFlow<NomineeState> = _state.asStateFlow()
}
```

That's it! Your module is ready to use.

---

## Module Checklist

### Pre-Creation Checklist
- [ ] Decide module name (e.g., nominee, verify, location)
- [ ] List all screens in this module (10 for nominee)
- [ ] Identify shared components needed
- [ ] Plan API endpoints

### Module Creation Checklist
- [ ] Create module directory structure
- [ ] Create `build.gradle.kts`
- [ ] Add to `settings.gradle.kts`
- [ ] Add to `app/build.gradle.kts`
- [ ] Sync Gradle

### Folder Structure Checklist
- [ ] `presentation/screen/` directory created
- [ ] `presentation/viewmodel/` directory created
- [ ] `presentation/composable/` directory created
- [ ] `data/model/` directory created
- [ ] `data/repository/` directory created
- [ ] `data/api/` directory created
- [ ] `navigation/` directory created (optional)
- [ ] `di/` directory created (optional)

### Initial Files Checklist
- [ ] At least 1 Screen file created
- [ ] At least 1 ViewModel file created
- [ ] At least 1 Model file created
- [ ] At least 1 Repository file created
- [ ] Package names match module structure

### Integration Checklist
- [ ] Module imports correctly in MainActivity
- [ ] Android Studio recognizes module (no red underlines)
- [ ] Project builds successfully
- [ ] Screen composable appears in MainActivity

### Documentation Checklist
- [ ] README.md in module root
- [ ] Screen list documented
- [ ] API endpoints listed
- [ ] Dependencies documented

---

## Common Issues & Solutions

### Issue: Module doesn't appear in project structure
**Solution**: 
1. Restart Android Studio
2. File → Sync with Gradle Files
3. Delete `.gradle` folder and rebuild

### Issue: Cannot import from feature module in app
**Solution**:
1. Verify `include(":feature:module_name")` in `settings.gradle.kts`
2. Verify dependency in `app/build.gradle.kts`
3. Run Gradle sync

### Issue: Red underlines on imports
**Solution**:
1. Check namespace in `build.gradle.kts`
2. Verify package name matches namespace
3. Rebuild project

### Issue: No resources found
**Solution**:
1. Create `src/main/res/values/strings.xml` with basic content:
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">AnantApp</string>
</resources>
```

---

## Module Templates

### Minimal Screen Template
```kotlin
@Composable
fun YourScreen(
    onBackClick: () -> Unit = {},
    viewModel: YourViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    ThemedCard {
        // Your UI
    }
}
```

### Minimal ViewModel Template
```kotlin
data class YourState(
    val data: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class YourViewModel : ViewModel() {
    private val _state = MutableStateFlow(YourState())
    val state: StateFlow<YourState> = _state.asStateFlow()

    fun loadData() {
        // TODO: Implement
    }
}
```

### Minimal Model Template
```kotlin
data class YourModel(
    val id: String,
    val name: String,
    val description: String
)
```

### Minimal Repository Template
```kotlin
class YourRepository {
    suspend fun getData(): List<YourModel> {
        // TODO: Call API
        return emptyList()
    }
}
```

---

## Features Per Module (Comprehensive List)

### Nominee Module (10 screens)
1. **AddNomineeCardsScreen** - Add nominee cards
2. **NomineeDetailsScreen** - View nominee details
3. **NomineeOTPVerificationScreen** - OTP verification
4. **FamilyMemberDetailsScreen** - Family member info
5. **ManageNomineeScreen** - Manage nominees
6. **NomineeListScreen** - List of nominees
7. **NomineeEditScreen** - Edit nominee info
8. **NomineeApprovalScreen** - Approval workflow
9. **NomineeHistoryScreen** - History view
10. **NomineeSettingsScreen** - Nominee settings

### Verify Module (20 screens)
1. **PhotoUploadScreen** - Upload verification photo
2. **VerifyScreen** - Main verification
3. **VerifyBankScreen** - Bank verification
4. **VerifyAddressScreen** - Address verification
5. **DocumentScanScreen** - Document scanning
6. **FaceRecognitionScreen** - Face recognition
7. **OTPVerificationScreen** - OTP verification
8. **DocumentPreviewScreen** - Preview documents
9. **VerificationStatusScreen** - Status display
10. **VerificationHistoryScreen** - History
11. **DocumentCategoryScreen** - Category selection
12. **DocumentUploadScreen** - Upload documents
13. **DocumentReviewScreen** - Review documents
14. **VerificationFailedScreen** - Failed state
15. **VerificationSuccessScreen** - Success state
16. **RetryVerificationScreen** - Retry option
17. **ScanDocumentScreen** - Document scanning
18. **CropDocumentScreen** - Crop scanned image
19. **VerifyPersonalInfoScreen** - Personal info
20. **VerifyContactScreen** - Contact information

### Location Module (5 screens)
1. **ShareRealTimeLocationScreen** - Share location
2. **LocationSharedSuccessScreen** - Success
3. **ManageFamilyMembersScreen** - Manage members
4. **LiveLocationMapScreen** - Live map
5. **LocationHistoryScreen** - History

### Fundraiser Module (6 screens)
1. **CreateFundraiserScreen** - Create new
2. **SelectFundraiserCategoryScreen** - Select category
3. **TargetAndPaymentsScreen** - Target & payments
4. **GovernmentFundraisersScreen** - Gov fundraisers
5. **FundraiserDetailsScreen** - Details
6. **FundraiserManagementScreen** - Manage

### Wallet Module (10 screens)
1. **BalanceScreen** - Wallet balance
2. **AddBalanceScreen** - Add funds
3. **TransactionScreen** - Transactions
4. **DonationHistoryScreen** - Donation history
5. **DonorScreen** - Donor info
6. **PaymentMethodScreen** - Payment methods
7. **OrderSuccessScreen** - Order success
8. **OrderStatusScreen** - Order status
9. **ThankyouScreen** - Thank you
10. **WalletSettingsScreen** - Wallet settings

### QR Module (7 screens)
1. **GenerateQRCodeScreen** - Generate QR
2. **GenerateQRCodeInfoScreen** - QR info
3. **ViewQRCodeScreen** - View QR
4. **QRCodeScannerScreen** - Scan QR
5. **QRCodeHistoryScreen** - QR history
6. **ShareQRCodeScreen** - Share QR
7. **QRCodeSettingsScreen** - QR settings

---

## Dependency Installation

### Add to Module `build.gradle.kts`

**For UI Components**:
```gradle
implementation("androidx.compose.ui:ui:1.5.0")
implementation("androidx.compose.material3:material3:1.0.0")
implementation("androidx.compose.foundation:foundation:1.5.0")
```

**For ViewModels**:
```gradle
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0")
```

**For Networking**:
```gradle
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.okhttp3:okhttp:4.10.0")
```

**For Async Operations**:
```gradle
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.0")
```

---

## Testing Setup

### Create Test Directory
```bash
mkdir -p feature/nominee/src/test/java/com/example/anantapp/feature/nominee
mkdir -p feature/nominee/src/androidTest/java/com/example/anantapp/feature/nominee
```

### Add Test Dependencies
```gradle
testImplementation("junit:junit:4.13.2")
testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.0")
androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.0")
androidTestImplementation("androidx.test.ext:junit:1.1.5")
```

---

## Build Commands

```bash
# Clean build
./gradlew clean build

# Build specific module
./gradlew :feature:nominee:build

# Build with parallel execution (faster)
./gradlew build --parallel --max-workers=8

# Build for debugging
./gradlew assembleDebug

# Run tests
./gradlew test

# Run tests for specific module
./gradlew :feature:nominee:test
```

---

## Performance Tips

### Enable Gradle Optimization
Add to `gradle.properties`:
```properties
# Parallel builds
org.gradle.parallel=true
org.gradle.workers.max=8

# Daemon settings
org.gradle.daemon=true
org.gradle.jvmargs=-Xmx4096m -XX:MaxPermSize=512m

# Build cache
org.gradle.caching=true
```

### Incremental Building
```bash
# Clean build (slower, use when necessary)
./gradlew assembleDebug

# Incremental build (uses cache, faster)
./gradlew assembleDebug --build-cache
```

---

## Code Organization Best Practices

### ✅ DO

```kotlin
// ✅ Good: Clear separation
package com.example.anantapp.feature.nominee.presentation.screen
class NomineeDetailsScreen { }

// ✅ Good: Single responsibility
class NomineeViewModel { }

// ✅ Good: Descriptive names
class GetNomineesUseCase { }
```

### ❌ DON'T

```kotlin
// ❌ Bad: No package structure
package com.example.anantapp
class N1 { }

// ❌ Bad: Mixed concerns
class NomineeScreenAndViewModel { }

// ❌ Bad: Vague names
class Utils { }
class Data { }
```

---

## Useful Commands

```bash
# List all modules
./gradlew projects

# Check dependencies
./gradlew dependencies

# Check for conflicts
./gradlew projectReport

# Generate module documentation
./gradlew help

# Check build times
./gradlew build --profile

# View build report
# Check build/reports/build/report.html
```

---

## Resources

- **Android Modules**: https://developer.android.com/guide/modularization
- **Gradle Documentation**: https://gradle.org/
- **Compose**: https://developer.android.com/jetpack/compose
- **Kotlin**: https://kotlinlang.org/

---

**Quick Reference**: This guide + MODULAR_ARCHITECTURE.md = Complete implementation guide

**Version**: 1.0  
**Last Updated**: March 30, 2026
