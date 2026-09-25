import axios, { AxiosError } from "axios";
import type { ApiError } from "../types/api";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL ?? "/api",
  timeout: 10000,
  headers: { "Content-Type": "application/json" },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("agrismart_token");
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error: AxiosError) => {
    if (error.response?.status === 401 && !error.config?.url?.includes("/auth/")) {
      localStorage.removeItem("agrismart_token");
      localStorage.removeItem("agrismart_user");
      window.dispatchEvent(new Event("agrismart:unauthorized"));
    }
    return Promise.reject(error);
  },
);

export function getErrorMessage(error: unknown, fallback = "Something went wrong") {
  if (axios.isAxiosError<ApiError>(error)) {
    const validation = error.response?.data?.validationErrors;
    if (validation && Object.keys(validation).length) return Object.values(validation)[0];
    if (error.response?.data?.message) return error.response.data.message;
    if (error.code === "ECONNABORTED") return "The AgriSmart server took too long to respond.";
    if (!error.response) return "Cannot reach the AgriSmart server. Check that the backend is running, then try again.";
    return fallback;
  }
  return fallback;
}

export default api;
