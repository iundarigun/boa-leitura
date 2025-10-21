import {
  AlertDialog,
  AlertDialogContent,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogFooter,
  AlertDialogCancel,
} from "@/components/ui/alert-dialog";

import useSagaDetails from "@/features/sagas/hooks/useSagaDetails.js";
import {Card} from "@/components/ui/card.jsx";

export function SagaDetails({ saga }) {
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

export default function SagasDetailsDialog({ open, onClose, sagaId }) {
  const {
    saga,
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

        <SagaDetails saga={saga} />
        <AlertDialogFooter className="flex justify-end gap-3 mt-6">
          <AlertDialogCancel>Close</AlertDialogCancel>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
