import React from 'react';
import styles from './Divider.module.css';

export function Divider({
  flourish = '✦',
  variant = 'gold', // 'gold' | 'subtle' | 'dark'
  className = '',
  ...props
}) {
  return (
    <div className={`${styles.divider} ${styles[variant]} ${className}`} role="separator" {...props}>
      {flourish && <span className={styles.flourish}>{flourish}</span>}
    </div>
  );
}

export default Divider;
