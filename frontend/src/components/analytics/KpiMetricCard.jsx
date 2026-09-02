import React from 'react';
import LedgerCard from '../vintage/LedgerCard/LedgerCard';
import WaxSealBadge from '../vintage/WaxSealBadge/WaxSealBadge';
import IconWrapper from '../common/IconWrapper/IconWrapper';

export function KpiMetricCard({ title, value, subtitle, icon, variant = 'gold', badgeLabel, loading = false, error = null, onRetry }) {
  if (loading) {
    return (
      <LedgerCard variant="default">
        <div style={{ padding: '1rem', textAlign: 'center', color: 'var(--vestige-ink-light)' }}>
          <p>Gathering ledger data...</p>
        </div>
      </LedgerCard>
    );
  }

  if (error) {
    return (
      <LedgerCard variant="default">
        <div style={{ padding: '1rem', textAlign: 'center', color: 'var(--vestige-crimson)' }}>
          <p style={{ fontSize: '0.85rem' }}>Failed to load metric.</p>
          {onRetry && (
            <button
              onClick={onRetry}
              style={{
                marginTop: '0.5rem',
                background: 'none',
                border: '1px solid var(--vestige-crimson)',
                color: 'var(--vestige-crimson)',
                padding: '2px 8px',
                borderRadius: '4px',
                cursor: 'pointer',
                fontSize: '0.75rem',
              }}
            >
              Retry
            </button>
          )}
        </div>
      </LedgerCard>
    );
  }

  return (
    <LedgerCard
      variant="default"
      headerBadge={
        badgeLabel ? (
          <WaxSealBadge
            variant={variant}
            size="sm"
            icon={<IconWrapper name={icon || 'crest'} size={16} color="var(--vestige-ivory)" />}
            label={badgeLabel}
          />
        ) : null
      }
      title={title}
      subtitle={subtitle}
    >
      <div style={{ margin: '0.5rem 0 0.25rem', display: 'flex', alignItems: 'baseline', gap: '0.5rem' }}>
        <span style={{ fontFamily: 'var(--font-serif)', fontSize: '2rem', fontWeight: 'bold', color: 'var(--vestige-espresso)' }}>
          {value !== undefined && value !== null ? value : '—'}
        </span>
      </div>
    </LedgerCard>
  );
}

export default KpiMetricCard;
