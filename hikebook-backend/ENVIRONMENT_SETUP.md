# 🔧 Environment Setup Guide - HikeBook Backend

## 📋 Pregled

Ovaj vodič će vas provesti kroz proces postavljanja environment variables za HikeBook backend aplikaciju.

## 🎯 Zašto Environment Variables?

- **Security** - Osetljivi podaci se ne čuvaju u kodu
- **Flexibility** - Različite konfiguracije za različite environment-e
- **Best Practices** - Industrija standard
- **Deployment** - Lako prebacivanje između development i production

## 📁 Fajlovi

### 1. **env.example** (Template)
- Template fajl sa svim potrebnim varijablama
- **NE sadrži** stvarne vrednosti
- **MOŽE** se commit-ovati u Git

### 2. **.env** (Stvarne vrednosti)
- Sadrži stvarne vrednosti za vaš environment
- **NE TREBA** da se commit-uje u Git
- **KOPIRAJTE** iz `env.example`

### 3. **application.properties** (Glavna konfiguracija)
- Čita environment variables
- Fallback vrednosti za development
- **MOŽE** se commit-ovati u Git

### 4. **application-prod.properties** (Produkcija)
- Optimizovana za produkciju
- Koristi samo environment variables
- **MOŽE** se commit-ovati u Git

## 🚀 Koraci za Setup

### 1. **Kreirajte .env fajl**

```bash
# Kopirajte template
cp env.example .env

# Ili na Windows
copy env.example .env
```

### 2. **Popunite .env fajl**

```bash
# Otvorite .env fajl u editoru
code .env
```

### 3. **Postavite svoje vrednosti**

```bash
# ========================================
# DATABASE CONFIGURATION
# ========================================
DATABASE_URL=jdbc:postgresql://localhost:5432/hikebook_db
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=your_actual_password

# ========================================
# JWT CONFIGURATION
# ========================================
JWT_SECRET=your-super-secret-jwt-key-here-make-it-long-and-secure
JWT_EXPIRATION=86400000

# ========================================
# CLOUDINARY CONFIGURATION
# ========================================
CLOUDINARY_CLOUD_NAME=your_actual_cloud_name
CLOUDINARY_API_KEY=your_actual_api_key
CLOUDINARY_API_SECRET=your_actual_api_secret

# ========================================
# CORS CONFIGURATION
# ========================================
ALLOWED_ORIGINS=https://your-frontend-app.com,http://localhost:3000

# ========================================
# SERVER CONFIGURATION
# ========================================
SERVER_PORT=8080

# ========================================
# LOGGING CONFIGURATION
# ========================================
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_RS_HIKEANDBOOK=DEBUG
LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_SECURITY=WARN
```

## 🔐 Environment Variables Reference

### **Obavezni Variables**

| Variable | Opis | Primer |
|----------|------|--------|
| `DATABASE_URL` | PostgreSQL connection string | `jdbc:postgresql://localhost:5432/hikebook_db` |
| `DATABASE_USERNAME` | Database korisnik | `postgres` |
| `DATABASE_PASSWORD` | Database lozinka | `your_password` |
| `JWT_SECRET` | JWT signing secret | `your-super-secret-key` |

### **Opcioni Variables**

| Variable | Opis | Default |
|----------|------|---------|
| `JWT_EXPIRATION` | JWT token expiration (ms) | `86400000` (24h) |
| `CLOUDINARY_CLOUD_NAME` | Cloudinary cloud name | `dfefl6j9b` |
| `CLOUDINARY_API_KEY` | Cloudinary API key | `122342643456559` |
| `CLOUDINARY_API_SECRET` | Cloudinary API secret | `akR-1d2TRBUs5n6J7Vr6VXA7t9E` |
| `ALLOWED_ORIGINS` | CORS allowed origins | `https://*.onrender.com,https://*.vercel.app` |
| `SERVER_PORT` | Server port | `8080` |
| `LOGGING_LEVEL_ROOT` | Root logging level | `INFO` |
| `LOGGING_LEVEL_RS_HIKEANDBOOK` | App logging level | `DEBUG` |
| `LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_SECURITY` | Security logging level | `WARN` |

## 🛠️ Development Setup

### **Lokalni Development**

1. **Kreirajte .env fajl**
```bash
cp env.example .env
```

2. **Popunite lokalne vrednosti**
```bash
# Database
DATABASE_URL=jdbc:postgresql://localhost:5432/hikebook_db
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=your_local_password

# JWT
JWT_SECRET=local-development-secret-key

# CORS
ALLOWED_ORIGINS=http://localhost:3000,http://localhost:3001
```

3. **Pokrenite aplikaciju**
```bash
./gradlew bootRun --args='--spring.profiles.active=local'
```

