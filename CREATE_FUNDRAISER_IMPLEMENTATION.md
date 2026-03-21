# Create Fundraiser Feature Implementation

## Overview
Complete MVVM implementation for the "Create new Fundraiser" feature with full integration including data models, state management, repository pattern, and navigation setup.

## Files Created/Modified

### 1. **Data Layer**

#### `CreateFundraiserState.kt` (Data Models)
Location: `app/src/main/java/com/example/anantapp/data/model/CreateFundraiserState.kt`

Data classes and state containers:
- **FundraiserPhoto**: Represents individual photos in the gallery
  - Properties: `id`, `uri`, `isSelected`
  
- **CreateFundraiserState**: Main state holder for the entire form
  - Properties: `title`, `shortDescription`, `fullStory`, `selectedCategory`, `photos`, `countryCode`, `phoneNumber`, `isLoading`, `error`, `isDraft`, `isFormValid`
  
- **FundraiserCategoryItem**: Individual category representation
  - Properties: `id`, `name`
  
- **FundraiserCategories**: Companion object with 6 predefined categories
  - Categories: Medical, Education, Disaster, Community, Emergency, Other
  
- **CountryCode**: Country representation
  - Properties: `code` (e.g., "+91"), `name`, `flag`
  
- **CountryCodes**: Companion object with 5 countries
  - Countries: India (+91), USA (+1), UK (+44), China (+86), Japan (+81)

#### `CreateFundraiserRepository.kt` (Repository)
Location: `app/src/main/java/com/example/anantapp/data/repository/CreateFundraiserRepository.kt`

Handles all data operations:
- **saveDraft()**: Saves fundraiser as draft (local database)
- **submitFundraiser()**: Submits fundraiser to backend API
- **getDraft()**: Retrieves previously saved draft
- **uploadPhoto()**: Uploads individual photo to server
- **validateFundraiserData()**: Validates form completion and returns ValidationResult
- **ValidationResult**: Sealed class for success/error states

### 2. **Presentation Layer**

#### `CreateFundraiserViewModel.kt` (State Management)
Location: `app/src/main/java/com/example/anantapp/presentation/viewmodel/CreateFundraiserViewModel.kt`

ViewModel with repository injection:
- **Dependency**: Injected `CreateFundraiserRepository` (default initialized)
- **State**: Exposes `uiState: StateFlow<CreateFundraiserState>` with private `_uiState` MutableStateFlow

Methods:
- **updateTitle()**: Updates title with validation
- **updateShortDescription()**: Updates short description
- **updateFullStory()**: Updates full story details
- **updateCategory()**: Changes fundraiser category
- **updatePhoneNumber()**: Updates phone (validates 10+ digits)
- **updateCountryCode()**: Changes country code selector
- **addPhoto()**: Adds photo to gallery with unique timestamp ID
- **removePhoto()**: Removes photo by ID
- **saveDraft()**: Async operation calling repository.saveDraft()
- **proceedNext()**: Validates form and calls repository.submitFundraiser()
- **clearError()**: Clears error message from state

Validation Logic:
- Title: Must not be blank
- Short Description: Must not be blank
- Full Story: Must not be blank
- Phone: Minimum 10 digits
- Photos: Not required for draft, required for submission

#### `CreateFundraiserScreen.kt` (UI Composable)
Location: `app/src/main/java/com/example/anantapp/presentation/screen/CreateFundraiserScreen.kt`

Complete UI with proper MVVM integration:

**Function Signature**:
```kotlin
@Composable
fun CreateFundraiserScreen(
    viewModel: CreateFundraiserViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onDraftSaved: () -> Unit = {},
    onFundraiserCreated: (fundraiserId: String) -> Unit = {}
)
```

**UI Sections**:
1. **Header**: Back arrow + "Create new Fundraiser" title with onBackClick callback
2. **Photo Gallery**: 
   - UploadPhotoButton with onClick handler
   - Horizontal scrollable photo thumbnails with remove buttons
   - Each thumbnail shows image and X button for removal
3. **Fundraiser Details**:
   - Title input (purple background, single line)
   - Short Description (2-line text area)
   - Full Story (5-line text area)
   - Category dropdown (6 options from FundraiserCategories)
4. **Contact Details**:
   - Country code selector (5 options from CountryCodes)
   - Phone number input (number keyboard type)
5. **Action Buttons**:
   - Draft button (white outline, purple icon/text) → calls `viewModel.saveDraft()` then `onDraftSaved()`
   - Next button (purple fill, white text) → calls `viewModel.proceedNext()` then `onFundraiserCreated()`
6. **Error Display**: Shows error message from uiState.error

**Reusable Components**:
- **UploadPhotoButton**: Clickable box with + icon for adding photos
- **PhotoThumbnail**: Displays photo with remove button overlay

**ViewModel Integration**: Uses `collectAsState()` to subscribe to uiState StateFlow

#### `SelectFundraiserCategoryScreen.kt` (Updated)
Location: `app/src/main/java/com/example/anantapp/presentation/screen/SelectFundraiserCategoryScreen.kt`

Already implements MVVM properly with:
- Uses SelectFundraiserCategoryViewModel for state management
- Subscribes to uiState via collectAsState()
- Callbacks: `onBackClick()`, `onNextClick(selectedCategory, customTitle)`

### 3. **Navigation Layer** (NEW)

#### `FundraiserNavigation.kt` (Navigation Routes)
Location: `app/src/main/java/com/example/anantapp/navigation/FundraiserNavigation.kt`

