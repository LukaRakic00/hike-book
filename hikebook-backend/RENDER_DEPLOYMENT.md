# Render Deployment Guide - Explore API

## 🚀 Koraci za Deployment na Render

### 1. **Kreirajte Render Account**
- Idite na [render.com](https://render.com)
- Registrujte se i kreirajte account

### 2. **Kreirajte PostgreSQL Database**
1. U Render dashboard-u kliknite "New +"
2. Izaberite "PostgreSQL"
3. Unesite:
   - **Name:** `hikebook-database`
   - **Database:** `hikebook`
   - **User:** `hikebook_user`
   - **Region:** Najbliža vašoj lokaciji
4. Kliknite "Create Database"

### 3. **Kreirajte Web Service**
1. U Render dashboard-u kliknite "New +"
2. Izaberite "Web Service"
3. Povežite vaš GitHub repository
4. Unesite:
   - **Name:** `hikebook-backend`
   - **Environment:** `Java`
   - **Build Command:** `./gradlew build`
   - **Start Command:** `java -jar build/libs/hikebook-backend-0.0.1-SNAPSHOT.jar`

### 4. **Environment Variables**
Dodajte ove environment varijable u Web Service:

```bash
# Database Configuration
DATABASE_URL=postgresql://hikebook_user:password@host:port/hikebook
SPRING_DATASOURCE_URL=${DATABASE_URL}
SPRING_DATASOURCE_USERNAME=hikebook_user
SPRING_DATASOURCE_PASSWORD=your_database_password

# JPA/Hibernate
SPRING_JPA_HIBERNATE_DDL_AUTO=validate
SPRING_JPA_SHOW_SQL=false
SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect

# Server
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=production

# CORS
ALLOWED_ORIGINS=https://your-frontend-app.onrender.com
```

### 5. **Database Migration**
Nakon što se aplikacija pokrene, pokrenite SQL skriptu:

1. Idite na PostgreSQL database u Render
2. Kliknite "Connect" → "External Database"
3. Kopirajte connection string
4. Povežite se sa psql ili pgAdmin
5. Pokrenite:
   ```sql
   \i src/main/resources/sql/render_migration.sql
   ```

### 6. **Testiranje API-ja**

Nakon deployment-a, testirajte endpoint-e:

```bash
# Zamenite YOUR_APP_URL sa vašim Render URL-om
YOUR_APP_URL=https://hikebook-backend.onrender.com

# Test explore endpoint
curl $YOUR_APP_URL/api/explore

# Test destination details
curl $YOUR_APP_URL/api/explore/1

# Test trails with filters
curl "$YOUR_APP_URL/api/explore/1/trails?difficulty=moderate"
```

## 🔧 Troubleshooting

### Česti Problemi:

1. **Database Connection Error**
   - Proverite da li su environment varijable ispravno postavljene
   - Proverite da li je database kreiran i aktivan

2. **Build Error**
   - Proverite da li je `build.gradle` ispravan
   - Proverite da li su sve zavisnosti definisane

3. **CORS Error**
   - Proverite da li je CORS konfiguracija ispravna
   - Dodajte vaš frontend URL u `ALLOWED_ORIGINS`

4. **Migration Error**
   - Proverite da li je SQL skripta ispravna
   - Proverite da li imate dozvole za izvršavanje SQL komandi

## 📊 Monitoring

### Render Dashboard:
- **Logs:** Pratite aplikacijske logove
- **Metrics:** Pratite CPU, RAM, i network usage
- **Health Checks:** Proverite da li je aplikacija dostupna

### API Health Check:
```bash
curl $YOUR_APP_URL/api/explore
```

## 🔒 Security

### Produkcija:
- Koristite HTTPS
- Postavite ispravne CORS origins
- Koristite environment varijable za sensitive podatke
- Redovno ažurirajte zavisnosti

## 📝 Notes

- Render automatski restartuje aplikaciju kada detektuje promene u kodu
- Database backup se automatski kreira
- Možete postaviti custom domain ako je potrebno
- Monitorujte usage da ne pređete free tier limit

## 🎯 Next Steps

1. **Frontend Integration:** Povežite frontend sa backend API-jem
2. **Domain Setup:** Postavite custom domain ako je potrebno
3. **Monitoring:** Postavite alerting za greške
4. **Backup Strategy:** Planirajte regularne backup-ove
5. **Scaling:** Planirajte kako ćete skalirati kada aplikacija poraste 