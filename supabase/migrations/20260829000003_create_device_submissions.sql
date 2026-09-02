-- ==============================================================================
-- VESTIGE DATABASE MIGRATION 03: DEVICE SUBMISSIONS
-- ==============================================================================

-- 4. DEVICE SUBMISSIONS TABLE
CREATE TABLE IF NOT EXISTS public.device_submissions (
  id                    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id               UUID NOT NULL,
  device_type           device_category_type NOT NULL,
  brand                 VARCHAR(100) NOT NULL,
  model                 VARCHAR(100) NOT NULL,
  device_age_years      INTEGER NOT NULL,
  condition             device_condition_grade NOT NULL,
  known_issues          TEXT[] DEFAULT ARRAY[]::TEXT[],
  estimated_repair_cost DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  original_value        DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  part_availability     part_availability_status NOT NULL DEFAULT 'UNKNOWN',
  engine_score          INTEGER,
  engine_recommendation engine_recommendation_type,
  engine_confidence     engine_confidence_level,
  engine_rationale      TEXT,
  created_at            TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT fk_device_submissions_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE RESTRICT,
  CONSTRAINT chk_device_age_positive CHECK (device_age_years >= 0),
  CONSTRAINT chk_repair_cost_positive CHECK (estimated_repair_cost >= 0.00),
  CONSTRAINT chk_original_value_positive CHECK (original_value >= 0.00),
  CONSTRAINT chk_engine_score_range CHECK (engine_score IS NULL OR (engine_score >= 0 AND engine_score <= 100))
);
