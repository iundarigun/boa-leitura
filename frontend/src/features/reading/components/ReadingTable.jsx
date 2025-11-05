import {useState} from "react";

import {getLanguageDisplay} from "@/lib/languages.js";
import BookDetailsDialog from "@/features/books/components/BookDetailsDialog";
import SagaDetailsDialog from "@/features/sagas/components/SagaDetailsDialog";
import StarRating from "@/components/StarRating";
import SortableColumns from "@/components/SortableColumn.jsx";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger
} from "@/components/ui/dropdown-menu.jsx";
import {Button} from "@/components/ui/button.jsx";
import {Eye, MoreVertical, Pencil, Trash2} from "lucide-react";
import instagramImg from "@/assets/instagram.svg";
import CustomAlertDialog from "@/components/CustomAlertDialog.jsx";
import ReadingDetailsDialog from "@/features/reading/components/ReadingDetailsDialog.jsx";

export default function ReadingTable({readings, loading, onEdit, onDelete, sortField, sortDir, onSort}) {
  const [bookDetailsOpen, setBookDetailsOpen] = useState(false);
  const [readingDetailsOpen, setReadingDetailsOpen] = useState(false);
  const [selectedBook, setSelectedBook] = useState(null);
  const [selectedReading, setSelectedReading] = useState(null);
  const [sagaDetailsOpen, setSagaDetailsOpen] = useState(false);
  const [selectedSaga, setSelectedSaga] = useState(null);
  const [confirmDelete, setConfirmDelete] = useState(false);

  const handleBookView = (bookId) => {
    setSelectedBook(bookId);
    setBookDetailsOpen(true);
  };

  const handleReadingView = (readingId) => {
    setSelectedReading(readingId);
    setReadingDetailsOpen(true);
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
              label="My rating"
              orderFieldName="MY_RATING"
            />
            <th className="p-3 cursor-pointer">
              Reading language
            </th>
            <SortableColumns
              onSort={onSort}
              sortField={sortField}
              sortDir={sortDir}
              label="Date Read"
              orderFieldName="DATE_READ"
            />
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
                    <div className="w-12 h-16 bg-gray-200 rounded"/>
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
                <td className="p-3"><StarRating value={reading.myRating}/></td>
                <td className="p-3">{getLanguageDisplay(reading.language) || "-"}</td>
                <td className="p-3">
                  {new Date(reading.dateRead).toLocaleDateString()}
                </td>
                <td className="p-3 text-center">
                  <DropdownMenu>
                    <DropdownMenuTrigger asChild>
                      <Button variant="ghost" size="icon">
                        <MoreVertical className="h-4 w-4"/>
                      </Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent align="end">
                      <DropdownMenuLabel>Actions</DropdownMenuLabel>
                      <DropdownMenuItem onClick={() => handleBookView(reading?.book?.id)}>
                        <Eye className="h-4 w-4 mr-2"/>
                        View book details
                      </DropdownMenuItem>
                      <DropdownMenuItem onClick={() => handleReadingView(reading?.id)}>
                        <img src={instagramImg} className="h-4 w-4 mr-2"/>
                        Printed version
                      </DropdownMenuItem>
                      <DropdownMenuItem onClick={() => onEdit(reading)}>
                        <Pencil className="h-4 w-4 mr-2"/>
                        Edit
                      </DropdownMenuItem>
                      <DropdownMenuSeparator/>
                      <DropdownMenuItem
                        className="text-red-600 focus:text-red-700"
                        onClick={() => setConfirmDelete(true)}
                      >
                        <Trash2 className="h-4 w-4 mr-2"/>
                        Delete
                      </DropdownMenuItem>
                    </DropdownMenuContent>
                  </DropdownMenu>

                </td>
                <CustomAlertDialog
                  confirm={confirmDelete}
                  setConfirm={setConfirmDelete}
                  title="Confirm delete"
                  text={<>Are you sure you want to delete <b>{reading.book?.title} on {reading.dateRead}</b>?
                    This action cannot be undone.</>}
                  action={() => onDelete(reading)}
                />
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
      <ReadingDetailsDialog
        open={readingDetailsOpen}
        onClose={setReadingDetailsOpen}
        readingId={selectedReading}
      />
      <SagaDetailsDialog
        open={sagaDetailsOpen}
        onClose={setSagaDetailsOpen}
        sagaId={selectedSaga}
      />
    </>
  );
}
