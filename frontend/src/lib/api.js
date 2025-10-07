import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:1980",
  headers: {
    "Content-Type": "application/json",
  },
});

export async function apiCall(apiFunc) {
  try {
    const res = await apiFunc();
    return { data: res.data, error: null };
  } catch (err) {
    let message = "Erro desconhecido.";
    if (err.response?.data?.message) {
      message = err.response.data.message;
    } else if (err.message) {
      message = err.message;
    }
    return { data: null, error: message };
  }
}

export default api;