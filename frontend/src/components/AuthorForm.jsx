import { useState, useEffect } from "react";

export default function AuthorForm({ onAdd, onUpdate, editingAuthor, setEditingAuthor }) {
  const [name, setName] = useState("");

  useEffect(() => {
    if (editingAuthor) setName(editingAuthor.name);
  }, [editingAuthor]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!name) return;
    if (editingAuthor) {
      onUpdate(editingAuthor.id, name);
    } else {
      onAdd(name);
    }
    setName("");
    setEditingAuthor(null);
  };

  return (
    <form onSubmit={handleSubmit} className="flex gap-2 mb-4">
      <input
        className="border p-2 flex-1 rounded"
        placeholder="Nome do Autor"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />
      <button
        type="submit"
        className={`p-2 rounded text-white ${editingAuthor ? "bg-blue-500" : "bg-green-500"}`}
      >
        {editingAuthor ? "Atualizar" : "Adicionar"}
      </button>
    </form>
  );
}
