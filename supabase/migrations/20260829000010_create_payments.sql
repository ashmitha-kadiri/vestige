-- ==============================================================================
-- VESTIGE DATABASE MIGRATION 10: USER PAYMENT SYSTEM (RAZORPAY INTEGRATION)
-- ==============================================================================

-- 1. Payment Status Enum
DO $$ BEGIN
  CREATE TYPE payment_status_type AS ENUM ('PENDING', 'SUCCESS', 'FAILED', 'CANCELLED');
EXCEPTION
  WHEN duplicate_object THEN null;
END $$;

-- 2. Payments Table
CREATE TABLE IF NOT EXISTS public.payments (
  id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id             UUID NOT NULL,
  related_entity_type VARCHAR(50) NOT NULL, -- e.g. 'REPAIR_BOOKING', 'RECYCLING_LOGISTICS'
  related_entity_id   UUID NOT NULL,
  provider            VARCHAR(50) NOT NULL DEFAULT 'razorpay',
  provider_order_id   VARCHAR(100) NOT NULL,
  provider_payment_id VARCHAR(100),
  amount              DECIMAL(10,2) NOT NULL,
  currency            VARCHAR(10) NOT NULL DEFAULT 'INR',
  status              payment_status_type NOT NULL DEFAULT 'PENDING',
  failure_reason      TEXT,
  created_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at          TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT fk_payments_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE RESTRICT,
  CONSTRAINT chk_payments_amount_positive CHECK (amount > 0)
);

-- Indexes for performance & auditability
CREATE INDEX IF NOT EXISTS idx_payments_user_id ON public.payments(user_id);
CREATE INDEX IF NOT EXISTS idx_payments_provider_order_id ON public.payments(provider_order_id);
CREATE INDEX IF NOT EXISTS idx_payments_status ON public.payments(status);
CREATE INDEX IF NOT EXISTS idx_payments_related_entity ON public.payments(related_entity_type, related_entity_id);
CREATE INDEX IF NOT EXISTS idx_payments_created_at ON public.payments(created_at);

-- Grants for application role
GRANT SELECT, INSERT, UPDATE ON public.payments TO vestige_app;
