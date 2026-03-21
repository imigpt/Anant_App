## AnantApp - Development Guidelines & Best Practices

**Last Updated**: March 21, 2026  
**Project**: AnantApp (Production-Level Fundraising App)  
**Architecture**: MVVM + Clean Architecture  
**Framework**: Jetpack Compose  

---

## 1. CODE STRUCTURE & ORGANIZATION

### Folder Naming Conventions

```
✅ Correct Pattern:
- data/model/              (lowercase, descriptive)
- data/repository/         
- presentation/screen/     
- presentation/viewmodel/  
- ui/components/           
- ui/theme/

❌ Avoid:
- DataModels/              (PascalCase for folders)
- src/main/models          (redundant depth)
- Components/ui/           (reversed hierarchy)
```

### File Naming Conventions

```
✅ Correct:
- HomeScreen.kt            (Composable screens)
- HomeScreenViewModel.kt   (ViewModel suffix)
- HomeRepository.kt        (Repository suffix)
- HomeState.kt             (State data class)
- CommonComponents.kt      (Multiple components)
- Color.kt                 (Theme files)

❌ Avoid:
- home_screen.kt           (snake_case)
- ViewModelHome.kt         (reverse order)
- homeScreen.kt            (camelCase first letter)
```

---

## 2. IMPLEMENTING A NEW SCREEN

### Step-by-Step Guide

#### 1. Create Data Model (if API integration needed)
**File**: `data/model/[Feature]State.kt`

```kotlin
package com.example.anantapp.data.model

data class PaymentState(
    val selectedMethod: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val paymentHistory: List<Payment> = emptyList()
)

data class Payment(
    val id: String,
    val amount: Double,
    val date: String,
    val status: String
)
```

#### 2. Create Repository (if data operations needed)
**File**: `data/repository/[Feature]Repository.kt`

```kotlin
package com.example.anantapp.data.repository

class PaymentRepository {
    suspend fun getPaymentMethods(): Result<List<Payment>> {
        return try {
            val methods = apiService.fetchMethods()
            Result.success(methods)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

#### 3. Create ViewModel
**File**: `presentation/viewmodel/[Feature]ViewModel.kt`

```kotlin
package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PaymentMethodViewModel : ViewModel() {
    private val repository = PaymentRepository()
    
    private val _uiState = MutableStateFlow(PaymentMethodState())
    val uiState: StateFlow<PaymentMethodState> = _uiState.asStateFlow()
    
    init {
        loadPaymentMethods()
    }
    
    private fun loadPaymentMethods() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = repository.getPaymentMethods()
            result.onSuccess { methods ->
                _uiState.update { 
                    it.copy(
                        paymentHistory = methods,
                        isLoading = false
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        error = error.message,
                        isLoading = false
                    )
                }
            }
        }
    }
    
    fun selectMethod(methodId: String) {
        _uiState.update { it.copy(selectedMethod = methodId) }
    }
}

data class PaymentMethodState(
    val selectedMethod: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val paymentHistory: List<Payment> = emptyList()
)
```

#### 4. Create Screen (Composable)
**File**: `presentation/screen/PaymentMethodScreen.kt`

```kotlin
package com.example.anantapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.presentation.viewmodel.PaymentMethodViewModel
import com.example.anantapp.ui.theme.BankingTheme

@Composable
fun PaymentMethodScreen(
    onBackClick: () -> Unit = {},
    onMethodSelected: (methodId: String) -> Unit = {},
    viewModel: PaymentMethodViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(BankingTheme.Spacing.Medium)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = BankingTheme.Spacing.Large),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Select Payment Method",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = BankingTheme.Colors.TextPrimary
                )
            }
            
            // Loading State
            if (uiState.isLoading) {
                Text("Loading methods...")
            }
            
            // Error State
            uiState.error?.let { error ->
                Text(
                    text = "Error: $error",
                    color = BankingTheme.Colors.ErrorRed
                )
            }
            
            // Payment Methods List
            uiState.paymentHistory.forEach { method ->
                PaymentMethodItem(
                    payment = method,
                    isSelected = method.id == uiState.selectedMethod,
                    onClick = {
                        viewModel.selectMethod(method.id)
                        onMethodSelected(method.id)
                    }
                )
            }
        }
    }
}

@Composable
fun PaymentMethodItem(
    payment: Payment,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected) 
                    BankingTheme.Colors.GradientPurple 
                else 
                    Color.White,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(
                    BankingTheme.Radius.Medium
                )
            )
            .clickable { onClick() }
            .padding(BankingTheme.Spacing.Medium)
    ) {
        Column {
            Text(
                text = payment.id,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else BankingTheme.Colors.TextPrimary
            )
            Text(
                text = "₹${payment.amount}",
                color = if (isSelected) Color.White else BankingTheme.Colors.TextSecondary
            )
        }
    }
}
```

---

## 3. THEMING BEST PRACTICES

### Color Usage
```kotlin
// ✅ CORRECT: Use theme colors
Text(
    text = "Balance",
    color = BankingTheme.Colors.TextPrimary
)

