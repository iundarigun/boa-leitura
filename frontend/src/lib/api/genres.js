import api, { apiCall } from "@/lib/api.js";

const API_URL = "/genres";

export async function getGenres() {
  return await apiCall(() => api.get(`${API_URL}`));
}

export async function deleteGenre(id) {
  return await apiCall(() => api.delete(`${API_URL}/${id}`));
}

export async function getGenreById(id) {
  return await apiCall(() => api.get(`${API_URL}/${id}`));
}

export async function updateGenre(id, payload) {
  return await apiCall(() => api.put(`${API_URL}/${id}`, payload));
}

export async function createGenre(payload) {
  return await apiCall(() => api.post(`${API_URL}`, payload));
}

