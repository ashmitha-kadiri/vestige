-- ==============================================================================
-- VESTIGE DATABASE MIGRATION 07: SECURITY STRATEGY (OPTION B)
-- Dedicated Spring Boot application role, least-privilege grants, and anon/authenticated revocation.
-- ==============================================================================

-- 1. Create dedicated application role 'vestige_app' if not already present.
-- NOTE: Password is NOT embedded here; set out-of-band via Supabase CLI / project secrets.
DO $$
BEGIN
  IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'vestige_app') THEN
    CREATE ROLE vestige_app WITH
      LOGIN
      NOSUPERUSER
      NOBYPASSRLS
      NOCREATEDB
      NOCREATEROLE
      NOREPLICATION;
  END IF;
END
$$;

-- Ensure schema usage
GRANT USAGE ON SCHEMA public TO vestige_app;

-- 2. Explicit Table-Level Grants to 'vestige_app' (Least-Privilege: SELECT, INSERT, UPDATE only)
-- Note: NO DELETE, NO TRUNCATE, NO DDL granted on any application table.
GRANT SELECT, INSERT, UPDATE ON public.users TO vestige_app;
GRANT SELECT, INSERT, UPDATE ON public.vendor_profiles TO vestige_app;
GRANT SELECT, INSERT, UPDATE ON public.vendor_documents TO vestige_app;
GRANT SELECT, INSERT, UPDATE ON public.device_submissions TO vestige_app;
GRANT SELECT, INSERT, UPDATE ON public.repair_bookings TO vestige_app;
GRANT SELECT, INSERT, UPDATE ON public.repair_status_history TO vestige_app;
GRANT SELECT, INSERT, UPDATE ON public.recycling_requests TO vestige_app;
GRANT SELECT, INSERT, UPDATE ON public.recycling_status_history TO vestige_app;
GRANT SELECT, INSERT, UPDATE ON public.reward_accounts TO vestige_app;
GRANT SELECT, INSERT, UPDATE ON public.reward_transactions TO vestige_app;
GRANT SELECT, INSERT, UPDATE ON public.redemptions TO vestige_app;
GRANT SELECT, INSERT, UPDATE ON public.admin_actions TO vestige_app;

-- Grant sequence usage if any future sequence is added
GRANT USAGE ON ALL SEQUENCES IN SCHEMA public TO vestige_app;

-- 3. Explicitly Neutralize & Revoke all privileges from 'anon' and 'authenticated' roles on public schema
REVOKE ALL ON ALL TABLES IN SCHEMA public FROM anon, authenticated;
REVOKE ALL ON ALL SEQUENCES IN SCHEMA public FROM anon, authenticated;
REVOKE ALL ON ALL ROUTINES IN SCHEMA public FROM anon, authenticated;

-- Ensure Supabase default privileges do not grant future public tables to anon/authenticated
ALTER DEFAULT PRIVILEGES IN SCHEMA public REVOKE ALL ON TABLES FROM anon, authenticated;
ALTER DEFAULT PRIVILEGES IN SCHEMA public REVOKE ALL ON SEQUENCES FROM anon, authenticated;
ALTER DEFAULT PRIVILEGES IN SCHEMA public REVOKE ALL ON ROUTINES FROM anon, authenticated;

-- Ensure vestige_app is NOT granted any access to the Supabase internal auth schema
REVOKE ALL ON SCHEMA auth FROM vestige_app;
ALTER DEFAULT PRIVILEGES IN SCHEMA auth REVOKE ALL ON TABLES FROM vestige_app;
