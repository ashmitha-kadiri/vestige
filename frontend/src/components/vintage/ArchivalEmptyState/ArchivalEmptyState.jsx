import React from 'react';
import VintageIllustration from '../VintageIllustration/VintageIllustration';
import Button from '../../common/Button/Button';
import styles from './ArchivalEmptyState.module.css';

export function ArchivalEmptyState({
  illustration = 'books',
  title = 'No Archival Records Found',
  description = 'There are currently no ledger entries recorded under this category.',
  actionLabel,
  actionIcon,
  onAction,
  actionHref,
  className = '',
}) {
  return (
    <div className={`${styles.emptyWrapper} ${className}`} role="status">
      <div className={styles.illustrationBox} aria-hidden="true">
        <VintageIllustration
          name={illustration}
          size={64}
          opacity={0.45}
          color="var(--vestige-brass-dark)"
        />
      </div>

      <h3 className={styles.title}>{title}</h3>
      <p className={styles.description}>{description}</p>

      {(actionLabel && (onAction || actionHref)) && (
        <div className={styles.actionBlock}>
          {actionHref ? (
            <a href={actionHref} className={styles.actionLink}>
              <Button variant="primary" size="sm" icon={actionIcon}>
                {actionLabel}
              </Button>
            </a>
          ) : (
            <Button variant="primary" size="sm" icon={actionIcon} onClick={onAction}>
              {actionLabel}
            </Button>
          )}
        </div>
      )}
    </div>
  );
}

export default ArchivalEmptyState;
