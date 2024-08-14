// src/main/webapp/app/modules/home/App.tsx
import React from 'react';
import { UserProvider } from './UserContext';
import Dashboard from './Dashboard';

const App = () => {
  return (
    <UserProvider>
      <Dashboard />
    </UserProvider>
  );
};

export default App;
