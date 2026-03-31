# AnantApp - Implementation Roadmap

## Phase-wise Implementation Plan

This document outlines the step-by-step roadmap for implementing the modular architecture in AnantApp.

---

## Phase 1: Foundation Setup (Week 1)

### 1.1 Prepare Core Module
- [ ] Create `core/` directory structure
- [ ] Move theme files (VerifyTheme.kt, WalletTheme.kt, etc.)
- [ ] Move common UI components (customShadow, GradientButton, etc.)
- [ ] Create core API client infrastructure
- [ ] Create core data models
- [ ] Create core utilities and constants
- [ ] Create `core/build.gradle.kts` with all dependencies
- [ ] Verify core module builds successfully

**Files to move/create**:
```
core/ui/theme/
├── VerifyTheme.kt
├── WalletTheme.kt
├── NomineeTheme.kt
├── ProfileTheme.kt
├── ThemeManager.kt
├── CompositionLocal.kt
└── ThemedComponents.kt

core/ui/components/
├── customShadow.kt
├── GradientButton.kt
└── FrostedCard.kt

core/data/
├── api/
│   ├── ApiClient.kt
│   ├── ApiService.kt
│   └── ApiModels.kt
├── model/
│   └── CommonModels.kt
└── local/
    └── PreferencesDataStore.kt
```

---

## Phase 2: Create Feature Modules (Weeks 2-3)

### 2.1 Create Nominee Module

**Week 2, Day 1**:
- [ ] Create `feature/nominee/` directory and structure
- [ ] Create `build.gradle.kts`
- [ ] Update `settings.gradle.kts` and `app/build.gradle.kts`
- [ ] Create presentation layer structure

**Day 2-3: Create Screens**:
- [ ] AddNomineeCardsScreen.kt
- [ ] NomineeDetailsScreen.kt
- [ ] NomineeOTPVerificationScreen.kt
- [ ] FamilyMemberDetailsScreen.kt
- [ ] ManageNomineeScreen.kt
- [ ] NomineeListScreen.kt
- [ ] NomineeEditScreen.kt
- [ ] NomineeApprovalScreen.kt
- [ ] NomineeHistoryScreen.kt
- [ ] NomineeSettingsScreen.kt

**Day 4: Create ViewModels**:
- [ ] NomineeViewModel.kt
- [ ] AddNomineeViewModel.kt
- [ ] NomineeOTPViewModel.kt

**Day 5: Create Data Layer**:
- [ ] Models (Nominee.kt, FamilyMember.kt, etc.)
- [ ] NomineeRepository.kt
- [ ] NomineeApiService.kt

### 2.2 Create Verify Module

**Week 2, Day 6-7 → Week 3**:
- [ ] Create `feature/verify/` with structure
- [ ] Create 20 screens (split between days)
- [ ] Create ViewModels for complex screens
- [ ] Create data models and repository

**20 Screens** (create in batches):
- Batch 1: PhotoUploadScreen, VerifyScreen, VerifyBankScreen
- Batch 2: VerifyAddressScreen, DocumentScanScreen, FaceRecognitionScreen
- Batch 3: OTPVerificationScreen, DocumentPreviewScreen, VerificationStatusScreen
- Batch 4: VerificationHistoryScreen, DocumentCategoryScreen, DocumentUploadScreen
- Batch 5: DocumentReviewScreen, VerificationFailedScreen, VerificationSuccessScreen
- Batch 6: RetryVerificationScreen, ScanDocumentScreen, CropDocumentScreen
- Batch 7: VerifyPersonalInfoScreen, VerifyContactScreen

### 2.3 Create Location Module

**Week 3**:
- [ ] Create `feature/location/` with structure
- [ ] ShareRealTimeLocationScreen (refactor existing)
- [ ] LocationSharedSuccessScreen (refactor existing)
- [ ] ManageFamilyMembersScreen (refactor existing)
- [ ] LiveLocationMapScreen (refactor existing)
- [ ] LocationHistoryScreen (new)
- [ ] Create LocationRepository.kt
- [ ] Create LocationViewModel.kt

### 2.4 Create Fundraiser Module

**Week 3**:
- [ ] Create `feature/fundraiser/` with structure
- [ ] CreateFundraiserScreen
- [ ] SelectFundraiserCategoryScreen
- [ ] TargetAndPaymentsScreen
- [ ] GovernmentFundraisersScreen
- [ ] FundraiserDetailsScreen
- [ ] FundraiserManagementScreen
- [ ] Create FundraiserRepository.kt
- [ ] Create FundraiserViewModel.kt

### 2.5 Create Wallet Module

**Week 3-4**:
- [ ] Create `feature/wallet/` with structure
- [ ] BalanceScreen (refactor existing)
- [ ] AddBalanceScreen (refactor existing)
- [ ] TransactionScreen (refactor existing)
- [ ] DonationHistoryScreen (refactor existing)
- [ ] DonorScreen (refactor existing)
- [ ] PaymentMethodScreen
- [ ] OrderSuccessScreen
- [ ] OrderStatusScreen
- [ ] ThankyouScreen (refactor existing)
- [ ] WalletSettingsScreen
- [ ] Create WalletRepository.kt
- [ ] Create related ViewModels

