import React from 'react';
import styles from './PaperTexture.module.css';

export function PaperTexture({ children, variant = 'parchment', className = '' }) {
  return (
    <div className={`${styles.textureWrapper} ${styles[variant]} ${className}`}>
      {/* Background grain and subtle archival vignette */}
      <div className={styles.grainOverlay} aria-hidden="true" />
      <div className={styles.vignetteOverlay} aria-hidden="true" />
      <div className={styles.contentLayer}>{children}</div>
    </div>
  );
}

export default PaperTexture;
