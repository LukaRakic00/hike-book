# 🏔️ HikeBook Backend API

Kompletna Spring Boot backend aplikacija za HikeBook platformu - sistem za upravljanje hiking trail-ovima, destinacijama i korisničkim aktivnostima.

## 📋 Sadržaj

- [Pregled](#pregled)
- [Funkcionalnosti](#funkcionalnosti)
- [Tehnologije](#tehnologije)
- [Instalacija](#instalacija)
- [Konfiguracija](#konfiguracija)
- [Environment Setup](#environment-setup)
- [API Dokumentacija](#api-dokumentacija)
- [Baza Podataka](#baza-podataka)
- [Autentifikacija](#autentifikacija)
- [Admin Panel](#admin-panel)
- [Deployment](#deployment)
- [Razvoj](#razvoj)
- [Troubleshooting](#troubleshooting)

## 🎯 Pregled

HikeBook Backend je RESTful API napravljen u Spring Boot-u koji omogućava:

- **Upravljanje trail-ovima** - CRUD operacije za hiking staze
- **Upravljanje destinacijama** - Geografske lokacije i regioni
- **Korisničke aktivnosti** - Hiking, biking, running, walking
- **Sistem favorita** - Korisnici mogu čuvati omiljene trail-ove
- **Admin panel** - Administracija sistema
- **Explore funkcionalnost** - Map-based pretraga trail-ova
- **Upload slika** - Cloudinary integracija
- **JWT autentifikacija** - Siguran pristup

## ✨ Funkcionalnosti

### 🔐 Autentifikacija & Autorizacija
- **JWT Token** - Sigurna autentifikacija
- **Role-based access** - User i Admin role
- **Password hashing** - BCrypt enkripcija
- **Token validation** - Automatska validacija

### 🗺️ Explore Funkcionalnost
- **Map-based pretraga** - Geografska pretraga trail-ova
- **Filtering** - Po težini, aktivnosti, destinaciji
- **Destination grouping** - Grupisanje po regionima
- **Coordinates** - Precizne GPS koordinate

### 👨‍💼 Admin Panel
- **Dashboard** - Statistike sistema
- **User management** - Upravljanje korisnicima
- **Trail management** - CRUD za trail-ove
- **Destination management** - CRUD za destinacije
- **Role management** - Promena korisničkih uloga

### 📸 Media Management
- **Cloudinary integracija** - Upload slika
- **Photo management** - Upravljanje slikama trail-ova
- **Main photo** - Glavne slike za trail-ove
- **Multiple uploads** - Više slika po trail-u

### ❤️ Favorites System
- **Add/Remove** - Dodavanje/uklanjanje favorita
- **User favorites** - Lista omiljenih trail-ova
- **Persistent storage** - Čuvanje u bazi

## 🛠️ Tehnologije

### Backend Stack
- **Java 21** - Programski jezik
- **Spring Boot 3.5.3** - Framework
- **Spring Security** - Autentifikacija i autorizacija
- **Spring Data JPA** - ORM
- **Hibernate** - Database mapping
- **PostgreSQL** - Relaciona baza podataka
- **JWT** - JSON Web Tokens
- **BCrypt** - Password hashing

### Cloud Services
- **Cloudinary** - Image hosting
- **Render** - Hosting platforma

### Development Tools
- **Gradle** - Build tool
- **Docker** - Containerization
- **VS Code** - IDE konfiguracija

## 🚀 Instalacija

### Prerequisites
- Java 21 (JDK)
- PostgreSQL 15+
- Gradle 8.14.2+
- Git

### Koraci za Instalaciju

1. **Klonirajte repository**
```bash
git clone https://github.com/your-username/hikebook-backend.git
cd hikebook-backend
```

2. **Kreirajte bazu podataka**
```sql
CREATE DATABASE hikebook_db;
CREATE USER hikebook_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE hikebook_db TO hikebook_user;
```

3. **Pokrenite migraciju**
```sql
\i src/main/resources/sql/render_migration.sql
```

4. **Pokrenite aplikaciju**
```bash
./gradlew bootRun
```

Aplikacija će biti dostupna na `http://localhost:8080`

## ⚙️ Konfiguracija

📖 **Detaljne instrukcije:** [Environment Setup Guide](ENVIRONMENT_SETUP.md)
📖 **Brzi fix za logging problem:** [Logging Fix Guide](LOGGING_FIX.md)

### Environment Setup

#### Brzi Start:

**Linux/Mac:**
```bash
chmod +x setup-env.sh
./setup-env.sh
```

**Windows:**
```cmd
setup-env.bat
```

**Manuelno:**
1. **Kreirajte .env fajl**
```bash
cp env.example .env
```

2. **Popunite svoje vrednosti u .env fajlu**
```bash
# Database
DATABASE_URL=jdbc:postgresql://localhost:5432/hikebook_db
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=your_password

# JWT
JWT_SECRET=your-super-secret-jwt-key-here-make-it-long-and-secure

# Cloudinary (opciono)
CLOUDINARY_CLOUD_NAME=your-cloud-name
CLOUDINARY_API_KEY=your-api-key
CLOUDINARY_API_SECRET=your-api-secret

# Logging
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_RS_HIKEANDBOOK=DEBUG
LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_SECURITY=WARN
```

3. **Pokrenite aplikaciju**
```bash
./gradlew bootRun --args='--spring.profiles.active=local'
```

**VAŽNO:** Kreirajte `.env` fajl u root direktorijumu projekta (pored `build.gradle`) sa svim potrebnim varijablama iz `env.example` fajla.

## 🔧 Environment Setup

📖 **Detaljne instrukcije:** [Environment Setup Guide](ENVIRONMENT_SETUP.md)

### Brzi Start:
```bash
# 1. Kreirajte .env fajl
cp env.example .env

# 2. Popunite svoje vrednosti u .env fajlu
code .env

# 3. Pokrenite aplikaciju
./gradlew bootRun --args='--spring.profiles.active=local'
```

### Obavezni Environment Variables:
- `DATABASE_URL` - PostgreSQL connection string
- `DATABASE_USERNAME` - Database korisnik
- `DATABASE_PASSWORD` - Database lozinka
- `JWT_SECRET` - JWT signing secret

### Environment Variables

| Variable | Opis | Obavezno | Default |
|----------|------|----------|---------|
| `DATABASE_URL` | PostgreSQL connection string | ✅ | `jdbc:postgresql://localhost:5432/hikebook_db` |
| `DATABASE_USERNAME` | Database korisnik | ✅ | - |
| `DATABASE_PASSWORD` | Database lozinka | ✅ | - |
| `JWT_SECRET` | JWT signing secret | ✅ | - |
| `JWT_EXPIRATION` | JWT token expiration (ms) | ❌ | `86400000` |
| `SERVER_PORT` | Server port | ❌ | `8080` |
| `CLOUDINARY_CLOUD_NAME` | Cloudinary cloud name | ❌ | - |
| `CLOUDINARY_API_KEY` | Cloudinary API key | ❌ | - |
| `CLOUDINARY_API_SECRET` | Cloudinary API secret | ❌ | - |
| `LOGGING_LEVEL_ROOT` | Root logging level | ❌ | `INFO` |
| `LOGGING_LEVEL_RS_HIKEANDBOOK` | App logging level | ❌ | `DEBUG` |
| `LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_SECURITY` | Security logging level | ❌ | `WARN` |

### Produkcija Konfiguracija

Za produkciju koristite `application-prod.properties`:

```bash
# Postavite environment variables
DATABASE_URL=postgresql://user:pass@host:port/db
JWT_SECRET=your-production-secret
ALLOWED_ORIGINS=https://your-frontend.com

# Pokrenite sa production profile
java -jar app.jar --spring.profiles.active=prod
```

## 📚 API Dokumentacija

### 🔐 Autentifikacija

#### POST /api/auth/signup
Registracija novog korisnika
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "phoneNumber": "+38160000000"
}
```

#### POST /api/auth/signin
Prijava korisnika
```json
{
  "email": "admin@hikebook.com",
  "password": "password"
}
```

#### POST /api/auth/signout
Odjava korisnika
```
Authorization: Bearer <token>
```

### 🗺️ Explore API

#### GET /api/explore
Dohvatanje svih destinacija za mapu
```json
{
  "destinations": [
    {
      "id": 1,
      "name": "Fruška Gora",
      "region": "Vojvodina",
      "latitude": 45.1234,
      "longitude": 19.5678,
      "trailCount": 5,
      "imageUrl": "https://example.com/image.jpg",
      "trails": [...]
    }
  ],
  "availableActivities": ["Hiking", "Biking", "Running"],
  "availableDifficulties": ["easy", "moderate", "hard"]
}
```

#### GET /api/explore/{destinationId}
Detalji destinacije
```json
{
  "destination": {...},
  "trails": [
    {
      "id": 1,
      "name": "Stara Planina Trail",
      "difficulty": "moderate",
      "length": 8.5,
      "rating": 4.2,
      "activities": ["Hiking", "Walking"]
    }
  ]
}
```

#### GET /api/explore/{destinationId}/trails
Trail-ovi sa filterima
```
?difficulty=moderate&activity=1&limit=10&offset=0
```

### 🛤️ Trail API

#### GET /api/trails
Lista trail-ova sa filterima
```
?limit=10&offset=0&difficulty=moderate&activity_id=1&destination_id=1
```

#### GET /api/trails/{id}
Detalji trail-a
```json
{
  "id": 1,
  "name": "Kopaonik Peak Trail",
  "destination": {...},
  "length": 8.5,
  "difficulty": "moderate",
  "activities": [...],
  "rating": 4.5
}
```

#### GET /api/trails/favorites
Omiljeni trail-ovi (zahtevaju autentifikaciju)
```
Authorization: Bearer <token>
```

#### POST /api/trails/{id}/favorite
Dodaj trail u favorite
```
Authorization: Bearer <token>
```

#### DELETE /api/trails/{id}/favorite
Ukloni trail iz favorita
```
Authorization: Bearer <token>
```

### 👨‍💼 Admin API

#### GET /api/admin/stats
Statistike sistema
```json
{
  "users": 150,
  "trails": 45,
  "destinations": 12,
  "activities": 4,
  "photos": 230,
  "admins": 3,
  "active_users": 120
}
```

#### GET /api/admin/users
Lista korisnika
```
Authorization: Bearer <admin_token>
```

#### PUT /api/admin/users/{id}/role
Promena role korisnika
```json
{
  "role": "admin"
}
```

#### PUT /api/admin/users/{id}/status
Promena statusa korisnika
```json
{
  "active": false
}
```

#### GET /api/admin/trails
Lista trail-ova (admin view)
#### POST /api/admin/trails
Kreiraj novi trail
#### PUT /api/admin/trails/{id}
Ažuriraj trail
#### DELETE /api/admin/trails/{id}
Obriši trail

#### GET /api/admin/destinations
Lista destinacija (admin view)
#### POST /api/admin/destinations
Kreiraj novu destinaciju
#### PUT /api/admin/destinations/{id}
Ažuriraj destinaciju
#### DELETE /api/admin/destinations/{id}
Obriši destinaciju

### 📸 Photo API

#### GET /api/photos/trail/{trailId}
Slike trail-a
```
?limit=10&offset=0
```

#### POST /api/photos
Upload slike
```
Authorization: Bearer <token>
Content-Type: multipart/form-data
```

#### PUT /api/photos/{id}/main
Postavi glavnu sliku
```
Authorization: Bearer <token>
```

### 🏃 Activity API

#### GET /api/activities
Lista aktivnosti
```json
[
  {
    "id": 1,
    "name": "Hiking",
    "description": "Pešačenje po planinskim stazama",
    "emoji": "🥾"
  }
]
```

### 📤 Upload API

#### POST /api/upload/image
Upload slike na Cloudinary
```
Content-Type: multipart/form-data
```

#### POST /api/upload/image/{folder}
Upload slike u specifični folder
```
Content-Type: multipart/form-data
```

## 🗄️ Baza Podataka

### Glavne Tabele

#### `user`
- `id` - Primary key
- `name` - Ime korisnika
- `email` - Email (unique)
- `password_hash` - BCrypt hash
- `phone` - Telefon
- `role` - 'user' ili 'admin'
- `active` - Da li je aktivan
- `registration_date` - Datum registracije

#### `trail`
- `id` - Primary key
- `name` - Naziv trail-a
- `destination_id` - Foreign key
- `length` - Dužina u km
- `elevation_gain` - Uspon u metrima
- `difficulty` - 'easy', 'moderate', 'hard'
- `latitude`, `longitude` - GPS koordinate
- `center_latitude`, `center_longitude` - Centar za mapu
- `coordinates` - JSON array koordinata
- `main_image` - URL glavne slike

#### `destination`
- `id` - Primary key
- `name` - Naziv destinacije
- `country` - Država
- `region` - Regija
- `city` - Grad
- `latitude`, `longitude` - GPS koordinate
- `image` - URL slike

#### `activity`
- `id` - Primary key
- `name` - Naziv aktivnosti
- `description` - Opis
- `emoji` - Emoji ikona

#### `favorite_trail`
- `id` - Primary key
- `user_id` - Foreign key
- `trail_id` - Foreign key

#### `trail_activity`
- `trail_id` - Foreign key
- `activity_id` - Foreign key

#### `photo`
- `id` - Primary key
- `trail_id` - Foreign key
- `user_id` - Foreign key
- `url` - URL slike
- `description` - Opis
- `is_main` - Da li je glavna slika

### Indeksi
```sql
CREATE INDEX idx_trail_destination_id ON trail(destination_id);
CREATE INDEX idx_trail_center_coordinates ON trail(center_latitude, center_longitude);
CREATE INDEX idx_destination_region ON destination(region);
CREATE INDEX idx_user_role ON "user"(role);
CREATE INDEX idx_user_active ON "user"(active);
```

## 🔐 Autentifikacija

### JWT Token Struktura
```json
{
  "sub": "user@email.com",
  "userId": 123,
  "role": "admin",
  "iat": 1640995200,
  "exp": 1641081600
}
```

### Token Usage
```bash
# Dodajte token u header
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Role-based Access
- **Public endpoints** - `/api/auth/**`, `/api/explore/**`, `/api/activities`
- **User endpoints** - `/api/trails/favorites`, `/api/photos`
- **Admin endpoints** - `/api/admin/**` (zahtevaju admin role)

## 👨‍💼 Admin Panel

### Admin Korisnik
- **Email:** `admin@hikebook.com`
- **Password:** `password`
- **Role:** `admin`

### Admin Funkcionalnosti
1. **Dashboard** - Pregled statistika
2. **User Management** - Upravljanje korisnicima
3. **Trail Management** - CRUD trail-ova
4. **Destination Management** - CRUD destinacija
5. **Role Management** - Promena korisničkih uloga

### Admin API Endpoints
- `GET /api/admin/stats` - Statistike
- `GET /api/admin/users` - Lista korisnika
- `PUT /api/admin/users/{id}/role` - Promena role
- `PUT /api/admin/users/{id}/status` - Promena statusa
- `GET/POST/PUT/DELETE /api/admin/trails` - Trail management
- `GET/POST/PUT/DELETE /api/admin/destinations` - Destination management

## 🚀 Deployment

### Render Deployment
Detaljne instrukcije za deployment na Render platformu:

📖 [Render Deployment Guide](RENDER_DEPLOYMENT.md)

### Docker Deployment
```bash
# Build Docker image
docker build -t hikebook-backend .

# Run container
docker run -p 8080:8080 hikebook-backend
```

### Environment Variables
```bash
# Obavezni
DATABASE_URL=postgresql://user:pass@host:port/db
JWT_SECRET=your-secret-key
ALLOWED_ORIGINS=https://your-frontend.com

# Opciono
CLOUDINARY_CLOUD_NAME=your-cloud-name
CLOUDINARY_API_KEY=your-api-key
CLOUDINARY_API_SECRET=your-api-secret
```

## 💻 Razvoj

### Projektna Struktura
```
src/main/java/rs/hikeandbook/
├── config/          # Konfiguracija
├── controller/      # REST kontroleri
├── dto/            # Data Transfer Objects
├── model/          # JPA entiteti
├── repository/     # Spring Data repositories
├── service/        # Business logic
└── util/           # Utility klase
```

### Build Commands
```bash
# Clean build
./gradlew clean build

# Run tests
./gradlew test

# Run application
./gradlew bootRun

# Build JAR
./gradlew bootJar
```

### VS Code Konfiguracija
Projekat uključuje VS Code konfiguraciju:
- `.vscode/settings.json` - Java settings
- `.vscode/tasks.json` - Build tasks
- `.vscode/launch.json` - Debug konfiguracija

### Development Profile
```bash
# Pokrenite sa local profile
./gradlew bootRun --args='--spring.profiles.active=local'
```

## 🔧 Troubleshooting

📖 **Brzi fix za logging problem:** [Logging Fix Guide](LOGGING_FIX.md)

### Česti Problemi

#### 1. Database Connection Error
```
Error: Could not create connection to database server
```
**Rešenje:**
- Proverite da li je PostgreSQL pokrenut
- Proverite connection string u `application.properties`
- Proverite da li korisnik ima dozvole

#### 2. JWT Token Error
```
Error: Invalid token
```
**Rešenje:**
- Proverite da li je `JWT_SECRET` postavljen
- Proverite da li je token ispravno formatiran
- Proverite da li je token nije istekao

#### 3. CORS Error
```
Error: CORS policy blocked request
```
**Rešenje:**
- Proverite `ALLOWED_ORIGINS` konfiguraciju
- Dodajte frontend URL u dozvoljene origins
- Proverite da li je CORS middleware aktivan

#### 4. Build Error
```
Error: Build failed
```
**Rešenje:**
- Proverite da li je Java 21 instaliran
- Proverite da li su sve zavisnosti dostupne
- Proverite da li je `build.gradle` ispravan

#### 5. Migration Error
```
Error: SQL script failed
```
**Rešenje:**
- Proverite da li je database kreiran
- Proverite da li korisnik ima dozvole
- Proverite da li je SQL skripta ispravna

#### 6. Logging Level Error
```
Error: failed to convert java.lang.String to org.springframework.boot.logging.LogLevel
```
**Rešenje:**
1. **Kreirajte .env fajl:**
   ```bash
   cp env.example .env
   ```

2. **Proverite da li je .env fajl u root direktorijumu:**
   ```bash
   ls -la .env
   ```

3. **Proverite da li su logging varijable ispravno postavljene:**
   ```bash
   cat .env | grep LOGGING
   ```

4. **Validne vrednosti za logging:**
   - `INFO`, `DEBUG`, `ERROR`, `WARN`, `TRACE`, `OFF`

5. **Proverite da li je spring-dotenv dependency dodat:**
   ```bash
   cat build.gradle | grep spring-dotenv
   ```

6. **Pokrenite aplikaciju ponovo:**
   ```bash
   ./gradlew bootRun --args='--spring.profiles.active=local'
   ```

### Debug Mode
```bash
# Pokrenite sa debug logovima
./gradlew bootRun --args='--logging.level.rs.hikeandbook=DEBUG'

# Ili postavite u .env fajlu
LOGGING_LEVEL_ROOT=DEBUG
LOGGING_LEVEL_RS_HIKEANDBOOK=DEBUG
```

### Health Check
```bash
# Proverite da li aplikacija radi
curl http://localhost:8080/health
```

## 📞 Support

### Dokumentacija
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [JPA/Hibernate Documentation](https://hibernate.org/orm/documentation/)

### Community
- [Spring Community](https://spring.io/community)
- [Stack Overflow](https://stackoverflow.com/questions/tagged/spring-boot)

### Issues
Otvorite issue u GitHub repository-ju za:
- Bug reports
- Feature requests
- Documentation improvements

## 📄 License

Ovaj projekat je licenciran pod MIT licencom.

## 🤝 Contributing

1. Fork repository
2. Kreirajte feature branch (`git checkout -b feature/amazing-feature`)
3. Commit promene (`git commit -m 'Add amazing feature'`)
4. Push na branch (`git push origin feature/amazing-feature`)
5. Otvorite Pull Request

---

**Napomena:** Ovaj README je specifičan za HikeBook backend aplikaciju. Prilagodite prema vašim potrebama. 