import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import PageShell from '../../../components/layout/PageShell/PageShell';
import Container from '../../../components/layout/Container/Container';
import VestigeLogo from '../../../components/common/VestigeLogo/VestigeLogo';
import IconWrapper from '../../../components/common/IconWrapper/IconWrapper';
import Button from '../../../components/common/Button/Button';
import { useAuth } from '../../../contexts/AuthContext';
import useTranslation from '../../../i18n/useTranslation';
import styles from './Portals.module.css';

// Antique Scrollwork & Filigree Corner Vector (Matching Vector Set Vol. 2)
function AntiqueCorner({ position = 'topLeft', color = 'var(--vestige-gold-aged, #C49A45)' }) {
  return (
    <svg
      viewBox="0 0 44 44"
      className={`${styles.cornerFiligree} ${styles[position]}`}
      aria-hidden="true"
    >
      <g stroke={color} fill="none" strokeLinecap="round" strokeLinejoin="round">
        <path d="M2 42 L2 12 Q2 2 12 2 L42 2" strokeWidth="1.4" />
        <path d="M5 42 L5 14 Q5 5 14 5 L42 5" strokeWidth="0.75" strokeDasharray="3 1.5" opacity="0.6" />
        <circle cx="3.5" cy="3.5" r="2" fill={color} stroke="none" />
        {/* Acanthus Volute & S-Scroll */}
        <path
          d="M8 34 Q8 18 20 14 Q30 12 32 20 Q34 28 24 30 Q18 32 16 22 Q15 14 24 10 Q30 6 38 8"
          strokeWidth="1.1"
        />
        <path d="M12 20 Q16 16 20 18 Q24 20 22 24 Q20 28 16 26" fill={color} fillOpacity="0.18" strokeWidth="0.7" />
        <circle cx="24" cy="20" r="1.3" fill={color} stroke="none" />
      </g>
    </svg>
  );
}

