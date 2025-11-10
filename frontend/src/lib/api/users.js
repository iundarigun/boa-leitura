import api, {apiCall} from "@/lib/api.js";

const API_URL = "/users";

export async function doPassword(payload) {
  const { data, error } = await apiCall(() => api.patch(`${API_URL}/password`, payload));
  return {data, error};
}

export async function updateUserPreferences(payload) {
  const { data, error } = await apiCall(() => api.patch(`${API_URL}/preferences`, payload));
  return {data, error};
}

export async function getUser() {
  const { data, error } = await apiCall(() => api.get(API_URL));
  return {data, error};
}

