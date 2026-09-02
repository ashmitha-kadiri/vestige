import { supabase } from './supabaseClient';
import api from './api';

const TOKEN_KEY = 'vestige_auth_token';
const USER_KEY = 'vestige_user_profile';

// In-flight mutex locks to prevent duplicate submissions
let isSigningUpUser = false;
let isSigningUpVendor = false;
let isLoggingIn = false;

export const authService = {
  getToken: () => {
    return localStorage.getItem(TOKEN_KEY);
  },

  setToken: (token) => {
    if (token) {
      localStorage.setItem(TOKEN_KEY, token);
    } else {
      localStorage.removeItem(TOKEN_KEY);
    }
  },

  getCachedProfile: () => {
    try {
      const item = localStorage.getItem(USER_KEY);
      return item ? JSON.parse(item) : null;
    } catch {
      return null;
    }
  },

  setCachedProfile: (profile) => {
    if (profile) {
      localStorage.setItem(USER_KEY, JSON.stringify(profile));
    } else {
      localStorage.removeItem(USER_KEY);
    }
  },

  /**
   * Unified Authentication: Supabase Auth with Spring Boot Backend Verification and Credential Fallback
   */
  login: async (email, password, expectedRole = null) => {
    if (isLoggingIn) {
      throw new Error('Authentication is currently in progress. Please wait.');
    }

    isLoggingIn = true;
    const cleanEmail = email.trim();

    try {
      let sessionToken = null;
      let profile = null;

      // 1. Primary: Attempt authentication via Supabase Auth
      try {
        const { data, error } = await supabase.auth.signInWithPassword({
          email: cleanEmail,
          password,
        });

        if (!error && data?.session?.access_token) {
          sessionToken = data.session.access_token;
          authService.setToken(sessionToken);

          // Verify identity & authoritative role against Spring Boot database
          const meResponse = await api.get('/auth/me');
          profile = meResponse.data;
        }
      } catch (supabaseErr) {
        console.info('Supabase Auth primary sign-in notice, attempting backend verification:', supabaseErr.message);
      }

      // 2. Secondary: If Supabase Auth did not authenticate (e.g. seeded/development users), verify via backend credentials
      if (!profile) {
        const loginResponse = await api.post('/auth/login', {
          email: cleanEmail,
          password,
          expectedRole,
        });

        sessionToken = loginResponse.data?.token;
        profile = loginResponse.data?.profile;

        if (sessionToken) {
          authService.setToken(sessionToken);
        }
      }

      if (!profile) {
        throw new Error('Invalid login credentials. Please check your email and password.');
      }

      // Role check validation against backend-verified authority
      if (expectedRole && profile.role !== expectedRole && profile.role !== 'ADMIN') {
        authService.logout();
        throw new Error(
          `Access Denied: This portal is designated exclusively for ${expectedRole} accounts. Your profile holds the ${profile.role} role.`
        );
      }

      authService.setCachedProfile(profile);
      return { token: sessionToken, profile };
    } catch (err) {
      // Clear token on definitive authorization failures
      if (err.status === 401 || err.status === 403 || err.message?.includes('Access Denied')) {
        authService.logout();
      }
      throw err;
    } finally {
      isLoggingIn = false;
    }
  },

  /**
   * User Account Registration Flow (strictly creates Patron / USER accounts)
   * With deduplication, rate limit mitigation, and fallback for development testing
   */
  signUpUser: async (email, password, registrationData) => {
    if (isSigningUpUser) {
      throw new Error('Registration is currently in progress. Please wait.');
    }

    isSigningUpUser = true;
    const cleanEmail = email.trim();

    try {
      let sessionToken = null;
      let profile = null;
      let supabaseRateLimited = false;

      // 1. Attempt registration via Supabase Auth
      try {
        const { data, error } = await supabase.auth.signUp({
          email: cleanEmail,
          password,
          options: {
            data: {
              full_name: registrationData.fullName,
              role: 'USER',
            },
          },
        });

        if (error) {
          if (
            error.status === 429 ||
            error.code === 'over_email_send_rate_limit' ||
            error.message?.toLowerCase().includes('rate limit')
          ) {
            supabaseRateLimited = true;
          } else {
            throw new Error(error.message || 'Unable to register user account.');
          }
        } else if (data?.session?.access_token) {
          sessionToken = data.session.access_token;
          authService.setToken(sessionToken);

          // Register application patron profile in Spring Boot database
          const response = await api.post('/auth/register/user', {
            fullName: registrationData.fullName,
            phone: registrationData.phone,
            preferredLanguage: registrationData.preferredLanguage || 'en',
          });

          profile = response.data;
        }
      } catch (supErr) {
        if (
          supErr.status === 429 ||
          supErr.message?.toLowerCase().includes('rate limit') ||
          supErr.message?.toLowerCase().includes('invalid')
        ) {
          supabaseRateLimited = true;
        } else {
          throw supErr;
        }
      }

      // 2. If Supabase email confirmation was rate limited or did not issue an immediate session,
      // register directly via backend API for development/testing reliability
      if (!profile) {
        try {
          const directResponse = await api.post('/auth/register/public/user', {
            fullName: registrationData.fullName,
            email: cleanEmail,
            password,
            phone: registrationData.phone,
            preferredLanguage: registrationData.preferredLanguage || 'en',
          });

          sessionToken = directResponse.data?.token;
          profile = directResponse.data?.profile;

          if (sessionToken) {
            authService.setToken(sessionToken);
          }
        } catch (backendErr) {
          if (supabaseRateLimited && backendErr.status !== 409) {
            throw new Error(
              'The authentication email service is temporarily rate-limited. Please wait a few minutes before requesting another verification email, or sign in if your account is already active.'
            );
          }
          throw backendErr;
        }
      }

      if (profile) {
        authService.setCachedProfile(profile);
      }

      return { token: sessionToken, profile };
    } finally {
      isSigningUpUser = false;
    }
  },

  /**
   * Vendor Partner Application / Registration Flow (strictly creates PENDING VENDOR accounts)
   * With deduplication, rate limit mitigation, and fallback for development testing
   */
  signUpVendor: async (email, password, vendorData) => {
    if (isSigningUpVendor) {
      throw new Error('Workshop registration is currently in progress. Please wait.');
    }

    isSigningUpVendor = true;
    const cleanEmail = email.trim();

    try {
      let sessionToken = null;
      let profile = null;
      let supabaseRateLimited = false;

      // 1. Attempt registration via Supabase Auth
      try {
        const { data, error } = await supabase.auth.signUp({
          email: cleanEmail,
          password,
          options: {
            data: {
              full_name: vendorData.fullName,
              role: 'VENDOR',
            },
          },
        });

        if (error) {
          if (
            error.status === 429 ||
            error.code === 'over_email_send_rate_limit' ||
            error.message?.toLowerCase().includes('rate limit')
          ) {
            supabaseRateLimited = true;
          } else {
            throw new Error(error.message || 'Unable to register workshop account.');
          }
        } else if (data?.session?.access_token) {
          sessionToken = data.session.access_token;
          authService.setToken(sessionToken);

          const response = await api.post('/auth/register/vendor', {
            fullName: vendorData.fullName,
            phone: vendorData.phone,
            preferredLanguage: vendorData.preferredLanguage || 'en',
            businessName: vendorData.businessName,
            businessType: vendorData.businessType,
            address: vendorData.address,
            city: vendorData.city,
            state: vendorData.state,
            pincode: vendorData.pincode,
            whatsappNumber: vendorData.whatsappNumber,
            serviceTypes: vendorData.serviceTypes || [],
            deviceCategories: vendorData.deviceCategories || [],
            documentType: vendorData.documentType,
            documentUrl: vendorData.documentUrl,
          });

          profile = response.data;
        }
      } catch (supErr) {
        if (
          supErr.status === 429 ||
          supErr.message?.toLowerCase().includes('rate limit') ||
          supErr.message?.toLowerCase().includes('invalid')
        ) {
          supabaseRateLimited = true;
        } else {
          throw supErr;
        }
      }

      // 2. If Supabase email confirmation was rate limited or did not issue an immediate session,
      // register directly via backend API for development/testing reliability
      if (!profile) {
        try {
          const directResponse = await api.post('/auth/register/public/vendor', {
            fullName: vendorData.fullName,
            email: cleanEmail,
            password,
            phone: vendorData.phone,
            preferredLanguage: vendorData.preferredLanguage || 'en',
            businessName: vendorData.businessName,
            businessType: vendorData.businessType,
            address: vendorData.address,
            city: vendorData.city,
            state: vendorData.state,
            pincode: vendorData.pincode,
            whatsappNumber: vendorData.whatsappNumber,
            serviceTypes: vendorData.serviceTypes || [],
            deviceCategories: vendorData.deviceCategories || [],
            documentType: vendorData.documentType,
            documentUrl: vendorData.documentUrl,
          });

          sessionToken = directResponse.data?.token;
          profile = directResponse.data?.profile;

          if (sessionToken) {
            authService.setToken(sessionToken);
          }
        } catch (backendErr) {
          if (supabaseRateLimited && backendErr.status !== 409) {
            throw new Error(
              'The authentication email service is temporarily rate-limited. Please wait a few minutes before requesting another verification email, or sign in if your account is already active.'
            );
          }
          throw backendErr;
        }
      }

      if (profile) {
        authService.setCachedProfile(profile);
      }

      return { token: sessionToken, profile };
    } finally {
      isSigningUpVendor = false;
    }
  },

  getMe: async () => {
    const response = await api.get('/auth/me');
    const profile = response.data;
    authService.setCachedProfile(profile);
    return profile;
  },

  logout: async () => {
    try {
      await supabase.auth.signOut();
    } catch {
      // ignore
    }
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
  },

  resetPassword: async (email) => {
    return supabase.auth.resetPasswordForEmail(email.trim(), {
      redirectTo: `${window.location.origin}/reset-password`,
    });
  },
};

export default authService;
