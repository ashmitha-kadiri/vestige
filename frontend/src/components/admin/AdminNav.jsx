import React from 'react';
import { NavLink } from 'react-router-dom';
import { useTranslation } from '../../i18n/useTranslation';
import styles from './AdminNav.module.css';

export function AdminNav() {
  const { t } = useTranslation();

  const navItems = [
    { to: '/admin/dashboard', key: 'dashboard', label: 'Dashboard', icon: '🏛️' },
    { to: '/admin/users', key: 'users', label: 'Users', icon: '👥' },
    { to: '/admin/vendors', key: 'vendors', label: 'Vendors', icon: '🛠️' },
    { to: '/admin/repairs', key: 'repairs', label: 'Repairs', icon: '⚙️' },
    { to: '/admin/recycling', key: 'recycling', label: 'Recycling', icon: '♻️' },
    { to: '/admin/rewards', key: 'rewards', label: 'Rewards', icon: '🪙' },
    { to: '/admin/payments', key: 'payments', label: 'Payments', icon: '💳' },
    { to: '/admin/analytics', key: 'analytics', label: 'Analytics', icon: '📊' },
    { to: '/admin/performance', key: 'performance', label: 'Performance', icon: '⚡' },
    { to: '/admin/audit', key: 'audit', label: 'Audit Log', icon: '📜' },
  ];

  return (
    <nav className={styles.adminNav} aria-label="Admin Portal Navigation">
      {navItems.map((item) => (
        <NavLink
          key={item.to}
          to={item.to}
          className={({ isActive }) =>
            `${styles.navLink} ${isActive ? styles.activeLink : ''}`
          }
        >
          <span>{item.icon}</span>
          <span>{t(`admin.nav.${item.key}`, item.label)}</span>
        </NavLink>
      ))}
    </nav>
  );
}

export default AdminNav;
