# VESTIGE — Production Operational Runbook (Phase 14)

## 1. System Health & Probes
- **Liveness Probe**: `GET /api/health` — Returns basic service metadata and uptime in seconds.
- **Readiness Probe**: `GET /api/health/readiness` — Tests active database connectivity via Hikari pool and core subsystem status.
- **Uptime Monitoring**: Configure an external ping monitor (e.g., BetterStack, Pingdom, or Render health check) to probe `https://<domain>/api/health` every 60 seconds.

## 2. Backup & Data Recovery Protocol
- **Primary Database**: Supabase PostgreSQL 15+.
- **Backup Frequency**: Daily automated WAL-level backups with 7-day point-in-time recovery (PITR) enabled in the Supabase production dashboard.
- **Restoration Procedure**:
  1. Access the Supabase Project Settings ➔ Database ➔ Backups.
  2. Select target restoration timestamp.
  3. Spin up a staging clone or restore directly to a staging branch to verify foreign key integrity.
  4. Perform smoke test using `./mvnw.cmd test` and verify user records and balances.

## 3. Incident Response Protocol
1. **DETECT**: Automated alert triggers from health probe or error logging threshold (>5% 5xx rate over 5 min).
2. **CONTAIN**: Divert traffic to the static archival maintenance page if database corruption is detected; isolate compromised API keys immediately.
3. **FIX**: Apply targeted hotfix or rollback to the prior verified immutable release artifact.
4. **VERIFY**: Execute automated test suite (`.\mvnw.cmd test` and `npm run build`) and perform authenticated test login.
5. **COMMUNICATE**: Publish status update on system status channels.
6. **PREVENT RECURRENCE**: Log post-mortem incident report and add regression test case.

## 4. Rollback Readiness
- **Frontend (Vercel / Static CDN)**: Instant rollback to previous deployment hash via the deployment dashboard.
- **Backend (Render / Docker)**: Deploy prior tagged container image (`vestige-backend:vX.Y.Z`).
- **Database Schema**: All migrations are forward-compatible and non-destructive. Destructive schema drops are strictly forbidden in production.

## 5. Security & Logging Hygiene
- Sensitive parameters (passwords, JWT secrets, Razorpay API secret keys, user credentials) are strictly excluded from logging.
- Global exception handler maps exceptions to sanitized client responses.
- Method security (`@PreAuthorize`) and Spring Security role-based filters guard all admin, vendor, and user resources.
