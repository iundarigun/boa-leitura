import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {useDialog} from "@/context/DialogContext.jsx";
import {createReading, getReadingById, updateReading} from "@/lib/api/readings.js";
import {getBookById} from "@/lib/api/books.js";

export default function useReadingForm() {
  const {id, bookId} = useParams();
  const isEdit = Boolean(id);
  const [initialData, setInitialData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);

  const { showError, showSuccess } = useDialog();
  const navigate = useNavigate();

  useEffect(() => {
    fetchReading();
  }, [id, bookId]);

  const fetchReading = async () => {
    setLoading(true);
    if (id) {
      const {data, error} = await getReadingById(id);
      if (data) setInitialData(data);
      if (error) showError("Not possible to load the reading.");
      setLoading(false);
    } else if (bookId) {
      const {data, error} = await getBookById(bookId);
      if (data) setInitialData({"book": data});
      if (error) showError("Not possible to load the book.");
      setLoading(false);
    }
  }

  const handleSubmit = async (payload, maybeError) => {
    if (maybeError?.validationError) {
      showError(maybeError.validationError);
      return;
    }
    setSaving(true);
    const {error} = isEdit ? await updateReading(id, payload) : await createReading(payload);

    if (!error) {
      showSuccess(`Reading ${isEdit? "updated": "created"} successfully!`);
      navigate("/readings");
    } else {
      showError(error);
    }
    setSaving(false);
  };

  const handleCancel = () => navigate("/readings");

  return {
    isEdit,
    loading,
    saving,
    initialData,
    handleSubmit,
    handleCancel,
  };

}