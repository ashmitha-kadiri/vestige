import React from 'react';
import { generateWhatsAppLink, getSupportWhatsAppLink } from '../../../utils/whatsapp';
import { useTranslation } from '../../../i18n/useTranslation';
import styles from './WhatsAppButton.module.css';

export function WhatsAppButton({
  phoneNumber,
  message,
  label,
  variant = 'inline', // 'floating' | 'inline'
  className = '',
  ariaLabel,
}) {
  const { t } = useTranslation();

  const url = phoneNumber
    ? generateWhatsAppLink(phoneNumber, message || t('support.workshopGreeting'))
    : getSupportWhatsAppLink(message || t('support.whatsappGreeting'));

  const defaultLabel = phoneNumber
    ? (label || t('bookings.contactWorkshop', '💬 Contact Workshop'))
    : (label || t('support.contactSupport', 'Contact Support'));

  const accessibleLabel = ariaLabel || (phoneNumber ? 'Contact assigned workshop on WhatsApp' : 'Contact VESTIGE platform support on WhatsApp');

  const content = (
    <>
      <svg
        className={styles.icon}
        viewBox="0 0 24 24"
        aria-hidden="true"
      >
        <path d="M12.031 6.172c-3.181 0-5.767 2.586-5.768 5.766-.001 1.298.38 2.27 1.019 3.287l-.582 2.128 2.182-.573c.978.58 1.911.928 3.145.929 3.178 0 5.767-2.587 5.768-5.766.001-3.187-2.575-5.771-5.764-5.771zm3.392 8.244c-.144.405-.837.774-1.17.824-.312.045-.634.077-.99-.04-.492-.162-1.115-.494-1.928-1.205-.826-.723-1.396-1.583-1.637-1.996-.242-.413-.026-.637.1-.762.113-.113.25-.29.375-.446.126-.156.168-.265.252-.443.084-.177.042-.333-.021-.458-.063-.125-.563-1.356-.772-1.857-.203-.489-.41-.423-.563-.431-.146-.008-.313-.01-.48-.01-.167 0-.438.062-.667.312-.229.25-.875.855-.875 2.085 0 1.23.896 2.418 1.021 2.585.125.167 1.762 2.69 4.27 3.774.596.257 1.062.411 1.425.527.599.19 1.144.163 1.575.099.48-.072 1.479-.604 1.688-1.188.21-.584.21-1.084.147-1.189-.064-.104-.23-.166-.474-.288z" />
        <path d="M12 2C6.477 2 2 6.477 2 12c0 1.89.525 3.66 1.438 5.168L2 22l4.982-1.402A9.957 9.957 0 0012 22c5.523 0 10-4.477 10-10S17.523 2 12 2zm0 18.167c-1.677 0-3.238-.506-4.542-1.373l-.326-.217-2.964.833.844-2.887-.238-.344C3.842 14.807 3.333 13.439 3.333 12c0-4.78 3.887-8.667 8.667-8.667 4.78 0 8.667 3.887 8.667 8.667 0 4.78-3.887 8.667-8.667 8.667z" />
      </svg>
      <span>{defaultLabel}</span>
    </>
  );

  if (variant === 'floating') {
    return (
      <aside className={styles.floatingContainer} aria-label="Support Assistance">
        <a
          href={url}
          target="_blank"
          rel="noopener noreferrer"
          className={`${styles.floatingButton} ${className}`}
          aria-label={accessibleLabel}
        >
          {content}
        </a>
      </aside>
    );
  }

  return (
    <a
      href={url}
      target="_blank"
      rel="noopener noreferrer"
      className={`${styles.inlineButton} ${className}`}
      aria-label={accessibleLabel}
    >
      {content}
    </a>
  );
}

export default WhatsAppButton;
