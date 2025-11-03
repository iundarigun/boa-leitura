import {useSortable,} from "@dnd-kit/sortable";
import {CSS} from "@dnd-kit/utilities";
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
import CustomTooltip from "@/components/CustomTooltip.jsx";

export default function ToBeReadSortableRow({tbr, index, onEdit, onDelete, handleBookView, handleSagaView}) {
  const {
    attributes,
    listeners,
    setNodeRef,
    transform,
    transition,
    isDragging
  } = useSortable({
    id: tbr.id,
  });

  const style = {
    transform: CSS.Transform.toString(transform),
    transition,
    opacity: isDragging ? 0.5 : 1,
  };

  return (
    <tr ref={setNodeRef} style={style} className="border-t hover:bg-gray-50">
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
          <div className="w-12 h-16 bg-gray-200 rounded"/>
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