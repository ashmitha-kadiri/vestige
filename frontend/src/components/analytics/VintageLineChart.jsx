import React from 'react';
import LedgerCard from '../vintage/LedgerCard/LedgerCard';

export function VintageLineChart({ title, subtitle, points = [], loading = false, error = null, onRetry }) {
  if (loading) {
    return (
      <LedgerCard variant="default" title={title} subtitle={subtitle} watermark="books">
        <p style={{ textAlign: 'center', color: 'var(--vestige-ink-light)', padding: '2.5rem 0' }}>Plotting historical chronology...</p>
      </LedgerCard>
    );
  }

  if (error) {
    return (
      <LedgerCard variant="default" title={title} subtitle={subtitle}>
        <div style={{ textAlign: 'center', color: 'var(--vestige-rust)', padding: '2rem 0' }}>
          <p>Failed to load time series dataset.</p>
          {onRetry && (
            <button
              onClick={onRetry}
              style={{
                marginTop: '0.5rem',
                background: 'none',
                border: '1px solid var(--vestige-rust)',
                color: 'var(--vestige-rust)',
                padding: '4px 10px',
                borderRadius: '4px',
                cursor: 'pointer',
              }}
            >
              Retry
            </button>
          )}
        </div>
      </LedgerCard>
    );
  }

  if (!points || points.length === 0) {
    return (
      <LedgerCard variant="default" title={title} subtitle={subtitle} watermark="books">
        <p style={{ textAlign: 'center', color: 'var(--vestige-ink-light)', padding: '2.5rem 0' }}>No activity data found for this timeframe.</p>
      </LedgerCard>
    );
  }

  const maxVal = Math.max(...points.map((p) => p.count || 0), 1);
  const chartHeight = 140;
  const chartWidth = 500;
  const stepX = points.length > 1 ? chartWidth / (points.length - 1) : chartWidth / 2;

  const coords = points.map((p, idx) => {
    const x = idx * stepX;
    const y = chartHeight - ((p.count || 0) / maxVal) * (chartHeight - 30) - 15;
    return { x, y, label: p.label, count: p.count || 0 };
  });

  const pathD = coords.reduce((acc, pt, idx) => (idx === 0 ? `M ${pt.x} ${pt.y}` : `${acc} L ${pt.x} ${pt.y}`), '');

  return (
    <LedgerCard variant="default" title={title} subtitle={subtitle} watermark="globe" watermarkOpacity={0.08}>
      <div style={{ margin: '1rem 0 0.5rem', width: '100%', overflowX: 'auto' }}>
        <svg viewBox={`0 0 ${chartWidth} ${chartHeight + 30}`} style={{ width: '100%', height: 'auto', minWidth: '320px' }}>
          {/* Subtle grid lines */}
          <line x1="0" y1="15" x2={chartWidth} y2="15" stroke="var(--vestige-parchment-border)" strokeDasharray="3 3" />
          <line x1="0" y1={chartHeight / 2} x2={chartWidth} y2={chartHeight / 2} stroke="var(--vestige-parchment-border)" strokeDasharray="3 3" />
          <line x1="0" y1={chartHeight} x2={chartWidth} y2={chartHeight} stroke="var(--vestige-parchment-border)" />

          {/* Area fill */}
          <path
            d={`${pathD} L ${coords[coords.length - 1]?.x || chartWidth} ${chartHeight} L ${coords[0]?.x || 0} ${chartHeight} Z`}
            fill="var(--vestige-brass-light)"
            opacity="0.22"
          />

          {/* Trend line */}
          <path d={pathD} fill="none" stroke="var(--vestige-brass-dark)" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round" />

          {/* Nodes */}
          {coords.map((pt, idx) => (
            <g key={idx}>
              <circle cx={pt.x} cy={pt.y} r="4.5" fill="var(--vestige-espresso)" stroke="var(--vestige-ivory)" strokeWidth="2" />
              <text x={pt.x} y={pt.y - 8} textAnchor="middle" fontSize="11" fill="var(--vestige-espresso)" fontWeight="bold" fontFamily="var(--font-serif)">
                {pt.count}
              </text>
              <text x={pt.x} y={chartHeight + 20} textAnchor="middle" fontSize="10" fill="var(--vestige-ink-light)" fontFamily="var(--font-body)">
                {pt.label}
              </text>
            </g>
          ))}
        </svg>
      </div>
    </LedgerCard>
  );
}

export default VintageLineChart;
