import api, {apiCall} from "@/lib/api.js";

const API_URL = "/imports";

export async function importGoodreads(formData) {
  return await apiCall(() =>
    api.post(`${API_URL}/goodreads`, formData, {
      headers: {"Content-Type": "multipart/form-data"},
    }));
}