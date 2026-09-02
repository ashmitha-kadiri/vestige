import React from 'react';
import styles from './FormLabel.module.css';

export function FormLabel({
  htmlFor,
  children,
  required = false,
  className = '',
  ...props
}) {
  return (
    <label htmlFor={htmlFor} className={`${styles.label} ${className}`} {...props}>
      {children}
      {required && <span className={styles.required} aria-hidden="true"> *</span>}
    </label>
  );
}

export default FormLabel;
