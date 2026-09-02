import React from 'react';
import styles from './Card.module.css';

export function Card({
  children,
  className = '',
  variant = 'default', // 'default' | 'ornate' | 'inset'
  ...props
}) {
  return (
    <div className={`${styles.card} ${styles[variant]} ${className}`} {...props}>
      {children}
    </div>
  );
}

export default Card;
