-- ==============================================================================
-- VESTIGE DATABASE MIGRATION 05: REWARD ACCOUNTS, TRANSACTIONS, REDEMPTIONS & AUDIT LOGS
-- ==============================================================================

-- 9. REWARD ACCOUNTS TABLE
CREATE TABLE IF NOT EXISTS public.reward_accounts (
  id                UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id           UUID NOT NULL,
  balance           INTEGER NOT NULL DEFAULT 0,
  lifetime_earned   INTEGER NOT NULL DEFAULT 0,
  lifetime_redeemed INTEGER NOT NULL DEFAULT 0,
  updated_at        TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT uq_reward_accounts_user_id UNIQUE (user_id),
  CONSTRAINT fk_reward_accounts_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE,
  CONSTRAINT chk_reward_balance_positive CHECK (balance >= 0),
  CONSTRAINT chk_lifetime_earned_positive CHECK (lifetime_earned >= 0),
  CONSTRAINT chk_lifetime_redeemed_positive CHECK (lifetime_redeemed >= 0)
);

-- 10. REWARD TRANSACTIONS TABLE (APPEND-ONLY AUDIT LOG)
CREATE TABLE IF NOT EXISTS public.reward_transactions (
  id                UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  account_id        UUID NOT NULL,
  points            INTEGER NOT NULL,
  transaction_type  reward_transaction_type NOT NULL,
  source            reward_source_type NOT NULL,
  reference_id      UUID,
  description       TEXT,
  created_at        TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT fk_reward_transactions_account FOREIGN KEY (account_id) REFERENCES public.reward_accounts(id) ON DELETE CASCADE,
  CONSTRAINT chk_reward_transaction_points_positive CHECK (points > 0)
);

-- 11. REDEMPTIONS TABLE
CREATE TABLE IF NOT EXISTS public.redemptions (
  id                UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id           UUID NOT NULL,
  reward_item       VARCHAR(150) NOT NULL,
  points_used       INTEGER NOT NULL,
  status            redemption_status_type NOT NULL DEFAULT 'PENDING',
  fulfillment_notes TEXT,
  created_at        TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at        TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT fk_redemptions_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE RESTRICT,
  CONSTRAINT chk_redemption_points_positive CHECK (points_used > 0)
);

-- 12. ADMIN ACTIONS TABLE (APPEND-ONLY AUDIT LOG)
CREATE TABLE IF NOT EXISTS public.admin_actions (
  id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  admin_id      UUID NOT NULL,
  action_type   VARCHAR(100) NOT NULL,
  target_entity VARCHAR(100) NOT NULL,
  target_id     UUID NOT NULL,
  details       JSONB DEFAULT '{}'::jsonb,
  ip_address    VARCHAR(45),
  created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT fk_admin_actions_admin FOREIGN KEY (admin_id) REFERENCES public.users(id) ON DELETE RESTRICT
);