export function PortalsPage() {
  const { t } = useTranslation();
  const { login } = useAuth();
  const navigate = useNavigate();

  const [userForm, setUserForm] = useState({ email: 'user@vestige.internal', password: 'User@123', showPass: false });
  const [adminForm, setAdminForm] = useState({ email: 'admin@vestige.internal', password: 'Admin@123', showPass: false });
  const [vendorForm, setVendorForm] = useState({ email: 'aegis@vestige.internal', password: 'Vendor@123', showPass: false });

  const [loadingRole, setLoadingRole] = useState(null);
  const [errorMsg, setErrorMsg] = useState({});

  const handleLogin = async (e, role, formState, dest) => {
    e.preventDefault();
    if (loadingRole) return;
    setLoadingRole(role);
    setErrorMsg((prev) => ({ ...prev, [role]: '' }));

    try {
      await login(formState.email, formState.password, role);
      navigate(dest);
    } catch (err) {
      setErrorMsg((prev) => ({
        ...prev,
        [role]: err?.data?.message || err?.message || 'Authentication failed. Please verify credentials.',
      }));
    } finally {
      setLoadingRole(null);
    }
  };

  return (
    <PageShell>
      <div className={styles.portalsWrapper}>
        <Container size="lg">
          {/* Header Banner matching Reference 3 */}
          <div className={styles.headerBlock}>
            <VestigeLogo
              variant="vertical"
              size="lg"
              theme="dark"
              tagline={t('tagline', 'Give Technology a Second Life')}
            />
            <div className={styles.eyebrowOrnament}>
              <span className={styles.flourishWing}>─── ❖</span>
              <span className={styles.eyebrowText}>{t('portals.title', 'Choose Your Portal')}</span>
              <span className={styles.flourishWing}>❖ ───</span>
            </div>
            <p className={styles.portalSubtitle}>
              Separate login portals for User, Admin, and Vendor to ensure secure and role-based access.
            </p>
          </div>

          {/* 3 Arched Parchment Cards Grid with Antique Scrollwork Borders */}
          <div className={styles.portalsGrid}>
            {/* 1. USER / PATRON CARD */}
            <div className={`${styles.archedCard} ${styles.userCard}`}>
              {/* Antique Corner Filigrees */}
              <AntiqueCorner position="topLeft" />
              <AntiqueCorner position="topRight" />
              <AntiqueCorner position="bottomLeft" />
              <AntiqueCorner position="bottomRight" />

              {/* Arched Top Header */}
              <div className={styles.cardArchHeader}>
                <div className={styles.cameoEmblem}>
                  <svg viewBox="0 0 60 60" className={styles.crestSvg} aria-hidden="true">
                    <circle cx="30" cy="30" r="28" fill="#F3E6C8" stroke="var(--vestige-gold-aged)" strokeWidth="1.5" />
                    <circle cx="30" cy="30" r="24" fill="none" stroke="var(--vestige-gold-aged)" strokeWidth="0.8" strokeDasharray="3 2" />
                    {/* Cameo Profile Silhouette */}
                    <path
                      d="M30 14 C35 14 39 18 39 24 C39 28 36 32 32 33 C38 35 44 40 44 48 L16 48 C16 40 22 35 28 33 C24 32 21 28 21 24 C21 18 25 14 30 14 Z"
                      fill="var(--vestige-walnut)"
                    />
                  </svg>
                </div>
                <h2 className={styles.cardHeading}>USER LOGIN</h2>
                <p className={styles.cardSubtitle}>Welcome back! Please login to continue.</p>
              </div>

              {errorMsg.USER && <div className={styles.errorNotice}>{errorMsg.USER}</div>}

              {/* Login Form */}
              <form onSubmit={(e) => handleLogin(e, 'USER', userForm, '/user/dashboard')} className={styles.formBody}>
                <div className={styles.fieldGroup}>
                  <label className={styles.fieldLabel}>Email Address</label>
                  <div className={styles.inputWrapper}>
                    <span className={styles.inputIcon}><IconWrapper name="envelope" size={16} /></span>
                    <input
                      type="email"
                      value={userForm.email}
                      onChange={(e) => setUserForm((prev) => ({ ...prev, email: e.target.value }))}
                      placeholder="you@example.com"
                      className={styles.archivalInput}
                      required
                    />
                  </div>
                </div>

                <div className={styles.fieldGroup}>
                  <label className={styles.fieldLabel}>Password</label>
                  <div className={styles.inputWrapper}>
                    <span className={styles.inputIcon}><IconWrapper name="lock" size={16} /></span>
                    <input
                      type={userForm.showPass ? 'text' : 'password'}
                      value={userForm.password}
                      onChange={(e) => setUserForm((prev) => ({ ...prev, password: e.target.value }))}
                      placeholder="••••••••"
                      className={styles.archivalInput}
                      required
                    />
                    <button
                      type="button"
                      onClick={() => setUserForm((prev) => ({ ...prev, showPass: !prev.showPass }))}
                      className={styles.eyeToggle}
                      aria-label="Toggle password visibility"
                    >
                      <IconWrapper name={userForm.showPass ? 'eye-off' : 'eye'} size={16} />
                    </button>
                  </div>
                </div>

                <div className={styles.optionsRow}>
                  <label className={styles.rememberLabel}>
                    <input type="checkbox" defaultChecked className={styles.checkbox} />
                    <span>Remember me</span>
                  </label>
                  <Link to="/login/user" className={styles.forgotLink}>Forgot Password?</Link>
                </div>

                <Button
                  type="submit"
                  variant="primary"
                  size="md"
                  className={styles.userLoginBtn}
                  loading={loadingRole === 'USER'}
                >
                  LOGIN
                </Button>

                <div className={styles.accountAction}>
                  <span>New here?</span>
                  <Link to="/register/user" className={styles.registerPlaqueLink}>
                    CREATE AN ACCOUNT ❖
                  </Link>
                </div>
              </form>

              {/* Bottom Engraved Artwork: Books, Quill & Magnifier */}
              <div className={styles.bottomArtwork} aria-hidden="true">
                <svg viewBox="0 0 280 90" className={styles.artworkSvg}>
                  <g stroke="var(--vestige-walnut)" opacity="0.6" strokeWidth="1" fill="none" strokeLinecap="round" strokeLinejoin="round">
                    <path d="M40 70 L130 70 L140 85 L30 85 Z" fill="rgba(43,27,18,0.06)" />
                    <line x1="40" y1="75" x2="130" y2="75" strokeDasharray="2 2" />
                    <path d="M45 52 L135 52 L130 68 L40 68 Z" fill="rgba(43,27,18,0.08)" />
                    <line x1="45" y1="58" x2="135" y2="58" strokeDasharray="2 2" />
                    <circle cx="170" cy="62" r="16" strokeWidth="1.4" fill="rgba(243,230,200,0.4)" />
                    <line x1="182" y1="74" x2="205" y2="85" strokeWidth="2.5" />
                    <circle cx="170" cy="62" r="12" strokeDasharray="2 2" opacity="0.5" />
                    <path d="M225 65 L245 65 L240 85 L230 85 Z" fill="rgba(43,27,18,0.12)" />
                    <path d="M245 40 Q255 25 265 15 Q255 35 240 65" strokeWidth="1.2" />
                  </g>
                </svg>
              </div>
            </div>

            {/* 2. ADMIN CARD */}
            <div className={`${styles.archedCard} ${styles.adminCard}`}>
              {/* Antique Corner Filigrees */}
              <AntiqueCorner position="topLeft" color="var(--vestige-gold-royal, #D4AF37)" />
              <AntiqueCorner position="topRight" color="var(--vestige-gold-royal, #D4AF37)" />
              <AntiqueCorner position="bottomLeft" color="var(--vestige-gold-royal, #D4AF37)" />
              <AntiqueCorner position="bottomRight" color="var(--vestige-gold-royal, #D4AF37)" />

              {/* Arched Top Header */}
              <div className={styles.cardArchHeader}>
                <div className={styles.cameoEmblem}>
                  <svg viewBox="0 0 60 60" className={styles.crestSvg} aria-hidden="true">
                    <circle cx="30" cy="30" r="28" fill="#F3E6C8" stroke="var(--vestige-gold-royal)" strokeWidth="1.5" />
                    <circle cx="30" cy="30" r="24" fill="none" stroke="var(--vestige-gold-royal)" strokeWidth="0.8" strokeDasharray="3 2" />
                    <path d="M22 22 L30 16 L38 22 L36 36 L30 42 L24 36 Z" fill="var(--vestige-walnut)" />
                    <text x="30" y="32" fontSize="13" textAnchor="middle" fill="#FAF4E8" fontFamily="var(--font-serif)" fontWeight="bold">A</text>
                    <path d="M24 16 L27 12 L30 15 L33 12 L36 16 Z" fill="var(--vestige-gold-royal)" />
                  </svg>
                </div>
                <h2 className={styles.cardHeading}>ADMIN LOGIN</h2>
                <p className={styles.cardSubtitle}>Welcome Admin! Please login to continue.</p>
              </div>

              {errorMsg.ADMIN && <div className={styles.errorNotice}>{errorMsg.ADMIN}</div>}

              {/* Login Form */}
              <form onSubmit={(e) => handleLogin(e, 'ADMIN', adminForm, '/admin/dashboard')} className={styles.formBody}>
                <div className={styles.fieldGroup}>
                  <label className={styles.fieldLabel}>Email Address</label>
                  <div className={styles.inputWrapper}>
                    <span className={styles.inputIcon}><IconWrapper name="envelope" size={16} /></span>
                    <input
                      type="email"
                      value={adminForm.email}
                      onChange={(e) => setAdminForm((prev) => ({ ...prev, email: e.target.value }))}
                      placeholder="admin@vestige.com"
                      className={styles.archivalInput}
                      required
                    />
                  </div>
                </div>

                <div className={styles.fieldGroup}>
                  <label className={styles.fieldLabel}>Password</label>
                  <div className={styles.inputWrapper}>
                    <span className={styles.inputIcon}><IconWrapper name="lock" size={16} /></span>
                    <input
                      type={adminForm.showPass ? 'text' : 'password'}
                      value={adminForm.password}
                      onChange={(e) => setAdminForm((prev) => ({ ...prev, password: e.target.value }))}
                      placeholder="••••••••"
                      className={styles.archivalInput}
                      required
                    />
                    <button
                      type="button"
                      onClick={() => setAdminForm((prev) => ({ ...prev, showPass: !prev.showPass }))}
                      className={styles.eyeToggle}
                      aria-label="Toggle password visibility"
                    >
                      <IconWrapper name={adminForm.showPass ? 'eye-off' : 'eye'} size={16} />
                    </button>
                  </div>
                </div>

                <div className={styles.optionsRow}>
                  <label className={styles.rememberLabel}>
                    <input type="checkbox" defaultChecked className={styles.checkbox} />
                    <span>Remember me</span>
                  </label>
                  <Link to="/login/admin" className={styles.forgotLink}>Forgot Password?</Link>
                </div>

                <Button
                  type="submit"
                  variant="primary"
                  size="md"
                  className={styles.adminLoginBtn}
                  loading={loadingRole === 'ADMIN'}
                >
                  LOGIN
                </Button>

                <div className={styles.badgeLine}>
                  <span className={styles.goldLockIcon}>🔒</span>
                  <span>Secure Admin Governance Portal</span>
                </div>
              </form>

              {/* Bottom Engraved Artwork: Classical Archival Government/Palace Hall */}
              <div className={styles.bottomArtwork} aria-hidden="true">
                <svg viewBox="0 0 280 90" className={styles.artworkSvg}>
                  <g stroke="var(--vestige-walnut)" opacity="0.65" strokeWidth="1" fill="none" strokeLinecap="round" strokeLinejoin="round">
                    <path d="M50 85 L230 85" strokeWidth="1.5" />
                    <rect x="70" y="45" width="140" height="40" fill="rgba(43,27,18,0.06)" />
                    <line x1="85" y1="45" x2="85" y2="85" strokeWidth="1.5" />
                    <line x1="105" y1="45" x2="105" y2="85" strokeWidth="1.5" />
                    <line x1="125" y1="45" x2="125" y2="85" strokeWidth="1.5" />
                    <line x1="155" y1="45" x2="155" y2="85" strokeWidth="1.5" />
                    <line x1="175" y1="45" x2="175" y2="85" strokeWidth="1.5" />
                    <line x1="195" y1="45" x2="195" y2="85" strokeWidth="1.5" />
                    <path d="M60 45 L140 20 L220 45 Z" fill="rgba(184,134,11,0.08)" strokeWidth="1.3" />
                    <circle cx="140" cy="33" r="6" strokeWidth="0.8" />
                    <path d="M125 20 Q140 5 155 20" strokeWidth="1.2" />
                    <line x1="140" y1="5" x2="140" y2="0" strokeWidth="1.5" />
                  </g>
                </svg>
              </div>
            </div>

            {/* 3. VENDOR / CRAFTSMAN CARD */}
            <div className={`${styles.archedCard} ${styles.vendorCard}`}>
              {/* Antique Corner Filigrees */}
              <AntiqueCorner position="topLeft" color="var(--vestige-olive, #68704A)" />
              <AntiqueCorner position="topRight" color="var(--vestige-olive, #68704A)" />
              <AntiqueCorner position="bottomLeft" color="var(--vestige-olive, #68704A)" />
              <AntiqueCorner position="bottomRight" color="var(--vestige-olive, #68704A)" />

              {/* Arched Top Header */}
              <div className={styles.cardArchHeader}>
                <div className={styles.cameoEmblem}>
                  <svg viewBox="0 0 60 60" className={styles.crestSvg} aria-hidden="true">
                    <circle cx="30" cy="30" r="28" fill="#F3E6C8" stroke="var(--vestige-olive)" strokeWidth="1.5" />
                    <circle cx="30" cy="30" r="24" fill="none" stroke="var(--vestige-olive)" strokeWidth="0.8" strokeDasharray="3 2" />
                    <rect x="20" y="24" width="20" height="18" fill="var(--vestige-olive-dark)" />
                    <path d="M17 24 L30 16 L43 24 Z" fill="var(--vestige-gold-royal)" />
                    <rect x="27" y="32" width="6" height="10" fill="#FAF4E8" />
                    <rect x="22" y="27" width="4" height="4" fill="#FAF4E8" />
                    <rect x="34" y="27" width="4" height="4" fill="#FAF4E8" />
                  </svg>
                </div>
                <h2 className={styles.cardHeading}>VENDOR LOGIN</h2>
                <p className={styles.cardSubtitle}>Welcome Vendor! Please login to continue.</p>
              </div>

              {errorMsg.VENDOR && <div className={styles.errorNotice}>{errorMsg.VENDOR}</div>}

              {/* Login Form */}
              <form onSubmit={(e) => handleLogin(e, 'VENDOR', vendorForm, '/vendor/dashboard')} className={styles.formBody}>
                <div className={styles.fieldGroup}>
                  <label className={styles.fieldLabel}>Email Address</label>
                  <div className={styles.inputWrapper}>
                    <span className={styles.inputIcon}><IconWrapper name="envelope" size={16} /></span>
                    <input
                      type="email"
                      value={vendorForm.email}
                      onChange={(e) => setVendorForm((prev) => ({ ...prev, email: e.target.value }))}
                      placeholder="vendor@vestige.com"
                      className={styles.archivalInput}
                      required
                    />
                  </div>
                </div>

                <div className={styles.fieldGroup}>
                  <label className={styles.fieldLabel}>Password</label>
                  <div className={styles.inputWrapper}>
                    <span className={styles.inputIcon}><IconWrapper name="lock" size={16} /></span>
                    <input
                      type={vendorForm.showPass ? 'text' : 'password'}
                      value={vendorForm.password}
                      onChange={(e) => setVendorForm((prev) => ({ ...prev, password: e.target.value }))}
                      placeholder="••••••••"
                      className={styles.archivalInput}
                      required
                    />
                    <button
                      type="button"
                      onClick={() => setVendorForm((prev) => ({ ...prev, showPass: !prev.showPass }))}
                      className={styles.eyeToggle}
                      aria-label="Toggle password visibility"
                    >
                      <IconWrapper name={vendorForm.showPass ? 'eye-off' : 'eye'} size={16} />
                    </button>
                  </div>
                </div>

                <div className={styles.optionsRow}>
                  <label className={styles.rememberLabel}>
                    <input type="checkbox" defaultChecked className={styles.checkbox} />
                    <span>Remember me</span>
                  </label>
                  <Link to="/login/vendor" className={styles.forgotLink}>Forgot Password?</Link>
                </div>

                <Button
                  type="submit"
                  variant="primary"
                  size="md"
                  className={styles.vendorLoginBtn}
                  loading={loadingRole === 'VENDOR'}
                >
                  LOGIN
                </Button>

                <div className={styles.badgeLine}>
                  <span className={styles.recycleIcon}>♻️</span>
                  <span>Join our mission to repair, reuse & recycle.</span>
                </div>
              </form>

              {/* Bottom Engraved Artwork: Workshop Tool Chest */}
              <div className={styles.bottomArtwork} aria-hidden="true">
                <svg viewBox="0 0 280 90" className={styles.artworkSvg}>
                  <g stroke="var(--vestige-walnut)" opacity="0.65" strokeWidth="1" fill="none" strokeLinecap="round" strokeLinejoin="round">
                    <rect x="70" y="50" width="140" height="35" fill="rgba(70,80,56,0.1)" strokeWidth="1.4" />
                    <line x1="70" y1="62" x2="210" y2="62" />
                    <rect x="132" y="58" width="16" height="8" rx="1" fill="var(--vestige-gold-aged)" strokeWidth="0.8" />
                    <line x1="85" y1="50" x2="75" y2="20" strokeWidth="2.2" />
                    <circle cx="74" cy="18" r="5" strokeWidth="1.2" />
                    <line x1="110" y1="50" x2="105" y2="15" strokeWidth="1.8" />
                    <line x1="165" y1="50" x2="175" y2="22" strokeWidth="1.8" />
                    <path d="M190 50 L200 18 L206 22 L196 50 Z" fill="rgba(43,27,18,0.15)" />
                  </g>
                </svg>
              </div>
            </div>
          </div>

          {/* Bottom Archival Plaque matching Reference 3 */}
          <div className={styles.bottomPlaque}>
            <AntiqueCorner position="topLeft" color="var(--vestige-gold-royal, #D4AF37)" />
            <AntiqueCorner position="topRight" color="var(--vestige-gold-royal, #D4AF37)" />
            <AntiqueCorner position="bottomLeft" color="var(--vestige-gold-royal, #D4AF37)" />
            <AntiqueCorner position="bottomRight" color="var(--vestige-gold-royal, #D4AF37)" />

            <div className={styles.plaqueShield}>
              <IconWrapper name="shield" size={20} color="var(--vestige-gold-light)" />
            </div>
            <div className={styles.plaqueText}>
              <span className={styles.plaqueTitle}>One Platform. Three Roles. One Mission.</span>
              <span className={styles.plaqueMotto}>Repair. Reuse. Recycle. Reward.</span>
            </div>
          </div>

          <div className={styles.bottomQuote}>
            &ldquo;We don&apos;t just repair devices, we restore stories.&rdquo;
          </div>
        </Container>
      </div>
    </PageShell>
  );
}

export default PortalsPage;
