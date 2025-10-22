import api, { apiCall } from "@/lib/api.js";

const API_URL = "/book-information";

export async function getByParams(params ={}) {
  const query = new URLSearchParams();
  if (params.isbn) query.append("isbn", params.isbn);
  if (params.title) query.append("title", params.title);
  if (params.author) query.append("author", params.author);
  return await apiCall(() => api.get(`${API_URL}?${query.toString()}`));
}