// ❌ WRONG: Hardcoded colors
Text(
    text = "Balance",
    color = Color(0xFF1F2937)  // Never do this
)
```

### Spacing Usage
```kotlin
// ✅ CORRECT: Use spacing tokens
.padding(BankingTheme.Spacing.Medium)
.padding(horizontal = BankingTheme.Spacing.Large)

// ❌ WRONG: Arbitrary values
.padding(16.dp)
.padding(horizontal = 24.dp)
```

### Typography Usage
```kotlin
// ✅ CORRECT: Use Material 3 typography
Text(
    text = "Welcome",
    style = MaterialTheme.typography.titleLarge,
    color = BankingTheme.Colors.TextPrimary
)

// ❌ WRONG: Hardcoded text properties
Text(
    text = "Welcome",
    fontSize = 28.sp,
    fontWeight = FontWeight.Bold
)
```

---

## 4. STATE MANAGEMENT PATTERNS

### Proper StateFlow Usage

```kotlin
// ✅ CORRECT: Immutable StateFlow pattern
class MyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyState())
    val uiState: StateFlow<MyState> = _uiState.asStateFlow()
    
    fun updateState(newValue: String) {
        _uiState.update { currentState ->
            currentState.copy(value = newValue)
        }
    }
}

// ❌ WRONG: Mutable state exposed
class BadViewModel : ViewModel() {
    val uiState = MutableStateFlow(MyState())  // Never expose MutableStateFlow
}
```

### Data Class Pattern

```kotlin
// ✅ CORRECT: Proper UI state structure
data class HomeScreenState(
    val isLoading: Boolean = false,
    val walletBalance: Double = 0.0,
    val transactions: List<Transaction> = emptyList(),
    val errorMessage: String? = null,
    val selectedTransaction: String? = null
)

// ❌ WRONG: Mixing concerns
data class State(
    val data: Any,  // Too generic
    val status: Int  // Ambiguous status codes
)
```

---

## 5. COMPOSABLE BEST PRACTICES

### Screen Composable Structure

```kotlin
@Composable
fun MyScreen(
    // Parameters first (callbacks and viewModel)
    onBackClick: () -> Unit = {},
    onNavigateToDetail: (id: String) -> Unit = {},
    viewModel: MyViewModel = viewModel()
) {
    // Collect state at the top
    val uiState by viewModel.uiState.collectAsState()
    
    // Layout structure
    Column(modifier = Modifier.fillMaxSize()) {
        // Content
    }
}
```

### Reusable Components

```kotlin
// ✅ CORRECT: Stateless, parameter-driven components
@Composable
fun CardButton(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .background(/*...*/)
    ) {
        Row {
            Icon(imageVector = icon)
            Text(text = title)
        }
    }
}

// Usage
CardButton(
    title = "Settings",
    icon = Icons.Default.Settings,
    onClick = { navigateToSettings() }
)

// ❌ WRONG: Stateful components
@Composable
fun BadCardButton() {
    var isClicked by remember { mutableStateOf(false) }
    // Contains internal logic
}
```

---

## 6. NAVIGATION SETUP (Recommended)

### NavigationHost Setup (Currently Missing)

```kotlin
// MainActivity.kt
@Composable
fun MainContent() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToBalance = {
                    navController.navigate("balance")
                }
            )
        }
        
        composable("balance") {
            BalanceScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
```

---

## 7. ERROR HANDLING

### ViewModel Error Handling

```kotlin
// ✅ CORRECT: Proper error flow
private fun loadData() {
    _uiState.update { it.copy(isLoading = true, error = null) }
    
    viewModelScope.launch {
        try {
            val data = repository.fetchData()
            _uiState.update { 
                it.copy(
                    data = data,
                    isLoading = false
                ) 
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    error = e.message ?: "Unknown error",
                    isLoading = false
                )
            }
        }
    }
}

