/**
 * VESTIGE API Client Service Foundation
 * Handles REST requests to the Spring Boot backend with consistent error handling,
 * JWT token injection, and concurrent GET request deduplication.
 */

const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'https://vestige-owfd.onrender.com/api';
const TOKEN_KEY = 'vestige_auth_token';

// In-flight GET request cache to prevent duplicate simultaneous network requests
const inFlightRequests = new Map();

export async function apiClient(endpoint, options = {}) {
  const normalizedEndpoint = endpoint.startsWith('/api/')
    ? endpoint.substring(4)
    : endpoint.startsWith('/')
    ? endpoint
    : `/${endpoint}`;
  const url = `${BASE_URL}${normalizedEndpoint}`;
  
  const token = localStorage.getItem(TOKEN_KEY);

  const headers = {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
    ...(options.headers || {}),
  };

  const config = {
    ...options,
    headers,
  };

  const method = (config.method || 'GET').toUpperCase();
  const isGet = method === 'GET';
  const cacheKey = isGet ? `${url}_${token || ''}` : null;

  // If there is already an identical GET request in flight, reuse its promise
  if (isGet && inFlightRequests.has(cacheKey)) {
    return inFlightRequests.get(cacheKey);
  }

  const executeRequest = async () => {
    try {
      const response = await fetch(url, config);
      const data = await response.json().catch(() => null);

      if (!response.ok) {
        const error = new Error((data && data.message) || `HTTP error! status: ${response.status}`);
        error.status = response.status;
        error.data = data;
        throw error;
      }

      return data;
    } catch (err) {
      console.error(`API Error on ${endpoint}:`, err);
      throw err;
    } finally {
      if (isGet) {
        inFlightRequests.delete(cacheKey);
      }
    }
  };

  if (isGet) {
    const promise = executeRequest();
    inFlightRequests.set(cacheKey, promise);
    return promise;
  }

  return executeRequest();
}

export const api = {
  get: (endpoint, options = {}) => apiClient(endpoint, { method: 'GET', ...options }),
  post: (endpoint, body, options = {}) => apiClient(endpoint, { method: 'POST', body: JSON.stringify(body), ...options }),
  patch: (endpoint, body, options = {}) => apiClient(endpoint, { method: 'PATCH', body: JSON.stringify(body), ...options }),
  put: (endpoint, body, options = {}) => apiClient(endpoint, { method: 'PUT', body: JSON.stringify(body), ...options }),
  delete: (endpoint, options = {}) => apiClient(endpoint, { method: 'DELETE', ...options }),
};

export default api;
