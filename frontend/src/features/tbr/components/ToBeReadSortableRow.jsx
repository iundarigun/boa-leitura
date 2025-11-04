import {useSortable,} from "@dnd-kit/sortable";
import {CSS} from "@dnd-kit/utilities";
import {Button} from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import {BookOpen, Check, Eye, MoreVertical, Pencil, Trash2, BadgeEuro} from "lucide-react";
import CustomTooltip from "@/components/CustomTooltip.jsx";
import {useState} from "react";
import CustomAlertDialog from "@/components/CustomAlertDialog.jsx";

export default function ToBeReadSortableRow({
                                              tbr,
                                              index,
                                              isDragDisabled,
                                              onEdit,
                                              onDelete,
                                              handleBookView,
                                              handleSagaView,
                                              handleMarkAsDone,
                                              handleMarkAsBought
                                            }) {
  const {
    attributes,
    listeners,
    setNodeRef,
    transform,
    transition,
    isDragging
  } = useSortable({
    id: tbr.id,
    disabled: isDragDisabled
  });

  const [confirmDelete, setConfirmDelete] = useState(false);
  const [confirmMarkAsDone, setConfirmMarkAsDone] = useState(false);
  const [confirmMarkAsBought, setConfirmMarkAsBought] = useState(false);

  const style = {
    transform: CSS.Transform.toString(transform),
    transition,
    opacity: isDragging ? 0.5 : 1,
  };

  const tooltipContent = (tbr) => {
    if (tbr.notes || (tbr.tags && tbr.tags.length > 0)) {
      return (<>
        {tbr.notes || '-' }
        {tbr.tags && tbr.tags.length > 0 && <>
          <br/><br/><label>tags: </label>{tbr.tags.join(", ")}
        </>
        }
      </>);
    }
  }

  return (
    <tr ref={setNodeRef} style={style} className="border-t hover:bg-gray-50">
      <td
        className={`p-3 font-medium select-none ${
          isDragDisabled ? "cursor-default opacity-70" : "cursor-grab"
        }`}
        {...(!isDragDisabled ? {...attributes, ...listeners} : {})}
        title="Drag to change position"
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
          tooltipContent={tooltipContent(tbr)}
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
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant="ghost" size="icon">
              <MoreVertical className="h-4 w-4"/>
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuLabel>Actions</DropdownMenuLabel>
            <DropdownMenuItem onClick={() => handleBookView(tbr.book.id)}>
              <Eye className="h-4 w-4 mr-2"/>
              View book details
            </DropdownMenuItem>
            {!tbr.done && <DropdownMenuItem onClick={() => setConfirmMarkAsDone(true)}>
              <Check className="h-4 w-4 mr-2"/>
              Mark as done
            </DropdownMenuItem>
            }
            {!tbr.bought &&
              <DropdownMenuItem onClick={() => setConfirmMarkAsBought(true)}>
                <BadgeEuro className="h-4 w-4 mr-2"/>
                Mark as bought
              </DropdownMenuItem>
            }
            <DropdownMenuItem onClick={() => onEdit(tbr)}>
              <Pencil className="h-4 w-4 mr-2"/>
              Edit
            </DropdownMenuItem>
            {tbr.book.saga && (
              <DropdownMenuItem onClick={() => handleSagaView(tbr.book.saga.id)}>
                <BookOpen className="h-4 w-4 mr-2"/>
                View Saga
              </DropdownMenuItem>
            )}
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
        text={<>Are you sure you want to delete <b>{tbr.book?.title}</b> from TBR?
          This action cannot be undone.</>}
        action={() => onDelete(tbr)}
      />
      <CustomAlertDialog
        confirm={confirmMarkAsDone}
        setConfirm={setConfirmMarkAsDone}
        title="Mark as Done"
        text={<>Are you sure you want to mark as done <b>{tbr.book?.title}</b> from TBR?</>}
        action={() => handleMarkAsDone(tbr)}
      />
      <CustomAlertDialog
        confirm={confirmMarkAsBought}
        setConfirm={setConfirmMarkAsBought}
        title="Mark as Bought"
        text={<>Are you sure you want to mark as bought <b>{tbr.book?.title}</b>?</>}
        action={() => handleMarkAsBought(tbr)}
      />
    </tr>
  );
}