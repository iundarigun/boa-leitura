import api, {apiCall} from "@/lib/api.js";

const API_URL = "/users";

export async function doPassword(payload) {
  const { data, error } = await apiCall(() => api.patch(`${API_URL}/password`, payload));
  return {data, error};
}
