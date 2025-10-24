import api, { apiCall } from "@/lib/api.js";

const API_URL = "/statistics";

export async function getSummary(year) {
  const { data, error } = await apiCall(() => api.get(`${API_URL}/summary/${year}`));
  return {data, error};
}

export async function getLanguage(year) {
  const { data, error } = await apiCall(() => api.get(`${API_URL}/language/${year}`));
  return {data, error};
}

export async function getAuthor(year) {
  const { data, error } = await apiCall(() => api.get(`${API_URL}/author/${year}`));
  return {data, error};
}
