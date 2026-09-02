import apiClient from './api';

/**
 * Health check service for backend readiness.
 */
export async function getBackendHealth() {
  return apiClient('/health');
}

export default {
  getBackendHealth,
};
