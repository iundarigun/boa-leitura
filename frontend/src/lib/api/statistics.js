import api, { apiCall } from "@/lib/api.js";

const API_URL = "/statistics";

function buildQueryParams(params = {}) {
  const query = new URLSearchParams();
  if (params.year) query.append("year", params.year);

  return query.toString();
}

export async function getSummary(year) {
  const { data, error } = await apiCall(() => api.get(`${API_URL}/summary?${buildQueryParams({year: year})}`));
  return {data, error};
}

export async function getLanguage(year) {
  const { data, error } = await apiCall(() => api.get(`${API_URL}/language?${buildQueryParams({year: year})}`));
  return {data, error};
}

export async function getAuthor(year) {
  const { data, error } = await apiCall(() => api.get(`${API_URL}/author?${buildQueryParams({year: year})}`));
  return {data, error};
}

export async function getMood(year) {
  const { data, error } = await apiCall(() => api.get(`${API_URL}/mood?${buildQueryParams({year: year})}`));
  return {data, error};
}
