import React from 'react';
import heroImg from '../../../assets/vestige_hero.jpg';
import styles from './HeroArtwork.module.css';

export function HeroArtwork({ className = '' }) {
  return (
    <div className={`${styles.frameWrapper} ${className}`}>
      <div className={styles.imageCard}>
        <img
          src={heroImg}
          alt="Vestige Vintage Workshop & Device Restoration"
          className={styles.heroImage}
        />
        <div className={styles.vignetteOverlay} />
        <div className={styles.innerGlow} />
      </div>
    </div>
  );
}

export default HeroArtwork;
