# VESTIGE — Architecture Overview

## 1. System Architecture

VESTIGE is structured as a decoupled multi-tier web application designed for high reliability, maintainability, and clean separation of concerns.

```
┌────────────────────────────────────────────────────────┐
│                   Client Tier                          │
│  React 19 + Vite 8 SPA (Deployed on Vercel)            │
│  - User Portal (/user/*)                               │
│  - Vendor Portal (/vendor/*)                           │
│  - Admin Portal (/admin/*)                             │
└───────────────────────────┬────────────────────────────┘
                            │ HTTPS / REST (JSON)
┌───────────────────────────▼────────────────────────────┐
│                  Application Tier                      │
│  Java 21 + Spring Boot 3.4.3 (Deployed on Render)      │
│  - Controller Layer: REST API routing & validation     │
│  - Service Layer: Business logic & orchestration       │
│  - Decision Engine: Device repair vs recycle scoring   │
│  - Security Layer: JWT verification & RBAC             │
└───────────────────────────┬────────────────────────────┘
                            │ JDBC / TLS
┌───────────────────────────▼────────────────────────────┐
│                    Data Tier                           │
│  Supabase Cloud (PostgreSQL 15+)                       │
│  - Row Level Security (RLS) policies                   │
│  - Supabase Storage for vendor document uploads        │
└────────────────────────────────────────────────────────┘
```

## 2. Directory Layout

```
VESTIGE/
├── frontend/                     # React + Vite client
│   ├── public/                   # Static assets & textures
│   └── src/
│       ├── assets/               # Fonts, icons, branding images
│       ├── components/           # UI, layout, and shared widgets
│       │   ├── ui/               # Atoms: Button, Card, Badge, Input, Modal, Toast
│       │   ├── layout/           # Navbar, Footer, Sidebar, PageWrapper
│       │   └── shared/           # StatusBadge, LanguageSelector, WhatsAppButton
│       ├── contexts/             # AuthContext, UIContext
│       ├── hooks/                # Custom React hooks (useAuth, useUI, etc.)
│       ├── pages/                # Public, User, Vendor, Admin views
│       ├── router/               # AppRouter & ProtectedRoute guards
│       ├── services/             # API client & REST services
│       ├── styles/               # Design tokens, typography & animations
│       └── utils/                # WhatsApp link generators, formatters, validators
│
├── backend/                      # Java Spring Boot REST API
│   └── src/
│       ├── main/java/com/vestige/
│       │   ├── config/           # Security, CORS & Swagger configuration
│       │   ├── controller/       # REST API controllers
│       │   ├── dto/              # Request & Response data transfer objects
│       │   ├── engine/           # Decision Engine weighted scoring algorithm
│       │   ├── exception/        # Global exception handler & custom exceptions
│       │   ├── model/            # JPA entities (User, Vendor, Device, Booking, etc.)
│       │   ├── repository/       # Spring Data JPA repositories
│       │   ├── security/         # JWT filters and token utilities
│       │   └── service/          # Business logic services
│       └── main/resources/
│           ├── application.yml   # Base Spring Boot configuration
│           ├── application-dev.yml
│           └── application-prod.yml
│
├── docs/                         # Architecture, API & Design specifications
├── .gitignore                    # Root Git ignore configuration
└── README.md                     # Root project documentation
```

## 3. Technology Stack Reference

| Domain | Technology | Purpose |
|---|---|---|
| Frontend Framework | React 19.x | Component-based client interface |
| Frontend Bundler | Vite 8.x | High-performance build tool |
| Styling | CSS Modules + Vanilla CSS | Scoped vintage design tokens |
| Backend Runtime | Java 21 LTS (OpenJDK) | Strongly-typed backend platform |
| Backend Framework | Spring Boot 3.4.3 | Enterprise REST API server |
| Testing | JUnit 5, Mockito, MockMvc | Automated unit and integration testing |
| Database (Phase 2+) | Supabase PostgreSQL | Relational database with RLS |
