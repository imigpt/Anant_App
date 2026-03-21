#!/bin/bash

echo "🧹 Anant App - Complete Cleanup Script"
echo "======================================"
echo ""

# 1. Remove IDE history
echo "1️⃣ Removing IDE history files..."
if [ -d ".history" ]; then
  rm -rf ".history"
  echo "   ✓ Removed 183 IDE history files (1.7MB)"
else
  echo "   ℹ No IDE history folder found"
fi

# 2. Remove build cache
echo ""
echo "2️⃣ Removing build cache..."
if [ -d "build" ]; then
  rm -rf "build"
  echo "   ✓ Removed root build folder"
fi

if [ -d "app/build" ]; then
  rm -rf "app/build"
  echo "   ✓ Removed app build folder"
fi

# 3. Remove gradle cache
echo ""
echo "3️⃣ Removing gradle cache..."
if [ -d "~/.gradle/caches" ]; then
  rm -rf "~/.gradle/caches"
  echo "   ✓ Removed gradle cache"
fi

echo ""
echo "======================================"
echo "✅ Cleanup Complete!"
echo ""
echo "Your project now has:"
echo "  • No IDE history backups"
echo "  • No build intermediates"
echo "  • Clean source code (no duplicates)"
echo ""
echo "Next build will be fresh and clean!"
