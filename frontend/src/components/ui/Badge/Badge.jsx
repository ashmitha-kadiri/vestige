import React from 'react';
import styles from './Badge.module.css';

export function Badge({
  children,
  variant = 'default', // 'default' | 'success' | 'warning' | 'error' | 'gold'
  className = '',
  ...props
}) {
  return (
    <span className={`${styles.badge} ${styles[variant]} ${className}`} {...props}>
      {children}
    </span>
  );
}

export default Badge;
