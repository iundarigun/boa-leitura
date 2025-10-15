import { Button } from "@/components/ui/button";

export default function BookTable({ books, loading, sortField, sortDir, onSort, onView }) {
  const SortIcon = ({ field }) => {
    if (sortField !== field) return null;
    return sortDir === "asc" ? "▲" : "▼";
  };

  return (
    <div className="border rounded-lg overflow-hidden shadow-sm">
      <table className="w-full text-left border-collapse">
        <thead className="bg-gray-100">
          <tr>
            <th className="p-3 w-16">Cover</th>
            <th className="p-3 cursor-pointer" onClick={() => onSort("TITLE")}>
              Title <SortIcon field="TITLE" />
            </th>
            <th className="p-3 cursor-pointer" onClick={() => onSort("AUTHOR")}>
              Author <SortIcon field="AUTHOR" />
            </th>
            <th className="p-3 cursor-pointer" onClick={() => onSort("SAGA")}>
              Saga <SortIcon field="SAGA" />
            </th>
            <th className="p-3 cursor-pointer" onClick={() => onSort("GENRE")}>
              Genre <SortIcon field="GENRE" />
            </th>
            <th className="p-3 cursor-pointer" onClick={() => onSort("CREATED_AT")}>
              Added <SortIcon field="CREATED_AT" />
            </th>
            <th className="p-3 text-center">Read</th>
          </tr>
        </thead>
        <tbody>
          {loading ? (
            <tr>
              <td colSpan="7" className="text-center p-4">
                Loading...
              </td>
            </tr>
          ) : books.length > 0 ? (
            books.map((book) => (
              <tr key={book.id} 
                className="border-t hover:bg-gray-50 cursor-pointer"
                onClick={() => onView(book)}>
                <td className="p-3">
                  {book.urlImageSmall ? (
                    <img
                      src={book.urlImageSmall}
                      alt={book.title}
                      className="w-12 h-16 object-cover rounded"
                    />
                  ) : (
                    <div className="w-12 h-16 bg-gray-200 rounded" />
                  )}
                </td>
                <td className="p-3">{book.title}</td>
                <td className="p-3">{book.author}</td>
                <td className="p-3">{book.saga || "-"}</td>
                <td className="p-3">{book.genre || "-"}</td>
                <td className="p-3">
                  {new Date(book.createdAt).toLocaleDateString()}
                </td>
                <td className="p-3 text-center">{book.read ? "✅" : "❌"}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="7" className="text-center p-4 text-gray-500">
                No books found.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}
