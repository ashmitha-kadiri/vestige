import React from 'react';
import styles from './FormError.module.css';

export function FormError({
  id,
  message,
  className = '',
}) {
  if (!message) return null;

  return (
    <div id={id} className={`${styles.error} ${className}`} role="alert">
      <span className={styles.bullet}>&para;</span> {message}
    </div>
  );
}

export default FormError;
