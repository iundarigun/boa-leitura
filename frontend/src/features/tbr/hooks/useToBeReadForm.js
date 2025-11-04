import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {useDialog} from "@/context/DialogContext.jsx";
import {getToBeReadById, updateToBeRead} from "@/lib/api/tbr.js";

export default function useToBeReadForm() {
  const {id} = useParams();

  const [initialData, setInitialData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);

  const { showError, showSuccess } = useDialog();
  const navigate = useNavigate();

  useEffect(() => {
    fetchToBeRead();
  }, [id]);

  const fetchToBeRead = async () => {
    setLoading(true);
    if (id) {
      const {data, error} = await getToBeReadById(id);
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
    const {error} = await updateToBeRead(id, payload);

    if (!error) {
      showSuccess(`To Be Read details updated successfully!`);
      navigate("/tbr");
    } else {
      showError(error);
    }
    setSaving(false);
  };

  const handleCancel = () => navigate("/tbr");

  return {
    loading,
    saving,
    initialData,
    handleSubmit,
    handleCancel,
  };
}