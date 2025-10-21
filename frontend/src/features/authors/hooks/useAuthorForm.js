import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useDialog } from "@/context/DialogContext";
import {
  getAuthorById,
  createAuthor,
  updateAuthor,
} from "@/lib/api/authors";

export default function useAuthorForm() {
  const { id } = useParams();
  const isEdit = Boolean(id);
  const navigate = useNavigate();
  const { showError, showSuccess } = useDialog();

  const [initialData, setInitialData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    if (isEdit) fetchAuthor();
  }, [id]);

  const fetchAuthor = async () => {
    setLoading(true);
    const { data, error } = await getAuthorById(id);
    if (data) setInitialData(data);
    if (error) showError("Could not load the author.");
    setLoading(false);
  };

  const handleSubmit = async (payload, maybeError) => {
    if (maybeError?.validationError) {
      showError(maybeError.validationError);
      return;
    }

    setSaving(true);
    const { error } = isEdit
      ? await updateAuthor(id, payload)
      : await createAuthor(payload);

    if (!error) {
      showSuccess(`Author ${isEdit ? "updated" : "created"} successfully.`);
      navigate("/authors");
    } else {
      showError(error);
    }
    setSaving(false);
  };

  const handleCancel = () => navigate("/authors");

  return {
    isEdit,
    loading,
    saving,
    initialData,
    handleSubmit,
    handleCancel,
  };
}
