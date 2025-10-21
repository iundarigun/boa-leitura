import {useEffect, useState} from "react";
import {useDialog} from "@/context/DialogContext.jsx";
import {getSagaById} from "@/lib/api/saga.js";

export default function useSagaDetails(sagaId) {
  const { showError } = useDialog();
  const [saga, setSaga] = useState(null);
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
    setSaga(data);
  };

  return {
    saga,
    loading
  };
}