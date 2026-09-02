-- ==============================================================================
-- VESTIGE DATABASE MIGRATION 02: USERS, VENDOR PROFILES & DOCUMENTS
-- ==============================================================================

-- 1. USERS TABLE
CREATE TABLE IF NOT EXISTS public.users (
  id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  full_name       VARCHAR(100) NOT NULL,
  email           VARCHAR(150) NOT NULL,
  password_hash   VARCHAR(255) NOT NULL,
  phone           VARCHAR(20),
  role            user_role NOT NULL DEFAULT 'USER',
  preferred_lang  preferred_language NOT NULL DEFAULT 'en',
  is_active       BOOLEAN NOT NULL DEFAULT true,
  created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT uq_users_email UNIQUE (email),
  CONSTRAINT chk_users_email_format CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);

-- 2. VENDOR PROFILES TABLE
CREATE TABLE IF NOT EXISTS public.vendor_profiles (
  id                    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id               UUID NOT NULL,
  business_name         VARCHAR(150) NOT NULL,
  business_type         VARCHAR(100),
  address               TEXT NOT NULL,
  city                  VARCHAR(100) NOT NULL,
  state                 VARCHAR(100) NOT NULL,
  pincode               VARCHAR(10) NOT NULL,
  whatsapp_number       VARCHAR(20),
  operating_hours       JSONB DEFAULT '{}'::jsonb,
  service_types         TEXT[] NOT NULL DEFAULT ARRAY['REPAIR']::TEXT[],
  device_categories     TEXT[] NOT NULL DEFAULT ARRAY['SMARTPHONE']::TEXT[],
  verification_status   vendor_verification_status NOT NULL DEFAULT 'PENDING',
  rejection_reason      TEXT,
  rating_avg            DECIMAL(3,2) NOT NULL DEFAULT 0.00,
  rating_count          INTEGER NOT NULL DEFAULT 0,
  created_at            TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at            TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT uq_vendor_profiles_user_id UNIQUE (user_id),
  CONSTRAINT fk_vendor_profiles_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE RESTRICT,
  CONSTRAINT chk_vendor_rating_range CHECK (rating_avg >= 0.00 AND rating_avg <= 5.00),
  CONSTRAINT chk_vendor_rating_count CHECK (rating_count >= 0),
  CONSTRAINT chk_vendor_rejection_reason CHECK (
    (verification_status = 'REJECTED' AND rejection_reason IS NOT NULL AND trim(rejection_reason) <> '')
    OR (verification_status != 'REJECTED')
  )
);

-- 3. VENDOR DOCUMENTS TABLE
CREATE TABLE IF NOT EXISTS public.vendor_documents (
  id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  vendor_id       UUID NOT NULL,
  document_type   VARCHAR(100) NOT NULL, -- e.g. 'BUSINESS_REGISTRATION', 'GOVERNMENT_ID', 'CERTIFICATION'
  file_url        TEXT NOT NULL,
  file_name       VARCHAR(255),
  file_size_bytes BIGINT,
  mime_type       VARCHAR(100),
  uploaded_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT fk_vendor_documents_vendor FOREIGN KEY (vendor_id) REFERENCES public.vendor_profiles(id) ON DELETE CASCADE
);