### 2.6 Create QR Module

**Week 4**:
- [ ] Create `feature/qr/` with structure
- [ ] GenerateQRCodeScreen (refactor existing)
- [ ] GenerateQRCodeInfoScreen (refactor existing)
- [ ] ViewQRCodeScreen (refactor existing)
- [ ] QRCodeScannerScreen (refactor existing)
- [ ] QRCodeHistoryScreen (new)
- [ ] ShareQRCodeScreen (new)
- [ ] QRCodeSettingsScreen (new)
- [ ] Create QRRepository.kt
- [ ] Create QRViewModel.kt

---

## Phase 3: Integration & Navigation (Week 5)

### 3.1 Update MainActivity

- [ ] Import all feature modules
- [ ] Create central navigation router
- [ ] Map all screen routes
- [ ] Implement screen-to-screen navigation
- [ ] Test all navigation paths

**Navigation Structure**:
```kotlin
when (currentScreen.value) {
    // Nominee
    NomineeRoutes.DETAILS -> NomineeDetailsScreen()
    
    // Verify
    VerifyRoutes.VERIFY_SCREEN -> VerifyScreen()
    
    // Location
    LocationRoutes.SHARE_LOCATION -> ShareLocationScreen()
    
    // Fundraiser
    FundraiserRoutes.CREATE -> CreateFundraiserScreen()
    
    // Wallet
    WalletRoutes.BALANCE -> BalanceScreen()
    
    // QR
    QRRoutes.GENERATE -> GenerateQRCodeScreen()
}
```

### 3.2 Test Module Integration

- [ ] Verify all imports work
- [ ] Test builds successfully
- [ ] Navigate through all screens
- [ ] Test back button functionality
- [ ] Verify theme changes work

---

## Phase 4: API Integration (Weeks 6-8)

### 4.1 Retrofit Setup

- [ ] Configure Retrofit in core module
- [ ] Create API interface/service
- [ ] Set up authentication interceptor
- [ ] Create API response models
- [ ] Test API connectivity

### 4.2 Nominee API

- [ ] Create NomineeApiService
- [ ] Implement getNominees()
- [ ] Implement addNominee()
- [ ] Implement updateNominee()
- [ ] Implement deleteNominee()
- [ ] Update NomineeRepository to use API
- [ ] Handle errors and loading states

### 4.3 Verify API

- [ ] Create VerifyApiService
- [ ] Document upload API
- [ ] Face recognition API
- [ ] Verification status API
- [ ] Update VerifyRepository

### 4.4 Location API

- [ ] Create LocationApiService
- [ ] Real-time location tracking API
- [ ] Location history API
- [ ] Update LocationRepository

### 4.5 Fundraiser API

- [ ] Create FundraiserApiService
- [ ] Create fundraiser API
- [ ] Category listing API
- [ ] Gov fundraisers API
- [ ] Update FundraiserRepository

### 4.6 Wallet API

- [ ] Create WalletApiService
- [ ] Balance check API
- [ ] Transaction history API
- [ ] Donation tracking API
- [ ] Payment processing API
- [ ] Update WalletRepository

### 4.7 QR API

- [ ] Create QRApiService
- [ ] QR generation API
- [ ] QR scanning API
- [ ] QR sharing API
- [ ] Update QRRepository

---

## Phase 5: Testing (Weeks 9-10)

### 5.1 Unit Tests

- [ ] Test each ViewModel
- [ ] Test each Repository
- [ ] Test data models
- [ ] Mock API responses
- [ ] Test error handling

**Coverage Target**: 80%+

### 5.2 Integration Tests

- [ ] Test API calls
- [ ] Test data persistence
- [ ] Test theme switching
- [ ] Test navigation
- [ ] Test error scenarios

### 5.3 UI Tests

- [ ] Test screen rendering
- [ ] Test user interactions
- [ ] Test form validation
- [ ] Test navigation flow
- [ ] Test theme application

---

## Phase 6: Optimization (Week 11)

### 6.1 Performance

- [ ] Profile build times
- [ ] Optimize module dependencies
- [ ] Enable parallel builds
- [ ] Check bundle size
- [ ] Optimize image assets

### 6.2 Caching

- [ ] Implement repository caching
- [ ] Set up database caching
- [ ] Configure retrofit caching
- [ ] Test offline functionality

### 6.3 Error Handling

- [ ] Implement error logging
- [ ] Add error analytics
- [ ] User-friendly error messages
- [ ] Error recovery mechanisms

---

## Phase 7: Documentation & Release (Week 12)

### 7.1 Code Documentation

- [ ] Document all public APIs
- [ ] Comment complex logic
- [ ] Document module interactions
- [ ] Create API documentation
- [ ] Document state management

### 7.2 User Documentation

- [ ] Feature guides
- [ ] Troubleshooting guide
- [ ] FAQ document
- [ ] Release notes

### 7.3 Prepare Release

- [ ] Version bump
- [ ] Create release branch
- [ ] Tag release
- [ ] Update changelog
- [ ] Deploy to testing

---

## Checklist by Phase

