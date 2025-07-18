# Explore API Dokumentacija

## Pregled

Explore API omogućava frontend aplikaciji da prikaže interaktivnu mapu sa destinacijama i trail-ovima, omogućavajući korisnicima da istražuju različite lokacije i aktivnosti.

## Endpoint-i

### 1. GET /api/explore

Dohvata sve destinacije sa brojem trail-ova za prikaz na mapi.

**Response:**
```json
{
  "destinations": [
    {
      "id": 1,
      "name": "Fruška Gora",
      "region": "Vojvodina",
      "latitude": 45.1234,
      "longitude": 19.5678,
      "trailCount": 15,
      "imageUrl": "https://example.com/fruska-gora.jpg",
      "trails": []
    }
  ],
  "availableActivities": ["Hiking", "Biking", "Running", "Walking"],
  "availableDifficulties": ["easy", "moderate", "hard"]
}
```

### 2. GET /api/explore/{destinationId}

Dohvata detaljne informacije o određenoj destinaciji sa svim trail-ovima.

**Response:**
```json
{
  "destination": {
    "id": 1,
    "name": "Fruška Gora",
    "region": "Vojvodina",
    "latitude": 45.1234,
    "longitude": 19.5678,
    "trailCount": 15,
    "imageUrl": "https://example.com/fruska-gora.jpg"
  },
  "trails": [
    {
      "id": 1,
      "name": "Stara Planina Trail",
      "latitude": 45.1234,
      "longitude": 19.5678,
      "difficulty": "moderate",
      "length": 8.5,
      "rating": 4.2,
      "mainImage": "https://example.com/trail1.jpg",
      "activities": ["Hiking", "Walking"]
    }
  ]
}
```

### 3. GET /api/explore/{destinationId}/trails

Dohvata trail-ove za određenu destinaciju sa opcionim filterima.

**Query Parameters:**
- `activityId` (optional): ID aktivnosti za filtriranje
- `difficulty` (optional): Težina trail-a (easy, moderate, hard)

**Response:**
```json
[
  {
    "id": 1,
    "name": "Stara Planina Trail",
    "latitude": 45.1234,
    "longitude": 19.5678,
    "difficulty": "moderate",
    "length": 8.5,
    "rating": 4.2,
    "mainImage": "https://example.com/trail1.jpg",
    "activities": ["Hiking", "Walking"]
  }
]
```

## Modeli

### Trail Model (Proširen)

```java
@Entity
@Table(name = "trail")
public class Trail {
    // ... postojeća polja
    
    @Column(name = "coordinates", columnDefinition = "TEXT")
    private String coordinates; // JSON array koordinata za rutu
    
    @Column(name = "center_latitude", precision = 10, scale = 8)
    private BigDecimal centerLatitude;
    
    @Column(name = "center_longitude", precision = 11, scale = 8)
    private BigDecimal centerLongitude;
}
```

### Destination Model

Već postoji sa svim potrebnim poljima:
- id, name, country, region, city
- description, image
- latitude, longitude
- created_at, updated_at

## DTO Klase

### ExploreResponse
```java
public class ExploreResponse {
    private List<DestinationMapData> destinations;
    private List<String> availableActivities;
    private List<String> availableDifficulties;
}
```

### DestinationMapData
```java
public class DestinationMapData {
    private Integer id;
    private String name;
    private String region;
    private Double latitude;
    private Double longitude;
    private Integer trailCount;
    private String imageUrl;
    private List<TrailMarkerData> trails;
}
```

### TrailMarkerData
```java
public class TrailMarkerData {
    private Integer id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String difficulty;
    private Double length;
    private Double rating;
    private String mainImage;
    private List<String> activities;
}
```

## Baza Podataka

### SQL Skripta

```sql
-- Dodaj center koordinate u trail tabelu
ALTER TABLE trail ADD COLUMN IF NOT EXISTS center_latitude NUMERIC(10, 8);
ALTER TABLE trail ADD COLUMN IF NOT EXISTS center_longitude NUMERIC(11, 8);

-- Dodaj coordinates polje za JSON array koordinata
ALTER TABLE trail ADD COLUMN IF NOT EXISTS coordinates TEXT;

-- Indeksi za bolje performanse
CREATE INDEX IF NOT EXISTS idx_trail_destination_id ON trail(destination_id);
CREATE INDEX IF NOT EXISTS idx_trail_center_coordinates ON trail(center_latitude, center_longitude);
CREATE INDEX IF NOT EXISTS idx_destination_region ON destination(region);
CREATE INDEX IF NOT EXISTS idx_destination_coordinates ON destination(latitude, longitude);
```

## Error Handling

API vraća standardne HTTP status kodove:

- `200 OK`: Uspešan zahtev
- `404 Not Found`: Destinacija nije pronađena
- `500 Internal Server Error`: Greška na serveru

Error response format:
```json
{
  "message": "Error description"
}
```

## Primeri Korišćenja

### Frontend Integration

```javascript
// Dohvati sve destinacije za mapu
const response = await fetch('/api/explore');
const exploreData = await response.json();

// Prikaži destinacije na mapi
exploreData.destinations.forEach(destination => {
  addMarkerToMap(destination);
});

// Dohvati detalje destinacije
const destinationResponse = await fetch(`/api/explore/${destinationId}`);
const destinationDetail = await destinationResponse.json();

// Dohvati trail-ove sa filterima
const trailsResponse = await fetch(`/api/explore/${destinationId}/trails?difficulty=moderate&activityId=1`);
const trails = await trailsResponse.json();
```

## Napomene

1. **Koordinatni sistem**: Koristi se WGS84 koordinatni sistem (latitude/longitude)
2. **JSON koordinate**: Trail coordinates polje sadrži JSON array sa koordinatama rute
3. **Center koordinate**: Koriste se za pozicioniranje marker-a na mapi
4. **Filteri**: Podržani su filteri po aktivnosti i težini
5. **Performanse**: Dodati su indeksi za optimizaciju upita 