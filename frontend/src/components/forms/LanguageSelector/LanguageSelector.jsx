import React from 'react';
import IconWrapper from '../../common/IconWrapper/IconWrapper';
import { SUPPORTED_LANGUAGES } from '../../../i18n/languages';
import styles from './LanguageSelector.module.css';

export function LanguageSelector({
  currentLanguage = 'en',
  onLanguageChange,
  className = '',
}) {
  const handleChange = (e) => {
    const newLang = e.target.value;
    if (onLanguageChange) {
      onLanguageChange(newLang);
    }
  };

  return (
    <div className={`${styles.wrapper} ${className}`}>
      <label htmlFor="vestige-lang-select" className="sr-only">
        Select Interface Language
      </label>
      <span className={styles.iconBox} aria-hidden="true">
        <IconWrapper name="globe" size={15} color="var(--vestige-gold)" />
      </span>
      <select
        id="vestige-lang-select"
        value={currentLanguage}
        onChange={handleChange}
        className={styles.select}
        aria-label="Language selection"
      >
        {SUPPORTED_LANGUAGES.map((lang) => (
          <option key={lang.code} value={lang.code}>
            {lang.native} ({lang.code.toUpperCase()})
          </option>
        ))}
      </select>
    </div>
  );
}

export { SUPPORTED_LANGUAGES };
export default LanguageSelector;
