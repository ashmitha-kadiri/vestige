import React from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import VestigeLogo from '../../common/VestigeLogo/VestigeLogo';
import Container from '../Container/Container';
import { useAuth } from '../../../contexts/AuthContext';
import useTranslation from '../../../i18n/useTranslation';
import SUPPORTED_LANGUAGES from '../../../i18n/languages';
import styles from './Header.module.css';

export function Header() {
  const location = useLocation();
  const navigate = useNavigate();
  const { user, isAuthenticated, logout } = useAuth();
  const { t, language, setLanguage } = useTranslation();

  const isHomeActive = location.pathname === '/' && (!location.hash || location.hash === '#top');

  const handleSignOut = async () => {
    await logout();
    navigate('/');
  };

  const getDashboardPath = () => {
    if (!user) return '/login/user';
    if (user.role === 'ADMIN') return '/admin/dashboard';
    if (user.role === 'VENDOR') return '/vendor/dashboard';
    return '/user/dashboard';
  };

  const handleNav = (e, targetId) => {
    e.preventDefault();
    if (targetId === 'home') {
      if (location.pathname === '/') {
        window.scrollTo({ top: 0, behavior: 'smooth' });
        window.history.pushState(null, '', '/');
      } else {
        navigate('/');
      }
      return;
    }

    if (location.pathname !== '/') {
      navigate(`/#${targetId}`);
    } else {
      const el = document.getElementById(targetId);
      if (el) {
        el.scrollIntoView({ behavior: 'smooth', block: 'start' });
        window.history.pushState(null, '', `/#${targetId}`);
      }
    }
  };

  return (
    <header className={styles.header}>
      <Container size="lg" className={styles.container}>
        {/* Brand Monogram & Wordmark */}
        <Link
          to="/"
          onClick={(e) => handleNav(e, 'home')}
          className={styles.brandLink}
          aria-label="VESTIGE Home"
        >
          <VestigeLogo
            variant="horizontal"
            size="md"
            theme="light"
            tagline={t('tagline', 'Give Technology a Second Life')}
          />
        </Link>

        {/* Center Navigation Links */}
        <nav className={styles.nav} aria-label="Main Navigation">
          <Link
            to="/"
            onClick={(e) => handleNav(e, 'home')}
            className={`${styles.navLink} ${isHomeActive ? styles.navLinkActive : ''}`}
          >
            {t('nav.home', 'Home')}
            {isHomeActive && <span className={styles.activeBar} />}
          </Link>
          <a
            href="/#about"
            onClick={(e) => handleNav(e, 'about')}
            className={`${styles.navLink} ${location.hash === '#about' ? styles.navLinkActive : ''}`}
          >
            {t('nav.about', 'About Us')}
          </a>
          <a
            href="/#how-it-works"
            onClick={(e) => handleNav(e, 'how-it-works')}
            className={`${styles.navLink} ${location.hash === '#how-it-works' ? styles.navLinkActive : ''}`}
          >
            {t('nav.howItWorks', 'How It Works')}
          </a>
          <a
            href="/#services"
            onClick={(e) => handleNav(e, 'services')}
            className={`${styles.navLink} ${location.hash === '#services' ? styles.navLinkActive : ''}`}
          >
            {t('nav.services', 'Services')}
          </a>
          <a
            href="/#impact"
            onClick={(e) => handleNav(e, 'impact')}
            className={`${styles.navLink} ${location.hash === '#impact' ? styles.navLinkActive : ''}`}
          >
            {t('nav.impact', 'Impact')}
          </a>
          <a
            href="/#contact"
            onClick={(e) => handleNav(e, 'contact')}
            className={`${styles.navLink} ${location.hash === '#contact' ? styles.navLinkActive : ''}`}
          >
            {t('nav.contact', 'Contact')}
          </a>
        </nav>

        {/* Right Auth / Portal Action Buttons & Language Selector */}
        <div className={styles.controls}>
          <div className={styles.langSelectorWrapper}>
            <select
              id="header-lang-selector"
              aria-label="Select Language"
              value={language}
              onChange={(e) => setLanguage(e.target.value)}
              className={styles.langSelect}
            >
              {SUPPORTED_LANGUAGES.map((l) => (
                <option key={l.code} value={l.code}>
                  {l.native} ({l.code.toUpperCase()})
                </option>
              ))}
            </select>
          </div>

          {isAuthenticated && user ? (
            <div className={styles.authGroup}>
              <Link to={getDashboardPath()} className={styles.btnSolid}>
                {t('nav.dashboard', 'DASHBOARD')}
              </Link>
              <span className={styles.roleTag}>{user.role}</span>
              <button onClick={handleSignOut} className={styles.btnOutline}>
                {t('nav.signOut', 'SIGN OUT')}
              </button>
            </div>
          ) : (
            <div className={styles.authGroup}>
              <Link to="/login/user" className={styles.btnOutline}>
                {t('nav.login', 'LOGIN')}
              </Link>
              <Link to="/portals" className={styles.btnSolid}>
                {t('nav.joinUs', 'JOIN US')}
              </Link>
            </div>
          )}
        </div>
      </Container>
    </header>
  );
}

export default Header;
