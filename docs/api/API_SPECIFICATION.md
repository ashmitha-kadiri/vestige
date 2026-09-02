# VESTIGE — REST API Specification

## 1. Protocol & Conventions

- **Base URL (Dev):** `http://localhost:8080/api`
- **Base URL (Prod):** `https://vestige-backend.onrender.com/api`
- **Data Format:** JSON (`application/json;charset=UTF-8`)
- **Authentication (Phase 2+):** Bearer JWT Token in `Authorization: Bearer <token>` header

## 2. Standard Envelope Formats

### Success Response
```json
{
  "success": true,
  "message": "Human readable status message",
  "data": { ... },
  "timestamp": "2026-08-29T00:00:00.000Z"
}
```

### Error Response
```json
{
  "success": false,
  "message": "Error description",
  "data": null,
  "timestamp": "2026-08-29T00:00:00.000Z"
}
```

## 3. Endpoints Map

### Phase 1: Core Health & Readiness

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `GET` | `/api/health` | Service uptime and status check | No |

#### Example: `GET /api/health`
**Response (200 OK):**
```json
{
  "success": true,
  "message": "Vestige Backend Service is running",
  "data": {
    "status": "UP",
    "service": "vestige-backend",
    "environment": "dev",
    "version": "0.0.1-SNAPSHOT",
    "timestamp": "2026-08-29T00:00:00.000Z"
  },
  "timestamp": "2026-08-29T00:00:00.000Z"
}
```

---

### Phase 2+ Planned Endpoints

- **Authentication:** `POST /api/auth/register`, `POST /api/auth/login`, `POST /api/auth/logout`
- **Device Assessment:** `POST /api/devices/submit`, `GET /api/devices/{id}/recommendation`
- **Vendor Discovery:** `GET /api/vendors`, `GET /api/vendors/{id}`
- **Repair Bookings:** `POST /api/bookings`, `GET /api/bookings/my`, `PATCH /api/bookings/{id}/status`
- **Recycling & Rewards:** `POST /api/recycling`, `GET /api/rewards/balance`, `POST /api/rewards/redeem`
- **Admin Management:** `GET /api/admin/dashboard`, `PATCH /api/admin/vendors/{id}/verify`
