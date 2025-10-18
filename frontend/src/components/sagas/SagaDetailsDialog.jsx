import { useEffect, useState } from "react";
import {
  AlertDialog,
  AlertDialogContent,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogCancel,
  AlertDialogAction,
} from "@/components/ui/alert-dialog";
import { Card } from "@/components/ui/card";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import api, { apiCall } from "../../lib/api";
import { useDialog } from "../../context/DialogContext";
import SagaDetails from "./SagaDetails";

const API_URL = "/sagas";

export default function SagasDetailsDialog({ open, onClose, sagaId }) {
  const { showError } = useDialog();
  const [saga, setSaga] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!sagaId) return;

    fetchSaga();
  }, [sagaId, showError]);

  const fetchSaga = async () => {
    setLoading(true);
    const res = await apiCall(() => api.get(`${API_URL}/${sagaId}`));
    setLoading(false);

    if (res.error) {
      showError(res.error);
      return;
    }

    setSaga(res.data);
  };
  
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
