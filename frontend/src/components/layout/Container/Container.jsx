import React from 'react';
import styles from './Container.module.css';

export function Container({
  children,
  size = 'lg', // 'sm' | 'md' | 'lg' | 'full'
  className = '',
  ...props
}) {
  return (
    <div className={`${styles.container} ${styles[size]} ${className}`} {...props}>
      {children}
    </div>
  );
}

export default Container;
