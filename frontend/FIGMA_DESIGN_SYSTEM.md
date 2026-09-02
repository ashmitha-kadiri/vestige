# VESTIGE — Figma Design System Specification & Architecture

This document defines the **Figma-to-Code** styling structure for the VESTIGE platform, organizing tokens, variables, component variants, and auto-layout systems.

---

## 1. Token Collections & Variable Mapping

Our design system is structured into **2 Figma Variable Collections**:

### Collection A: `Primitives`
Raw values that serve as the foundation of the design system:

| Group | Token / Variable | Hex / Value | Description |
|---|---|---|---|
| **Walnut** | `color.walnut.900` | `#1E120A` | Darkest walnut desk base |
| | `color.walnut.800` | `#2B1B12` | Primary walnut wood (header, dark cards) |
| | `color.walnut.700` | `#3A2618` | Dark coffee panel & plaque hover |
| | `color.walnut.600` | `#5A3B21` | Antique brown leather |
| | `color.walnut.500` | `#7A542E` | Warm bronze accents |
| **Gold** | `color.gold.700` | `#B8860B` | Classic antique gold filigree |
| | `color.gold.600` | `#C49A45` | Aged gold rules & card borders |
| | `color.gold.500` | `#D4AF37` | Royal gold highlight & chart curves |
| | `color.gold.300` | `#EEC97A` | Soft luminous gold glow & badge text |
| **Parchment** | `color.parchment.200` | `#F7ECD7` | Canvas medium-burn parchment background |
| | `color.parchment.100` | `#FAF4E8` | High-legibility cream card surface |
| | `color.parchment.50` | `#FDF9F0` | Bright warm paper highlight |
| **Ink** | `color.ink.900` | `#221810` | Dark brown-black archival body ink |
| | `color.ink.700` | `#52473D` | Faded sepia secondary text |
| | `color.ink.500` | `#766A5D` | Manuscript annotation ink |
| **Olive** | `color.olive.700` | `#68704A` | Muted botanical olive (sustainability) |
| **Rust** | `color.rust.700` | `#8A2E1C` | Sealed wax crimson (danger/error) |

---

## 2. Figma Spacing Scale (4pt/8pt Grid)

| Figma Variable | Pixel Value | CSS Variable | Utility Class |
|---|---|---|---|
| `spacing.1` | `4px` | `--space-1` | `.figma-gap-1` / `.figma-p-1` |
| `spacing.2` | `8px` | `--space-2` | `.figma-gap-2` / `.figma-p-2` |
| `spacing.3` | `12px` | `--space-3` | `.figma-gap-3` / `.figma-p-3` |
| `spacing.4` | `16px` | `--space-4` | `.figma-gap-4` / `.figma-p-4` |
| `spacing.5` | `20px` | `--space-5` | `.figma-gap-5` / `.figma-p-5` |
| `spacing.6` | `24px` | `--space-6` | `.figma-gap-6` / `.figma-p-6` |
| `spacing.8` | `32px` | `--space-8` | `.figma-gap-8` / `.figma-p-8` |
| `spacing.10` | `40px` | `--space-10` | `.figma-gap-10` |
| `spacing.12` | `48px` | `--space-12` | `.figma-gap-12` |

---

## 3. Figma Corner Radii

- `radius.none`: `0px` (Square archival rules)
- `radius.xs`: `2px` (Antique plaque bevels, small badges)
- `radius.sm`: `4px` (Buttons, input fields, ledger sheets)
- `radius.md`: `6px` (Standard containers)
- `radius.lg`: `8px` (Elevated modals)
- `radius.pill`: `9999px` (Circular seals and avatars only)

---

## 4. Figma Auto-Layout Utilities in Code (`figma-layout.css`)

Our styling code provides direct Figma Auto-Layout equivalents:

```html
<!-- Figma Vertical Auto-Layout Frame with Gap: 16px, Padding: 24px -->
<div class="figma-stack figma-gap-4 figma-p-6 figma-frame-card">
  <div class="figma-row figma-justify-between figma-align-center">
    <h3>Card Title</h3>
    <span class="badge">VERIFIED</span>
  </div>
  <p>Card body content with archival styling.</p>
</div>
```

---

## 5. Importing into Figma

To sync this system directly into your Figma workspace:
1. Open the **Tokens Studio for Figma** plugin (or Figma Variables API).
2. Import [`frontend/src/styles/figma-tokens.json`](file:///c:/Users/Asmitha%20kadiri/OneDrive/ZYLO/frontend/src/styles/figma-tokens.json).
3. The variables and styles will be automatically loaded into your Figma file.
