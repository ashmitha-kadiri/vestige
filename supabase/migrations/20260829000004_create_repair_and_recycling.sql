-- ==============================================================================
-- VESTIGE DATABASE MIGRATION 04: REPAIR & RECYCLING WORKFLOWS & STATUS AUDIT TRAILS
-- ==============================================================================

-- 5. REPAIR BOOKINGS TABLE
CREATE TABLE IF NOT EXISTS public.repair_bookings (
  id                UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id           UUID NOT NULL,
  vendor_id         UUID NOT NULL,
  submission_id     UUID NOT NULL,
  preferred_date    DATE NOT NULL,
  preferred_time    TIME,
  issue_description TEXT NOT NULL,
  status            booking_status_type NOT NULL DEFAULT 'PENDING',
  rejection_reason  TEXT,
  user_rating       INTEGER,
  user_feedback     TEXT,
  created_at        TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at        TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT fk_repair_bookings_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE RESTRICT,
  CONSTRAINT fk_repair_bookings_vendor FOREIGN KEY (vendor_id) REFERENCES public.vendor_profiles(id) ON DELETE RESTRICT,
  CONSTRAINT fk_repair_bookings_submission FOREIGN KEY (submission_id) REFERENCES public.device_submissions(id) ON DELETE RESTRICT,
  CONSTRAINT chk_booking_rating_range CHECK (user_rating IS NULL OR (user_rating >= 1 AND user_rating <= 5))
);

-- 6. REPAIR STATUS HISTORY TABLE (APPEND-ONLY AUDIT LOG)
CREATE TABLE IF NOT EXISTS public.repair_status_history (
  id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  booking_id          UUID NOT NULL,
  previous_status     booking_status_type,
  new_status          booking_status_type NOT NULL,
  changed_by_user_id  UUID NOT NULL,
  notes               TEXT,
  created_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT fk_repair_history_booking FOREIGN KEY (booking_id) REFERENCES public.repair_bookings(id) ON DELETE CASCADE,
  CONSTRAINT fk_repair_history_user FOREIGN KEY (changed_by_user_id) REFERENCES public.users(id) ON DELETE RESTRICT
);

-- 7. RECYCLING REQUESTS TABLE
CREATE TABLE IF NOT EXISTS public.recycling_requests (
  id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id         UUID NOT NULL,
  vendor_id       UUID NOT NULL,
  submission_id   UUID NOT NULL,
  pickup_address  TEXT NOT NULL,
  pickup_date     DATE NOT NULL,
  pickup_time     TIME,
  device_count    INTEGER NOT NULL DEFAULT 1,
  status          recycling_status_type NOT NULL DEFAULT 'PENDING',
  points_awarded  INTEGER NOT NULL DEFAULT 0,
  created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT fk_recycling_requests_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE RESTRICT,
  CONSTRAINT fk_recycling_requests_vendor FOREIGN KEY (vendor_id) REFERENCES public.vendor_profiles(id) ON DELETE RESTRICT,
  CONSTRAINT fk_recycling_requests_submission FOREIGN KEY (submission_id) REFERENCES public.device_submissions(id) ON DELETE RESTRICT,
  CONSTRAINT chk_recycling_device_count CHECK (device_count >= 1),
  CONSTRAINT chk_recycling_points_positive CHECK (points_awarded >= 0)
);

-- 8. RECYCLING STATUS HISTORY TABLE (APPEND-ONLY AUDIT LOG)
CREATE TABLE IF NOT EXISTS public.recycling_status_history (
  id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  request_id          UUID NOT NULL,
  previous_status     recycling_status_type,
  new_status          recycling_status_type NOT NULL,
  changed_by_user_id  UUID NOT NULL,
  notes               TEXT,
  created_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT fk_recycling_history_request FOREIGN KEY (request_id) REFERENCES public.recycling_requests(id) ON DELETE CASCADE,
  CONSTRAINT fk_recycling_history_user FOREIGN KEY (changed_by_user_id) REFERENCES public.users(id) ON DELETE RESTRICT
);
