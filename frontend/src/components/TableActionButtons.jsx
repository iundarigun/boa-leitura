import {Button} from "@/components/ui/button.jsx";
import {useState} from "react";
import CustomAlertDialog from "@/components/CustomAlertDialog.jsx";

export default function TableActionButtons({ entity, onDetails, onEdit, onDelete, onSelect, warningProperty, deleteMessage}) {
  const [confirmDelete, setConfirmDelete] = useState(false);
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
        {onDelete && <Button size="sm" variant="destructive"  onClick={() => setConfirmDelete(true)}>Delete</Button> }
      </div>
      <CustomAlertDialog
        confirm={confirmDelete}
        setConfirm={setConfirmDelete}
        title="Confirm delete"
        text={<>{deleteMessage ? (deleteMessage(entity))
          :<>Are you sure you want to delete<b>{entity[warningProperty]}</b>?</>
        }
        This action cannot be undone.</>}
        action={() => onDelete(entity)}
      />
    </>
  );

}