import React from 'react';
import styles from './VestigeLogo.module.css';

/**
 * VESTIGE Heritage Brand Logo
 *
 * Variants: horizontal | vertical | icon-only | badge
 * Sizes: sm | md | lg | xl
 * Themes: dark | light | gold
 */
export function VestigeLogo({
  variant = 'horizontal',
  size = 'md',
  theme = 'dark',
  tagline = 'Give Technology a Second Life',
  className = '',
  ...props
}) {
  const emblemSize = {
    sm: 34,
    md: 46,
    lg: 62,
    xl: 82,
  }[size] || 46;

  const gradientId = `vestigeGold-${size}-${theme}`;

  const renderEmblem = () => (
    <div
      className={`${styles.emblemWrapper} ${styles[size]} ${styles[theme]}`}
      aria-hidden="true"
    >
      <svg
        width={emblemSize}
        height={emblemSize}
        viewBox="0 0 120 120"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
        className={styles.emblemSvg}
      >
        <defs>
          <linearGradient
            id={gradientId}
            x1="18"
            y1="15"
            x2="100"
            y2="105"
            gradientUnits="userSpaceOnUse"
          >
            <stop offset="0" stopColor="#F3DDA5" />
            <stop offset="0.28" stopColor="#D6B45A" />
            <stop offset="0.58" stopColor="#B78B2D" />
            <stop offset="0.82" stopColor="#E0C77B" />
            <stop offset="1" stopColor="#8B692C" />
          </linearGradient>

          <filter id={`${gradientId}-shadow`} x="-30%" y="-30%" width="160%" height="160%">
            <feDropShadow dx="0" dy="1" stdDeviation="1.2" floodOpacity="0.3" />
          </filter>
        </defs>

        {/* =========================================
            OUTER RING SYSTEM
        ========================================= */}

        {/* Solid outer ring */}
        <circle
          cx="60" cy="60" r="56"
          stroke={`url(#${gradientId})`}
          strokeWidth="1.1"
          opacity="0.9"
        />

        {/* Dotted inner ring */}
        <circle
          cx="60" cy="60" r="50"
          stroke={`url(#${gradientId})`}
          strokeWidth="1"
          strokeDasharray="0.5 4.2"
          strokeLinecap="round"
          opacity="0.75"
        />

        {/* =========================================
            TOP FINIAL (fleur-de-lis on the ring)
        ========================================= */}

        <path
          d="M60 2
             C58.5 7 56 9.5 51.5 11
             C55.5 12.8 58 12.6 60 11.6
             C62 12.6 64.5 12.8 68.5 11
             C64 9.5 61.5 7 60 2Z"
          fill={`url(#${gradientId})`}
        />
        <path
          d="M53 6C51 5 49.5 3.5 48.5 1.5"
          stroke={`url(#${gradientId})`}
          strokeWidth="0.9"
          strokeLinecap="round"
        />
        <path
          d="M67 6C69 5 70.5 3.5 71.5 1.5"
          stroke={`url(#${gradientId})`}
          strokeWidth="0.9"
          strokeLinecap="round"
        />
        <circle cx="60" cy="4" r="1.4" fill={`url(#${gradientId})`} />

        {/* =========================================
            SIDE + BOTTOM RING ORNAMENTS
        ========================================= */}

        {/* Left */}
        <g>
          <path
            d="M4 55C4 52.5 5.5 50.5 8 49.5C6 51.2 5.2 53 5.2 55C5.2 57 6 58.8 8 60.5C5.5 59.5 4 57.5 4 55Z"
            fill={`url(#${gradientId})`}
          />
          <path
            d="M9 55L1 55M6 51L2 48M6 59L2 62"
            stroke={`url(#${gradientId})`}
            strokeWidth="0.8"
            strokeLinecap="round"
          />
        </g>

        {/* Right (mirrored) */}
        <g>
          <path
            d="M116 55C116 52.5 114.5 50.5 112 49.5C114 51.2 114.8 53 114.8 55C114.8 57 114 58.8 112 60.5C114.5 59.5 116 57.5 116 55Z"
            fill={`url(#${gradientId})`}
          />
          <path
            d="M111 55L119 55M114 51L118 48M114 59L118 62"
            stroke={`url(#${gradientId})`}
            strokeWidth="0.8"
            strokeLinecap="round"
          />
        </g>

        {/* Bottom */}
        <g>
          <path
            d="M60 110L64 114L60 118L56 114Z"
            fill={`url(#${gradientId})`}
          />
          <path
            d="M50 114C53 116 56.5 116.6 60 116M70 114C67 116 63.5 116.6 60 116"
            stroke={`url(#${gradientId})`}
            strokeWidth="0.8"
            strokeLinecap="round"
          />
        </g>

        {/* =========================================
            SMALL FLEUR ABOVE THE V
        ========================================= */}

        <path
          d="M60 26
             C58.5 30 56.5 32 53 33.2
             C56.5 34.6 58.5 34.4 60 33.6
             C61.5 34.4 63.5 34.6 67 33.2
             C63.5 32 61.5 30 60 26Z"
          fill={`url(#${gradientId})`}
          opacity="0.95"
        />
        <path
          d="M55.5 30C54 29.2 53 28 52.3 26.5M64.5 30C66 29.2 67 28 67.7 26.5"
          stroke={`url(#${gradientId})`}
          strokeWidth="0.7"
          strokeLinecap="round"
        />

        {/* =========================================
            LAUREL LEFT — fuller, curled tip
        ========================================= */}

        <path
          d="M27 40
             C19 48 16 58 18 68
             C20 78 27 86 37 92
             C34 88 32 84 32 80"
          stroke={`url(#${gradientId})`}
          strokeWidth="1.4"
          strokeLinecap="round"
        />

        {[
          { d: "M27 43C23 42 20 40 18 37", },
          { d: "M23 51C19 50 16 48 14 45", },
          { d: "M20 59C16 58 13 56 11 53", },
          { d: "M19 68C15 68 12 66 10 64", },
          { d: "M20 77C16 78 13 76 11 74", },
          { d: "M24 85C21 87 18 87 15 86", },
          { d: "M31 91C28 93 25 93 22 92", },
        ].map((leaf, i) => (
          <path
            key={`ll-${i}`}
            d={leaf.d}
            stroke={`url(#${gradientId})`}
            strokeWidth="1.1"
            strokeLinecap="round"
          />
        ))}

        {/* Curled tip */}
        <path
          d="M37 92C39 94.5 39 97 37.5 99"
          stroke={`url(#${gradientId})`}
          strokeWidth="1.2"
          strokeLinecap="round"
        />

        {/* =========================================
            LAUREL RIGHT — mirrored
        ========================================= */}

        <path
          d="M93 40
             C101 48 104 58 102 68
             C100 78 93 86 83 92
             C86 88 88 84 88 80"
          stroke={`url(#${gradientId})`}
          strokeWidth="1.4"
          strokeLinecap="round"
        />

        {[
          { d: "M93 43C97 42 100 40 102 37", },
          { d: "M97 51C101 50 104 48 106 45", },
          { d: "M100 59C104 58 107 56 109 53", },
          { d: "M101 68C105 68 108 66 110 64", },
          { d: "M100 77C104 78 107 76 109 74", },
          { d: "M96 85C99 87 102 87 105 86", },
          { d: "M89 91C92 93 95 93 98 92", },
        ].map((leaf, i) => (
          <path
            key={`lr-${i}`}
            d={leaf.d}
            stroke={`url(#${gradientId})`}
            strokeWidth="1.1"
            strokeLinecap="round"
          />
        ))}

        <path
          d="M83 92C81 94.5 81 97 82.5 99"
          stroke={`url(#${gradientId})`}
          strokeWidth="1.2"
          strokeLinecap="round"
        />

        {/* =========================================
            CENTRAL V CREST — flared serif feet
        ========================================= */}

        <g filter={`url(#${gradientId}-shadow)`}>
          {/* Left stroke with flared top serif */}
          <path
            d="M32 34
               C32 32 33.5 30.5 36 30.5
               L50 30.5
               C52 30.5 53 31.7 53.4 33.5
               L60 68
               L66.6 33.5
               C67 31.7 68 30.5 70 30.5
               L84 30.5
               C86.5 30.5 88 32 88 34
               L88 34.6
               C88 36.4 86.7 37.6 84.8 37.8
               L76.5 38.6
               L65 84
               C64.3 87 62.4 89 60 89
               C57.6 89 55.7 87 55 84
               L43.5 38.6
               L35.2 37.8
               C33.3 37.6 32 36.4 32 34.6
               Z"
            fill={`url(#${gradientId})`}
          />

          {/* Inner engraved V line */}
          <path
            d="M42 37L60 80L78 37"
            stroke="#5c4110"
            strokeWidth="0.8"
            opacity="0.5"
          />

          {/* Base ball ornament */}
          <circle cx="60" cy="93" r="2.2" fill={`url(#${gradientId})`} />
        </g>

        {/* =========================================
            LOWER DIVIDER (inside emblem, above ring)
        ========================================= */}

        <path
          d="M40 102H50M70 102H80"
          stroke={`url(#${gradientId})`}
          strokeWidth="1"
          strokeLinecap="round"
        />
      </svg>
    </div>
  );

  if (variant === 'icon-only') {
    return (
      <div className={`${styles.logoContainer} ${className}`} {...props}>
        {renderEmblem()}
      </div>
    );
  }

  return (
    <div
      className={`
        ${styles.logoContainer}
        ${styles[variant]}
        ${styles[theme]}
        ${styles[size]}
        ${className}
      `}
      {...props}
    >
      {renderEmblem()}

      <div className={styles.typographyBlock}>
        <span className={styles.wordmark}>VESTIGE</span>

        <div className={styles.divider} aria-hidden="true">
          <span className={styles.dividerLine} />
          <span className={styles.dividerOrnament}>
            <svg viewBox="0 0 40 12" width="28" height="8">
              <path
                d="M20 0C19 3 17 4.5 14 5C17 5.8 19 5.6 20 5
                   C21 5.6 23 5.8 26 5C23 4.5 21 3 20 0Z"
                fill="currentColor"
              />
              <path d="M14 6C10 6.5 6 6 3 4.5" stroke="currentColor" strokeWidth="0.6" fill="none" />
              <path d="M26 6C30 6.5 34 6 37 4.5" stroke="currentColor" strokeWidth="0.6" fill="none" />
            </svg>
          </span>
          <span className={styles.dividerLine} />
        </div>

        {tagline && <span className={styles.tagline}>{tagline}</span>}
      </div>
    </div>
  );
}

export default VestigeLogo;