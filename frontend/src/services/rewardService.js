import { api } from './api';

export const rewardService = {
  /**
   * Get circular reward account balance and lifetime stats
   */
  getAccount: async (userId) => {
    const endpoint = userId ? `/rewards/account?userId=${userId}` : '/rewards/account';
    return await api.get(endpoint);
  },

  /**
   * Get append-only transaction ledger
   */
  getTransactions: async (userId) => {
    const endpoint = userId ? `/rewards/transactions?userId=${userId}` : '/rewards/transactions';
    return await api.get(endpoint);
  },

  /**
   * Get active rewards catalog
   */
  getCatalog: async () => {
    return await api.get('/rewards/catalog');
  },

  /**
   * Redeem reward item from points balance
   */
  redeemReward: async (payload) => {
    return await api.post('/rewards/redeem', payload);
  },

  /**
   * Get user redemptions history
   */
  getRedemptions: async (userId) => {
    const endpoint = userId ? `/rewards/redemptions?userId=${userId}` : '/rewards/redemptions';
    return await api.get(endpoint);
  },
};

export default rewardService;
