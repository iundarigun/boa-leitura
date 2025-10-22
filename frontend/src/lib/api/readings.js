import api, { apiCall } from "@/lib/api.js";
import {format} from "date-fns";

const API_URL = "/readings";

export async function getReadings(params = {}) {
  const query = new URLSearchParams();

  if (params.page) query.append("page", params.page);
  if (params.sortField) query.append("order", params.sortField);
  if (params.sortDir !== undefined) {
    query.append("directionAsc", params.sortDir === 'asc');
  }
  if (params.keyword) query.append("keyword", params.keyword);
  if (params.filterDateFrom) query.append("dateFrom", format(params.filterDateFrom, "yyyy-MM-dd"));
  if (params.filterDateTo) query.append("dateTo", format(params.filterDateTo, "yyyy-MM-dd"));

  const { data, error } = await apiCall(() => api.get(`${API_URL}?${query.toString()}`));
  return {data, error};
}

export async function deleteReading(id) {
  return await apiCall(() => api.delete(`${API_URL}/${id}`));
}

export async function getReadingById(id) {
  return await apiCall(() => api.get(`${API_URL}/${id}`));
}

export async function updateReading(id, payload) {
  return await apiCall(() => api.put(`${API_URL}/${id}`, payload));
}

export async function createReading(payload) {
  return await apiCall(() => api.post(`${API_URL}`, payload));
}

