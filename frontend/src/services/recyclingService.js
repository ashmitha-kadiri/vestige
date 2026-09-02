import { api } from './api';

export const recyclingService = {
  /**
   * Schedule an e-waste recycling collection pickup (Primary function)
   */
  createRecyclingRequest: async (payload) => {
    return await api.post('/recycling', payload);
  },

  /**
   * Alias for createRecyclingRequest for backward compatibility
   */
  schedulePickup: async (payload) => {
    return await api.post('/recycling', payload);
  },

  /**
   * Get user's recycling requests
   */
  getMyRecyclingRequests: async (userId) => {
    const endpoint = userId ? `/recycling/user/${userId}` : '/recycling/my';
    return await api.get(endpoint);
  },

  /**
   * Get recycling request details by ID
   */
  getRecyclingRequest: async (id) => {
    return await api.get(`/recycling/${id}`);
  },

  /**
   * Cancel a pending recycling request
   */
  cancelRecyclingRequest: async (id, notes = 'Cancelled by patron') => {
    return await api.patch(`/recycling/${id}/status`, { status: 'CANCELLED', notes });
  },

  /**
   * Update status of a recycling request (e.g. mark COMPLETED to trigger reward points)
   */
  updateStatus: async (id, statusData) => {
    return await api.patch(`/recycling/${id}/status`, statusData);
  },

  /**
   * List all recycling requests (admin/vendor view)
   */
  getAllRequests: async (status) => {
    const endpoint = status ? `/recycling?status=${status}` : '/recycling';
    return await api.get(endpoint);
  },
};

export default recyclingService;
