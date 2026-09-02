import React, { useState } from 'react';
import paymentService from '../../services/paymentService';
import useTranslation from '../../i18n/useTranslation';
import styles from './RazorpayCheckoutModal.module.css';

export function RazorpayCheckoutModal({ booking, onClose, onSuccess }) {
  const { t } = useTranslation();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  const amount = booking?.submission?.estimatedRepairCost || 500.0;
  const brand = booking?.submission?.brand || 'Device';
  const model = booking?.submission?.model || 'Hardware';

  const handlePay = async () => {
    try {
      setLoading(true);
      setError(null);

      // 1. Request order creation from Spring Boot backend (server-computed amount)
      const orderRes = await paymentService.createRepairPaymentOrder(booking.id);
      const orderData = orderRes.data;

      // 2. Check if Razorpay script is loaded
      if (window.Razorpay) {
        const options = {
          key: orderData.razorpayKeyId,
          amount: Math.round(orderData.amount * 100),
          currency: orderData.currency,
          name: orderData.businessName || 'VESTIGE Restoration Atelier',
          description: orderData.description,
          order_id: orderData.providerOrderId,
          prefill: {
            email: orderData.customerEmail,
            contact: orderData.customerPhone,
          },
          theme: {
            color: '#c9a84c',
          },
          handler: async function (response) {
            try {
              // 3. Verify signature server-side
              const verifyRes = await paymentService.verifyPaymentSignature({
                razorpayOrderId: response.razorpay_order_id,
                razorpayPaymentId: response.razorpay_payment_id,
                razorpaySignature: response.razorpay_signature,
              });
              setSuccess(true);
              setTimeout(() => {
                if (onSuccess) onSuccess(verifyRes.data);
                if (onClose) onClose();
              }, 1200);
            } catch (vErr) {
              setError(vErr.response?.data?.message || 'Signature verification failed.');
            }
          },
          modal: {
            ondismiss: function () {
              setLoading(false);
            },
          },
        };

        const rzp = new window.Razorpay(options);
        rzp.open();
      } else {
        // Fallback for development/sandbox testing without Razorpay checkout.js script
        const mockPaymentId = `pay_mock_${Date.now()}`;
        const verifyRes = await paymentService.verifyPaymentSignature({
          razorpayOrderId: orderData.providerOrderId,
          razorpayPaymentId: mockPaymentId,
          razorpaySignature: 'mock_signature_test',
        });
        setSuccess(true);
        setTimeout(() => {
          if (onSuccess) onSuccess(verifyRes.data);
          if (onClose) onClose();
        }, 1200);
      }
    } catch (err) {
      setError(err.response?.data?.message || err.message || 'Failed to initialize payment.');
      setLoading(false);
    }
  };

  return (
    <div className={styles.overlay} role="dialog" aria-modal="true" aria-labelledby="pay-modal-title">
      <div className={styles.modal}>
        <button className={styles.closeBtn} onClick={onClose} aria-label="Close modal">
          &times;
        </button>

        <div className={styles.header}>
          <h2 id="pay-modal-title" className={styles.title}>
            {t('payment.title', 'Hardware Restoration Service Fee')}
          </h2>
          <p className={styles.subtitle}>
            Order Ref: #{booking?.id?.substring(0, 8) || 'N/A'}
          </p>
        </div>

        <div className={styles.badge}>
          <span>🛡️</span>
          <span>{t('payment.secureBadge', 'Razorpay 256-Bit Encrypted Checkout')}</span>
        </div>

        <div className={styles.billSummary}>
          <div className={styles.billRow}>
            <span>Item:</span>
            <span>{brand} {model} Restoration</span>
          </div>
          <div className={styles.billRow}>
            <span>Workshop:</span>
            <span>{booking?.vendor?.businessName || 'Certified Atelier'}</span>
          </div>
          <div className={styles.billRow}>
            <span>Diagnostic & Parts Base:</span>
            <span>₹{Number(amount).toFixed(2)}</span>
          </div>
          <div className={styles.billTotal}>
            <span>{t('payment.amountPayable', 'Amount Payable')}:</span>
            <span>₹{Number(amount).toFixed(2)}</span>
          </div>
        </div>

        {error && <div className={`${styles.statusMessage} ${styles.statusError}`}>{error}</div>}
        {success && (
          <div className={`${styles.statusMessage} ${styles.statusSuccess}`}>
            ✓ {t('payment.success', 'Payment Verified & Confirmed')}
          </div>
        )}

        <div className={styles.actions}>
          <button
            className={styles.payBtn}
            onClick={handlePay}
            disabled={loading || success}
          >
            {loading ? t('payment.verifying', 'Processing...') : `${t('payment.proceedBtn', 'Pay with Razorpay')} (₹${Number(amount).toFixed(2)})`}
          </button>
          <button className={styles.cancelBtn} onClick={onClose} disabled={loading}>
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
}

export default RazorpayCheckoutModal;
