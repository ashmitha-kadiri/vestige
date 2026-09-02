import React from 'react';
import { Link } from 'react-router-dom';
import PageShell from '../../../components/layout/PageShell/PageShell';
import Container from '../../../components/layout/Container/Container';
import LedgerCard from '../../../components/vintage/LedgerCard/LedgerCard';
import WaxSealBadge from '../../../components/vintage/WaxSealBadge/WaxSealBadge';
import IconWrapper from '../../../components/common/IconWrapper/IconWrapper';
import Button from '../../../components/common/Button/Button';
import styles from './NotFound.module.css';

export function NotFoundPage() {
  return (
    <PageShell>
      <div className={styles.container}>
        <Container size="sm">
          <div className={styles.card}>
            <LedgerCard
              variant="default"
              watermark="archive"
              watermarkSize={160}
              watermarkOpacity={0.06}
              headerBadge={
                <WaxSealBadge
                  variant="espresso"
                  size="md"
                  icon={<IconWrapper name="search" size={22} color="var(--vestige-gold-aged)" />}
                  label="UNRECORDED"
                />
              }
              title="Page Not Found"
              subtitle="The requested document has no recorded entry in our platform registry."
            >
              <div className={styles.numeral}>404</div>
              <p className={styles.description}>
                The folio or terminal you are attempting to access does not exist, has been relocated, or was never catalogued in the VESTIGE registry.
              </p>
              <div className={styles.actions}>
                <Link to="/">
                  <Button variant="primary" icon={<IconWrapper name="arrow-left" size={16} />}>
                    Return to Grand Archive
                  </Button>
                </Link>
                <Link to="/portals">
                  <Button variant="ghost">
                    Explore Institutional Portals
                  </Button>
                </Link>
              </div>
            </LedgerCard>
          </div>
        </Container>
      </div>
    </PageShell>
  );
}

export default NotFoundPage;
