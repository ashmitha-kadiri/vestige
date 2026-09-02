/**
 * VESTIGE Figma Design Tokens (JS Mirror)
 * Programmatic constants mirroring Figma Variables & Styles for charts, animations, and dynamic styles.
 */

export const FIGMA_PRIMITIVES = {
  color: {
    walnut: {
      900: '#1E120A',
      800: '#2B1B12',
      700: '#3A2618',
      600: '#5A3B21',
      500: '#7A542E',
      400: '#8C6E3A',
    },
    gold: {
      900: '#826330',
      800: '#A8834A',
      700: '#B8860B',
      600: '#C49A45',
      500: '#D4AF37',
      400: '#D4AF63',
      300: '#EEC97A',
      200: '#F2D89A',
    },
    parchment: {
      900: '#CBB998',
      800: '#D8C39A',
      700: '#DEC79E',
      600: '#E6D5B8',
      500: '#EAD9B8',
      400: '#E8D7B5',
      300: '#F3E6C8',
      200: '#F7ECD7',
      100: '#FAF4E8',
      50: '#FDF9F0',
    },
    ink: {
      900: '#221810',
      700: '#52473D',
      500: '#766A5D',
      400: '#8A6A45',
    },
    olive: {
      900: '#303825',
      800: '#465038',
      700: '#68704A',
      500: '#828C5E',
    },
    rust: {
      900: '#641C10',
      700: '#8A2E1C',
      500: '#A33B26',
    },
  },
  spacing: {
    0: 0,
    1: 4,
    2: 8,
    3: 12,
    4: 16,
    5: 20,
    6: 24,
    8: 32,
    10: 40,
    12: 48,
    16: 64,
    20: 80,
    24: 96,
  },
  radius: {
    none: 0,
    xs: 2,
    sm: 4,
    md: 6,
    lg: 8,
    xl: 12,
    pill: 9999,
  },
  fonts: {
    heading: "'Playfair Display', 'Cinzel', Georgia, serif",
    serif: "'Cinzel', 'Playfair Display', Georgia, serif",
    body: "'Lora', 'Libre Baskerville', Garamond, serif",
    ui: "'Inter', -apple-system, sans-serif",
    mono: "'Courier Prime', monospace",
  },
};

export const FIGMA_SEMANTIC = {
  bg: {
    canvas: FIGMA_PRIMITIVES.color.parchment[200],
    surface: FIGMA_PRIMITIVES.color.parchment[100],
    surfaceElevated: FIGMA_PRIMITIVES.color.parchment[500],
    surfaceDark: FIGMA_PRIMITIVES.color.walnut[800],
  },
  text: {
    primary: FIGMA_PRIMITIVES.color.ink[900],
    secondary: FIGMA_PRIMITIVES.color.ink[700],
    muted: FIGMA_PRIMITIVES.color.ink[500],
    brand: FIGMA_PRIMITIVES.color.gold[700],
    inverse: FIGMA_PRIMITIVES.color.parchment[50],
  },
  border: {
    default: FIGMA_PRIMITIVES.color.gold[600],
    ornamental: FIGMA_PRIMITIVES.color.gold[700],
    focus: FIGMA_PRIMITIVES.color.gold[500],
  },
  status: {
    success: FIGMA_PRIMITIVES.color.olive[800],
    warning: FIGMA_PRIMITIVES.color.walnut[500],
    danger: FIGMA_PRIMITIVES.color.rust[700],
    gold: FIGMA_PRIMITIVES.color.gold[500],
  },
};

export default {
  primitives: FIGMA_PRIMITIVES,
  semantic: FIGMA_SEMANTIC,
  // Backward compatibility alias
  colors: {
    espresso: FIGMA_PRIMITIVES.color.walnut[900],
    walnut: FIGMA_PRIMITIVES.color.walnut[800],
    antiqueBrown: FIGMA_PRIMITIVES.color.walnut[600],
    parchment: FIGMA_PRIMITIVES.color.parchment[200],
    ivory: FIGMA_PRIMITIVES.color.parchment[100],
    gold: FIGMA_PRIMITIVES.color.gold[700],
    goldLight: FIGMA_PRIMITIVES.color.gold[500],
    goldDark: FIGMA_PRIMITIVES.color.gold[900],
    olive: FIGMA_PRIMITIVES.color.olive[700],
    rust: FIGMA_PRIMITIVES.color.rust[700],
  },
  fonts: FIGMA_PRIMITIVES.fonts,
};
