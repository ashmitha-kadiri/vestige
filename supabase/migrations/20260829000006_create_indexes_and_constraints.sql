-- ==============================================================================
-- VESTIGE DATABASE MIGRATION 06: INDEXES & PERFORMANCE OPTIMIZATIONS
-- ==============================================================================

-- 1. Users Indexes
CREATE INDEX IF NOT EXISTS idx_users_email ON public.users (email);
CREATE INDEX IF NOT EXISTS idx_users_role_status ON public.users (role, is_active);

-- 2. Vendor Profiles Indexes
CREATE INDEX IF NOT EXISTS idx_vendor_profiles_user_id ON public.vendor_profiles (user_id);
CREATE INDEX IF NOT EXISTS idx_vendor_profiles_status ON public.vendor_profiles (verification_status);
CREATE INDEX IF NOT EXISTS idx_vendor_profiles_city_status ON public.vendor_profiles (city, verification_status);
CREATE INDEX IF NOT EXISTS idx_vendor_profiles_services ON public.vendor_profiles USING GIN (service_types);
CREATE INDEX IF NOT EXISTS idx_vendor_profiles_categories ON public.vendor_profiles USING GIN (device_categories);

-- 3. Vendor Documents Indexes
CREATE INDEX IF NOT EXISTS idx_vendor_documents_vendor_id ON public.vendor_documents (vendor_id);

-- 4. Device Submissions Indexes
CREATE INDEX IF NOT EXISTS idx_device_submissions_user_id ON public.device_submissions (user_id);
CREATE INDEX IF NOT EXISTS idx_device_submissions_recommendation ON public.device_submissions (engine_recommendation);
CREATE INDEX IF NOT EXISTS idx_device_submissions_created_at ON public.device_submissions (created_at DESC);

-- 5. Repair Bookings Indexes
CREATE INDEX IF NOT EXISTS idx_repair_bookings_user_id ON public.repair_bookings (user_id);
CREATE INDEX IF NOT EXISTS idx_repair_bookings_vendor_id ON public.repair_bookings (vendor_id);
CREATE INDEX IF NOT EXISTS idx_repair_bookings_status ON public.repair_bookings (status);
CREATE INDEX IF NOT EXISTS idx_repair_bookings_date ON public.repair_bookings (preferred_date);

-- 6. Repair Status History Indexes
CREATE INDEX IF NOT EXISTS idx_repair_status_history_booking_id ON public.repair_status_history (booking_id);
CREATE INDEX IF NOT EXISTS idx_repair_status_history_created_at ON public.repair_status_history (created_at DESC);

-- 7. Recycling Requests Indexes
CREATE INDEX IF NOT EXISTS idx_recycling_requests_user_id ON public.recycling_requests (user_id);
CREATE INDEX IF NOT EXISTS idx_recycling_requests_vendor_id ON public.recycling_requests (vendor_id);
CREATE INDEX IF NOT EXISTS idx_recycling_requests_status ON public.recycling_requests (status);
CREATE INDEX IF NOT EXISTS idx_recycling_requests_date ON public.recycling_requests (pickup_date);

-- 8. Recycling Status History Indexes
CREATE INDEX IF NOT EXISTS idx_recycling_status_history_request_id ON public.recycling_status_history (request_id);
CREATE INDEX IF NOT EXISTS idx_recycling_status_history_created_at ON public.recycling_status_history (created_at DESC);

-- 9. Reward Accounts Indexes
CREATE INDEX IF NOT EXISTS idx_reward_accounts_user_id ON public.reward_accounts (user_id);

-- 10. Reward Transactions Indexes
CREATE INDEX IF NOT EXISTS idx_reward_transactions_account_id ON public.reward_transactions (account_id);
CREATE INDEX IF NOT EXISTS idx_reward_transactions_created_at ON public.reward_transactions (created_at DESC);

-- 11. Redemptions Indexes
CREATE INDEX IF NOT EXISTS idx_redemptions_user_id ON public.redemptions (user_id);
CREATE INDEX IF NOT EXISTS idx_redemptions_status ON public.redemptions (status);
CREATE INDEX IF NOT EXISTS idx_redemptions_created_at ON public.redemptions (created_at DESC);

-- 12. Admin Actions Indexes
CREATE INDEX IF NOT EXISTS idx_admin_actions_admin_id ON public.admin_actions (admin_id);
CREATE INDEX IF NOT EXISTS idx_admin_actions_action_type ON public.admin_actions (action_type);
CREATE INDEX IF NOT EXISTS idx_admin_actions_created_at ON public.admin_actions (created_at DESC);
