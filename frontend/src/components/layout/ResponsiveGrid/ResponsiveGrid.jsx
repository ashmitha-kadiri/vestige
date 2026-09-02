import React from 'react';
import styles from './ResponsiveGrid.module.css';

export function ResponsiveGrid({
  children,
  columns = 3, // 1 | 2 | 3 | 4
  gap = 'lg',  // 'sm' | 'md' | 'lg' | 'xl'
  className = '',
  ...props
}) {
  return (
    <div
      className={`${styles.grid} ${styles[`cols-${columns}`]} ${styles[`gap-${gap}`]} ${className}`}
      {...props}
    >
      {children}
    </div>
  );
}

export default ResponsiveGrid;
