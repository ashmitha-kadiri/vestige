import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';
import { UIProvider } from './contexts/UIContext';
import AppRouter from './router/AppRouter';

export function App() {
  return (
    <BrowserRouter>
      <UIProvider>
        <AuthProvider>
          <AppRouter />
        </AuthProvider>
      </UIProvider>
    </BrowserRouter>
  );
}

export default App;
