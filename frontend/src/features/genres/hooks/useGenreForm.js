import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {useDialog} from "@/context/DialogContext.jsx";
import {createGenre, getGenreById, getGenres, updateGenre} from "@/lib/api/genres.js";

export default function useGenreForm() {
  const { id } = useParams();
  const isEdit = Boolean(id);
  const [allGenres, setAllGenres] = useState([]);
  const [initialData, setInitialData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);

  const navigate = useNavigate();
  const { showError, showSuccess } = useDialog();

  // Load genre when editing
  useEffect(() => {
    fetchAllGenres();
    fetchGenre();
  }, [id]);

  const fetchGenre = async () => {
    if (id) {
      setLoading(true);
      const {data, error} = await getGenreById(id)
      if (data) setInitialData(data);
      if (error) showError(error);
      setLoading(false);
    }
  };

  const fetchAllGenres = async () => {
    const {data, error} = await getGenres();
    if (data) {
      setAllGenres(data);
    } else {
      showError(error);
    }
  };

  const handleSubmit = async (payload, maybeError) => {
    if (maybeError?.validationError) {
      showError(maybeError.validationError);
      return;
    }

    setSaving(true);
    const {error} = isEdit ? await updateGenre(id, payload) : await createGenre(payload);

    if (!error) {
      showSuccess(`Genre ${isEdit ? "updated" : "created"} successfully.`);
      navigate("/genres");
    } else {
      showError(error);
    }
    setSaving(false);
  };

  const handleCancel = () => navigate("/genres");

  return {
    isEdit,
    loading,
    saving,
    initialData,
    allGenres,
    handleSubmit,
    handleCancel,
  };
}