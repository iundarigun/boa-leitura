import { useState } from "react";
import { Button } from "@/components/ui/button";
import {
  AlertDialog,
  AlertDialogContent,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogCancel,
  AlertDialogAction,
} from "@/components/ui/alert-dialog";

import { getLanguageDisplay } from "../../lib/languages";
import BookDetailsDialog from "../books/BookDetailsDialog";
import SagaDetailsDialog from "../sagas/SagaDetailsDialog";
import { useDialog } from "../../context/DialogContext";
import StarRating from "../StarRating";

export default function ReadingTable({ readings, loading, onEdit, onDelete, sortField, sortDir, onSort }) {
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
              <th></th>
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
                  className="border-t hover:bg-gray-50">
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
                  <td className="p-3 text-center">
                    <div className="flex justify-center gap-2">
                      <Button variant="outline" size="sm" onClick={() => onEdit(reading)}>
                        Edit
                      </Button>
                      <AlertDialog>
                        <AlertDialogTrigger asChild>
                          <Button variant="destructive" size="sm">Delete</Button>
                        </AlertDialogTrigger>
                        <AlertDialogContent>
                          <AlertDialogHeader>
                            <AlertDialogTitle>Confirm delete</AlertDialogTitle>
                            <AlertDialogDescription>
                              Are you sure you want to delete <b>{reading.book?.title} on {reading.dateRead}</b>?  
                              This action cannot be undone.
                            </AlertDialogDescription>
                          </AlertDialogHeader>
                          <AlertDialogFooter>
                            <AlertDialogCancel>Cancel</AlertDialogCancel>
                            <AlertDialogAction
                              className="bg-red-600 hover:bg-red-700"
                              onClick={() => onDelete(reading)}
                            >
                              Delete
                            </AlertDialogAction>
                          </AlertDialogFooter>
                        </AlertDialogContent>
                      </AlertDialog>
                    </div>
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
