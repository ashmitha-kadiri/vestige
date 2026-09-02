import React from 'react';
import styles from './Button.module.css';

export function Button({
  children,
  variant = 'primary', // 'primary' | 'secondary' | 'ornate' | 'outline' | 'ghost'
  size = 'md',        // 'sm' | 'md' | 'lg'
  disabled = false,
  loading = false,
  onClick,
  type = 'button',
  className = '',
  icon,
  iconPosition = 'right',
  ...props
}) {
  return (
    <button
      type={type}
      disabled={disabled || loading}
      onClick={onClick}
      className={`${styles.button} ${styles[variant]} ${styles[size]} ${className}`}
      {...props}
    >
      {loading && <span className={styles.spinner} aria-hidden="true" />}
      {icon && iconPosition === 'left' && <span className={styles.iconWrapper}>{icon}</span>}
      <span className={styles.label}>{children}</span>
      {icon && iconPosition === 'right' && <span className={styles.iconWrapper}>{icon}</span>}
    </button>
  );
}

export default Button;
