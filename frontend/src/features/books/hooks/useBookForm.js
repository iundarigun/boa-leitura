import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {useDialog} from "@/context/DialogContext.jsx";
import {createBook, getBookById, updateBook} from "@/lib/api/books.js";

export default function useBookForm() {
  const { id } = useParams();
  const isEdit = Boolean(id);
  const navigate = useNavigate();
  const { showError, showSuccess } = useDialog();

  const [initialData, setInitialData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    if (isEdit) fetchBook();
  }, [id]);

  const fetchBook = async () => {
    setLoading(true);
    const { data, error } = await getBookById(id);
    if (data) setInitialData(data);
    if (error) showError(error);
    setLoading(false);
  };

  const handleSubmit = async (payload, maybeError) => {
    if (maybeError?.validationError) {
      showError(maybeError.validationError);
      return;
    }

    setSaving(true);
    const { error } = isEdit ? await updateBook(id, payload) : await createBook(payload);

    if (!error) {
      showSuccess(`Book ${isEdit ? "updated" : "created"} successfully.`);
      navigate("/books");
    } else {
      showError(error);
    }
    setSaving(false);
  };

  const handleCancel = () => navigate("/books");

  return {
    isEdit,
    loading,
    saving,
    initialData,
    handleSubmit,
    handleCancel,
  };
}