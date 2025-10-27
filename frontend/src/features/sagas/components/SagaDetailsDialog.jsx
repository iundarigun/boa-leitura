import {
  AlertDialog,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import {Label} from "@/components/ui/label";
import useSagaDetails from "@/features/sagas/hooks/useSagaDetails.js";
import {Card} from "@/components/ui/card.jsx";
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue} from "@/components/ui/select.jsx";
import {SAGA_STATUS} from "@/lib/sagaStatus.js";
import {Button} from "@/components/ui/button.jsx";

export function SagaDetails({saga}) {
  if (!saga) return null;

  return (
    <Card className="p-4 space-y-4">
      {saga.mainTitles && saga.mainTitles.length > 0 && (
        <div className="space-y-2">
          <p className="font-bold text-lg">Main titles:</p>
          {saga.mainTitles.map((title, idx) => (
            <p key={idx}>{title}</p>
          ))}
        </div>
      )}

      {saga.complementaryTitles && saga.complementaryTitles.length > 0 && (
        <div className="space-y-2">
          <p className="font-bold text-lg">Other titles:</p>
          {saga.complementaryTitles.map((title, idx) => (
            <p key={idx}>{title}</p>
          ))}
        </div>
      )}
    </Card>
  );
}

export default function SagasDetailsDialog({open, onClose, sagaId}) {
  const {
    saga,
    status,
    setStatus,
    handleUpdateStatus,
    loading
  } = useSagaDetails(sagaId);

  if (!open || !sagaId) return null;

  if (loading) {
    return (
      <div className="p-4 text-center text-gray-500">
        Loading saga details...
      </div>
    );
  }

  if (!saga) {
    return (
      <div className="p-4 text-center text-gray-500">
        No saga data available.
      </div>
    );
  }

  return (
    <AlertDialog open={open} onOpenChange={onClose}>
      <AlertDialogContent className="max-w-2xl">
        <AlertDialogHeader>
          <AlertDialogTitle>{saga.name}</AlertDialogTitle>
        </AlertDialogHeader>

        <SagaDetails saga={saga}/>

        <div>
          <Label>Status</Label>
          <Select
            key={status}
            value={status}
            onValueChange={setStatus}>
            <SelectTrigger>
              <SelectValue placeholder="Select status" />
            </SelectTrigger>
            <SelectContent>
              {SAGA_STATUS.map((stat) => (
                <SelectItem key={stat.code} value={stat.code}>
                  {stat.label}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>

        <AlertDialogFooter className="flex justify-end gap-3 mt-6">
          <Button variant="outline" size="sm" onClick={() => handleUpdateStatus()}>
            Update status
          </Button>
          <AlertDialogCancel>Close</AlertDialogCancel>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
