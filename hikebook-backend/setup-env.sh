#!/bin/bash

# ========================================
# HikeBook Backend - Environment Setup Script
# ========================================

echo "🏔️  HikeBook Backend - Environment Setup"
echo "========================================"

# Proveri da li .env već postoji
if [ -f ".env" ]; then
    echo "⚠️  .env fajl već postoji!"
    read -p "Da li želite da ga prepišete? (y/N): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        echo "❌ Setup prekinut."
        exit 1
    fi
fi

# Kopiraj template
echo "📋 Kopiram env.example u .env..."
cp env.example .env

if [ $? -eq 0 ]; then
    echo "✅ .env fajl kreiran uspešno!"
else
    echo "❌ Greška pri kreiranju .env fajla!"
    exit 1
fi

echo ""
echo "🔧 Sada treba da popunite .env fajl sa svojim vrednostima:"
echo ""
echo "📝 Otvorite .env fajl u editoru:"
echo "   code .env"
echo ""
echo "🔐 Obavezno popunite:"
echo "   - DATABASE_URL"
echo "   - DATABASE_USERNAME" 
echo "   - DATABASE_PASSWORD"
echo "   - JWT_SECRET"
echo ""
echo "📊 Logging varijable (opciono, imaju default vrednosti):"
echo "   - LOGGING_LEVEL_ROOT (default: INFO)"
echo "   - LOGGING_LEVEL_RS_HIKEANDBOOK (default: DEBUG)"
echo "   - LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_SECURITY (default: WARN)"
echo ""
echo "📚 Za detaljne instrukcije pogledajte:"
echo "   ENVIRONMENT_SETUP.md"
echo ""
echo "🚀 Nakon popunjavanja pokrenite:"
echo "   ./gradlew bootRun --args='--spring.profiles.active=local'"
echo ""
echo "✅ Setup završen!" 