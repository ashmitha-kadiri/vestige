import React from 'react';
import styles from './VintageIllustration.module.css';

export function VintageIllustration({
  name = 'crest',
  size = 64,
  color = 'var(--vestige-ink, #2B1F16)',
  opacity = 1,
  className = '',
  ariaLabel,
  ...props
}) {
  const renderIllustration = () => {
    switch (name) {
      case 'laptop':
        return (
          <g stroke={color} strokeWidth="1.25" fill="none" strokeLinecap="round" strokeLinejoin="round">
            {/* Screen */}
            <rect x="14" y="10" width="36" height="26" rx="2" strokeWidth="1.5" />
            <rect x="18" y="14" width="28" height="18" fill="rgba(176, 141, 87, 0.08)" />
            {/* Screen detail lines / schematics */}
            <line x1="22" y1="19" x2="42" y2="19" strokeDasharray="1 2" strokeWidth="1" />
            <line x1="22" y1="23" x2="38" y2="23" strokeDasharray="1 2" strokeWidth="1" />
            <line x1="22" y1="27" x2="34" y2="27" strokeDasharray="1 2" strokeWidth="1" />
            <circle cx="32" cy="12" r="0.75" fill={color} />
            {/* Hinge & Base */}
            <path d="M10 40 L54 40 L58 48 L6 48 Z" strokeWidth="1.5" fill="rgba(43, 31, 22, 0.04)" />
            {/* Keyboard grid hatching */}
            <line x1="16" y1="42" x2="48" y2="42" />
            <line x1="14" y1="45" x2="50" y2="45" />
            <line x1="22" y1="40" x2="20" y2="46" />
            <line x1="32" y1="40" x2="32" y2="46" />
            <line x1="42" y1="40" x2="44" y2="46" />
            {/* Trackpad */}
            <rect x="28" y="45.5" width="8" height="2" rx="0.5" strokeWidth="0.75" />
          </g>
        );

      case 'tools':
      case 'wrench':
        return (
          <g stroke={color} strokeWidth="1.35" fill="none" strokeLinecap="round" strokeLinejoin="round">
            {/* Crossed Wrench & Screwdriver */}
            {/* Screwdriver */}
            <path d="M12 52 L26 38" strokeWidth="2.5" stroke="rgba(176, 141, 87, 0.3)" />
            <path d="M12 52 L24 40" strokeWidth="2" />
            <path d="M24 40 L44 20 L48 16 L52 20 L48 24 L28 44 Z" strokeWidth="1.2" fill="rgba(43, 31, 22, 0.05)" />
            <line x1="48" y1="16" x2="54" y2="10" strokeWidth="1.5" />
            {/* Wrench */}
            <path d="M52 52 L38 38" strokeWidth="2.5" stroke="rgba(176, 141, 87, 0.3)" />
            <path d="M52 52 L24 24" strokeWidth="2" />
            {/* Wrench head */}
            <path d="M24 24 C20 20 14 18 10 22 C6 26 8 32 12 36 C16 34 20 34 22 32 Z" strokeWidth="1.3" fill="rgba(43, 31, 22, 0.05)" />
            <circle cx="16" cy="28" r="2.5" strokeWidth="1" />
            {/* Decorative filigree knot at center */}
            <circle cx="32" cy="32" r="3.5" stroke={color} fill="var(--vestige-ivory, #FBF7EE)" strokeWidth="1.5" />
            <circle cx="32" cy="32" r="1.2" fill={color} />
          </g>
        );

      case 'recycling':
        return (
          <g stroke={color} strokeWidth="1.3" fill="none" strokeLinecap="round" strokeLinejoin="round">
            {/* Triangular Circular Ribbon */}
            <path d="M32 10 L44 26 L38 26 L38 34 L26 34 L26 26 L20 26 Z" strokeWidth="1.2" fill="rgba(74, 83, 62, 0.08)" />
            <path d="M48 30 L54 48 L48 45 L42 52 L34 44 L40 37 L34 37 Z" strokeWidth="1.2" fill="rgba(74, 83, 62, 0.08)" />
            <path d="M16 30 L30 37 L24 37 L30 44 L22 52 L16 45 L10 48 Z" strokeWidth="1.2" fill="rgba(74, 83, 62, 0.08)" />
            {/* Engraved laurel / botanical sprigs around center */}
            <path d="M28 28 Q32 24 36 28 Q32 32 28 28" fill="rgba(176, 141, 87, 0.2)" strokeWidth="1" />
            <circle cx="32" cy="32" r="1.5" fill={color} />
          </g>
        );

      case 'globe':
        return (
          <g stroke={color} strokeWidth="1.25" fill="none" strokeLinecap="round" strokeLinejoin="round">
            {/* Armillary Ring */}
            <circle cx="32" cy="28" r="18" strokeWidth="1.5" />
            <ellipse cx="32" cy="28" rx="8" ry="18" strokeWidth="1" strokeDasharray="2 1" />
            <line x1="14" y1="28" x2="50" y2="28" strokeWidth="1" />
            <path d="M17 20 Q32 24 47 20" strokeWidth="0.8" />
            <path d="M17 36 Q32 32 47 36" strokeWidth="0.8" />
            {/* Pivot Axis & Stand */}
            <path d="M32 8 L32 10" strokeWidth="2" />
            <path d="M10 28 C10 40 20 48 32 48 C44 48 54 40 54 28" strokeWidth="1.75" />
            <line x1="32" y1="48" x2="32" y2="56" strokeWidth="2" />
            {/* Ornate Turned Brass Base */}
            <path d="M20 56 L44 56 L48 60 L16 60 Z" fill="rgba(176, 141, 87, 0.2)" strokeWidth="1.4" />
          </g>
        );

      case 'scales':
        return (
          <g stroke={color} strokeWidth="1.25" fill="none" strokeLinecap="round" strokeLinejoin="round">
            {/* Center Pillar */}
            <line x1="32" y1="10" x2="32" y2="54" strokeWidth="2" />
            <circle cx="32" cy="10" r="3" fill="var(--vestige-ivory, #FBF7EE)" strokeWidth="1.5" />
            {/* Crossbeam */}
            <path d="M14 18 Q32 16 50 18" strokeWidth="1.75" />
            <circle cx="14" cy="18" r="1.5" fill={color} />
            <circle cx="50" cy="18" r="1.5" fill={color} />
            {/* Left Pan */}
            <line x1="14" y1="18" x2="8" y2="34" strokeWidth="0.9" />
            <line x1="14" y1="18" x2="20" y2="34" strokeWidth="0.9" />
            <path d="M6 34 Q14 40 22 34 Z" fill="rgba(176, 141, 87, 0.15)" strokeWidth="1.25" />
            {/* Right Pan */}
            <line x1="50" y1="18" x2="44" y2="34" strokeWidth="0.9" />
            <line x1="50" y1="18" x2="56" y2="34" strokeWidth="0.9" />
            <path d="M42 34 Q50 40 58 34 Z" fill="rgba(176, 141, 87, 0.15)" strokeWidth="1.25" />
            {/* Heavy Archival Base */}
            <ellipse cx="32" cy="54" rx="14" ry="4" fill="rgba(43, 31, 22, 0.08)" strokeWidth="1.5" />
          </g>
        );

      case 'typewriter':
        return (
          <g stroke={color} strokeWidth="1.25" fill="none" strokeLinecap="round" strokeLinejoin="round">
            {/* Paper Sheet */}
            <path d="M22 6 L42 6 L42 22 L22 22 Z" fill="var(--vestige-ivory-warm, #FFFDF7)" strokeWidth="1.2" />
            <line x1="26" y1="10" x2="38" y2="10" strokeDasharray="1 1.5" strokeWidth="0.8" />
            <line x1="26" y1="14" x2="38" y2="14" strokeDasharray="1 1.5" strokeWidth="0.8" />
            {/* Carriage Roller */}
            <rect x="12" y="20" width="40" height="8" rx="2" fill="rgba(43, 31, 22, 0.1)" strokeWidth="1.5" />
            <circle cx="10" cy="24" r="3" fill={color} />
            <circle cx="54" cy="24" r="3" fill={color} />
            {/* Main Body Chassis */}
            <path d="M14 28 L50 28 L56 52 L8 52 Z" strokeWidth="1.5" fill="rgba(26, 20, 15, 0.05)" />
            {/* Key Rows */}
            <line x1="16" y1="36" x2="48" y2="36" strokeDasharray="2 3" strokeWidth="1.5" />
            <line x1="14" y1="42" x2="50" y2="42" strokeDasharray="2 3" strokeWidth="1.5" />
            <line x1="12" y1="48" x2="52" y2="48" strokeDasharray="2 3" strokeWidth="1.5" />
            {/* Spacebar */}
            <rect x="22" y="52" width="20" height="3" rx="1" fill={color} />
          </g>
        );

      case 'pocketwatch':
        return (
          <g stroke={color} strokeWidth="1.3" fill="none" strokeLinecap="round" strokeLinejoin="round">
            {/* Crown Loop */}
            <circle cx="32" cy="8" r="4.5" strokeWidth="1.2" />
            <rect x="30" y="12" width="4" height="3" fill={color} />
            {/* Outer Casing */}
            <circle cx="32" cy="36" r="22" strokeWidth="1.75" fill="rgba(176, 141, 87, 0.08)" />
            <circle cx="32" cy="36" r="18" strokeWidth="1" />
            {/* Roman Numerals / Hour Markers */}
            <line x1="32" y1="20" x2="32" y2="23" strokeWidth="1.5" />
            <line x1="32" y1="49" x2="32" y2="52" strokeWidth="1.5" />
            <line x1="16" y1="36" x2="19" y2="36" strokeWidth="1.5" />
            <line x1="45" y1="36" x2="48" y2="36" strokeWidth="1.5" />
            {/* Clock Hands */}
            <line x1="32" y1="36" x2="32" y2="25" strokeWidth="1.5" />
            <line x1="32" y1="36" x2="41" y2="31" strokeWidth="1.2" />
            <circle cx="32" cy="36" r="1.5" fill={color} />
          </g>
        );

      case 'magnifyingglass':
        return (
          <g stroke={color} strokeWidth="1.35" fill="none" strokeLinecap="round" strokeLinejoin="round">
            {/* Lens Frame */}
            <circle cx="26" cy="26" r="16" strokeWidth="2" fill="rgba(176, 141, 87, 0.08)" />
            <circle cx="26" cy="26" r="13" strokeWidth="0.75" strokeDasharray="2 2" />
            {/* Lens Reflection highlight */}
            <path d="M18 18 Q26 12 34 18" strokeWidth="1.2" stroke="rgba(251, 247, 238, 0.9)" />
            {/* Turned Brass Ferrule & Handle */}
            <rect x="37" y="37" width="5" height="4" transform="rotate(-45 37 37)" fill={color} />
            <path d="M40 40 L54 54" strokeWidth="4" strokeLinecap="round" stroke={color} />
            <circle cx="55" cy="55" r="1.5" fill={color} />
          </g>
        );

      case 'books':
        return (
          <g stroke={color} strokeWidth="1.3" fill="none" strokeLinecap="round" strokeLinejoin="round">
            {/* Stack of Three Archival Ledgers */}
            {/* Book 1 (Bottom) */}
            <path d="M12 46 L50 46 L54 54 L8 54 Z" fill="rgba(43, 31, 22, 0.12)" strokeWidth="1.5" />
            <path d="M8 50 Q10 46 12 46" strokeWidth="1.5" />
            {/* Book 2 (Middle) */}
            <path d="M14 36 L52 36 L50 44 L10 44 Z" fill="rgba(176, 141, 87, 0.14)" strokeWidth="1.5" />
            <line x1="16" y1="40" x2="48" y2="40" strokeWidth="0.75" strokeDasharray="2 1" />
            {/* Book 3 (Top) */}
            <path d="M16 26 L48 26 L52 34 L12 34 Z" fill="rgba(74, 83, 62, 0.15)" strokeWidth="1.5" />
            <line x1="18" y1="30" x2="46" y2="30" strokeWidth="0.75" strokeDasharray="2 1" />
            {/* Bookmark ribbon */}
            <path d="M40 26 L40 42 L43 39 L46 42 L46 26" fill={color} stroke="none" />
          </g>
        );

      case 'lamp':
        return (
          <g stroke={color} strokeWidth="1.3" fill="none" strokeLinecap="round" strokeLinejoin="round">
            {/* Shade */}
            <path d="M18 20 Q32 10 46 20 L50 26 L14 26 Z" fill="rgba(74, 83, 62, 0.2)" strokeWidth="1.5" />
            {/* Curved Arm */}
            <path d="M32 20 Q48 28 32 46" strokeWidth="2" />
            <circle cx="32" cy="20" r="1.5" fill={color} />
            {/* Pull Chain */}
            <line x1="24" y1="26" x2="24" y2="36" strokeDasharray="1 1" strokeWidth="0.9" />
            <circle cx="24" cy="37" r="1.2" fill={color} />
            {/* Stepped Base */}
            <ellipse cx="32" cy="50" rx="14" ry="4" fill="rgba(176, 141, 87, 0.15)" strokeWidth="1.5" />
            <ellipse cx="32" cy="54" rx="16" ry="3" fill={color} />
          </g>
        );

      case 'botanical':
        return (
          <g stroke={color} strokeWidth="1.25" fill="none" strokeLinecap="round" strokeLinejoin="round">
            {/* Laurel sprigs left & right */}
            <path d="M32 52 Q22 36 24 16" strokeWidth="1.4" />
            <path d="M32 52 Q42 36 40 16" strokeWidth="1.4" />
            {/* Left leaves */}
            <path d="M24 16 Q18 12 20 20 Q22 20 24 16" fill="rgba(74, 83, 62, 0.15)" />
            <path d="M23 26 Q16 24 19 32 Q22 30 23 26" fill="rgba(74, 83, 62, 0.15)" />
            <path d="M25 38 Q18 38 22 44 Q25 42 25 38" fill="rgba(74, 83, 62, 0.15)" />
            {/* Right leaves */}
            <path d="M40 16 Q46 12 44 20 Q42 20 40 16" fill="rgba(74, 83, 62, 0.15)" />
            <path d="M41 26 Q48 24 45 32 Q42 30 41 26" fill="rgba(74, 83, 62, 0.15)" />
            <path d="M39 38 Q46 38 42 44 Q39 42 39 38" fill="rgba(74, 83, 62, 0.15)" />
            {/* Center ribbon knot */}
            <circle cx="32" cy="50" r="2" fill={color} />
          </g>
        );

      case 'building':
      case 'atelier':
        return (
          <g stroke={color} strokeWidth="1.2" fill="none" strokeLinecap="round" strokeLinejoin="round">
            {/* Pediment Roof */}
            <polygon points="32,8 10,22 54,22" fill="rgba(176, 141, 87, 0.12)" strokeWidth="1.5" />
            <circle cx="32" cy="16" r="2" fill={color} />
            {/* Entablature */}
            <rect x="12" y="22" width="40" height="4" fill="var(--vestige-ivory, #FBF7EE)" strokeWidth="1.2" />
            {/* Classical Columns */}
            <line x1="16" y1="26" x2="16" y2="48" strokeWidth="2" />
            <line x1="26" y1="26" x2="26" y2="48" strokeWidth="2" />
            <line x1="38" y1="26" x2="38" y2="48" strokeWidth="2" />
            <line x1="48" y1="26" x2="48" y2="48" strokeWidth="2" />
            {/* Central Arch Portal */}
            <path d="M28 48 L28 36 Q32 32 36 36 L36 48 Z" fill="rgba(43, 31, 22, 0.15)" strokeWidth="1.2" />
            {/* Stepped Foundation */}
            <rect x="8" y="48" width="48" height="4" fill="rgba(43, 31, 22, 0.08)" strokeWidth="1.5" />
            <rect x="4" y="52" width="56" height="4" fill={color} />
          </g>
        );

      case 'quill':
      case 'inkwell':
        return (
          <g stroke={color} strokeWidth="1.3" fill="none" strokeLinecap="round" strokeLinejoin="round">
            {/* Glass Ink Bottle */}
            <path d="M38 38 L54 38 L56 54 L36 54 Z" fill="rgba(43, 31, 22, 0.2)" strokeWidth="1.5" />
            <rect x="42" y="34" width="8" height="4" fill="var(--vestige-brass, #B08D57)" strokeWidth="1.2" />
            <line x1="40" y1="46" x2="52" y2="46" strokeDasharray="1 1" stroke="var(--vestige-ivory, #FBF7EE)" />
            {/* Quill Feather */}
            <path d="M10 8 Q24 16 44 38" strokeWidth="2" stroke="var(--vestige-ink, #2B1F16)" />
            <path d="M10 8 Q16 2 28 14 Q38 26 44 38 Q32 26 22 22 Q14 20 10 8" fill="rgba(176, 141, 87, 0.18)" strokeWidth="1" />
            <line x1="18" y1="12" x2="24" y2="16" strokeWidth="0.75" />
            <line x1="24" y1="18" x2="30" y2="22" strokeWidth="0.75" />
            <line x1="30" y1="24" x2="36" y2="28" strokeWidth="0.75" />
          </g>
        );

      case 'ribbon':
      case 'award':
      case 'reward':
        return (
          <g stroke={color} strokeWidth="1.35" fill="none" strokeLinecap="round" strokeLinejoin="round">
            {/* Ornate Awareness / Reward Ribbon Loop */}
            <path d="M22 48 L28 34 C24 28 24 18 32 14 C40 18 40 28 36 34 L42 48 L35 44 L32 50 L29 44 Z" strokeWidth="1.4" fill="rgba(212, 175, 55, 0.1)" />
            <circle cx="32" cy="24" r="4" strokeWidth="1.2" />
            <circle cx="32" cy="24" r="1.5" fill={color} />
          </g>
        );

      case 'everyone':
      case 'people':
      case 'user':
        return (
          <g stroke={color} strokeWidth="1.35" fill="none" strokeLinecap="round" strokeLinejoin="round">
            <circle cx="32" cy="22" r="8" strokeWidth="1.5" />
            <path d="M18 46 C18 37 24 34 32 34 C40 34 46 37 46 46" strokeWidth="1.5" />
          </g>
        );

      case 'partner':
      case 'check':
      case 'verified':
        return (
          <g stroke={color} strokeWidth="1.35" fill="none" strokeLinecap="round" strokeLinejoin="round">
            <circle cx="32" cy="32" r="16" strokeWidth="1.4" strokeDasharray="3 2" />
            <path d="M24 32 L30 38 L40 26" strokeWidth="1.8" />
          </g>
        );

      case 'leaf':
      case 'impact':
        return (
          <g stroke={color} strokeWidth="1.35" fill="none" strokeLinecap="round" strokeLinejoin="round">
            <path d="M22 42 C22 42 20 26 34 20 C44 26 44 42 22 42 Z" strokeWidth="1.5" />
            <path d="M22 42 Q30 32 40 24" strokeWidth="1.3" />
          </g>
        );

      case 'lock':
      case 'security':
        return (
          <g stroke={color} strokeWidth="1.35" fill="none" strokeLinecap="round" strokeLinejoin="round">
            <rect x="20" y="26" width="24" height="20" rx="3" strokeWidth="1.5" />
            <path d="M26 26 V20 C26 16.7 28.7 14 32 14 C35.3 14 38 16.7 38 20 V26" strokeWidth="1.5" />
            <circle cx="32" cy="35" r="2" fill={color} />
            <line x1="32" y1="37" x2="32" y2="40" strokeWidth="1.5" />
          </g>
        );

      case 'crest':
      default:
        return (
          <g stroke={color} strokeWidth="1.35" fill="none" strokeLinecap="round" strokeLinejoin="round">
            {/* Crown Top */}
            <path d="M24 12 L26 8 L32 11 L38 8 L40 12 Z" fill="var(--vestige-brass, #B08D57)" strokeWidth="1" />
            {/* Shield Outline */}
            <path d="M32 14 L16 20 L16 38 C16 48 24 54 32 58 C40 54 48 48 48 38 L48 20 Z" strokeWidth="1.75" fill="rgba(176, 141, 87, 0.08)" />
            {/* Inner Archival Letter V */}
            <path d="M24 26 L32 44 L40 26" strokeWidth="2.5" strokeLinecap="round" />
            <line x1="28" y1="35" x2="36" y2="35" strokeWidth="1.5" />
          </g>
        );
    }
  };

  return (
    <svg
      viewBox="0 0 64 64"
      width={size}
      height={size}
      className={`${styles.illustration} ${className}`}
      style={{ opacity, color }}
      role={ariaLabel ? 'img' : 'presentation'}
      aria-label={ariaLabel}
      aria-hidden={!ariaLabel}
      {...props}
    >
      {renderIllustration()}
    </svg>
  );
}

export default VintageIllustration;
