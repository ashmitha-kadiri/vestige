/**
 * VESTIGE Validation Helpers
 */

export function isValidEmail(email) {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}

export function isValidPhone(phone) {
  const phoneRegex = /^[0-9+-\s()]{7,20}$/;
  return phoneRegex.test(phone);
}
