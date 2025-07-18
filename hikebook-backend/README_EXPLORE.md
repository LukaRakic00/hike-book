# Explore Funkcionalnost - Hike&Book Backend

## Pregled

Explore funkcionalnost omogućava korisnicima da istražuju destinacije i trail-ove na interaktivnoj mapi. Implementirana je kao REST API sa sledećim glavnim komponentama:

## Komponente

### 1. Modeli
- **Trail** (proširen) - dodata polja za center koordinate i JSON koordinate
- **Destination** (postojeći) - već ima sva potrebna polja
- **Activity** (postojeći) - za aktivnosti na trail-ovima

### 2. DTO Klase
- `ExploreResponse` - glavni response za explore data
- `DestinationMapData` - podaci o destinaciji za mapu
- `TrailMarkerData` - podaci o trail-u za map marker
- `DestinationDetailResponse` - detaljni podaci o destinaciji

### 3. Repository Klase
- `DestinationRepository` - upiti za destinacije sa brojem trail-ova
- `TrailRepository` (proširen) - dodatni upiti za filtriranje

### 4. Service Klasa
- `DestinationService` - business logika za explore funkcionalnost

### 5. Controller
- `ExploreController` - REST endpoint-i

## Pokretanje

### 1. Baza Podataka

Pokreni SQL skripte u sledećem redosledu:

```sql
-- 1. Schema izmene
\i src/main/resources/sql/explore_schema.sql

-- 2. Test podaci (opciono)
\i src/main/resources/sql/explore_test_data.sql
```

### 2. Aplikacija

```bash
# Pokreni aplikaciju
./gradlew bootRun
```

ili

```bash
# Build i pokreni
./gradlew build
java -jar build/libs/hikebook-backend-0.0.1-SNAPSHOT.jar
```

## API Endpoint-i

### Glavni endpoint-i:

1. **GET /api/explore** - Dohvata sve destinacije za mapu
2. **GET /api/explore/{destinationId}** - Detalji destinacije
3. **GET /api/explore/{destinationId}/trails** - Trail-ovi sa filterima

### Primeri korišćenja:

```bash
# Dohvati sve destinacije
curl http://localhost:8080/api/explore

# Dohvati detalje destinacije
curl http://localhost:8080/api/explore/1

# Dohvati trail-ove sa filterima
curl "http://localhost:8080/api/explore/1/trails?difficulty=moderate&activityId=1"
```

## Test Podaci

Test podaci uključuju:
- 4 destinacije (Fruška Gora, Kopaonik, Tara, Zlatibor)
- 5 trail-ova sa različitim težinama
- 4 aktivnosti (Hiking, Biking, Running, Walking)
- Povezivanje trail-ova sa aktivnostima

## Struktura Fajlova

```
src/main/java/rs/hikeandbook/
├── model/
│   ├── Trail.java (proširen)
│   ├── Destination.java (postojeći)
│   └── Activity.java (postojeći)
├── dto/
│   ├── ExploreResponse.java (novo)
│   ├── DestinationMapData.java (novo)
│   ├── TrailMarkerData.java (novo)
│   └── DestinationDetailResponse.java (novo)
├── repository/
│   ├── DestinationRepository.java (novo)
│   └── TrailRepository.java (proširen)
├── service/
│   └── DestinationService.java (novo)
├── controller/
│   ├── ExploreController.java (novo)
│   └── GlobalExceptionHandler.java (proširen)
└── util/
    └── ResourceNotFoundException.java (novo)

src/main/resources/
├── sql/
│   ├── explore_schema.sql (novo)
│   └── explore_test_data.sql (novo)
└── docs/
    └── EXPLORE_API.md (novo)
```

## Napomene

1. **Koordinatni sistem**: WGS84 (latitude/longitude)
2. **JSON koordinate**: Trail coordinates polje sadrži JSON array
3. **Center koordinate**: Za pozicioniranje marker-a na mapi
4. **Filteri**: Podržani po aktivnosti i težini
5. **Error handling**: Standardni HTTP status kodovi sa JSON response-ima

## Troubleshooting

### Česti problemi:

1. **Database connection error**
   - Proveri `application.properties` konfiguraciju
   - Proveri da li je baza pokrenuta

2. **Column not found error**
   - Pokreni `explore_schema.sql` skriptu
   - Proveri da li su sve kolone dodate

3. **Foreign key constraint error**
   - Proveri da li postoje destinacije pre dodavanja trail-ova
   - Pokreni test podatke u pravom redosledu

## Dokumentacija

Detaljna API dokumentacija: `docs/EXPLORE_API.md` 