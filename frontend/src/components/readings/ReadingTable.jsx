import { useState } from "react";
import { Button } from "@/components/ui/button";
import { getLanguageDisplay } from "../../lib/languages";
import BookDetailsDialog from "../books/BookDetailsDialog";
import SagaDetailsDialog from "../sagas/SagaDetailsDialog";
import { useDialog } from "../../context/DialogContext";
import StarRating from "../StarRating";

export default function ReadingTable({ readings, loading, sortField, sortDir, onSort }) {
  const [bookDetailsOpen, setBookDetailsOpen] = useState(false);
  const [selectedBook, setSelectedBook] = useState(null);
  const [sagaDetailsOpen, setSagaDetailsOpen] = useState(false);
  const [selectedSaga, setSelectedSaga] = useState(null);
  
  const { showError, showSuccess } = useDialog();

  const SortIcon = ({ field }) => {
    if (sortField !== field) return null;
    return sortDir === "asc" ? "▲" : "▼";
  };

  const handleBookView = (bookId) => {
    setSelectedBook(bookId);
    setBookDetailsOpen(true);
  };

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
              <th className="p-3 cursor-pointer" onClick={() => onSort("MY_RATING")}>
                My Rating <SortIcon field="MY_RATING" />
              </th>
              <th className="p-3 cursor-pointer">
                Reading language 
              </th>
              <th className="p-3 cursor-pointer" onClick={() => onSort("DATE_READ")}>
                Date Read <SortIcon field="DATE_READ" />
              </th>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr>
                <td colSpan="7" className="text-center p-4">
                  Loading...
                </td>
              </tr>
            ) : readings.length > 0 ? (
              readings.map((reading) => (
                <tr key={reading.id} 
                  className="bhover:bg-gray-50order-t ">
                  <td className="p-3">
                    {reading.book.urlImageSmall ? (
                      <img
                        src={reading.book.urlImageSmall}
                        alt={reading.book.title}
                        className="w-12 h-16 object-cover rounded"
                      />
                    ) : (
                      <div className="w-12 h-16 bg-gray-200 rounded" />
                    )}
                  </td>
                  <td className="p-3 cursor-pointer" 
                      onClick={() => handleBookView(reading.book.id)}>
                    {reading.book.title}
                  </td>
                  <td className="p-3">{reading.book.author}</td>
                  <td className="p-3 cursor-pointer" 
                      onClick={() => reading.book.saga && handleSagaView(reading.book.saga.id)}>
                        {reading.book.saga?.name || "-"}</td>
                  <td className="p-3">{reading.book.genre || "-"}</td>
                  <td className="p-3"><StarRating value={reading.myRating} /></td>
                  <td className="p-3">{getLanguageDisplay(reading.language) || "-"}</td>
                  <td className="p-3">
                    {new Date(reading.dateRead).toLocaleDateString()}
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="7" className="text-center p-4 text-gray-500">
                  No readings found.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
      <BookDetailsDialog
        open={bookDetailsOpen}
        onClose={setBookDetailsOpen}
        bookId={selectedBook}
      />
      <SagaDetailsDialog
          open={sagaDetailsOpen}
          onClose={setSagaDetailsOpen}
          sagaId={selectedSaga}
        />
    </>
  );
}
