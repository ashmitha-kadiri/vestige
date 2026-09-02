import React from 'react';
import LedgerCard from '../vintage/LedgerCard/LedgerCard';

export function VintageBarChart({ title, subtitle, items = [], loading = false, error = null, onRetry }) {
  if (loading) {
    return (
      <LedgerCard variant="default" title={title} subtitle={subtitle}>
        <p style={{ textAlign: 'center', color: 'var(--vestige-ink-light)', padding: '2rem 0' }}>Plotting historical distribution...</p>
      </LedgerCard>
    );
  }

  if (error) {
    return (
      <LedgerCard variant="default" title={title} subtitle={subtitle}>
        <div style={{ textAlign: 'center', color: 'var(--vestige-crimson)', padding: '1.5rem 0' }}>
          <p>Failed to retrieve dataset.</p>
          {onRetry && (
            <button onClick={onRetry} style={{ marginTop: '0.5rem', background: 'none', border: '1px solid var(--vestige-crimson)', color: 'var(--vestige-crimson)', padding: '4px 10px', borderRadius: '4px', cursor: 'pointer' }}>
              Retry
            </button>
          )}
        </div>
      </LedgerCard>
    );
  }

  if (!items || items.length === 0) {
    return (
      <LedgerCard variant="default" title={title} subtitle={subtitle}>
        <p style={{ textAlign: 'center', color: 'var(--vestige-ink-light)', padding: '2rem 0' }}>No records recorded for this archival period.</p>
      </LedgerCard>
    );
  }

  const maxCount = Math.max(...items.map((i) => i.count || 0), 1);

  return (
    <LedgerCard variant="default" title={title} subtitle={subtitle}>
      <div style={{ display: 'flex', flexDirection: 'column', gap: '0.85rem', marginTop: '1rem' }}>
        {items.map((item, idx) => {
          const count = item.count || 0;
          const pct = item.percentage !== undefined ? item.percentage : Math.round((count / maxCount) * 100);
          const barWidth = Math.max(Math.round((count / maxCount) * 100), 2);

          return (
            <div key={item.key || idx}>
              <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '0.85rem', marginBottom: '0.25rem' }}>
                <span style={{ fontWeight: '600', color: 'var(--vestige-espresso)' }}>{item.label || item.key}</span>
                <span style={{ color: 'var(--vestige-ink-light)' }}>
                  {count} ({pct}%)
                </span>
              </div>
              <div style={{ height: '10px', background: 'var(--vestige-parchment-border)', borderRadius: '3px', overflow: 'hidden' }}>
                <div
                  style={{
                    height: '100%',
                    width: `${barWidth}%`,
                    background: idx % 2 === 0 ? 'var(--vestige-gold-dark)' : 'var(--vestige-olive)',
                    transition: 'width 0.4s ease',
                  }}
                />
              </div>
            </div>
          );
        })}
      </div>
    </LedgerCard>
  );
}

export default VintageBarChart;