### Phase 1 ✅
- Core module created ✓
- All shared code centralized ✓
- build.gradle.kts configured ✓
- Builds successfully ✓

### Phase 2 ✅
- 6 feature modules created ✓
- All 50+ screens migrated ✓
- All ViewModels created ✓
- All data models created ✓
- All repositories created ✓

### Phase 3 ✅
- MainActivity updated ✓
- Central navigation implemented ✓
- All routes mapped ✓
- Navigation tested ✓
- Back button working ✓

### Phase 4 ✅
- Retrofit configured ✓
- All API services created ✓
- Repositories integrated with APIs ✓
- Error handling implemented ✓
- Loading states working ✓

### Phase 5 ✅
- 80%+ test coverage ✓
- All tests passing ✓
- Integration tests complete ✓
- UI tests complete ✓
- Error scenarios handled ✓

### Phase 6 ✅
- Build optimized ✓
- Caching implemented ✓
- Offline support ✓
- Performance profiled ✓
- Bundle size optimized ✓

### Phase 7 ✅
- Code documented ✓
- API documented ✓
- Release notes created ✓
- Ready for release ✓

---

## Weekly Timeline

```
Week 1:  Core module setup
Week 2:  Nominee + Verify modules (partial)
Week 3:  Verify (complete) + Location + Fundraiser
Week 4:  Wallet + QR modules
Week 5:  Navigation & Integration
Week 6:  API Phase 1 (Nominee, Verify)
Week 7:  API Phase 2 (Location, Fundraiser)
Week 8:  API Phase 3 (Wallet, QR)
Week 9:  Testing Phase 1 (Unit tests)
Week 10: Testing Phase 2 (Integration & UI tests)
Week 11: Optimization
Week 12: Documentation & Release
```

**Total Duration**: 12 weeks (~3 months)

---

## Resource Allocation

### Team Structure Example
```
Team Lead: 1
├── Core Module Dev: 1
├── Nominee/Verify Dev: 1
├── Location/Fundraiser Dev: 1
├── Wallet/QR Dev: 1
├── QA/Testing: 1
└── Documentation: 1

Total: 7 people
```

### One Developer Timeline
```
If one developer: 24 weeks (6 months)
If two developers: 12 weeks (3 months)
If three developers: 8 weeks (2 months)
```

---

## Risk Mitigation

### Risk 1: Build Failures During Module Creation
**Mitigation**:
- Create modules one at a time
- Test each module after creation
- Use version control (git) for rollback

### Risk 2: Navigation Complexity
**Mitigation**:
- Create navigation routes early
- Test navigation frequently
- Document routes clearly

### Risk 3: API Integration Issues
**Mitigation**:
- Use mock data initially
- Gradual API integration
- Comprehensive error handling

### Risk 4: Test Coverage Gaps
**Mitigation**:
- Write tests as code is written
- Use coverage tools
- Review test cases regularly

---

## Success Metrics

| Metric | Target | Status |
|--------|--------|--------|
| Module Structure | 6 modules | ⬜ |
| Build Time | < 2 min | ⬜ |
| Test Coverage | 80%+ | ⬜ |
| Navigation Routes | 50+ | ⬜ |
| API Integration | 100% | ⬜ |
| Code Documentation | 100% | ⬜ |
| Release Readiness | ✓ | ⬜ |

---

## Communication Plan

### Daily Standup (15 mins)
- Report blockers
- Share progress
- Plan day's work

### Weekly Review (30 mins)
- Review completed tasks
- Discuss challenges
- Plan next week

### Bi-weekly Demo (30 mins)
- Show completed features
- Stakeholder feedback
- Update roadmap

---

## Next Steps

1. **Approve Roadmap** - Get stakeholder sign-off
2. **Allocate Resources** - Assign developers
3. **Setup Version Control** - Prepare git branches
4. **Start Phase 1** - Begin core module development
5. **Track Progress** - Weekly updates

---

**Document Owner**: Engineering Team  
**Last Updated**: March 30, 2026  
**Version**: 1.0

---

## Appendix: File Estimates

### Files to Create/Move

```
Core Module:        ~25 files
├── Theme files:     6
├── Components:      8
├── Data:            8
└── Utils:           3

Nominee Module:     ~20 files
├── Screens:        10
├── ViewModels:      3
├── Data:            5
└── Navigation:      2

Verify Module:      ~35 files
├── Screens:        20
├── ViewModels:      8
├── Data:            5
└── Navigation:      2

Location Module:    ~12 files
├── Screens:         5
├── ViewModels:      3
├── Data:            3
└── Navigation:      1

Fundraiser Module:  ~15 files
├── Screens:         6
├── ViewModels:      4
├── Data:            4
└── Navigation:      1

Wallet Module:      ~22 files
├── Screens:        10
├── ViewModels:      5
├── Data:            5
└── Navigation:      2

QR Module:          ~15 files
├── Screens:         7
├── ViewModels:      3
├── Data:            4
└── Navigation:      1

App Module:          ~5 files
├── MainActivity:    1
├── Build files:     1
└── Manifest:        1

TOTAL:             ~149 files
```

---

End of Document
