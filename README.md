# VESTIGE
> *"Give Technology a Second Life."*

[![Build Status](https://img.shields.io/badge/Production%20Freeze-Phase%2016%20Complete-3A5C3A.svg)](#)
[![Java](https://img.shields.io/badge/Java-21%20LTS-6B4226.svg)](#)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-2D4A3E.svg)](#)
[![React](https://img.shields.io/badge/React-19.x-C9A84C.svg)](#)
[![Vite](https://img.shields.io/badge/Vite-8.x-A07850.svg)](#)

---

## 1. Overview

**VESTIGE** is a unified circular economy platform for consumer and enterprise electronics. It seamlessly connects device owners, certified repair ateliers, and certified zero-landfill recycling facilities.

### Core Philosophy
```
ASSESS → REPAIR → REUSE → RECYCLE → REWARD
```

---

## 2. System Architecture

VESTIGE is architected as a decoupled, multi-tier enterprise web application:

```
┌────────────────────────────────────────────────────────┐
│                   Client Tier                          │
│  React 19 + Vite 8 SPA                                 │
│  - User Portal (/user/*)                               │
│  - Vendor Portal (/vendor/*)                           │
│  - Admin Portal (/admin/*)                             │
│  - Public Landing, Portals & Multilingual i18n         │
└───────────────────────────┬────────────────────────────┘
                            │ HTTPS / REST (JSON)
┌───────────────────────────▼────────────────────────────┐
│                  Application Tier                      │
│  Java 21 + Spring Boot 3.4.3                           │
│  - Controller Layer: REST API routing & validation     │
│  - Service Layer: Business logic & orchestration       │
│  - Decision Engine: Device assessment & LCA scoring    │
│  - Payment Gateway: Razorpay HMAC SHA256 verification  │
│  - Security Layer: JWT verification & RBAC             │
└───────────────────────────┬────────────────────────────┘
                            │ JDBC / TLS
┌───────────────────────────▼────────────────────────────┐
│                    Data Tier                           │
│  Supabase PostgreSQL 15+ / In-Memory H2                │
│  - Row Level Security (RLS) policies                   │
│  - Transactional integrity & audit logs                │
└────────────────────────────────────────────────────────┘
```

---

## 3. Directory Layout

```
VESTIGE/
├── frontend/                     # React 19 + Vite client
│   ├── public/                   # Static assets, robots.txt, textures
│   ├── src/
│   │   ├── assets/               # Branding images, textures, icons
│   │   ├── components/           # UI, layout, shared widgets & charts
│   │   ├── contexts/             # AuthContext, UIContext
│   │   ├── hooks/                # Custom React hooks (useAuth, useUI, etc.)
│   │   ├── i18n/                 # Localization dictionaries (en, hi, ta, te, kn, ja, de, fr)
│   │   ├── pages/                # Public, User, Vendor, Admin views
│   │   ├── router/               # AppRouter & ProtectedRoute guards
│   │   ├── services/             # API client & REST services
│   │   ├── styles/               # Vintage design tokens & typography
│   │   └── utils/                # Formatters, validators, whatsapp links
│   ├── .env.example              # Safe environment variable template
│   └── vite.config.js            # Build & proxy configuration
│
├── backend/                      # Java Spring Boot REST API
│   ├── src/
│   │   ├── main/java/com/vestige/
│   │   │   ├── config/           # Security, CORS & Swagger configuration
│   │   │   ├── controller/       # REST API controllers
│   │   │   ├── dto/              # Request & Response data transfer objects
│   │   │   ├── engine/           # Decision Engine weighted scoring algorithm
│   │   │   ├── exception/        # Centralized Global Exception Handler
│   │   │   ├── model/            # JPA entities
│   │   │   ├── repository/       # Spring Data JPA repositories
│   │   │   ├── security/         # JWT filters and token utilities
│   │   │   └── service/          # Business logic services
│   │   └── resources/
│   │       ├── application.yml   # Base Spring Boot configuration
│   │       ├── application-dev.yml
│   │       └── application-prod.yml
│   ├── .env.example              # Safe environment variable template
│   └── pom.xml                   # Maven dependencies & build lifecycle
│
├── docs/                         # Specifications & Runbooks
│   ├── architecture/             # Architecture overview & diagrams
│   ├── api/                      # REST API endpoint contracts
│   ├── design/                   # Vintage design tokens & typography scale
│   ├── intelligence/             # Phase 15 methodology & audit reports
│   └── operations/               # Production runbook & backup protocols
│
├── .gitignore                    # Monorepo Git exclusion rules
└── README.md                     # Project documentation
```

---

## 4. Prerequisites

- **Node.js**: v20+ or v22+
- **Java Development Kit (JDK)**: OpenJDK 21 LTS
- **Git**: v2.40+

---

## 5. Getting Started

### 5.1 Backend Setup
```bash
cd backend

# Run automated tests
./mvnw clean test

# Start the Spring Boot application (dev profile with in-memory H2)
./mvnw spring-boot:run
```
The REST API starts on `http://localhost:8088`.

Health endpoints:
- Liveness: `GET http://localhost:8088/api/health`
- Readiness: `GET http://localhost:8088/api/health/readiness`

### 5.2 Frontend Setup
```bash
cd frontend

# Install dependencies
npm install

# Start Vite development server
npm run dev
```
The frontend interface is accessible at `http://localhost:5173`.

---

## 6. Testing & Quality Assurance

- **Backend Unit & Integration Tests**:
  ```bash
  cd backend
  ./mvnw test
  ```
  *Result*: 55 / 55 tests pass (0 failures).

- **Frontend Linter & Production Build**:
  ```bash
  cd frontend
  npm run lint
  npm run build
  ```
  *Result*: Clean compilation with chunk splitting.

---

## 7. Security Notes

- No secrets or credentials are hardcoded or tracked in Git.
- Client applications read only public/publishable variables prefixed with `VITE_`.
- Server-side credentials (database credentials, service-role keys, Razorpay secret keys) remain strictly in environment variables.
- All endpoints follow strict Role-Based Access Control (RBAC) and IDOR ownership verification.
