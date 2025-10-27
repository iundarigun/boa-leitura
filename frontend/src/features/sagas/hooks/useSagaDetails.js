import {useEffect, useState} from "react";
import {useDialog} from "@/context/DialogContext.jsx";
import {getSagaById, updateStatusSaga} from "@/lib/api/saga.js";

export default function useSagaDetails(sagaId) {
  const { showSuccess, showError } = useDialog();
  const [saga, setSaga] = useState(null);
  const [status, setStatus] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!sagaId) return;
    fetchSaga();
  }, [sagaId]);

  const fetchSaga = async () => {
    setLoading(true);
    const {data, error} = await getSagaById(sagaId);
    setLoading(false);
    if (error) {
      showError(error);
      return;
    }
    setStatus(data.sagaStatus || "");
    setSaga(data);
  };

  const handleUpdateStatus = async () => {
    if (!status || !status.trim()) {
      showError("Select the status of the saga");
    }
    const {error} = await updateStatusSaga(sagaId, status);
    if (!error) {
      showSuccess(`Status of the saga updated successfully!`);
    } else {
      showError(error);
    }
  };

  return {
    saga,
    status,
    setStatus,
    handleUpdateStatus,
    loading
  };
}