import { createContext, useContext, useEffect, useMemo, useState, type ReactNode } from "react";
import api from "../services/api";
import type { AuthResponse, User } from "../types/api";

type RegisterInput = { fullName: string; email: string; phone?: string; password: string };
type AuthContextValue = {
  user: User | null;
  isAuthenticated: boolean;
  login: (email: string, password: string) => Promise<void>;
  register: (input: RegisterInput) => Promise<void>;
  logout: () => void;
  setUser: (user: User) => void;
};

const AuthContext = createContext<AuthContextValue | null>(null);

function storedUser(): User | null {
  try {
    const value = localStorage.getItem("agrismart_user");
    return value ? (JSON.parse(value) as User) : null;
  } catch {
    return null;
  }
}

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, updateUser] = useState<User | null>(storedUser);

  const saveAuth = (response: AuthResponse) => {
    const nextUser: User = {
      id: response.userId,
      fullName: response.fullName,
      email: response.email,
      role: response.role,
    };
    localStorage.setItem("agrismart_token", response.accessToken);
    localStorage.setItem("agrismart_user", JSON.stringify(nextUser));
    updateUser(nextUser);
  };

  const logout = () => {
    localStorage.removeItem("agrismart_token");
    localStorage.removeItem("agrismart_user");
    updateUser(null);
  };

  useEffect(() => {
    window.addEventListener("agrismart:unauthorized", logout);
    return () => window.removeEventListener("agrismart:unauthorized", logout);
  }, []);

  const value = useMemo<AuthContextValue>(() => ({
    user,
    isAuthenticated: Boolean(user && localStorage.getItem("agrismart_token")),
    login: async (email, password) => saveAuth((await api.post<AuthResponse>("/auth/login", { email, password })).data),
    register: async (input) => saveAuth((await api.post<AuthResponse>("/auth/register", input)).data),
    logout,
    setUser: (nextUser) => {
      localStorage.setItem("agrismart_user", JSON.stringify(nextUser));
      updateUser(nextUser);
    },
  }), [user]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) throw new Error("useAuth must be used within AuthProvider");
  return context;
}
