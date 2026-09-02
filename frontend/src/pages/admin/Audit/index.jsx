import React, { useEffect, useState } from 'react';
import PageShell from '../../../components/layout/PageShell/PageShell';
import Container from '../../../components/layout/Container/Container';
import LedgerCard from '../../../components/vintage/LedgerCard/LedgerCard';
import AdminNav from '../../../components/admin/AdminNav';
import apiClient from '../../../services/apiClient';

export function AdminAuditPage() {
  const [actions, setActions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');

  const loadActions = async () => {
    setLoading(true);
    try {
      const res = await apiClient.get('/api/admin/actions');
      setActions(res.data?.data || []);
    } catch (err) {
      console.warn('Failed to load audit logs:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadActions();
  }, []);

  const filteredActions = actions.filter((a) => {
    if (!searchTerm) return true;
    const term = searchTerm.toLowerCase();
    return (
      a.actionType?.toLowerCase().includes(term) ||
      a.targetEntity?.toLowerCase().includes(term) ||
      a.targetId?.toLowerCase().includes(term)
    );
  });

  return (
    <PageShell>
      <Container size="lg">
        <div style={{ margin: '2rem 0 1rem' }}>
          <span style={{ fontSize: '0.85rem', color: 'var(--vestige-gold-dark)', textTransform: 'uppercase', letterSpacing: '2px', fontWeight: 'bold' }}>
            ✦ Cryptographic Governance Trail
          </span>
          <h1 style={{ fontFamily: 'var(--font-serif)', fontSize: '2rem', color: 'var(--vestige-espresso)', margin: '0.25rem 0 0' }}>
            Institutional Audit Log
          </h1>
        </div>

        <AdminNav />

        <LedgerCard
          variant="admin"
          title={`Administrative Action Ledger (${filteredActions.length})`}
          subtitle="Append-only immutable record of all moderation and clearance events"
        >
          <div style={{ marginBottom: '1.25rem' }}>
            <input
              type="text"
              placeholder="Search action type, target entity, or ID..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              style={{
                width: '100%',
                maxWidth: '400px',
                padding: '0.5rem 0.75rem',
                borderRadius: '4px',
                border: '1px solid var(--vestige-parchment-border)',
                background: 'var(--vestige-parchment-white)',
                fontSize: '0.88rem',
              }}
            />
          </div>

          {loading ? (
            <p style={{ color: 'var(--vestige-ink-light)' }}>Loading audit trail...</p>
          ) : filteredActions.length === 0 ? (
            <p style={{ color: 'var(--vestige-ink-light)' }}>No recorded administrative actions found.</p>
          ) : (
            <div style={{ overflowX: 'auto' }}>
              <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: '0.85rem' }}>
                <thead>
                  <tr style={{ borderBottom: '2px solid var(--vestige-gold-dark)', textAlign: 'left', color: 'var(--vestige-espresso)' }}>
                    <th style={{ padding: '0.6rem' }}>Action Type</th>
                    <th style={{ padding: '0.6rem' }}>Target Entity</th>
                    <th style={{ padding: '0.6rem' }}>Target ID</th>
                    <th style={{ padding: '0.6rem' }}>Details</th>
                    <th style={{ padding: '0.6rem' }}>Origin IP</th>
                    <th style={{ padding: '0.6rem' }}>Timestamp</th>
                  </tr>
                </thead>
                <tbody>
                  {filteredActions.map((log) => (
                    <tr key={log.id} style={{ borderBottom: '1px solid var(--vestige-parchment-border)' }}>
                      <td style={{ padding: '0.6rem', fontWeight: 'bold', color: 'var(--vestige-espresso)' }}>
                        {log.actionType}
                      </td>
                      <td style={{ padding: '0.6rem', color: 'var(--vestige-gold-dark)' }}>
                        {log.targetEntity}
                      </td>
                      <td style={{ padding: '0.6rem', fontFamily: 'var(--font-mono)', fontSize: '0.78rem' }}>
                        {log.targetId ? String(log.targetId).substring(0, 8) : 'N/A'}
                      </td>
                      <td style={{ padding: '0.6rem', color: 'var(--vestige-ink-light)', maxWidth: '280px', wordBreak: 'break-all' }}>
                        {log.details || '—'}
                      </td>
                      <td style={{ padding: '0.6rem', color: 'var(--vestige-ink-light)' }}>
                        {log.ipAddress || '127.0.0.1'}
                      </td>
                      <td style={{ padding: '0.6rem', color: 'var(--vestige-ink-light)' }}>
                        {log.createdAt ? new Date(log.createdAt).toLocaleString() : 'N/A'}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </LedgerCard>
      </Container>
    </PageShell>
  );
}

export default AdminAuditPage;
