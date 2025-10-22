import api, { apiCall } from "@/lib/api.js";

const API_URL = "/statistics";

export async function getSummary(year) {
  const { data, error } = await apiCall(() => api.get(`${API_URL}/summary/${year}`));
  return {data, error};
}


