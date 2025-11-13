import api, { apiCall } from "@/lib/api.js";

const API_URL = "/bests";

export async function getBestOfTheYear(year) {
  const { data, error } = await apiCall(() => api.get(`${API_URL}/${year}`));
  return {data, error};
}
