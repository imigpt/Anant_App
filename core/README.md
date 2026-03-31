# Core Module

This is the core module containing shared components, utilities, and base classes used across all feature modules.

## Structure

```
core/
├── src/main/java/com/example/anantapp/core/
│   ├── presentation/
│   │   ├── theme/           # Theme files (Color, Typography, Theme)
│   │   ├── base/            # Base ViewModels
│   │   └── components/      # Shared UI components (Buttons, Cards, etc.)
│   ├── data/
│   │   ├── api/             # API client and services
│   │   ├── model/           # Common data models
│   │   └── local/           # Local storage (SharedPreferences, DataStore)
│   └── utils/               # Utility functions and constants
├── build.gradle.kts         # Module dependencies
└── README.md               # This file
```

## What's Included

### Theme System (`presentation/theme/`)
- **Color.kt** - Central color palette
- **Type.kt** - Typography definitions
- **Theme.kt** - Main theme composable with dark/light mode support

### Base Classes (`presentation/base/`)
- **BaseViewModel.kt** - Abstract ViewModel with state management
  - Provides UI state and event management
  - StateFlow for UI state
  - SharedFlow for UI events

### Shared Components (`presentation/components/`)
- **Buttons.kt** - Gradient and outline buttons
- More components can be added here

### Utilities (`utils/`)
- **Constants.kt** - App-wide constants
- **ValidationUtils.kt** - Common validation functions

## Usage

### Using Theme
```kotlin
import com.example.anantapp.core.presentation.theme.AnantAppTheme

@Composable
fun MyApp() {
    AnantAppTheme {
        // Your content here
    }
}
```

### Using BaseViewModel
```kotlin
import com.example.anantapp.core.presentation.base.BaseViewModel

data class HomeUiState(val loading: Boolean = false)

sealed class HomeUiEvent {
    object LoadData : HomeUiEvent()
}

class HomeViewModel : BaseViewModel<HomeUiState, HomeUiEvent>(
    initialState = HomeUiState()
) {
    fun loadData() {
        updateState { it.copy(loading = true) }
    }
}
```

### Using Shared Components
```kotlin
import com.example.anantapp.core.presentation.components.GradientButton

GradientButton(
    text = "Click Me",
    onClick = { /* Handle click */ }
)
```

## Dependencies

All feature modules should depend on the core module:

```kotlin
dependencies {
    implementation(project(":core"))
}
```

## Building the Core Module

```bash
./gradlew :core:build
```

## Testing

```bash
./gradlew :core:test
./gradlew :core:androidTest
```

---

**Created**: March 31, 2026  
**Status**: Active  
**Owner**: Engineering Team
