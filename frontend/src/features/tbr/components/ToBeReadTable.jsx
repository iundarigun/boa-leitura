import {useState} from "react";
import {Button} from "@/components/ui/button";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";
import BookDetailsDialog from "@/features/books/components/BookDetailsDialog";
import SagaDetailsDialog from "@/features/sagas/components/SagaDetailsDialog";
import SortableColumns from "@/components/SortableColumn.jsx";
import {Tooltip, TooltipContent, TooltipProvider, TooltipTrigger} from "@/components/ui/tooltip.jsx";
import CustomTooltip from "@/components/CustomTooltip.jsx";

export default function ToBeReadTable({toBeReads, loading, onEdit, onDelete, sortField, sortDir, onSort}) {
  const [bookDetailsOpen, setBookDetailsOpen] = useState(false);
  const [selectedBook, setSelectedBook] = useState(null);
  const [sagaDetailsOpen, setSagaDetailsOpen] = useState(false);
  const [selectedSaga, setSelectedSaga] = useState(null);

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
      <TooltipProvider>
        <div className="border rounded-lg overflow-hidden shadow-sm">
          <table className="w-full text-left border-collapse">
            <thead className="bg-gray-100">
            <tr>
              <SortableColumns
                onSort={onSort}
                sortField={sortField}
                sortDir={sortDir}
                label="#"
                orderFieldName="POSITION"
              />
              <th className="p-3 w-16">Cover</th>
              <SortableColumns
                onSort={onSort}
                sortField={sortField}
                sortDir={sortDir}
                label="Title"
                orderFieldName="TITLE"
              />
              <th className="p-3 w-16">Saga</th>
              <SortableColumns
                onSort={onSort}
                sortField={sortField}
                sortDir={sortDir}
                label="Date Add"
                orderFieldName="CREATED_AT"
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
            ) : toBeReads.length > 0 ? (
              toBeReads.map((tbr) => (
                <tr key={tbr.id}
                    className="border-t hover:bg-gray-50">
                  <td className="p-3">
                    {tbr.position}
                  </td>
                  <td className="p-3">
                    {tbr.book.urlImageSmall ? (
                      <img
                        src={tbr.book.urlImageSmall}
                        alt={tbr.book.title}
                        className="w-12 h-16 object-cover rounded"
                      />
                    ) : (
                      <div className="w-12 h-16 bg-gray-200 rounded"/>
                    )}
                  </td>
                  <td className="p-3 cursor-pointer"
                      onClick={() => handleBookView(tbr.book.id)}>
                    <CustomTooltip
                      content={<>{tbr.book.title} ({tbr.book.author})</>}
                      tooltipContent={tbr.notes}
                      />
                  </td>
                  <td className="p-3 cursor-pointer"
                      onClick={() => tbr.book.saga && handleSagaView(tbr.book.saga.id)}>
                    {tbr.book.saga?.name || "-"}</td>
                  <td className="p-3">
                    {new Date(tbr.addedAt).toLocaleDateString()}
                  </td>
                  <td className="p-3 text-center">
                    <div className="flex justify-center gap-2">
                      <Button variant="outline" size="sm" onClick={() => onEdit(tbr)}>
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
                              Are you sure you want to delete <b>{tbr.book?.title} from TBR</b>?
                              This action cannot be undone.
                            </AlertDialogDescription>
                          </AlertDialogHeader>
                          <AlertDialogFooter>
                            <AlertDialogCancel>Cancel</AlertDialogCancel>
                            <AlertDialogAction
                              className="bg-red-600 hover:bg-red-700"
                              onClick={() => onDelete(tbr)}
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
      </TooltipProvider>
    </>
  );
}
