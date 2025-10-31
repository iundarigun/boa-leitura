import api, { apiCall } from "@/lib/api.js";

const API_URL = "/tbr";

export async function getToBeRead(params = {}) {
  const query = new URLSearchParams();

  if (params.page) query.append("page", params.page);
  if (params.sortField) query.append("order", params.sortField);
  if (params.sortDir !== undefined) {
    query.append("directionAsc", params.sortDir === 'asc');
  }
  if (params.name) query.append("name", params.name);

  const { data, error } = await apiCall(() => api.get(`${API_URL}?${query.toString()}`));
  return {data, error};
}

export async function addToBeRead(payload) {
  return await apiCall(() => api.post(`${API_URL}`, payload));
}

