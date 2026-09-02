-- ==============================================================================
-- VESTIGE DATABASE MIGRATION 01: EXTENSIONS & CUSTOM ENUM TYPES
-- ==============================================================================

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- User Roles
DO $$ BEGIN
  CREATE TYPE user_role AS ENUM ('USER', 'VENDOR', 'ADMIN');
EXCEPTION
  WHEN duplicate_object THEN null;
END $$;

-- Preferred Languages
DO $$ BEGIN
  CREATE TYPE preferred_language AS ENUM ('en', 'ta', 'te', 'ja');
EXCEPTION
  WHEN duplicate_object THEN null;
END $$;

-- Vendor Verification Statuses
DO $$ BEGIN
  CREATE TYPE vendor_verification_status AS ENUM ('PENDING', 'VERIFIED', 'REJECTED');
EXCEPTION
  WHEN duplicate_object THEN null;
END $$;

-- Device Category Types
DO $$ BEGIN
  CREATE TYPE device_category_type AS ENUM ('SMARTPHONE', 'LAPTOP', 'TABLET', 'DESKTOP', 'OTHER');
EXCEPTION
  WHEN duplicate_object THEN null;
END $$;

-- Device Condition Grades
DO $$ BEGIN
  CREATE TYPE device_condition_grade AS ENUM ('POOR', 'FAIR', 'GOOD');
EXCEPTION
  WHEN duplicate_object THEN null;
END $$;

-- Part Availability Status
DO $$ BEGIN
  CREATE TYPE part_availability_status AS ENUM ('AVAILABLE', 'UNKNOWN', 'UNAVAILABLE');
EXCEPTION
  WHEN duplicate_object THEN null;
END $$;

-- Decision Engine Recommendations
DO $$ BEGIN
  CREATE TYPE engine_recommendation_type AS ENUM ('REPAIR', 'RECYCLE');
EXCEPTION
  WHEN duplicate_object THEN null;
END $$;

-- Decision Engine Confidence Levels
DO $$ BEGIN
  CREATE TYPE engine_confidence_level AS ENUM ('HIGH', 'MEDIUM', 'LOW');
EXCEPTION
  WHEN duplicate_object THEN null;
END $$;

-- Repair Booking Statuses
DO $$ BEGIN
  CREATE TYPE booking_status_type AS ENUM ('PENDING', 'ACCEPTED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED', 'REJECTED');
EXCEPTION
  WHEN duplicate_object THEN null;
END $$;

-- Recycling Request Statuses
DO $$ BEGIN
  CREATE TYPE recycling_status_type AS ENUM ('PENDING', 'ACCEPTED', 'SCHEDULED', 'COMPLETED', 'CANCELLED');
EXCEPTION
  WHEN duplicate_object THEN null;
END $$;

-- Reward Transaction Types
DO $$ BEGIN
  CREATE TYPE reward_transaction_type AS ENUM ('EARNED', 'REDEEMED');
EXCEPTION
  WHEN duplicate_object THEN null;
END $$;

-- Reward Source Types
DO $$ BEGIN
  CREATE TYPE reward_source_type AS ENUM ('RECYCLING_PICKUP', 'REPAIR_COMPLETION', 'REDEMPTION', 'ADMIN_ADJUSTMENT');
EXCEPTION
  WHEN duplicate_object THEN null;
END $$;

-- Redemption Statuses
DO $$ BEGIN
  CREATE TYPE redemption_status_type AS ENUM ('PENDING', 'FULFILLED', 'CANCELLED');
EXCEPTION
  WHEN duplicate_object THEN null;
END $$;
