import {useEffect, useState} from "react";
import {deleteGenre, getGenres} from "@/lib/api/genres.js";
import {useDialog} from "@/context/DialogContext.jsx";

export default function useGenres() {
  const [genres, setGenres] = useState({"content": []});
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);

  const {showError, showSuccess} = useDialog();

  useEffect(() => {
    fetchGenres();
  }, [page]);

  const fetchGenres = async () => {
    setLoading(true);
    const {data, error} = await getGenres();
    if (error) {
      showError(error);
    }
    if (data) {
      setGenres(data);
      setPage(data.page);
      setTotalPages(data.totalPages);
    }
    setLoading(false);
  };

  const handleDelete = async (genre) => {
    const {error} = await deleteGenre(genre.id);
    if (error) {
      showError(error);
    } else {
      fetchGenres()
      showSuccess(`Genre "${genre.name}" deleted successfully.`);
    }
  };

  return {
    genres,
    loading,
    page,
    setPage,
    totalPages,
    handleDelete,
  }
}