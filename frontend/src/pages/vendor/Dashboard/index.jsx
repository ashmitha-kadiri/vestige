import React, { useEffect, useState } from 'react';
import PageShell from '../../../components/layout/PageShell/PageShell';
import Container from '../../../components/layout/Container/Container';
import LedgerCard from '../../../components/vintage/LedgerCard/LedgerCard';
import WaxSealBadge from '../../../components/vintage/WaxSealBadge/WaxSealBadge';
import IconWrapper from '../../../components/common/IconWrapper/IconWrapper';
import Button from '../../../components/common/Button/Button';
import ArchivalEmptyState from '../../../components/vintage/ArchivalEmptyState/ArchivalEmptyState';
import ArchivalSkeleton from '../../../components/vintage/ArchivalSkeleton/ArchivalSkeleton';
import ConfirmModal from '../../../components/common/ConfirmModal/ConfirmModal';
import { useAuth } from '../../../contexts/AuthContext';
import api from '../../../services/api';

export function VendorDashboard() {
  const { user, logout } = useAuth();
  const [repairs, setRepairs] = useState([]);
  const [recycling, setRecycling] = useState([]);
  const [loading, setLoading] = useState(true);
  const [actionNotice, setActionNotice] = useState('');
  const [filterMode, setFilterMode] = useState('ALL'); // 'ALL' | 'ACTIVE' | 'COMPLETED'

  // Confirm Modal State
  const [confirmModalConfig, setConfirmModalConfig] = useState({
    isOpen: false,
    title: '',
    message: '',
    onConfirm: null,
    isDestructive: false,
  });

  const vendor = user?.vendorProfile;
  const isVerified = vendor?.verificationStatus === 'VERIFIED';

  const loadData = React.useCallback(async () => {
    if (vendor?.id && isVerified) {
      try {
        const [repairsRes, recyclingRes] = await Promise.all([
          api.get(`/repairs/vendor/${vendor.id}`).catch(() => ({ data: [] })),
          api.get(`/recycling/vendor/${vendor.id}`).catch(() => ({ data: [] })),
        ]);
        setRepairs(repairsRes.data || []);
        setRecycling(recyclingRes.data || []);
      } catch (err) {
        console.warn('Could not load dispatches:', err);
      }
    }
    setLoading(false);
  }, [vendor?.id, isVerified]);

  useEffect(() => {
    loadData();
  }, [loadData]);

  const handleUpdateRepairStatus = async (repairId, nextStatus) => {
    setActionNotice('');
    try {
      await api.patch(`/repairs/${repairId}/status`, {
        status: nextStatus,
        notes: `Progressed by craftsman to ${nextStatus}`,
      });
      setActionNotice(`Repair booking #${repairId.substring(0, 8)} transitioned to ${nextStatus}.`);
      loadData();
    } catch (err) {
      setActionNotice(`Status update failed: ${err.message}`);
    }
  };

  const handleUpdateRecyclingStatus = async (requestId, nextStatus) => {
    setActionNotice('');
    try {
      await api.patch(`/recycling/${requestId}/status`, {
        status: nextStatus,
        notes: `Progressed by logistics partner to ${nextStatus}`,
      });
      setActionNotice(`E-waste collection #${requestId.substring(0, 8)} transitioned to ${nextStatus}.`);
      loadData();
    } catch (err) {
      setActionNotice(`Status update failed: ${err.message}`);
    }
  };

  const promptDeclineRepair = (repairId) => {
    setConfirmModalConfig({
      isOpen: true,
      title: 'Decline Repair Request',
      message: 'Are you certain you wish to decline this assigned repair request? It will be re-routed to an alternate accredited workshop.',
      confirmLabel: 'Decline Job',
      isDestructive: true,
      onConfirm: () => {
        setConfirmModalConfig((prev) => ({ ...prev, isOpen: false }));
        handleUpdateRepairStatus(repairId, 'REJECTED');
      },
    });
  };

  const filteredRepairs = repairs.filter((r) => {
    if (filterMode === 'ACTIVE') return r.status === 'PENDING' || r.status === 'ACCEPTED' || r.status === 'IN_PROGRESS';
    if (filterMode === 'COMPLETED') return r.status === 'COMPLETED';
    return true;
  });

  const filteredRecycling = recycling.filter((req) => {
    if (filterMode === 'ACTIVE') return req.status === 'PENDING' || req.status === 'ACCEPTED' || req.status === 'SCHEDULED';
    if (filterMode === 'COMPLETED') return req.status === 'COMPLETED';
    return true;
  });

  return (
    <PageShell>
      <Container size="lg">
        {/* Header Ribbon */}
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', margin: '2rem 0 1.5rem', flexWrap: 'wrap', gap: '1rem' }}>
          <div>
            <span style={{ fontSize: '0.82rem', color: 'var(--vestige-olive)', textTransform: 'uppercase', letterSpacing: '2px', fontWeight: 'bold' }}>
              ✦ Certified Craftsman Workbench
            </span>
            <h1 style={{ fontFamily: 'var(--font-heading)', fontSize: '2.25rem', color: 'var(--vestige-espresso)', margin: '0.25rem 0 0' }}>
              {vendor?.businessName || 'Partner Workshop'}
            </h1>
          </div>
          <div style={{ display: 'flex', gap: '0.75rem' }}>
            <a href="/vendor/analytics" style={{ textDecoration: 'none' }}>
              <Button variant="primary" size="sm" icon={<IconWrapper name="search" size={16} />}>
                Workshop Analytics
              </Button>
            </a>
            <Button variant="ghost" size="sm" onClick={logout} icon={<IconWrapper name="lock" size={16} />}>
              Sign Out
            </Button>
          </div>
        </div>

        {actionNotice && (
          <div style={{ padding: '0.75rem 1rem', background: 'var(--vestige-parchment-light)', border: '1px solid var(--vestige-brass)', borderRadius: 'var(--radius-xs)', marginBottom: '1.5rem', color: 'var(--vestige-espresso)', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <p style={{ margin: 0, fontSize: '0.9rem' }}>{actionNotice}</p>
            <button onClick={() => setActionNotice('')} style={{ background: 'none', border: 'none', cursor: 'pointer', color: 'var(--vestige-ink-light)' }}>✕</button>
          </div>
        )}

        {/* Status Filter Bar */}
        <div style={{ display: 'flex', gap: '0.5rem', marginBottom: '1.5rem', alignItems: 'center' }}>
          <span style={{ fontSize: '0.82rem', color: 'var(--vestige-ink-light)', fontFamily: 'var(--font-serif)', marginRight: '0.5rem' }}>
            Filter Jobs:
          </span>
          {['ALL', 'ACTIVE', 'COMPLETED'].map((mode) => (
            <button
              key={mode}
              onClick={() => setFilterMode(mode)}
              style={{
                padding: '0.35rem 0.85rem',
                fontSize: '0.78rem',
                fontFamily: 'var(--font-serif)',
                letterSpacing: '0.5px',
                borderRadius: 'var(--radius-xs)',
                border: '1px solid var(--vestige-parchment-border)',
                background: filterMode === mode ? 'var(--vestige-espresso)' : 'var(--vestige-parchment-light)',
                color: filterMode === mode ? 'var(--vestige-ivory-warm)' : 'var(--vestige-ink)',
                cursor: 'pointer',
                fontWeight: filterMode === mode ? 'bold' : 'normal',
                transition: 'all var(--transition-fast)',
              }}
            >
              {mode} ({mode === 'ALL' ? repairs.length + recycling.length : mode === 'ACTIVE' ? repairs.filter(r => r.status !== 'COMPLETED' && r.status !== 'CANCELLED').length + recycling.filter(r => r.status !== 'COMPLETED').length : repairs.filter(r => r.status === 'COMPLETED').length + recycling.filter(r => r.status === 'COMPLETED').length})
            </button>
          ))}
        </div>

        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(320px, 1fr))', gap: '1.5rem', marginBottom: '2rem' }}>
          {/* Workshop Dossier */}
          <LedgerCard
            variant="vendor"
            watermark="building"
            watermarkSize={140}
            watermarkOpacity={0.10}
            headerBadge={
              <WaxSealBadge
                variant={isVerified ? 'olive' : 'gold'}
                size="sm"
                icon={<IconWrapper name="tools" size={18} color="var(--vestige-ivory)" />}
                label={vendor?.verificationStatus || 'PENDING'}
              />
            }
            title="Workshop Dossier"
            subtitle="Accredited operational credentials"
          >
            <div style={{ display: 'flex', flexDirection: 'column', gap: '0.75rem', fontSize: '0.92rem', color: 'var(--vestige-ink)' }}>
              <div>
                <span style={{ color: 'var(--vestige-ink-light)', fontSize: '0.8rem', textTransform: 'uppercase' }}>Lead Technician:</span>
                <div style={{ fontWeight: 'bold', fontFamily: 'var(--font-serif)' }}>{user?.fullName} ({user?.email})</div>
              </div>
              <div>
                <span style={{ color: 'var(--vestige-ink-light)', fontSize: '0.8rem', textTransform: 'uppercase' }}>Location:</span>
                <div>{vendor?.address}, {vendor?.city}, {vendor?.state} - {vendor?.pincode}</div>
              </div>
              <div>
                <span style={{ color: 'var(--vestige-ink-light)', fontSize: '0.8rem', textTransform: 'uppercase' }}>Accreditation Status:</span>
                <div style={{ fontWeight: 'bold', color: isVerified ? 'var(--vestige-olive)' : 'var(--vestige-brass-dark)' }}>
                  {vendor?.verificationStatus || 'PENDING REVIEW'}
                </div>
              </div>
              <div>
                <span style={{ color: 'var(--vestige-ink-light)', fontSize: '0.8rem', textTransform: 'uppercase' }}>Authorized Categories:</span>
                <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap', marginTop: '0.25rem' }}>
                  {(vendor?.deviceCategories || ['SMARTPHONE', 'LAPTOP']).map((cat) => (
                    <span key={cat} style={{ background: 'var(--vestige-parchment-border)', padding: '2px 8px', borderRadius: 'var(--radius-xs)', fontSize: '0.75rem', fontFamily: 'var(--font-mono)' }}>
                      {cat}
                    </span>
                  ))}
                </div>
              </div>
            </div>
          </LedgerCard>

          {/* Active Repair Workbench */}
          <LedgerCard
            variant="default"
            watermark="tools"
            watermarkSize={140}
            watermarkOpacity={0.10}
            headerBadge={
              <WaxSealBadge
                variant="gold"
                size="sm"
                icon={<IconWrapper name="tools" size={16} color="var(--vestige-espresso)" />}
                label={`${filteredRepairs.length} JOBS`}
              />
            }
            title="Assigned Hardware Restorations"
            subtitle="Diagnostic repairs allocated to your workbench"
          >
            {loading ? (
              <ArchivalSkeleton variant="card" height="120px" />
            ) : !isVerified ? (
              <div style={{ padding: '1rem', background: 'var(--vestige-parchment-light)', border: '1px dashed var(--vestige-brass)', borderRadius: 'var(--radius-xs)', color: 'var(--vestige-brass-dark)' }}>
                Accreditation pending administrative review. Verification officer will audit credentials shortly.
              </div>
            ) : filteredRepairs.length === 0 ? (
              <ArchivalEmptyState
                illustration="tools"
                title="No Repairs In This View"
                description={filterMode === 'ALL' ? 'No active repair bookings allocated to this workshop.' : `No ${filterMode.toLowerCase()} repair jobs found.`}
              />
            ) : (
              <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
                {filteredRepairs.map((r) => (
                  <div key={r.id} style={{ padding: '1rem', border: '1px solid var(--vestige-parchment-border)', borderRadius: 'var(--radius-xs)', background: 'var(--vestige-parchment-light)' }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', fontWeight: 'bold' }}>
                      <span style={{ fontFamily: 'var(--font-serif)', color: 'var(--vestige-espresso)' }}>{r.brand} {r.model}</span>
                      <span style={{ fontSize: '0.8rem', color: 'var(--vestige-brass-dark)', fontFamily: 'var(--font-mono)' }}>{r.status}</span>
                    </div>
                    <div style={{ fontSize: '0.82rem', color: 'var(--vestige-ink-light)', marginTop: '0.25rem' }}>
                      Patron: {r.userName} &bull; Schedule: {r.preferredDate}
                    </div>
                    <div style={{ fontSize: '0.85rem', marginTop: '0.35rem', fontStyle: 'italic', color: 'var(--vestige-ink)' }}>
                      "{r.issueDescription}"
                    </div>
                    <div style={{ marginTop: '0.75rem', display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
                      {r.status === 'PENDING' && (
                        <>
                          <Button variant="primary" size="sm" onClick={() => handleUpdateRepairStatus(r.id, 'ACCEPTED')}>
                            Accept Job
                          </Button>
                          <Button variant="ghost" size="sm" onClick={() => promptDeclineRepair(r.id)} style={{ color: 'var(--vestige-rust)' }}>
                            Decline
                          </Button>
                        </>
                      )}
                      {r.status === 'ACCEPTED' && (
                        <Button variant="primary" size="sm" onClick={() => handleUpdateRepairStatus(r.id, 'IN_PROGRESS')}>
                          Start Repair
                        </Button>
                      )}
                      {r.status === 'IN_PROGRESS' && (
                        <Button variant="ornate" size="sm" onClick={() => handleUpdateRepairStatus(r.id, 'COMPLETED')}>
                          Mark Completed (+100 PTS)
                        </Button>
                      )}
                    </div>
                  </div>
                ))}
              </div>
            )}
          </LedgerCard>
        </div>

        {/* E-Waste Collections Workbench */}
        <LedgerCard
          variant="vendor"
          headerBadge={
            <WaxSealBadge
              variant="olive"
              size="sm"
              icon={<IconWrapper name="recycle" size={16} color="var(--vestige-ivory)" />}
              label={`${filteredRecycling.length} PARCELS`}
            />
          }
          title="Assigned E-Waste Pickups"
          subtitle="Doorstep collection dispatches for ethical circular recycling"
        >
          {loading ? (
            <ArchivalSkeleton variant="card" height="120px" />
          ) : !isVerified ? (
            <p style={{ color: 'var(--vestige-brass-dark)' }}>Accreditation pending administrative review.</p>
          ) : filteredRecycling.length === 0 ? (
            <ArchivalEmptyState
              illustration="recycling"
              title="No E-Waste Collections"
              description="No doorstep collection dispatches currently allocated to your facility."
            />
          ) : (
            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '1rem' }}>
              {filteredRecycling.map((req) => (
                <div key={req.id} style={{ padding: '1rem', border: '1px solid var(--vestige-parchment-border)', borderRadius: 'var(--radius-xs)', background: 'var(--vestige-parchment-light)' }}>
                  <div style={{ display: 'flex', justifyContent: 'space-between', fontWeight: 'bold' }}>
                    <span style={{ fontFamily: 'var(--font-serif)' }}>{req.brand} {req.model} ({req.deviceCount} items)</span>
                    <span style={{ fontSize: '0.8rem', color: 'var(--vestige-olive)', fontFamily: 'var(--font-mono)' }}>{req.status}</span>
                  </div>
                  <div style={{ fontSize: '0.82rem', color: 'var(--vestige-ink-light)', marginTop: '0.25rem' }}>
                    Address: {req.pickupAddress} on {req.pickupDate}
                  </div>
                  <div style={{ marginTop: '0.75rem', display: 'flex', gap: '0.5rem' }}>
                    {req.status === 'PENDING' && (
                      <Button variant="primary" size="sm" onClick={() => handleUpdateRecyclingStatus(req.id, 'ACCEPTED')}>
                        Accept Pickup
                      </Button>
                    )}
                    {req.status === 'ACCEPTED' && (
                      <Button variant="primary" size="sm" onClick={() => handleUpdateRecyclingStatus(req.id, 'SCHEDULED')}>
                        Dispatch Logistics Agent
                      </Button>
                    )}
                    {req.status === 'SCHEDULED' && (
                      <Button variant="ornate" size="sm" onClick={() => handleUpdateRecyclingStatus(req.id, 'COMPLETED')}>
                        Confirm Received & Award Points
                      </Button>
                    )}
                  </div>
                </div>
              ))}
            </div>
          )}
        </LedgerCard>

        {/* Confirmation Modal */}
        <ConfirmModal
          isOpen={confirmModalConfig.isOpen}
          title={confirmModalConfig.title}
          message={confirmModalConfig.message}
          confirmLabel={confirmModalConfig.confirmLabel || 'Confirm'}
          isDestructive={confirmModalConfig.isDestructive}
          onConfirm={confirmModalConfig.onConfirm}
          onCancel={() => setConfirmModalConfig((prev) => ({ ...prev, isOpen: false }))}
        />
      </Container>
    </PageShell>
  );
}

export default VendorDashboard;
