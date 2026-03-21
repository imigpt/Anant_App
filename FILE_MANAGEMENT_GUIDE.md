# 🚀 Anant App - File Management Guide

## ✅ Current Status (After Cleanup)

**Duplicates Found & Fixed:**
- ✓ Old VerifyScreen.kt removed from `ui/components/`
- ✓ IDE history files will be cleaned (183 backups)
- ✓ Build cache will be removed
- ✓ No duplicate source code

**Clean Project Structure:**
```
app/src/main/java/
├── com/example/anantapp/
│   ├── ui/
│   │   ├── theme/        (Colors, Typography)
│   │   ├── components/   (Reusable UI Components)
│   │   ├── onboarding/   (OnboardingScreen, ViewModel)
│   │   ├── login/        (LoginScreen, ViewModel)
│   │   └── verify/       (VerifyScreen, VerifyBank, etc.)
│   ├── data/
│   │   ├── model/        (State classes)
│   │   └── repository/   (Business logic)
│   ├── presentation/     (New screens: EnableLocation, FamilyDetails)
│   └── MainActivity.kt   (Entry point)
```

---

## 🛡️ Rules to Prevent File Duplicates

### 1️⃣ **One Screen Component = One Location**
Files should exist in ONLY one place:
```
✗ BAD (Don't do this):
  - app/src/main/java/com/example/anantapp/ui/components/VerifyScreen.kt
  - app/src/main/java/com/example/anantapp/ui/verify/VerifyScreen.kt

✓ GOOD (Do this):
  - app/src/main/java/com/example/anantapp/ui/verify/VerifyScreen.kt (ONE version, latest)
```

### 2️⃣ **Screen = Package Organization**
```
ScreenName Structure:
├── ScreenName.kt           (UI Layout)
├── ScreenNameViewModel.kt  (State Management)
└── (Optional) ScreenNameRepository.kt (Data Access)

Example (Verify Flow):
├── VerifyScreen.kt               ✓
├── VerifyViewModel.kt            ✓
├── VerifyBankScreen.kt           ✓
├── VerifyBankViewModel.kt        ✓
├── VerifyAddressScreen.kt        ✓
├── VerifyAddressViewModel.kt     ✓
└── PhotoUploadScreen.kt          ✓
```

### 3️⃣ **Naming Conventions**
Keep names consistent and meaningful:
```
✓ Good Names           ✗ Bad Names
EnableLocationScreen   EnableScreen2, LocationScreen1
VerifyBankScreen      VerifyScreen2, BankVerifyScreen_OLD
PhotoUploadViewModel  upload_viewmodel, PhotoVM
```

### 4️⃣ **Use AndroidStudio's Refactor Feature**
If moving/renaming files: **Right-click → Refactor → Move/Rename**
- Automatically updates all imports
- Prevents accidental duplicates

### 5️⃣ **Cleanup Regularly**
```bash
# Before major commits or releases:
./cleanup.sh

# Or manually in Android Studio:
# Build → Clean Project
# Then File → Invalidate Caches... → Invalidate and Restart
```

---

## 📊 Duplicate Detection Commands

### Check for duplicate Kotlin files by name:
```bash
find app/src/main/java -type f -name "*.kt" | xargs basename -a | sort | uniq -d
```

### Check for duplicate resources:
```bash
find app/src/main/res -type f -name "*.xml" | xargs basename -a | sort | uniq -d
```

### Check entire project size:
```bash
du -sh . # Full project
du -sh .history/ # IDE backups
du -sh build/ # Build cache
du -sh .gradle/ # Gradle cache
```

---

## 🗑️ Files/Folders to ALWAYS Ignore/Delete

### IDE Generated (Never commit):
```
.idea/                          # IDE settings
.history/                       # IDE history backups
*.iml                           # IDE project files
local.properties               # Local machine settings
```

### Build Generated (Auto-recreated, don't commit):
```
build/                         # Build outputs
app/build/                     # App build
.gradle/                       # Gradle cache
*.apk                          # Compiled APK
*.aab                          # Bundle
```

### Should be in .gitignore:
```
# In your .gitignore file:
.idea/
.history/
*.iml
build/
.gradle/
local.properties
*.apk
*.aab
```

---

## ✅ Checklist Before Each Release

- [ ] Run lint: `./gradlew lint`
- [ ] Clean project: `./cleanup.sh` (if needed)
- [ ] Search for duplicate files: See "Duplicate Detection Commands" above
- [ ] Check file naming is consistent
- [ ] All ViewModels in same package as their Screen
- [ ] No "OLD", "_backup", or version numbers in filenames
- [ ] No duplicate drawable resources (except mipmap densities - those are normal)
- [ ] Build succeeds: `./gradlew build`
- [ ] No build warnings

---

## 📝 Current Project Statistics

**Source Code:**
- 36 Kotlin files (No duplicates ✓)
- 14 UI screens + components
- 13 Data models/repositories
- 1 Theme configuration

**Resources:**
- 23 drawable files (icons, vectors)
- 10 launcher icons (5 densities each - NORMAL)
- 3 image backgrounds

**Build Output (temporary, auto-removed by cleanup.sh):**
- Build intermediates
- IDE history backups

---

## 🎯 Next Steps

1. Run cleanup.sh to remove build cache and IDE history
2. Commit these changes (with .gitignore update if needed)
3. Share this guide with your team
4. Use it as reference for future development
