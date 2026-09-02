-- ==============================================================================
-- VESTIGE DATABASE MIGRATION 09: INITIAL SEED DATA (ADMIN INITIALIZATION)
-- ==============================================================================

-- Seed Default Platform Administrative Officer (Pass: 'VestigeAdmin2026!')
-- BCrypt 12-round hash: $2a$12$e6mZt65P3y21r5XNf2lRxe46GZ/P698UjUa9lX19b52wYQ0p3H90. (Placeholder)
INSERT INTO public.users (
  id,
  full_name,
  email,
  password_hash,
  phone,
  role,
  preferred_lang,
  is_active,
  created_at,
  updated_at
)
VALUES (
  '00000000-0000-0000-0000-000000000001',
  'VESTIGE Master Registrar',
  'admin@vestige.internal',
  '$2a$12$e6mZt65P3y21r5XNf2lRxe46GZ/P698UjUa9lX19b52wYQ0p3H90.',
  '+910000000000',
  'ADMIN',
  'en',
  true,
  now(),
  now()
)
ON CONFLICT (email) DO NOTHING;
