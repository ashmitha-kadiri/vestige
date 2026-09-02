import React from 'react';
import styles from './Input.module.css';

export const Input = React.forwardRef(function Input({
  type = 'text',
  id,
  name,
  value,
  onChange,
  placeholder,
  disabled = false,
  required = false,
  hasError = false,
  icon,
  iconRight,
  className = '',
  'aria-describedby': ariaDescribedBy,
  ...props
}, ref) {
  return (
    <div className={`${styles.container} ${hasError ? styles.errorContainer : ''} ${className}`}>
      {icon && <span className={styles.iconLeft}>{icon}</span>}
      <input
        ref={ref}
        type={type}
        id={id}
        name={name}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        disabled={disabled}
        required={required}
        aria-invalid={hasError}
        aria-describedby={ariaDescribedBy}
        className={`${styles.input} ${icon ? styles.hasLeftIcon : ''} ${iconRight ? styles.hasRightIcon : ''}`}
        {...props}
      />
      {iconRight && <span className={styles.iconRight}>{iconRight}</span>}
    </div>
  );
});

export default Input;