### **VS Code Development**

1. **Postavite environment u VS Code**
```json
// .vscode/settings.json
{
    "java.configuration.runtimeArgs": [
        "--spring.profiles.active=local"
    ]
}
```

2. **Pokrenite debug**
- Pritisnite `F5` ili koristite VS Code debug panel

## 🚀 Production Setup

### **Render Deployment**

1. **Postavite environment variables u Render**
```bash
DATABASE_URL=postgresql://user:pass@host:port/db
JWT_SECRET=your-production-secret
ALLOWED_ORIGINS=https://your-frontend.com
CLOUDINARY_CLOUD_NAME=your-cloud-name
CLOUDINARY_API_KEY=your-api-key
CLOUDINARY_API_SECRET=your-api-secret
```

2. **Pokrenite sa production profile**
```bash
java -jar app.jar --spring.profiles.active=prod
```

### **Docker Deployment**

1. **Kreirajte .env fajl za Docker**
```bash
cp env.example .env.docker
```

2. **Pokrenite Docker sa environment**
```bash
docker run --env-file .env.docker -p 8080:8080 hikebook-backend
```

## 🔒 Security Best Practices

### **1. Nikad ne commit-ujte .env**
```bash
# .gitignore već uključuje
.env
```

### **2. Koristite jake secrete**
```bash
# Dobro
JWT_SECRET=your-super-secret-jwt-key-here-make-it-long-and-secure-for-production

# Loše
JWT_SECRET=secret
```

### **3. Različiti secrete za različite environment-e**
```bash
# Development
JWT_SECRET=dev-secret-key

# Production
JWT_SECRET=prod-super-secure-key-here
```

### **4. Rotirajte secrete redovno**
```bash
# Mesečno menjajte JWT_SECRET
# Kvartalno menjajte database passwords
```

## 🔧 Troubleshooting

📖 **Brzi fix za logging problem:** [Logging Fix Guide](LOGGING_FIX.md)

### **Problem: Aplikacija ne čita .env fajl**

**Rešenje:**
```bash
# Proverite da li .env postoji
ls -la .env

# Proverite da li je u root direktorijumu
pwd
ls -la .env
```

### **Problem: Environment variables se ne čitaju**

**Rešenje:**
```bash
# Proverite sintaksu u .env
# Nema razmaka oko =

# Dobro
DATABASE_URL=jdbc:postgresql://localhost:5432/db

# Loše
DATABASE_URL = jdbc:postgresql://localhost:5432/db
```

### **Problem: Fallback vrednosti se koriste**

**Rešenje:**
```bash
# Proverite da li su environment variables postavljeni
echo $DATABASE_URL

# Proverite da li aplikacija čita .env
./gradlew bootRun --args='--spring.profiles.active=local'
```

### **Problem: Logging Level Error**

**Greška:**
```
Error: failed to convert java.lang.String to org.springframework.boot.logging.LogLevel
```

**Rešenje:**
```bash
# 1. Proverite da li je .env fajl kreiran
ls -la .env

# 2. Proverite da li su logging varijable ispravno postavljene
cat .env | grep LOGGING

# 3. Validne vrednosti za logging:
# INFO, DEBUG, ERROR, WARN, TRACE, OFF

# 4. Proverite da li je spring-dotenv dependency dodat
cat build.gradle | grep spring-dotenv
```

## 📝 Primeri

### **Development .env**
```bash
# Database
DATABASE_URL=jdbc:postgresql://localhost:5432/hikebook_dev
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=dev_password

# JWT
JWT_SECRET=dev-jwt-secret-key-2024

# CORS
ALLOWED_ORIGINS=http://localhost:3000,http://localhost:3001

# Logging
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_RS_HIKEANDBOOK=DEBUG
LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_SECURITY=WARN
```

### **Production .env**
```bash
# Database
DATABASE_URL=postgresql://prod_user:prod_pass@prod_host:5432/prod_db
DATABASE_USERNAME=prod_user
DATABASE_PASSWORD=super-secure-prod-password-2024

# JWT
JWT_SECRET=prod-super-secure-jwt-secret-key-2024-make-it-long-and-secure

# CORS
ALLOWED_ORIGINS=https://hikebook.com,https://app.hikebook.com

# Logging
LOGGING_LEVEL_ROOT=WARN
LOGGING_LEVEL_RS_HIKEANDBOOK=INFO
LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_SECURITY=ERROR
```

## 📚 Dodatni Resursi

- [Spring Boot Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config)
- [Environment Variables Best Practices](https://12factor.net/config)
- [Spring Profiles](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.profiles)

---

**Napomena:** Uvek proverite da li je `.env` fajl u `.gitignore` pre commit-ovanja! 