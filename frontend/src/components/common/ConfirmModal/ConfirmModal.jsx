import React from 'react';
import Button from '../Button/Button';
import IconWrapper from '../IconWrapper/IconWrapper';
import styles from './ConfirmModal.module.css';

export function ConfirmModal({
  isOpen,
  title = 'Confirm Action',
  message = 'Are you certain you wish to proceed with this operation?',
  confirmLabel = 'Confirm',
  cancelLabel = 'Cancel',
  isDestructive = false,
  loading = false,
  onConfirm,
  onCancel,
}) {
  if (!isOpen) return null;

  return (
    <div
      className={styles.overlay}
      role="dialog"
      aria-modal="true"
      aria-labelledby="confirm-modal-title"
    >
      <div className={styles.modal}>
        <div className={styles.header}>
          <div
            className={styles.iconCircle}
            style={{
              borderColor: isDestructive ? 'var(--vestige-rust)' : 'var(--vestige-brass)',
              color: isDestructive ? 'var(--vestige-rust)' : 'var(--vestige-brass-dark)',
            }}
          >
            <IconWrapper name={isDestructive ? 'alert-triangle' : 'shield'} size={20} />
          </div>
          <h2 id="confirm-modal-title" className={styles.title}>
            {title}
          </h2>
        </div>

        <p className={styles.message}>{message}</p>

        <div className={styles.actions}>
          <Button
            variant="ghost"
            size="md"
            onClick={onCancel}
            disabled={loading}
          >
            {cancelLabel}
          </Button>
          <Button
            variant={isDestructive ? 'primary' : 'ornate'}
            size="md"
            onClick={onConfirm}
            loading={loading}
            style={
              isDestructive
                ? {
                    backgroundColor: 'var(--vestige-rust)',
                    borderColor: 'var(--vestige-rust-light)',
                    color: 'var(--vestige-ivory-warm)',
                  }
                : {}
            }
          >
            {confirmLabel}
          </Button>
        </div>
      </div>
    </div>
  );
}

export default ConfirmModal;
