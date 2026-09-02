import { api } from './api';

export const deviceService = {
  /**
   * Submit device details to Decision Engine
   */
  assessDevice: async (payload) => {
    return await api.post('/devices/assess', payload);
  },

  /**
   * Get device assessment by ID
   */
  getDeviceAssessment: async (id) => {
    return await api.get(`/devices/${id}`);
  },

  /**
   * Get all device assessments for a user
   */
  getUserAssessments: async (userId) => {
    return await api.get(`/devices/user/${userId}`);
  },
};

export default deviceService;
