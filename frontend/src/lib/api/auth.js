import api, { apiCall } from "@/lib/api.js";

const API_URL = "/auth";

export async function doLogin(payload) {
  const { data, error } = await apiCall(() => api.post(`${API_URL}/login`, payload));
  return {data, error};
}
