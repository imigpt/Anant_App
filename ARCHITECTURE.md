# Production-Level Architecture Documentation

## Overview
This app follows **MVVM + Clean Architecture** principles with a themeable UI system designed for scalability.

## Architecture Layers

### 1. **Presentation Layer** (`presentation/`)
Handles UI and user interactions.

```
presentation/
├── screen/               # Composable screens
│   ├── HomeScreen.kt           # Wallet/banking screen
│   ├── BalanceScreen.kt        # Balance display screen
│   ├── DashboardScreen.kt      # Analytics screen
│   └── DashboardScreenNew.kt   # Refactored analytics (production)
│
└── viewmodel/            # State management
    ├── HomeScreenViewModel.kt
    ├── BalanceScreenViewModel.kt
    └── DashboardScreenViewModel.kt
```

### 2. **UI Layer** (`ui/`)
Reusable components and theming.

```
ui/
├── components/
│   └── CommonComponents.kt     # Shared UI elements:
│       ├── NavigationItem
│       ├── BottomNavigationBar
│       ├── ActionButton
│       ├── QuickActionCircle
│       ├── DecorativeCircle
│       └── GradientSurface
│
└── theme/
    └── ProductionTheme.kt      # Centralized theme system:
        ├── BankingTheme       # For wallet/home screens
        ├── DashboardTheme     # For analytics screens
        └── BalanceTheme       # For balance screens
```

## Theme System

The app supports **multiple themeable designs**:

### **BankingTheme**
- **Usage**: Home, Wallet, Transfer screens
- **Colors**: Red/Orange → Pink → Magenta → Purple → Blue gradient
- **Primary Accent**: Purple (#9C27B0)

```kotlin
BankingTheme.Colors.GradientStart  // Red
BankingTheme.Colors.GradientEnd    // Blue
BankingTheme.Colors.PrimaryAccent  // Purple
```

### **DashboardTheme**
- **Usage**: Analytics, Reports screens
- **Colors**: Dark background with white cards
- **Primary Accent**: Purple

### **BalanceTheme**
- **Usage**: Balance display, Simple info screens
- **Colors**: Pink → Magenta → Purple → Black gradient
- **Primary Accent**: Purple

### **Why This Approach?**
- Next 20 screens can use a **different theme** without code changes
- Each theme has consistent **Colors**, **Spacing**, **Radius**, **Elevation**
- Easy to maintain and extend

## State Management (MVVM)

Each screen has a dedicated ViewModel using Jetpack Compose's `StateFlow`:

```kotlin
// Example: HomeScreenViewModel
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    
    // UI reacts to state changes automatically
}
```

**State Class Example:**
```kotlin
data class HomeScreenState(
    val userName: String = "Mahendra",
    val walletBalance: Double = 10456.05,
    val selectedNavItem: String = "home",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
```

## Component Library

All reusable components are in `CommonComponents.kt`:

### **BottomNavigationBar**
```kotlin
BottomNavigationBar(
    selectedItem = "home",
    onHomeClick = {},
    onAnalyticsClick = {},
    onNotificationClick = {},
    onProfileClick = {},
    modifier = Modifier.align(Alignment.BottomCenter)
)
```

### **QuickActionCircle**
```kotlin
QuickActionCircle(
    icon = "⚡",
    label = "My Donations",
    onClick = {},
    borderColor = BankingTheme.Colors.Border,
    backgroundColor = BankingTheme.Colors.CardBackground
)
```

### **GradientSurface**
```kotlin
GradientSurface(
    modifier = Modifier.fillMaxWidth(),
    colors = listOf(color1, color2, color3)
) {
    // Content goes here
}
```

## Best Practices

### 1. **Always Use Theme Constants**
❌ **Bad:**
```kotlin
Box(modifier = Modifier.padding(24.dp))
```

✅ **Good:**
```kotlin
Box(modifier = Modifier.padding(BankingTheme.Spacing.Large))
```

### 2. **Create ViewModels for State Management**
❌ **Bad:**
```kotlin
@Composable
fun HomeScreen() {
    var balance by remember { mutableStateOf(0.0) }
}
```

✅ **Good:**
```kotlin
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
}
```

### 3. **Use Reusable Components**
❌ **Bad:** Defining navigation bar in every screen
✅ **Good:** Use `BottomNavigationBar()` component

### 4. **Document Public Functions**
```kotlin
/**
 * Production-level Home Screen
 * Manages wallet balance, quick actions, and navigation
 * Follows MVVM architecture with proper state management
 */
@Composable
fun HomeScreen(...)
```

## Scaling to New Features

### Adding a New Screen (e.g., TransactionHistory)

1. **Create ViewModel** (`presentation/viewmodel/TransactionHistoryViewModel.kt`):
```kotlin
data class TransactionHistoryState(
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = false
)

class TransactionHistoryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TransactionHistoryState())
    val uiState: StateFlow<TransactionHistoryState> = _uiState.asStateFlow()
}
```

2. **Create Screen** (`presentation/screen/TransactionHistoryScreen.kt`):
```kotlin
@Composable
fun TransactionHistoryScreen(
    viewModel: TransactionHistoryViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Use theme constants and components
    Box(modifier = Modifier
        .fillMaxSize()
        .background(BankingTheme.Colors.CardBackground)
    ) {
        // Your UI here
        BottomNavigationBar(...)
    }
}
```

3. **Add to Navigation** (MainActivity.kt):
```kotlin
is TransactionHistoryScreen -> {
    TransactionHistoryScreen(
        onHomeClick = { currentScreen.value = "home" },
        // ...
    )
}
```

## Testing

- **Unit Tests**: Test ViewModels in isolation
- **Compose Tests**: Test UI components
- **Integration Tests**: Test screen-to-screen navigation

## Performance Considerations

1. **Recomposition**: Use `remember` for expensive computations
2. **StateFlow**: Efficient state emission
3. **Lazy Layouts**: Use `LazyColumn`, `LazyRow` for large lists
4. **Image Loading**: Consider using `coil` or `Glide` with caching

## File Structure Summary

```
AnantApp/
├── app/src/main/java/com/example/anantapp/
│   ├── presentation/
│   │   ├── screen/
│   │   └── viewmodel/
│   ├── ui/
│   │   ├── components/
│   │   ├── theme/
│   │   ├── login/
│   │   ├── verify/
│   │   └── onboarding/
│   ├── data/        # Future: repositories, models
│   └── domain/      # Future: use cases, entities
│
└── build.gradle.kts
```

---

**Last Updated**: March 2026  
**Architecture Version**: 1.0  
**Team**: AnantApp Development Team
