import React, { useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom';
import PageShell from '../../../components/layout/PageShell/PageShell';
import Container from '../../../components/layout/Container/Container';
import HeroArtwork from '../../../components/vintage/HeroArtwork/HeroArtwork';
import VintageIllustration from '../../../components/vintage/VintageIllustration/VintageIllustration';
import useTranslation from '../../../i18n/useTranslation';
import styles from './Landing.module.css';

export function LandingPage() {
  const { t } = useTranslation();
  const location = useLocation();

  useEffect(() => {
    const hash = location.hash || window.location.hash;
    if (hash) {
      const id = hash.replace('#', '');
      const element = document.getElementById(id);
      if (element) {
        setTimeout(() => {
          element.scrollIntoView({ behavior: 'smooth', block: 'start' });
        }, 80);
      }
    }
  }, [location.hash, location.pathname]);

  return (
    <PageShell>
      {/* 1. Hero Section matching Reference Specification */}
      <section className={styles.heroSection} id="home">
        <Container size="lg" className={styles.heroContainer}>
          <div className={styles.heroGrid}>
            {/* Left Column: Headline, Description, 3-Pillar Badges & Primary CTA */}
            <div className={styles.heroContent}>
              <div className={styles.editorialBadge}>
                <span className={styles.ornamentLine}>── • ──</span>
                <span>{t('hero.editorialLabel', 'WELCOME TO VESTIGE')}</span>
                <span className={styles.ornamentLine}>── • ──</span>
              </div>

              <h1 className={styles.mainTitle}>
                Give Technology<br />A Second Life.
              </h1>

              <p className={styles.leadCopy}>
                {t(
                  'hero.lead',
                  'We connect people, repair experts, and recycling partners to extend the life of electronic devices and build a more sustainable tomorrow.'
                )}
              </p>

              {/* 3-Pillar Badges with Vertical Dividers */}
              <div className={styles.valueBadgesRow} id="how-it-works">
                <div className={styles.valueBadgeItem}>
                  <div className={styles.valueIconBox}>
                    <VintageIllustration name="tools" size={20} color="var(--vestige-bronze, #7A542E)" opacity={0.95} />
                  </div>
                  <div className={styles.valueTextGroup}>
                    <span className={styles.valueTitle}>REPAIR</span>
                    <span className={styles.valueSubtitle}>Fix. Reuse.</span>
                  </div>
                </div>

                <div className={styles.badgeDivider} />

                <div className={styles.valueBadgeItem}>
                  <div className={styles.valueIconBox}>
                    <VintageIllustration name="recycling" size={20} color="var(--vestige-bronze, #7A542E)" opacity={0.95} />
                  </div>
                  <div className={styles.valueTextGroup}>
                    <span className={styles.valueTitle}>RECYCLE</span>
                    <span className={styles.valueSubtitle}>Reduce Waste.</span>
                  </div>
                </div>

                <div className={styles.badgeDivider} />

                <div className={styles.valueBadgeItem}>
                  <div className={styles.valueIconBox}>
                    <VintageIllustration name="ribbon" size={20} color="var(--vestige-bronze, #7A542E)" opacity={0.95} />
                  </div>
                  <div className={styles.valueTextGroup}>
                    <span className={styles.valueTitle}>REWARD</span>
                    <span className={styles.valueSubtitle}>Earn. Redeem.</span>
                  </div>
                </div>
              </div>

              {/* Primary Plaque CTA Button -> Navigates to Portal Selection Page */}
              <div className={styles.ctaGroup}>
                <Link to="/portals" className={styles.ctaLink}>
                  <button className={styles.assessPlaqueBtn}>
                    {t('hero.cta.assess', 'Assess Your Device →')}
                  </button>
                </Link>
              </div>
            </div>

            {/* Right Column: Hero Artwork Illustration */}
            <div className={styles.heroVisual}>
              <HeroArtwork />
            </div>
          </div>
        </Container>
      </section>

      {/* 2. Dark Walnut Supporting Feature Strip */}
      <section className={styles.supportingStrip} id="services">
        <Container size="lg">
          <div className={styles.supportingGrid}>
            <div className={styles.supportingItem}>
              <div className={styles.supportingIconCircle}>
                <VintageIllustration name="everyone" size={22} color="#D4AF63" />
              </div>
              <div className={styles.supportingText}>
                <h3 className={styles.supportingTitle}>For Everyone</h3>
                <p className={styles.supportingDesc}>Individuals, businesses, and communities.</p>
              </div>
            </div>

            <div className={styles.supportingItem}>
              <div className={styles.supportingIconCircle}>
                <VintageIllustration name="partner" size={22} color="#D4AF63" />
              </div>
              <div className={styles.supportingText}>
                <h3 className={styles.supportingTitle}>Trusted Partners</h3>
                <p className={styles.supportingDesc}>Verified repair &amp; recycling experts.</p>
              </div>
            </div>

            <div className={styles.supportingItem} id="impact">
              <div className={styles.supportingIconCircle}>
                <VintageIllustration name="leaf" size={22} color="#D4AF63" />
              </div>
              <div className={styles.supportingText}>
                <h3 className={styles.supportingTitle}>Sustainable Impact</h3>
                <p className={styles.supportingDesc}>Together, we reduce e-waste and protect our planet.</p>
              </div>
            </div>

            <div className={styles.supportingItem}>
              <div className={styles.supportingIconCircle}>
                <VintageIllustration name="lock" size={22} color="#D4AF63" />
              </div>
              <div className={styles.supportingText}>
                <h3 className={styles.supportingTitle}>Secure &amp; Reliable</h3>
                <p className={styles.supportingDesc}>Your data and devices are always safe.</p>
              </div>
            </div>
          </div>

          <div className={styles.supportingOrnament}>
            <span className={styles.flourish}>❧</span>
          </div>
        </Container>
      </section>

      {/* 3. About & Manifesto Section */}
      <section className={styles.manifestoSection} id="about">
        <Container size="md">
          <div className={styles.manifestoCard}>
            <div className={styles.manifestoContent}>
              <div className={styles.manifestoInsignia}>
                <VintageIllustration name="crest" size={36} opacity={0.85} color="var(--vestige-walnut, #2B1B12)" />
              </div>
              <h2 className={styles.manifestoTitle}>The Archival Standard of Electronics</h2>
              <p className={styles.manifestoText}>
                &ldquo;In an era of disposable electronics, VESTIGE treats every piece of hardware as a piece of human ingenuity worth preserving. We unite patrons who cherish their instruments with artisans who possess the timeless craft to restore them.&rdquo;
              </p>
              <div className={styles.manifestoSignoff}>
                <span className={styles.signoffSeal}>❖</span>
                <span>The Directorate of Archival Conservation, VESTIGE</span>
              </div>
            </div>
          </div>
        </Container>
      </section>
    </PageShell>
  );
}

export default LandingPage;
