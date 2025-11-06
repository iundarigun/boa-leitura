import { createContext, useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import api from "@/lib/api";

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [token, setToken] = useState(localStorage.getItem("jwt") || null);
  const [user, setUser] = useState(null);
  const navigate = useNavigate?.() ?? (() => {});

  useEffect(() => {
    if (token) {
      try {
        const decoded = jwtDecode(token);
        if (decoded.exp * 1000 < Date.now()) {
          console.warn("Token expired!");
          logout();
        } else {
          setUser(decoded);
          localStorage.setItem("jwt", token);
        }
      } catch (err) {
        console.error("Invalid token:", err);
        logout();
      }
    } else {
      localStorage.removeItem("jwt");
      setUser(null);
    }
  }, [token]);

  useEffect(() => {
    const interceptor = api.interceptors.response.use(
      (response) => response,
      (error) => {
        if (error.response && error.response.status === 401) {
          console.warn("⚠Token expired");
          logout();
          navigate("/login");
        }
        return Promise.reject(error);
      }
    );
    return () => api.interceptors.response.eject(interceptor);
  }, [navigate]);

  // Scheduled verification of token expirations
  useEffect(() => {
    if (!user?.exp) return;

    const checkExpiration = () => {
      const now = Date.now();
      const exp = user.exp * 1000;
      if (exp <= now) {
        console.warn("Token expired.");
        logout();
      }
    };

    const intervalId = setInterval(checkExpiration, 60 * 1000);

    return () => clearInterval(intervalId);
  }, [user]);

  const login = (jwt) => {
    try {
      const decoded = jwtDecode(jwt);
      if (decoded.exp * 1000 < Date.now()) {
        throw new Error("Token expirado");
      }
      setToken(jwt);
      setUser(decoded);
      localStorage.setItem("jwt", jwt);
    } catch (err) {
      console.error("Erro ao processar JWT:", err);
      logout();
    }
  };

  const logout = () => {
    setToken(null);
    setUser(null);
    localStorage.removeItem("jwt");
    navigate("/login");
  };

  return (
    <AuthContext.Provider value={{ token, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
