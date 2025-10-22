import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {useDialog} from "@/context/DialogContext.jsx";
import {createSaga, getSagaById, updateSaga} from "@/lib/api/saga.js";

export default function useSagaForm() {
  const { id } = useParams();
  const isEdit = Boolean(id);
  const [initialData, setInitialData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);

  const navigate = useNavigate();
  const { showError, showSuccess } = useDialog();

  useEffect(() => {
    fetchSaga();
  }, [id]);

  const fetchSaga = async () => {
    if (id) {
      setLoading(true);
      const {data, error} = await getSagaById(id)
      if (data) setInitialData(data);
      if (error) showError(error);
      setLoading(false);
    }
  }

  const handleSubmit = async (payload, maybeError) => {
    if (maybeError?.validationError) {
      showError(maybeError.validationError);
      return;
    }

    setSaving(true);
    const {error} = isEdit ? await updateSaga(id, payload) : await createSaga(payload);

    if (!error) {
      showSuccess(`Saga ${id? "updated": "created"} successfully!`);
      navigate("/sagas");
    } else {
      showError(error);
    }
    setSaving(false);
  };

  const handleCancel = () => navigate("/sagas");

  return {
    isEdit,
    loading,
    saving,
    initialData,
    handleSubmit,
    handleCancel,
  };
}