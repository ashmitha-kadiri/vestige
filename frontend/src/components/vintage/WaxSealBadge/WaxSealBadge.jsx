import React from 'react';
import styles from './WaxSealBadge.module.css';

export function WaxSealBadge({
  icon,
  label,
  variant = 'gold', // 'gold' | 'olive' | 'espresso' | 'rust'
  size = 'md',      // 'sm' | 'md' | 'lg'
  className = '',
}) {
  return (
    <div className={`${styles.seal} ${styles[variant]} ${styles[size]} ${className}`} aria-hidden="true">
      <div className={styles.innerRing}>
        {icon && <span className={styles.icon}>{icon}</span>}
        {label && <span className={styles.label}>{label}</span>}
      </div>
    </div>
  );
}

export default WaxSealBadge;
