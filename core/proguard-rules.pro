# Core Module ProGuard Rules
# Keep all public classes in the core module
-keep public class com.example.anantapp.core.** { *; }

# Keep UI components
-keep class com.example.anantapp.core.presentation.** { *; }

# Keep utility classes
-keep class com.example.anantapp.core.utils.** { *; }

# Keep ViewModels
-keepclasseswithmembernames class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}
