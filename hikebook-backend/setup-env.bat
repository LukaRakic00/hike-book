@echo off
REM ========================================
REM HikeBook Backend - Environment Setup Script
REM ========================================

echo 🏔️  HikeBook Backend - Environment Setup
echo ========================================

REM Proveri da li .env već postoji
if exist ".env" (
    echo ⚠️  .env fajl već postoji!
    set /p overwrite="Da li želite da ga prepišete? (y/N): "
    if /i not "%overwrite%"=="y" (
        echo ❌ Setup prekinut.
        exit /b 1
    )
)

REM Kopiraj template
echo 📋 Kopiram env.example u .env...
copy env.example .env >nul

if %errorlevel% equ 0 (
    echo ✅ .env fajl kreiran uspešno!
) else (
    echo ❌ Greška pri kreiranju .env fajla!
    exit /b 1
)

echo.
echo 🔧 Sada treba da popunite .env fajl sa svojim vrednostima:
echo.
echo 📝 Otvorite .env fajl u editoru:
echo    code .env
echo.
echo 🔐 Obavezno popunite:
echo    - DATABASE_URL
echo    - DATABASE_USERNAME
echo    - DATABASE_PASSWORD
echo    - JWT_SECRET
echo.
echo 📊 Logging varijable (opciono, imaju default vrednosti):
echo    - LOGGING_LEVEL_ROOT (default: INFO)
echo    - LOGGING_LEVEL_RS_HIKEANDBOOK (default: DEBUG)
echo    - LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_SECURITY (default: WARN)
echo.
echo 📚 Za detaljne instrukcije pogledajte:
echo    ENVIRONMENT_SETUP.md
echo.
echo 🚀 Nakon popunjavanja pokrenite:
echo    gradlew.bat bootRun --args="--spring.profiles.active=local"
echo.
echo ✅ Setup završen!
pause 