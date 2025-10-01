export default function AuthorList({ authors, onEdit, onDelete }) {
  return (
    <ul className="space-y-3">
      {authors.list.map((author) => (
        <li
          key={author.id}
          className="flex justify-between items-center bg-gray-50 border rounded-lg p-4 shadow-sm hover:shadow-md transition"
        >
          <span className="text-lg text-gray-700">{author.name}</span>
          <div className="flex gap-2">
            <button
              className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-lg transition"
              onClick={() => onEdit(author)}
            >
              Editar
            </button>
            <button
              className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-lg transition"
              onClick={() => onDelete(author.id)}
            >
              Deletar
            </button>
          </div>
        </li>
      ))}
    </ul>
  );
}
