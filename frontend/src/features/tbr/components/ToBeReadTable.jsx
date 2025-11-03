import {useState} from "react";
import {closestCenter, DndContext} from "@dnd-kit/core";
import {SortableContext, verticalListSortingStrategy,} from "@dnd-kit/sortable";
import BookDetailsDialog from "@/features/books/components/BookDetailsDialog";
import SagaDetailsDialog from "@/features/sagas/components/SagaDetailsDialog";
import SortableColumns from "@/components/SortableColumn.jsx";
import {TooltipProvider} from "@/components/ui/tooltip.jsx";
import ToBeReadSortableRow from "@/features/tbr/components/ToBeReadSortableRow.jsx";

export default function ToBeReadTable({
                                        toBeReads,
                                        loading,
                                        onEdit,
                                        onDelete,
                                        sortField,
                                        sortDir,
                                        onSort,
                                        onDragEnd,
                                        onMarkAsDone
                                      }) {
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
          <DndContext collisionDetection={closestCenter} onDragEnd={onDragEnd}>
            <SortableContext items={toBeReads.map((i) => i.id)} strategy={verticalListSortingStrategy}>
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
                  toBeReads.map((tbr, index) => (
                    <ToBeReadSortableRow
                      key={tbr.id}
                      tbr={tbr}
                      index={index}
                      onEdit={onEdit}
                      onDelete={onDelete}
                      handleBookView={handleBookView}
                      handleSagaView={handleSagaView}
                      handleMarkAsDone={onMarkAsDone}
                    />
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
            </SortableContext>
          </DndContext>
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
