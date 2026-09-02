import React from 'react';
import styles from './ArchivalSkeleton.module.css';

export function ArchivalSkeleton({
  variant = 'text', // 'text' | 'card' | 'table' | 'chart'
  rows = 3,
  height,
  width,
  className = '',
}) {
  if (variant === 'card') {
    return (
      <div className={`${styles.cardSkeleton} ${className}`} style={{ height: height || '180px', width: width || '100%' }}>
        <div className={styles.shimmer} />
        <div className={styles.lineShort} />
        <div className={styles.lineTitle} />
        <div className={styles.lineLong} />
      </div>
    );
  }

  if (variant === 'table') {
    return (
      <div className={`${styles.tableSkeleton} ${className}`}>
        <div className={styles.tableHeader} />
        {Array.from({ length: rows }).map((_, idx) => (
          <div key={idx} className={styles.tableRow}>
            <div className={styles.cellShort} />
            <div className={styles.cellLong} />
            <div className={styles.cellMedium} />
            <div className={styles.cellAction} />
          </div>
        ))}
      </div>
    );
  }

  if (variant === 'chart') {
    return (
      <div className={`${styles.chartSkeleton} ${className}`} style={{ height: height || '220px' }}>
        <div className={styles.shimmer} />
        <div className={styles.chartAxisY} />
        <div className={styles.chartAxisX} />
      </div>
    );
  }

  return (
    <div className={`${styles.textSkeletonGroup} ${className}`}>
      {Array.from({ length: rows }).map((_, idx) => (
        <div
          key={idx}
          className={styles.textLine}
          style={{
            width: idx === rows - 1 ? '60%' : '100%',
            height: height || '16px',
          }}
        />
      ))}
    </div>
  );
}

export default ArchivalSkeleton;
