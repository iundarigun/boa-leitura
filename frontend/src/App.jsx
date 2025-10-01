import { useState, useEffect } from "react";
import axios from "axios";
import AuthorForm from "./components/AuthorForm";
import AuthorList from "./components/AuthorList";

const API_URL = "http://localhost:1980/authors";

export default function App() {
  const [authors, setAuthors] = useState({"list": []});
  const [editingAuthor, setEditingAuthor] = useState(null);

  useEffect(() => {
    axios.get(API_URL).then((res) => setAuthors(res.data));
  }, []);

  const addAuthor = async (name) => {
    const res = await axios.post(API_URL, { name });
    setAuthors([...authors, res.data]);
  };

  const updateAuthor = async (id, name) => {
    const res = await axios.put(`${API_URL}/${id}`, { name });
    setAuthors(authors.map((u) => (u.id === id ? { ...u, name: res.data.name } : u)));
  };

  const deleteAuthor = async (id) => {
    await axios.delete(`${API_URL}/${id}`);
    setAuthors(authors.filter((u) => u.id !== id));
  };

return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="bg-white shadow-2xl rounded-2xl p-8 w-full max-w-2xl">
        <h1 className="text-3xl font-bold text-gray-800 mb-6 text-center">
          ✍️ Gerenciamento de Autores
        </h1>

        <AuthorForm
          onAdd={addAuthor}
          onUpdate={updateAuthor}
          editingAuthor={editingAuthor}
          setEditingAuthor={setEditingAuthor}
        />

        <AuthorList
          authors={authors}
          onEdit={setEditingAuthor}
          onDelete={deleteAuthor}
        />
      </div>
    </div>
  );
}
