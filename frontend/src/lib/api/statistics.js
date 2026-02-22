import api, { apiCall } from "@/lib/api.js";

const API_URL = "/statistics";

function buildQueryParams(params = {}) {
  const query = new URLSearchParams();
  if (params.year) query.append("year", params.year);
  if (params.excludeRereading) query.append("excludeRereading", params.excludeRereading)
  return query.toString();
}

export async function getSummary(params = {}) {
  const { data, error } = await apiCall(() => api.get(`${API_URL}/summary?${buildQueryParams(params)}`));
  return {data, error};
}

export async function getLanguage(params = {}) {
  const { data, error } = await apiCall(() => api.get(`${API_URL}/language?${buildQueryParams(params)}`));
  return {data, error};
}

export async function getAuthor(params = {}) {
  const { data, error } = await apiCall(() => api.get(`${API_URL}/author?${buildQueryParams(params)}`));
  return {data, error};
}

export async function getMood(params = {}) {
  const { data, error } = await apiCall(() => api.get(`${API_URL}/mood?${buildQueryParams(params)}`));
  return {data, error};
}
