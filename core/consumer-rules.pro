# Consumer ProGuard Rules for Core Module
# Keep Android support library classes
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# Keep Compose classes
-keep class androidx.compose.** { *; }
