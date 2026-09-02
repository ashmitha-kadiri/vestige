import React, { useState } from 'react';
import Input from '../../common/Input/Input';
import IconWrapper from '../../common/IconWrapper/IconWrapper';
import styles from './PasswordInput.module.css';

export const PasswordInput = React.forwardRef(function PasswordInput({
  id,
  name = 'password',
  value,
  onChange,
  placeholder = 'Enter password',
  disabled = false,
  required = false,
  hasError = false,
  className = '',
  'aria-describedby': ariaDescribedBy,
  ...props
}, ref) {
  const [showPassword, setShowPassword] = useState(false);

  const toggleVisibility = () => {
    setShowPassword((prev) => !prev);
  };

  return (
    <Input
      ref={ref}
      type={showPassword ? 'text' : 'password'}
      id={id}
      name={name}
      value={value}
      onChange={onChange}
      placeholder={placeholder}
      disabled={disabled}
      required={required}
      hasError={hasError}
      className={className}
      aria-describedby={ariaDescribedBy}
      icon={<IconWrapper name="lock" size={18} />}
      iconRight={
        <button
          type="button"
          onClick={toggleVisibility}
          disabled={disabled}
          className={styles.toggleBtn}
          aria-label={showPassword ? 'Hide password' : 'Show password'}
          aria-pressed={showPassword}
          tabIndex={0}
        >
          <IconWrapper name={showPassword ? 'eye-off' : 'eye'} size={18} />
        </button>
      }
      {...props}
    />
  );
});

export default PasswordInput;