Navigation setup:
- **FundraiserRoutes** object with constants:
  - `SELECT_CATEGORY = "fundraiser/select_category"`
  - `CREATE_FUNDRAISER = "fundraiser/create"`

- **fundraiserNavigation()** NavGraphBuilder extension:
  - Defines navigation for SELECT_CATEGORY route
  - Defines navigation for CREATE_FUNDRAISER route
  - Handles screen transitions and backstack operations

- **Helper Functions**:
  - `navigateToFundraiserFlow()`: Navigate to category selection
  - `navigateToCreateFundraiser()`: Navigate directly to create screen

**Integration Usage**:
```kotlin
NavHost(navController, startDestination = "home") {
    fundraiserNavigation(navController)
    // Other routes...
}
```

## Architecture Patterns Applied

### MVVM Pattern
- **Model**: CreateFundraiserState data classes in data/model/
- **View**: CreateFundraiserScreen composable in presentation/screen/
- **ViewModel**: CreateFundraiserViewModel in presentation/viewmodel/

### Repository Pattern
- CreateFundraiserRepository abstracts data sources (local DB, API)
- Repository handles async operations (suspend functions)
- ViewModel delegates to repository for business logic

### State Management
- **StateFlow** for immutable state exposure
- **MutableStateFlow** private for internal updates
- **collectAsState()** in composables for reactive UI updates

### Validation
- Form validation in ViewModel's `isFormValid()` method
- Validation result persisted in state
- Error messages displayed in UI from state

### Dependency Injection
- Repository injected into ViewModel constructor (default instance)
- ViewModel provided by viewModel() in composable (can be replaced for testing)
- Prepared for Hilt integration (remove default parameters for Hilt @Inject)

## File Structure
```
app/src/main/java/com/example/anantapp/
├── data/
│   ├── model/
│   │   └── CreateFundraiserState.kt (NEW)
│   └── repository/
│       └── CreateFundraiserRepository.kt (NEW)
├── presentation/
│   ├── screen/
│   │   ├── CreateFundraiserScreen.kt (NEW)
│   │   └── SelectFundraiserCategoryScreen.kt (UPDATED)
│   └── viewmodel/
│       ├── CreateFundraiserViewModel.kt (NEW - with repository)
│       └── SelectFundraiserCategoryViewModel.kt
└── navigation/
    └── FundraiserNavigation.kt (NEW)
```

## Integration Steps

### 1. Connect to NavHost
In your MainActivity or NavGraph:
```kotlin
NavHost(navController = navController, startDestination = "home") {
    fundraiserNavigation(navController)
}
```

### 2. Navigate from existing screens:
```kotlin
// From any screen
navController.navigateToFundraiserFlow()
// or directly to create
navController.navigateToCreateFundraiser()
```

### 3. Set up Dependency Injection (Optional - for Hilt)
Remove default parameters:
```kotlin
class CreateFundraiserViewModel @Inject constructor(
    private val repository: CreateFundraiserRepository
) : ViewModel()
```

## TODO / Future Implementation

### Priority 1: Network Integration
- [ ] Implement Retrofit API client for submitFundraiser()
- [ ] Add OkHttp interceptors for authentication
- [ ] Create API request/response DTOs
- [ ] Map between domain models and API models

### Priority 2: Photo Upload
- [ ] Implement Activity Result Contract for photo picker
- [ ] Add gallery/camera permission handling
- [ ] Implement uploadPhoto() with multipart form data
- [ ] Add photo compression before upload

### Priority 3: Database Integration
- [ ] Implement Room database for draft storage
- [ ] Add migration handling for schema changes
- [ ] Implement getDraft() database query
- [ ] Add draft recovery on app restart

### Priority 4: Enhanced Error Handling
- [ ] Add retry logic for failed submissions
- [ ] Implement network error specific messages
- [ ] Add loading UI indicators
- [ ] Implement timeout handling

### Priority 5: Form Enhancement
- [ ] Add real-time validation feedback
- [ ] Implement keyboard dismissal on successful action
- [ ] Add photo size/format validation
- [ ] Implement form pre-filling from draft

## Testing Recommendations

### Unit Tests
- Test CreateFundraiserViewModel state mutations
- Test validation logic for all fields
- Mock repository for ViewModel tests

### Integration Tests
- Test navigation flow between screens
- Test ViewModel integration with repository
- Test state persistence across configuration changes

### UI Tests
- Test screen rendering with different states
- Test button click handlers
- Test dropdown menu selection
- Test form input changes

## Code Quality Notes

✅ **Implemented**:
- Proper MVVM separation of concerns
- Immutable StateFlow for thread-safe state
- Repository pattern for data abstraction
- Coroutine integration with viewModelScope
- Type-safe navigation routes
- Reusable composable components
- Comprehensive data validation

⚠️ **Considerations**:
- Photo upload UI implemented but onClick is TODO
- Repository methods have TODO comments for API/DB integration
- Default ViewModel instantiation - ready for Hilt conversion
- Photography permissions not yet requested

## Integration Checklist

- [ ] Copy all 5 new files to project
- [ ] Update existing CreateFundraiserScreen imports if needed
- [ ] Add FundraiserNavigation to your main NavHost
- [ ] Verify compilation succeeds
- [ ] Test navigation between screens
- [ ] Implement repository API methods
- [ ] Connect photo picker to UploadPhotoButton
- [ ] Migrate to Hilt for dependency injection
- [ ] Add unit tests for ViewModel
- [ ] Add UI tests for screen rendering
