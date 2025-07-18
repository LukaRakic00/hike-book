# HikeBook Backend API

A Spring Boot backend API for the HikeBook application, providing endpoints for managing trails, activities, and user favorites.

## Features

- **Trail Management**: Get trails with filtering options
- **Activity Management**: Get all available activities
- **User Favorites**: Add/remove trails from user favorites
- **JWT Authentication**: Secure endpoints with JWT token validation
- **PostgreSQL Database**: Robust data storage with proper relationships

## API Endpoints

### Activities

#### GET /api/activities
Get all available activities.

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "Hiking",
      "description": "Walking on trails in nature",
      "icon": "hiking-icon",
      "emoji": "🥾",
      "createdAt": "2024-01-01T10:00:00"
    }
  ],
  "message": "Activities retrieved successfully"
}
```

### Trails

#### GET /api/trails
Get all trails with optional filtering.

**Query Parameters:**
- `limit` (optional): Number of trails to return (default: 10)
- `offset` (optional): Number of trails to skip (default: 0)
- `difficulty` (optional): Filter by difficulty (easy, moderate, hard)
- `activity_id` (optional): Filter by activity ID
- `destination_id` (optional): Filter by destination ID

**Example:**
```
GET /api/trails?limit=10&offset=0&difficulty=moderate&activity_id=1&destination_id=1
```

**Response:**
```json
{
  "success": true,
  "data": {
    "content": [
      {
        "id": 1,
        "name": "Kopaonik Peak Trail",
        "destination": {
          "id": 1,
          "name": "Kopaonik",
          "country": "Serbia",
          "region": "Central Serbia",
          "city": "Kopaonik"
        },
        "length": 8.5,
        "elevationGain": 450,
        "estimatedTime": 240,
        "trailType": "out-and-back",
        "difficulty": "moderate",
        "description": "Beautiful trail leading to the highest peak of Kopaonik",
        "rating": 4.5,
        "reviewCount": 12,
        "activities": [
          {
            "id": 1,
            "name": "Hiking",
            "emoji": "🥾"
          }
        ]
      }
    ],
    "totalElements": 3,
    "totalPages": 1,
    "size": 10,
    "number": 0
  },
  "message": "Trails retrieved successfully"
}
```

#### GET /api/trails/{id}
Get a specific trail by ID.

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "Kopaonik Peak Trail",
    "destination": {...},
    "length": 8.5,
    "elevationGain": 450,
    "estimatedTime": 240,
    "trailType": "out-and-back",
    "difficulty": "moderate",
    "description": "Beautiful trail leading to the highest peak of Kopaonik",
    "rating": 4.5,
    "reviewCount": 12,
    "activities": [...]
  },
  "message": "Trail retrieved successfully"
}
```

### User Favorites

#### GET /api/trails/favorites
Get user's favorite trails (requires JWT authentication).

**Headers:**
```
Authorization: Bearer <jwt_token>
```

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "Kopaonik Peak Trail",
      "destination": {...},
      "length": 8.5,
      "difficulty": "moderate"
    }
  ],
  "message": "Favorite trails retrieved successfully"
}
```

#### POST /api/trails/{id}/favorite
Add a trail to user's favorites (requires JWT authentication).

**Headers:**
```
Authorization: Bearer <jwt_token>
```

**Response:**
```json
{
  "success": true,
  "data": "Trail added to favorites successfully",
  "message": "Trail added to favorites"
}
```

#### DELETE /api/trails/{id}/favorite
Remove a trail from user's favorites (requires JWT authentication).

**Headers:**
```
Authorization: Bearer <jwt_token>
```

**Response:**
```json
{
  "success": true,
  "data": "Trail removed from favorites successfully",
  "message": "Trail removed from favorites"
}
```

### Image Upload

#### POST /api/upload/image
Upload an image to Cloudinary.

**Form Data:**
- `file`: Image file (multipart/form-data)

**Response:**
```json
{
  "success": true,
  "data": {
    "url": "https://res.cloudinary.com/your-cloud/image/upload/v1234567890/hikeBook/pictureOfDestination/image.jpg",
    "publicId": null,
    "message": "Image uploaded successfully",
    "success": true
  },
  "message": "Image uploaded successfully"
}
```

#### POST /api/upload/image/{folder}
Upload an image to a specific folder in Cloudinary.

**Form Data:**
- `file`: Image file (multipart/form-data)

**Path Parameters:**
- `folder`: Folder name (e.g., "trails", "destinations", "users")

**Response:**
```json
{
  "success": true,
  "data": {
    "url": "https://res.cloudinary.com/your-cloud/image/upload/v1234567890/hikeBook/trails/image.jpg",
    "publicId": null,
    "message": "Image uploaded successfully",
    "success": true
  },
  "message": "Image uploaded successfully"
}
```

## Authentication

The API uses JWT (JSON Web Tokens) for authentication. To access protected endpoints:

1. **Sign up** or **Sign in** using the auth endpoints
2. Include the JWT token in the `Authorization` header: `Bearer <token>`
3. The token will be automatically validated and the user ID extracted

## Database Schema

The application uses your existing PostgreSQL database with the following main tables:

- **destination**: Geographic locations
- **activity**: Available activities (hiking, biking, etc.)
- **trail**: Hiking trails with details
- **user**: User accounts
- **favorite_trails**: User's favorite trails (many-to-many)
- **trail_activity**: Trail-activity relationships (many-to-many)
- **map**: Trail maps
- **reservation**: User reservations
- **review**: User reviews
- **conditions**: Trail conditions
- **photo**: Trail photos
- **comment**: Trail comments

## Setup and Running

1. **Database Setup:**
   - The application is configured to work with your existing PostgreSQL database
   - Update `application.properties` with your database credentials if needed
   - The application will validate the existing schema

2. **Cloudinary Setup:**
   - Create a free account at [Cloudinary](https://cloudinary.com/)
   - Get your Cloud Name, API Key, and API Secret from the dashboard
   - Set environment variables or update `application.properties`:
     ```properties
     cloudinary.cloud-name=your-cloud-name
     cloudinary.api-key=your-api-key
     cloudinary.api-secret=your-api-secret
     ```

3. **Run the Application:**
   ```bash
   ./gradlew bootRun
   ```

4. **API Testing:**
   - The API will be available at `http://localhost:8080`
   - Use tools like Postman or curl to test the endpoints
   - Your existing data will be available through the API endpoints

## Error Handling

All API responses follow a consistent format:

```json
{
  "success": false,
  "data": null,
  "message": "Error description"
}
```

Common error messages:
- "Authorization header is required" - Missing JWT token
- "Invalid token format" - Malformed JWT token
- "Invalid or expired token" - Invalid JWT token
- "Trail not found" - Trail doesn't exist
- "User not found" - User doesn't exist
- "Trail is already in favorites" - Duplicate favorite
- "Trail is not in favorites" - Trying to remove non-existent favorite 