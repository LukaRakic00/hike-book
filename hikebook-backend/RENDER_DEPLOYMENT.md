# 🚀 Render Deployment Guide - HikeBook Backend

## 📋 Pregled

Ovaj vodič će vas provesti kroz proces deployment-a HikeBook backend aplikacije na Render platformu.

## 🎯 Prerequisites

- GitHub repository sa HikeBook backend kodom
- Render account (besplatan na [render.com](https://render.com))
- PostgreSQL database (može biti Render PostgreSQL ili eksterni)

## 📝 Koraci za Deployment

### 1. **Kreirajte Render Account**
- Idite na [render.com](https://render.com)
- Registrujte se i kreirajte account
- Povežite vaš GitHub account

### 2. **Kreirajte PostgreSQL Database**

1. U Render dashboard-u kliknite "New +"
2. Izaberite "PostgreSQL"
3. Unesite sledeće podatke:
   - **Name:** `hikebook-database`
   - **Database:** `hikebook`
   - **User:** `hikebook_user`
   - **Region:** Najbliža vašoj lokaciji (npr. Frankfurt)
   - **PostgreSQL Version:** 15 (ili najnovija)
4. Kliknite "Create Database"

### 3. **Kreirajte Web Service**

1. U Render dashboard-u kliknite "New +"
2. Izaberite "Web Service"
3. Povežite vaš GitHub repository
4. Unesite sledeće podatke:
   - **Name:** `hikebook-backend`
   - **Environment:** `Java`
   - **Region:** Ista kao database
   - **Branch:** `main` (ili vaš glavni branch)
   - **Build Command:** `./gradlew build`
   - **Start Command:** `java -jar build/libs/hikebook-backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod`

### 4. **Environment Variables**

Dodajte ove environment varijable u Web Service:

```bash
# Database Configuration
DATABASE_URL=postgresql://hikebook_user:password@host:port/hikebook
DATABASE_USERNAME=hikebook_user
DATABASE_PASSWORD=your_database_password

# JWT Configuration
JWT_SECRET=your-super-secret-jwt-key-here-make-it-long-and-secure-for-production
JWT_EXPIRATION=86400000

# CORS Configuration
ALLOWED_ORIGINS=https://your-frontend-app.onrender.com,https://your-frontend-app.vercel.app

# Server Configuration
SERVER_PORT=8080

# Spring Profile
SPRING_PROFILES_ACTIVE=prod

# Cloudinary Configuration (opciono)
CLOUDINARY_CLOUD_NAME=your-cloud-name
CLOUDINARY_API_KEY=your-api-key
CLOUDINARY_API_SECRET=your-api-secret

# Logging Configuration
LOGGING_LEVEL_ROOT=WARN
LOGGING_LEVEL_RS_HIKEANDBOOK=INFO
```

### 5. **Database Migration**

Nakon što se aplikacija pokrene, pokrenite SQL skriptu:

1. Idite na PostgreSQL database u Render
2. Kliknite "Connect" → "External Database"
3. Kopirajte connection string
4. Povežite se sa psql ili pgAdmin
5. Pokrenite migraciju:

```sql
\i src/main/resources/sql/render_migration.sql
```

### 6. **Testiranje API-ja**

Nakon deployment-a, testirajte endpoint-e:

```bash
# Zamenite YOUR_APP_URL sa vašim Render URL-om
YOUR_APP_URL=https://hikebook-backend.onrender.com

# Test health check
curl $YOUR_APP_URL/health

# Test explore endpoint
curl $YOUR_APP_URL/api/explore

# Test admin login
curl -X POST $YOUR_APP_URL/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@hikebook.com","password":"password"}'

# Test admin stats (sa token-om)
curl $YOUR_APP_URL/api/admin/stats \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

## 🔧 Troubleshooting

### Česti Problemi:

#### 1. **Database Connection Error**
```
Error: Could not create connection to database server
```
**Rešenje:**
- Proverite da li su environment varijable ispravno postavljene
- Proverite da li je database kreiran i aktivan
- Proverite da li je DATABASE_URL ispravan format

#### 2. **Build Error**
```
Error: Build failed
```
**Rešenje:**
- Proverite da li je `build.gradle` ispravan
- Proverite da li su sve zavisnosti definisane
- Proverite da li je Java 21 dostupan

#### 3. **CORS Error**
```
Error: CORS policy blocked request
```
**Rešenje:**
- Proverite da li je CORS konfiguracija ispravna
- Dodajte vaš frontend URL u `ALLOWED_ORIGINS`
- Proverite da li je `ALLOWED_ORIGINS` ispravno postavljen

#### 4. **Migration Error**
```
Error: SQL script failed
```
**Rešenje:**
- Proverite da li je SQL skripta ispravna
- Proverite da li imate dozvole za izvršavanje SQL komandi
- Proverite da li je database konekcija aktivan

#### 5. **JWT Error**
```
Error: Invalid token
```
**Rešenje:**
- Proverite da li je `JWT_SECRET` postavljen
- Proverite da li je token ispravno formatiran
- Proverite da li je token nije istekao

## 📊 Monitoring

### Render Dashboard:
- **Logs:** Pratite aplikacijske logove u real-time
- **Metrics:** Pratite CPU, RAM, i network usage
- **Health Checks:** Proverite da li je aplikacija dostupna
- **Deployments:** Pratite deployment istoriju

### API Health Check:
```bash
curl $YOUR_APP_URL/health
```

### Log Monitoring:
```bash
# Pratite logove u Render dashboard-u
# Ili koristite Render CLI ako je dostupan
```

## 🔒 Security

### Produkcija Best Practices:
- Koristite HTTPS (Render automatski omogućava)
- Postavite ispravne CORS origins
- Koristite environment varijable za sensitive podatke
- Redovno ažurirajte zavisnosti
- Koristite jake JWT secrete
- Postavite rate limiting ako je potrebno

### Environment Variables Security:
- Nikad ne commit-ujte sensitive podatke u kod
- Koristite Render environment variables
- Redovno rotirajte secrete
- Koristite različite secrete za različite environment-e

## 📈 Scaling

### Free Tier Limitations:
- 750 sati/mesečno
- 512MB RAM
- Shared CPU
- 1GB disk space

### Upgrade Options:
- **Starter:** $7/mesečno - 1GB RAM, dedicated CPU
- **Standard:** $25/mesečno - 2GB RAM, dedicated CPU
- **Pro:** $50/mesečno - 4GB RAM, dedicated CPU

## 🔄 Continuous Deployment

### Automatski Deployment:
- Render automatski detektuje promene u kodu
- Pokreće build i deployment
- Restartuje aplikaciju ako je potrebno

### Manual Deployment:
- Možete ručno pokrenuti deployment
- Možete rollback na prethodnu verziju
- Možete preview deployment pre objavljivanja

## 📝 Notes

- Render automatski restartuje aplikaciju kada detektuje promene u kodu
- Database backup se automatski kreira
- Možete postaviti custom domain ako je potrebno
- Monitorujte usage da ne pređete free tier limit
- Logovi se čuvaju 30 dana na free tier-u

## 🎯 Next Steps

1. **Frontend Integration:** Povežite frontend sa backend API-jem
2. **Domain Setup:** Postavite custom domain ako je potrebno
3. **Monitoring:** Postavite alerting za greške
4. **Backup Strategy:** Planirajte regularne backup-ove
5. **Scaling:** Planirajte kako ćete skalirati kada aplikacija poraste
6. **CI/CD:** Postavite automatsko testiranje pre deployment-a

## 📞 Support

- **Render Documentation:** [docs.render.com](https://docs.render.com)
- **Render Community:** [community.render.com](https://community.render.com)
- **GitHub Issues:** Otvorite issue u vašem repository-ju

---

**Napomena:** Ovaj vodič je specifičan za HikeBook backend aplikaciju. Prilagodite korake prema vašim potrebama. 