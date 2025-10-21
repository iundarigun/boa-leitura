import {Button} from "@/components/ui/button.jsx";
import {
  AlertDialog, AlertDialogAction, AlertDialogCancel,
  AlertDialogContent, AlertDialogDescription, AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger
} from "@/components/ui/alert-dialog.jsx";

export default function TableActionButtons({ entity, onDetails, onEdit, onDelete, onSelect, warningProperty}) {

  return (
    <>
      <div className="flex justify-center gap-2">
        {onDetails &&!onSelect && <Button size="sm" variant="outline" onClick={onDetails}>
          Details
        </Button>
        }
        {onEdit && <Button variant="outline" size="sm" onClick={() => onEdit(entity)}>
          Edit
        </Button>}
        {onSelect && <Button size="sm" onClick={() => onSelect(entity)}>Select</Button> }
        {onDelete && <AlertDialog>
          <AlertDialogTrigger asChild>
            <Button variant="destructive" size="sm">Delete</Button>
          </AlertDialogTrigger>
          <AlertDialogContent>
            <AlertDialogHeader>
              <AlertDialogTitle>Confirm delete</AlertDialogTitle>
              <AlertDialogDescription>
                Are you sure you want to delete <b>{entity[warningProperty]}</b>?
                This action cannot be undone.
              </AlertDialogDescription>
            </AlertDialogHeader>
            <AlertDialogFooter>
              <AlertDialogCancel>Cancel</AlertDialogCancel>
              <AlertDialogAction
                className="bg-red-600 hover:bg-red-700"
                onClick={() => onDelete(entity)}
              >
                Delete
              </AlertDialogAction>
            </AlertDialogFooter>
          </AlertDialogContent>
        </AlertDialog>}
      </div>
    </>
  );

}