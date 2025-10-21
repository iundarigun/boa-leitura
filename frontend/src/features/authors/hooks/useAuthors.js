import {useDialog} from "@/context/DialogContext.jsx";
import {useEffect, useState} from "react";
import {deleteAuthor, getAuthors} from "@/lib/api/authors.js";

export default function useAuthors() {
  const [authors, setAuthors] = useState({ content: [] });
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [search, setSearch] = useState("");
  const [searchApplied, setSearchApplied] = useState("");

  const { showError, showSuccess } = useDialog();

  useEffect(() => {
    fetchAuthors();
  }, [page, searchApplied]);

  const fetchAuthors = async () => {
    setLoading(true);
    const {data, error} = await getAuthors({
      page: page,
      name: searchApplied
    });
    if (error) {
      showError(error);
    }
    if(data) {
      setAuthors(data);
      setPage(data.page);
      setTotalPages(data.totalPages);
    }
    setLoading(false);
  };

  const handleDelete = async (author) => {
    const {error } = await deleteAuthor(author.id);
    if (error) {
      showError(error);
    }else {
      fetchAuthors();
      showSuccess(`Author "${author.name}" deleted successfully.`);
    }
  };

  const handleSearch = () => {
    setPage(1);
    setSearchApplied(search);
  };

  return {
    authors,
    loading,
    page,
    setPage,
    totalPages,
    search,
    setSearch,
    handleSearch,
    handleDelete,
  }
}