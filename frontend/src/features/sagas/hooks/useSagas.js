import {useEffect, useState} from "react";
import {useDialog} from "@/context/DialogContext.jsx";
import {deleteSaga, getSagas} from "@/lib/api/saga.js";

export default function useSagas() {
  const [sagas, setSagas] = useState({ content: [] });
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [searchApplied, setSearchApplied] = useState("");
  const [sortField, setSortField] = useState("NAME");
  const [sortDir, setSortDir] = useState("asc");


  const { showError, showSuccess } = useDialog();

  useEffect(() => {
    fetchSagas();
  }, [page, sortField, sortDir, searchApplied]);

  const fetchSagas = async () => {
    setLoading(true);
    const {data, error} = await getSagas({
      page: page,
      name: search,
      sortField: sortField,
      sortDir: sortDir
    })
    if (error) {
      showError(error);
    }
    if(data) {
      setSagas(data);
      setPage(data.page);
      setTotalPages(data.totalPages);
    }
    setLoading(false);
  };

  const handleDelete = async (saga) => {
    const {error} = await deleteSaga(saga.id)
    if (error) {
      showError(error);
    } else {
      fetchSagas();
      showSuccess(`Saga "${saga.name}" deleted successfully.`);
    }
  };

  const handleSearch = () => {
    setPage(1);
    setSearchApplied(search);
  };

  const handleSort = (field) => {
    if (sortField === field) {
      setSortDir((prev) => (prev === "asc" ? "desc" : "asc"));
    } else {
      setSortField(field);
      setSortDir("asc");
    }
  };

  return {
    sagas,
    loading,
    page,
    setPage,
    totalPages,
    search,
    setSearch,
    sortField,
    sortDir,
    handleSearch,
    handleDelete,
    handleSort
  }
}