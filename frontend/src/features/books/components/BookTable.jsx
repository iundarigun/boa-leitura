import {useState} from "react";
import SagaDetailsDialog from "@/features/sagas/components/SagaDetailsDialog";
import SortableColumns from "@/components/SortableColumn.jsx";

export default function BookTable({books, loading, sortField, sortDir, onSort, onView}) {
  const [sagaDetailsOpen, setSagaDetailsOpen] = useState(false);
  const [selectedSaga, setSelectedSaga] = useState(null);

  const handleSagaView = (sagaId) => {
    setSelectedSaga(sagaId);
    setSagaDetailsOpen(true);
  };

  return (
    <>
      <div className="border rounded-lg overflow-hidden shadow-sm">
        <table className="w-full text-left border-collapse">
          <thead className="bg-gray-100">
          <tr>
            <th className="p-3 w-16">Cover</th>
            <SortableColumns
              onSort={onSort}
              sortField={sortField}
              sortDir={sortDir}
              label="Title"
              orderFieldName="TITLE"
            />
            <SortableColumns
              onSort={onSort}
              sortField={sortField}
              sortDir={sortDir}
              label="Author"
              orderFieldName="AUTHOR"
            />
            <SortableColumns
              onSort={onSort}
              sortField={sortField}
              sortDir={sortDir}
              label="Saga"
              orderFieldName="SAGA"
            />
            <SortableColumns
              onSort={onSort}
              sortField={sortField}
              sortDir={sortDir}
              label="Genre"
              orderFieldName="GENRE"
            />
            <SortableColumns
              onSort={onSort}
              sortField={sortField}
              sortDir={sortDir}
              label="Added"
              orderFieldName="CREATED_AT"
            />
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
              <tr key={book.id} className="border-t hover:bg-gray-50 ">
                <td className="p-3">
                  {book.urlImageSmall ? (
                    <img
                      src={book.urlImageSmall}
                      alt={book.title}
                      className="w-12 h-16 object-cover rounded"
                    />
                  ) : (
                    <div className="w-12 h-16 bg-gray-200 rounded"/>
                  )}
                </td>
                <td className="p-3 cursor-pointer" onClick={() => onView(book.id)}>
                  {book.title}
                </td>
                <td className="p-3">{book.author}</td>
                <td className="p-3 cursor-pointer" onClick={() => handleSagaView(book.saga.id)}>
                  {book.saga?.name || "-"
                  }</td>
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
      <SagaDetailsDialog
        open={sagaDetailsOpen}
        onClose={setSagaDetailsOpen}
        sagaId={selectedSaga}
      />
    </>
  );
}
