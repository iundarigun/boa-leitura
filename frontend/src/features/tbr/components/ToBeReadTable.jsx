import {useEffect, useState} from "react";
import { DndContext, closestCenter } from "@dnd-kit/core";
import {
  arrayMove,
  SortableContext,
  useSortable,
  verticalListSortingStrategy,
} from "@dnd-kit/sortable";
import { CSS } from "@dnd-kit/utilities";
import { Button } from "@/components/ui/button";
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
import { TooltipProvider } from "@/components/ui/tooltip.jsx";
import CustomTooltip from "@/components/CustomTooltip.jsx";

// 🔹 Componente para tornar cada linha "arrastável"
function SortableRow({ tbr, index, onEdit, onDelete, handleBookView, handleSagaView }) {
  const { attributes, listeners, setNodeRef, transform, transition, isDragging } = useSortable({
    id: tbr.id,
  });

  const style = {
    transform: CSS.Transform.toString(transform),
    transition,
    opacity: isDragging ? 0.5 : 1,
  };

  return (
    <tr ref={setNodeRef} style={style} className="border-t hover:bg-gray-50">
      {/* 🔹 Só esta célula é arrastável */}
      <td
        className="p-3 font-medium cursor-grab select-none"
        {...attributes}
        {...listeners}
        title="Arraste para mudar a posição"
      >
        {index + 1}
      </td>

      <td className="p-3">
        {tbr.book.urlImageSmall ? (
          <img
            src={tbr.book.urlImageSmall}
            alt={tbr.book.title}
            className="w-12 h-16 object-cover rounded"
          />
        ) : (
          <div className="w-12 h-16 bg-gray-200 rounded" />
        )}
      </td>

      <td
        className="p-3 cursor-pointer"
        onClick={() => handleBookView(tbr.book.id)}
      >
        <CustomTooltip
          content={
            <>
              {tbr.book.title} ({tbr.book.author})
            </>
          }
          tooltipContent={tbr.notes}
        />
      </td>

      <td
        className="p-3 cursor-pointer"
        onClick={() => tbr.book.saga && handleSagaView(tbr.book.saga.id)}
      >
        {tbr.book.saga?.name || "-"}
      </td>

      <td className="p-3">{new Date(tbr.addedAt).toLocaleDateString()}</td>

      <td className="p-3 text-center">
        <div className="flex justify-center gap-2">
          <Button variant="outline" size="sm" onClick={() => onEdit(tbr)}>
            Edit
          </Button>
          <AlertDialog>
            <AlertDialogTrigger asChild>
              <Button variant="destructive" size="sm">
                Delete
              </Button>
            </AlertDialogTrigger>
            <AlertDialogContent>
              <AlertDialogHeader>
                <AlertDialogTitle>Confirm delete</AlertDialogTitle>
                <AlertDialogDescription>
                  Are you sure you want to delete <b>{tbr.book?.title}</b> from TBR?
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
  );
}


export default function ToBeReadTable({
                                        toBeReads,
                                        loading,
                                        onEdit,
                                        onDelete,
                                        sortField,
                                        sortDir,
                                        onSort,
                                      }) {
  const [items, setItems] = useState(toBeReads);
  const [bookDetailsOpen, setBookDetailsOpen] = useState(false);
  const [selectedBook, setSelectedBook] = useState(null);
  const [sagaDetailsOpen, setSagaDetailsOpen] = useState(false);
  const [selectedSaga, setSelectedSaga] = useState(null);

  useEffect(() => setItems(toBeReads), [toBeReads]);

  const handleBookView = (bookId) => {
    setSelectedBook(bookId);
    setBookDetailsOpen(true);
  };

  const handleSagaView = (sagaId) => {
    setSelectedSaga(sagaId);
    setSagaDetailsOpen(true);
  };

  const handleDragEnd = async (event) => {
    const { active, over } = event;
    if (!over || active.id === over.id) return;

    const oldIndex = items.findIndex((i) => i.id === active.id);
    const newIndex = items.findIndex((i) => i.id === over.id);
    const direction = oldIndex < newIndex ? "DOWN" : "UP";

    const newItems = arrayMove(items, oldIndex, newIndex);
    setItems(newItems);

    // 🔹 Quando o endpoint existir:
    // await api.patch("/tbr/reorder", {
    //   movedId: active.id,
    //   targetId: over.id,
    //   direction,
    // });
  };

  if (loading || items.length === 0) {
    return (
      <div className="text-center p-4 text-gray-500">
        No readings found.
      </div>
    );
  }

  return (
    <>
      <TooltipProvider>
        <div className="border rounded-lg overflow-hidden shadow-sm">
        <DndContext collisionDetection={closestCenter} onDragEnd={handleDragEnd}>
            <SortableContext items={items.map((i) => i.id)} strategy={verticalListSortingStrategy}>
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
                ) : items.length > 0 ? (
                  items.map((tbr, index) => (
                    <SortableRow
                      key={tbr.id}
                      tbr={tbr}
                      index={index}
                      onEdit={onEdit}
                      onDelete={onDelete}
                      handleBookView={handleBookView}
                      handleSagaView={handleSagaView}
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
