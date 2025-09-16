# 🔧 Logging Problem Fix - HikeBook Backend

## 🚨 Problem

Greška pri pokretanju aplikacije:
```
Error: failed to convert java.lang.String to org.springframework.boot.logging.LogLevel
```

## ✅ Rešenje

### 1. **Kreirajte .env fajl**
```bash
# Kopirajte template
cp env.example .env
```

### 2. **Popunite .env fajl sa svojim vrednostima**
```bash
# Otvorite .env fajl
code .env
```

**Obavezno popunite:**
```bash
# Database
DATABASE_URL=jdbc:postgresql://localhost:5432/hikebook_db
DATABASE_USERNAME=your_username
DATABASE_PASSWORD=your_password

# JWT
JWT_SECRET=your-super-secret-jwt-key-here-make-it-long-and-secure

# Logging (opciono, imaju default vrednosti)
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_RS_HIKEANDBOOK=DEBUG
LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_SECURITY=WARN
```

### 3. **Pokrenite aplikaciju**
```bash
./gradlew bootRun --args='--spring.profiles.active=local'
```

## 🔍 Validne Logging Vrednosti

- `INFO` - Informacioni logovi
- `DEBUG` - Debug logovi (najviše detalja)
- `ERROR` - Samo greške
- `WARN` - Upozorenja i greške
- `TRACE` - Sve logove
- `OFF` - Isključi logging

## 📝 Primer .env fajla

```bash
# ========================================
# HikeBook Backend Environment Variables
# ========================================

# DATABASE CONFIGURATION
DATABASE_URL=jdbc:postgresql://localhost:5432/hikebook_db
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=your_password

# SERVER CONFIGURATION
SERVER_PORT=8080

# JWT CONFIGURATION
JWT_SECRET=your_jwt_secret_key_here_make_it_long_and_secure
JWT_EXPIRATION=86400000

# CLOUDINARY CONFIGURATION (opciono)
CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret

# LOGGING CONFIGURATION
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_RS_HIKEANDBOOK=DEBUG
LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_SECURITY=WARN
```

## 🚀 Brzi Start

```bash
# 1. Kreirajte .env fajl
cp env.example .env

# 2. Popunite svoje vrednosti u .env fajlu

# 3. Pokrenite aplikaciju
./gradlew bootRun --args='--spring.profiles.active=local'
```

## 📚 Dodatne Informacije

- [Environment Setup Guide](ENVIRONMENT_SETUP.md)
- [README.md](README.md#troubleshooting)
- [Spring Boot Logging](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.logging) 