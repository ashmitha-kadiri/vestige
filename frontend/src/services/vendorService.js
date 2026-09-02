import { api } from './api';

export const vendorService = {
  /**
   * Get verified vendors (optionally filter by serviceType: RECYCLE | REPAIR)
   */
  getVendors: async (serviceType) => {
    const endpoint = serviceType ? `/vendors?serviceType=${serviceType}` : '/vendors';
    return await api.get(endpoint);
  },

  /**
   * Get vendor details by ID
   */
  getVendorById: async (id) => {
    return await api.get(`/vendors/${id}`);
  },
};

export default vendorService;
