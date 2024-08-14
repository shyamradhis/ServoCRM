// src/main/webapp/app/modules/home/UserContext.tsx
import React, { createContext, useContext, useState, Dispatch, SetStateAction } from 'react';

interface UserContextType {
  user: { name: string } | null;
  setUser: Dispatch<SetStateAction<{ name: string } | null>>;
}

const defaultValue: UserContextType = {
  user: null,
  setUser: () => {},
};

const UserContext = createContext<UserContextType>(defaultValue);

export const UserProvider = ({ children }) => {
  const [user, setUser] = useState<{ name: string } | null>({ name: 'John Doe' }); // Example user info

  return <UserContext.Provider value={{ user, setUser }}>{children}</UserContext.Provider>;
};

export const useUser = () => {
  return useContext(UserContext);
};