// UI Display
if (uiState.error != null) {
    ErrorDialog(message = uiState.error)
}
```

---

## 8. REPOSITORY PATTERN

### Proper Repository Implementation

```kotlin
// ✅ CORRECT: Repository with error handling
class UserRepository {
    suspend fun getUser(id: String): Result<User> = withContext(Dispatchers.IO) {
        return@withContext try {
            val user = apiService.fetchUser(id)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// Usage in ViewModel
private fun loadUser() {
    viewModelScope.launch {
        val result = repository.getUser("123")
        result.onSuccess { user ->
            // Handle success
        }.onFailure { error ->
            // Handle error
        }
    }
}
```

---

## 9. MODULE ORGANIZATION (Future)

### Suggested Project Growth Structure

```
app/
├── data/
│   ├── auth/
│   │   ├── model/
│   │   └── repository/
│   ├── payment/
│   │   ├── model/
│   │   └── repository/
│   └── common/
│       └── network/
│
├── presentation/
│   ├── auth/
│   │   ├── screen/
│   │   └── viewmodel/
│   ├── payment/
│   │   ├── screen/
│   │   └── viewmodel/
│   └── common/
│
├── domain/  (Future - separate layer for use cases)
│   ├── auth/
│   ├── payment/
│   └── common/
│
└── ui/
    ├── components/
    ├── theme/
    └── icons/
```

---

## 10. TESTING GUIDELINES

### ViewModel Testing

```kotlin
// ✅ CORRECT: Unit test structure
@RunWith(AndroidRunner::class)
class HomeScreenViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    
    private lateinit var viewModel: HomeScreenViewModel
    
    @Before
    fun before() {
        viewModel = HomeScreenViewModel()
    }
    
    @Test
    fun testInitialStateLoadsData() {
        val state = viewModel.uiState.value
        assertEquals(true, state.isLoading)
    }
}
```

### Composable Testing

```kotlin
// ✅ CORRECT: UI test structure
@RunWith(AndroidRunner::class)
class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun testBalanceDisplayed() {
        composeTestRule.setContent {
            HomeScreen()
        }
        composeTestRule.onNodeWithText("Balance").assertIsDisplayed()
    }
}
```

---

## 11. PERFORMANCE OPTIMIZATION

### Remember Usage

```kotlin
// ✅ CORRECT: Remember expensive computations
@Composable
fun ExpensiveScreen() {
    val expensiveCalculation = remember {
        calculateExpensiveValue()
    }
}

// ❌ WRONG: Recalculates on every recomposition
@Composable
fun BadScreen() {
    val expensiveCalculation = calculateExpensiveValue()
}
```

### LazyColumn for Lists

```kotlin
// ✅ CORRECT: Lazy loading for large lists
LazyColumn {
    items(largeList.size) { index ->
        ListItem(item = largeList[index])
    }
}

// ❌ WRONG: Creates all items at once
Column {
    largeList.forEach { item ->
        ListItem(item = item)
    }
}
```

---

## 12. COMMON PITFALLS TO AVOID

| Pitfall | Problem | Solution |
|---------|---------|----------|
| Hardcoded colors | Breaks theming | Use `BankingTheme.Colors.*` |
| Hardcoded spacing | Inconsistent UI | Use `BankingTheme.Spacing.*` |
| Mutable state in VM | Race conditions | Use `update { }` pattern |
| Side effects in composables | Performance issues | Use `LaunchedEffect` |
| Exposing MutableStateFlow | State mutation outside VM | Export as `StateFlow` |
| No error handling | Crashes | Add try-catch in repositories |
| Mixing concerns in VM | Hard to test | Keep VM and Repository separate |
| Recreating objects in recompose | Memory leaks | Use `remember {}` |

---

## 13. CODE REVIEW CHECKLIST

Before submitting code for review:

- [ ] Follows naming conventions (camelCase for variables, PascalCase for classes)
- [ ] Uses theme tokens (colors, spacing, typography)
- [ ] StateFlow properly immutable
- [ ] No hardcoded values (colors, strings, sizes)
- [ ] Proper error handling with try-catch
- [ ] Repository methods marked `suspend`
- [ ] ViewModel uses `viewModelScope`
- [ ] Composables stateless and reusable
- [ ] No memory leaks (proper scope usage)
- [ ] Tests written for business logic
- [ ] No console output (use Timber in production)

---

## 14. USEFUL SHORTCUTS & PATTERNS

### Quick Data Class
```kotlin
data class MyState(
    val isLoading: Boolean = false,
    val data: String = "",
    val error: String? = null
)
```

### Quick ViewModel Template
```kotlin
class MyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyState())
    val uiState: StateFlow<MyState> = _uiState.asStateFlow()
    
    fun updateData(newData: String) {
        _uiState.update { it.copy(data = newData) }
    }
}
```

### Quick Composable Template
```kotlin
@Composable
fun MyScreen(
    onNavigate: () -> Unit = {},
    viewModel: MyViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(modifier = Modifier.fillMaxSize()) {
        // Content
    }
}
```

---

## 15. RESOURCES & REFERENCES

- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Android Architecture Components](https://developer.android.com/jetpack/guide)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [StateFlow vs LiveData](https://developer.android.com/kotlin/flow)

---

## Summary

Follow these guidelines for:
- ✅ Consistent, clean code
- ✅ Maintainable architecture
- ✅ Scalable project structure
- ✅ Easy team collaboration
- ✅ Production-ready quality

Questions? Refer back to the THEME_SYSTEM.md or FILE_STRUCTURE_AUDIT.md for architecture details.
