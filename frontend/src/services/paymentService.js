import apiClient from './apiClient';

/**
 * VESTIGE Payment Service (Razorpay Integration)
 */
export const paymentService = {
  /**
   * Initialize a Razorpay payment order for a repair booking
   * @param {string} bookingId - UUID of the repair booking
   */
  async createRepairPaymentOrder(bookingId) {
    const response = await apiClient.post('/api/payments/create-order', {
      bookingId,
    });
    return response.data;
  },

  /**
   * Verify Razorpay payment signature after client checkout completion
   * @param {Object} payload - { razorpayOrderId, razorpayPaymentId, razorpaySignature }
   */
  async verifyPaymentSignature(payload) {
    const response = await apiClient.post('/api/payments/verify', payload);
    return response.data;
  },

  /**
   * Retrieve patron's payment transaction history
   */
  async getMyPaymentHistory() {
    const response = await apiClient.get('/api/payments/my-history');
    return response.data;
  },

  /**
   * Retrieve platform-wide payment financial metrics (ADMIN ONLY)
   */
  async getAdminPaymentMetrics() {
    const response = await apiClient.get('/api/admin/payments');
    return response.data;
  },
};

export default paymentService;
