import api, { apiCall } from "@/lib/api.js";

const API_URL = "/sagas";

export async function getSagas(params = {}) {
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

export async function deleteSaga(id) {
  return await apiCall(() => api.delete(`${API_URL}/${id}`));
}

export async function getSagaById(id) {
  return await apiCall(() => api.get(`${API_URL}/${id}`));
}

export async function updateSaga(id, payload) {
  return await apiCall(() => api.put(`${API_URL}/${id}`, payload));
}

export async function createSaga(payload) {
  return await apiCall(() => api.post(`${API_URL}`, payload));
}

