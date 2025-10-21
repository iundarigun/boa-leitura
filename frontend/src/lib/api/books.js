import api, { apiCall } from "@/lib/api.js";

const API_URL = "/books";

export async function getBooks(params = {}) {
  const query = new URLSearchParams();

  if (params.page) query.append("page", params.page);
  if (params.sortField) query.append("order", params.sortField);
  if (params.sortDir !== undefined) {
    query.append("directionAsc", params.sortDir === 'asc');
  }
  if (params.title) query.append("title", params.title);
  if (params.filterRead !== "both") query.append("read", params.filterRead === "read");

  const { data, error } = await apiCall(() => api.get(`${API_URL}?${query.toString()}`));
  return {data, error};
}

export async function deleteBook(id) {
  return await apiCall(() => api.delete(`${API_URL}/${id}`));
}

export async function getBookById(id) {
  return await apiCall(() => api.get(`${API_URL}/${id}`));
}

export async function updateBook(id, payload) {
  return await apiCall(() => api.put(`${API_URL}/${id}`, payload));
}

export async function createBook(payload) {
  return await apiCall(() => api.post(`${API_URL}`, payload));
}

