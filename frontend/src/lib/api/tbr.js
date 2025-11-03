import api, {apiCall} from "@/lib/api.js";

const API_URL = "/tbr";

export async function getToBeRead(params = {}) {
  const query = new URLSearchParams();

  if (params.page) query.append("page", params.page);
  if (params.sortField) query.append("order", params.sortField);
  if (params.sortDir !== undefined) {
    query.append("directionAsc", params.sortDir === 'asc');
  }
  if (params.filterKeyword) query.append("keyword", params.filterKeyword);
  if (params.filterBought !== undefined) {
    query.append("bought", params.filterBought);
  }
  if (params.filterDone !== undefined) {
    query.append("done", params.filterDone);
  }

  const { data, error } = await apiCall(() => api.get(`${API_URL}?${query.toString()}`));
  return {data, error};
}

export async function addToBeRead(payload) {
  return await apiCall(() => api.post(`${API_URL}`, payload));
}

export async function deleteToBeRead(id) {
  return await apiCall(() => api.delete(`${API_URL}/${id}`));
}


export async function reorderToBeRead(id, payload) {
  return await apiCall(() => api.patch(`${API_URL}/${id}/reorder`, payload));
}

export async function markAsDoneToBeRead(id) {
  return await apiCall(() => api.patch(`${API_URL}/${id}`, {done: true}));
}

export async function markAsBoughtToBeRead(id) {
  return await apiCall(() => api.patch(`${API_URL}/${id}`, {bought: true}));
}
