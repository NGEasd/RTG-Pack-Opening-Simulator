import { createContext} from 'react';
import { User } from '../types/types';

export type AuthContextType = {
  user: User | null;
  login: (userData: User) => void;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextType | null>(null);