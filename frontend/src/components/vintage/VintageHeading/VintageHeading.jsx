import React from 'react';
import styles from './VintageHeading.module.css';

export function VintageHeading({
  level = 1,
  children,
  subtitle,
  eyebrow,
  align = 'center', // 'center' | 'left' | 'right'
  className = '',
}) {
  const HeadingTag = `h${level}`;

  return (
    <header className={`${styles.header} ${styles[align]} ${className}`}>
      {eyebrow && <p className={styles.eyebrow}>{eyebrow}</p>}
      <HeadingTag className={styles.title}>{children}</HeadingTag>
      {subtitle && <p className={styles.subtitle}>{subtitle}</p>}
    </header>
  );
}

export default VintageHeading;
