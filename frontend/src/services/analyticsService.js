import api from './api';

export const analyticsService = {
  getAdminOverview: (params) => api.get('/analytics/admin/overview', { params }).then((res) => res.data),
  getAdminDevices: (params) => api.get('/analytics/admin/devices', { params }).then((res) => res.data),
  getAdminRepairs: (params) => api.get('/analytics/admin/repairs', { params }).then((res) => res.data),
  getAdminRecycling: (params) => api.get('/analytics/admin/recycling', { params }).then((res) => res.data),
  getAdminRewards: (params) => api.get('/analytics/admin/rewards', { params }).then((res) => res.data),
  getAdminVendorWorkload: (params) => api.get('/analytics/admin/vendors', { params }).then((res) => res.data),
  getAdminPerformance: (params) => api.get('/admin/performance', { params }).then((res) => res.data),
  getVendorOverview: (params) => api.get('/analytics/vendor/overview', { params }).then((res) => res.data),
  getUserOverview: (params) => api.get('/analytics/user/overview', { params }).then((res) => res.data),
};

export default analyticsService;
