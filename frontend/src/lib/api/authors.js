import api, { apiCall } from "@/lib/api.js";

const API_URL = "/authors";

export async function getAuthors(params = {}) {
  const query = new URLSearchParams();

  if (params.page) query.append("page", params.page);
  if (params.order) query.append("order", params.order);
  if (params.directionAsc !== undefined) {
    query.append("directionAsc", params.directionAsc);
  }
  if (params.name) query.append("name", params.name);

  const { data, error } = await apiCall(() => api.get(`${API_URL}?${query.toString()}`));
  return {data, error};
}

export async function deleteAuthor(id) {
  return await apiCall(() => api.delete(`${API_URL}/${id}`));
}

export async function getAuthorById(id) {
  return await apiCall(() => api.get(`${API_URL}/${id}`));
}

export async function updateAuthor(id, payload) {
  return await apiCall(() => api.put(`${API_URL}/${id}`, payload));
}

export async function createAuthor(payload) {
  return await apiCall(() => api.post(`${API_URL}`, payload));
}


// export async function fetchAuthor(params = {}) {
//
//   if (id) {
//     setLoading(true);
//     const res = await apiCall(() => api.get(`${API_URL}/${id}`));
//     if (res.data) {
//       setEditingAuthor(res.data);
//     } else {
//       showError("Not possible to load the author.");
//     }
//     setLoading(false);
//   }
// }
