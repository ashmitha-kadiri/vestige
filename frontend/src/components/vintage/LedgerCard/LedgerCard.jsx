import React from 'react';
import OrnamentalBorder from '../OrnamentalBorder/OrnamentalBorder';
import VintageIllustration from '../VintageIllustration/VintageIllustration';
import styles from './LedgerCard.module.css';

export function LedgerCard({
  children,
  headerBadge,
  title,
  subtitle,
  footer,
  watermark, // e.g. 'laptop', 'scales', 'tools', 'recycling', 'typewriter', 'pocketwatch', 'books'
  watermarkOpacity = 0.08,
  watermarkSize = 120,
  variant = 'default', // 'default' | 'elevated' | 'admin' | 'vendor' | 'user' | 'plate'
  interactive = false,
  className = '',
  ...props
}) {
  return (
    <div
      className={`${styles.card} ${styles[variant]} ${interactive ? styles.interactive : ''} ${className}`}
      {...props}
    >
      <OrnamentalBorder variant="double" cornerVariant="scrollwork" className={styles.borderFrame}>
        <div className={styles.innerContent}>
          {/* Subtle Faded Background Watermark Illustration */}
          {watermark && (
            <div className={styles.watermarkWrapper} aria-hidden="true">
              <VintageIllustration
                name={watermark}
                size={watermarkSize}
                opacity={watermarkOpacity}
                color="var(--vestige-ink, #2B1F16)"
              />
            </div>
          )}

          {(headerBadge || title) && (
            <div className={styles.header}>
              {headerBadge && <div className={styles.badgeWrapper}>{headerBadge}</div>}
              {title && <h3 className={styles.title}>{title}</h3>}
              {subtitle && <p className={styles.subtitle}>{subtitle}</p>}
            </div>
          )}

          <div className={styles.body}>{children}</div>

          {footer && <div className={styles.footer}>{footer}</div>}
        </div>
      </OrnamentalBorder>
    </div>
  );
}

export default LedgerCard;
