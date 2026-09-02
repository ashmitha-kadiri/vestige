/**
 * VESTIGE WhatsApp Integration Utility Foundation
 */
import { SUPPORT_WHATSAPP_NUMBER } from '../config/support';

export function generateWhatsAppLink(phoneNumber, message) {
  if (!phoneNumber) return '';
  const cleanPhone = phoneNumber.replace(/[^0-9]/g, '');
  const encodedText = message ? encodeURIComponent(message) : '';
  return `https://wa.me/${cleanPhone}${encodedText ? `?text=${encodedText}` : ''}`;
}

export function getSupportWhatsAppLink(message = 'Hello VESTIGE Support, I have an inquiry regarding the circular platform.') {
  return generateWhatsAppLink(SUPPORT_WHATSAPP_NUMBER, message);
}

export function getVendorWhatsAppLink(vendorPhone, message = 'Hello, inquiring about my VESTIGE repair booking.') {
  if (!vendorPhone) return '';
  return generateWhatsAppLink(vendorPhone, message);
}

export default {
  generateWhatsAppLink,
  getSupportWhatsAppLink,
  getVendorWhatsAppLink,
};
