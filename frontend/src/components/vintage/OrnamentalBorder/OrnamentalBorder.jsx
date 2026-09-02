import React from 'react';
import styles from './OrnamentalBorder.module.css';

/**
 * OrnamentalBorder Component
 * Renders an antique archival border frame with authentic Victorian, Baroque,
 * and Acanthus scrollwork and filigree corner ornaments.
 */
export function OrnamentalBorder({
  children,
  variant = 'double', // 'double' | 'engraved' | 'corner-flourish' | 'plaque' | 'card'
  cornerVariant = 'scrollwork', // 'scrollwork' | 'acanthus' | 'filigree' | 'heraldic' | 'botanical' | 'brass' | 'none'
  color = 'var(--vestige-gold-aged, #C49A45)',
  className = '',
  ...props
}) {
  const renderCorner = (positionClass) => {
    if (cornerVariant === 'none') return null;

    // 1. Victorian Antique Scrollwork & Filigree (Default — matching Vol. 2 Vector Set)
    if (cornerVariant === 'scrollwork') {
      return (
        <svg
          viewBox="0 0 48 48"
          className={`${styles.cornerSvg} ${styles.cornerScrollwork} ${positionClass}`}
          aria-hidden="true"
        >
          <g stroke={color} fill="none" strokeLinecap="round" strokeLinejoin="round">
            {/* Outer Corner Guide Rules */}
            <path d="M2 46 L2 14 Q2 2 14 2 L46 2" strokeWidth="1.5" />
            <path d="M6 46 L6 16 Q6 6 16 6 L46 6" strokeWidth="0.75" strokeDasharray="3 1.5" opacity="0.7" />
            {/* Corner Finial / Bead */}
            <circle cx="4" cy="4" r="2.2" fill={color} stroke="none" />
            {/* Main Baroque S-Scroll & Volute */}
            <path
              d="M10 38 Q10 20 24 16 Q34 14 36 24 Q38 32 28 34 Q20 36 18 26 Q17 18 26 12 Q34 7 42 10"
              strokeWidth="1.2"
              fill="none"
            />
            {/* Acanthus Leaf Shading / Flourish Spines */}
            <path d="M14 24 Q18 20 24 22 Q28 24 26 28 Q24 32 20 30" fill={color} fillOpacity="0.2" strokeWidth="0.8" />
            <path d="M26 14 Q30 18 34 16" strokeWidth="0.9" />
            <path d="M12 34 Q16 30 20 32" strokeWidth="0.8" />
            {/* Delicate Accent Tendrils & Dots */}
            <circle cx="28" cy="24" r="1.4" fill={color} stroke="none" />
            <circle cx="42" cy="10" r="1.2" fill={color} stroke="none" />
            <circle cx="10" cy="38" r="1.2" fill={color} stroke="none" />
            <path d="M2 14 Q6 10 14 2" strokeWidth="0.75" opacity="0.5" />
          </g>
        </svg>
      );
    }

    // 2. Acanthus & Floral Baroque Corner
    if (cornerVariant === 'acanthus') {
      return (
        <svg
          viewBox="0 0 44 44"
          className={`${styles.cornerSvg} ${styles.cornerAcanthus} ${positionClass}`}
          aria-hidden="true"
        >
          <g stroke={color} fill="none" strokeLinecap="round" strokeLinejoin="round">
            {/* Border Lines */}
            <path d="M3 42 L3 12 C3 5 5 3 12 3 L42 3" strokeWidth="1.4" />
            {/* Blooming Rosette / Petals */}
            <circle cx="14" cy="14" r="5" strokeWidth="1.2" fill={color} fillOpacity="0.15" />
            <circle cx="14" cy="14" r="2" fill={color} stroke="none" />
            <path d="M14 6 C16 9 16 11 14 14 C12 11 12 9 14 6 Z" fill={color} fillOpacity="0.4" strokeWidth="0.7" />
            <path d="M6 14 C9 16 11 16 14 14 C11 12 9 12 6 14 Z" fill={color} fillOpacity="0.4" strokeWidth="0.7" />
            <path d="M14 22 C12 19 12 17 14 14 C16 17 16 19 14 22 Z" fill={color} fillOpacity="0.4" strokeWidth="0.7" />
            <path d="M22 14 C19 12 17 12 14 14 C17 16 19 16 22 14 Z" fill={color} fillOpacity="0.4" strokeWidth="0.7" />
            {/* Outer Vine Scrolls */}
            <path d="M19 14 Q28 14 34 8 Q38 4 42 6" strokeWidth="1.1" />
            <path d="M14 19 Q14 28 8 34 Q4 38 6 42" strokeWidth="1.1" />
            <path d="M22 8 Q28 6 34 10" strokeWidth="0.75" />
            <path d="M8 22 Q6 28 10 34" strokeWidth="0.75" />
          </g>
        </svg>
      );
    }

    // 3. Delicate Arabesque Filigree
    if (cornerVariant === 'filigree') {
      return (
        <svg
          viewBox="0 0 36 36"
          className={`${styles.cornerSvg} ${styles.cornerFiligree} ${positionClass}`}
          aria-hidden="true"
        >
          <g stroke={color} fill="none" strokeLinecap="round" strokeLinejoin="round">
            <path d="M2 34 L2 10 Q2 2 10 2 L34 2" strokeWidth="1.2" />
            <path d="M6 6 Q16 6 22 16 Q26 22 32 20 Q36 18 34 12 Q32 8 26 10 Q20 12 18 20 Q16 28 8 26" strokeWidth="1" />
            <circle cx="8" cy="8" r="1.5" fill={color} stroke="none" />
            <circle cx="22" cy="16" r="1.2" fill={color} stroke="none" />
            <path d="M6 18 Q12 14 16 18" strokeWidth="0.7" opacity="0.6" />
          </g>
        </svg>
      );
    }

    // 4. Heraldic / Engraved Notched Corner
    if (cornerVariant === 'heraldic') {
      return (
        <svg
          viewBox="0 0 32 32"
          className={`${styles.cornerSvg} ${styles.cornerHeraldic} ${positionClass}`}
          aria-hidden="true"
        >
          <g stroke={color} fill="none" strokeLinecap="round" strokeLinejoin="round">
            <path d="M2 30 L2 8 L8 2 L30 2" strokeWidth="1.5" />
            <path d="M5 30 L5 10 L10 5 L30 5" strokeWidth="0.8" strokeDasharray="2 2" opacity="0.7" />
            <rect x="8" y="8" width="4" height="4" fill={color} fillOpacity="0.3" strokeWidth="0.8" />
            <circle cx="10" cy="10" r="1" fill={color} stroke="none" />
            <path d="M12 2 L2 12" strokeWidth="1" />
          </g>
        </svg>
      );
    }

    // 5. Botanical
    if (cornerVariant === 'botanical') {
      return (
        <svg
          viewBox="0 0 28 28"
          className={`${styles.cornerSvg} ${positionClass}`}
          aria-hidden="true"
        >
          <g stroke={color} fill="none" strokeLinecap="round" strokeLinejoin="round">
            <path d="M2 26 L2 8 Q2 2 8 2 L26 2" strokeWidth="1.2" />
            <path d="M6 6 Q14 4 18 10 Q20 14 16 18 Q12 20 8 16 Q6 12 12 10" strokeWidth="1" fill={color} fillOpacity="0.15" />
            <circle cx="6" cy="6" r="1.5" fill={color} stroke="none" />
          </g>
        </svg>
      );
    }

    // 6. Brass notched
    return (
      <svg
        viewBox="0 0 16 16"
        className={`${styles.cornerSvg} ${positionClass}`}
        aria-hidden="true"
      >
        <path d="M0 0 L16 0 L16 3 L3 3 L3 16 L0 16 Z" fill={color} />
        <rect x="5" y="5" width="2" height="2" fill={color} />
      </svg>
    );
  };

  return (
    <div className={`${styles.frame} ${styles[variant]} ${className}`} {...props}>
      {renderCorner(styles.topLeft)}
      {renderCorner(styles.topRight)}
      {renderCorner(styles.bottomLeft)}
      {renderCorner(styles.bottomRight)}
      <div className={styles.content}>{children}</div>
    </div>
  );
}

export default OrnamentalBorder;
