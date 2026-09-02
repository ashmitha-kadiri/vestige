-- ==============================================================================
-- VESTIGE DATABASE MIGRATION 08: STORAGE BUCKET CONFIGURATION
-- Dedicated private bucket for vendor verification documents.
-- Backend accesses via Supabase service-role key which bypasses RLS by design.
-- ==============================================================================

DO $$
BEGIN
  IF EXISTS (SELECT FROM information_schema.schemata WHERE schema_name = 'storage') THEN
    INSERT INTO storage.buckets (id, name, public, file_size_limit, allowed_mime_types)
    VALUES (
      'vendor-verification-documents',
      'vendor-verification-documents',
      false, -- Strictly private
      5242880, -- 5 MB limit per document
      ARRAY['application/pdf', 'image/jpeg', 'image/png']::text[]
    )
    ON CONFLICT (id) DO UPDATE SET
      public = false,
      file_size_limit = 5242880,
      allowed_mime_types = ARRAY['application/pdf', 'image/jpeg', 'image/png']::text[];
  END IF;
END
$$;
