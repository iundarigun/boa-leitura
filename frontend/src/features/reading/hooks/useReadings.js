import {useEffect, useState} from "react";
import {useDialog} from "@/context/DialogContext.jsx";
import {deleteReading, getReadings} from "@/lib/api/readings.js";

export default function useReadings() {
  const [readings, setReadings] = useState({"content": []});
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading] = useState(false);

  const [filterKeyword, setFilterKeyword] = useState("");
  const [filterDateFrom, setFilterDateFrom] = useState(null);
  const [filterDateTo, setFilterDateTo] = useState(null);
  const [sortField, setSortField] = useState("DATE_READ");
  const [sortDir, setSortDir] = useState("desc");
  const [searchApplied, setSearchApplied] = useState("");

  const { showError, showSuccess } = useDialog();

  useEffect(() => {
    fetchReadings();
  }, [page, sortField, sortDir, searchApplied]);


  const fetchReadings = async () => {
    setLoading(true);
    setSearchApplied("");
    const {data, error} = await getReadings({
      page: page,
      filterKeyword: filterKeyword,
      filterDateTo: filterDateTo,
      filterDateFrom: filterDateFrom,
      sortField: sortField,
      sortDir: sortDir
    })

    if (error) {
      showError(error);
    }
    if (data) {
      setReadings(data.content);
      setPage(data.page);
      setTotalPages(data.totalPages);
    }
    setLoading(false);
  };

  const handleSearch = () => {
    setPage(1);
    setSearchApplied("search")
  };

  const handleSort = (field) => {
    if (sortField === field) {
      setSortDir((prev) => (prev === "asc" ? "desc" : "asc"));
    } else {
      setSortField(field);
      setSortDir("asc");
    }
  };

  const handleEdit = (reading) => {
    window.location.href = `/readings/${reading.id}/edit`;
  };

  const handleDelete = async (reading) => {
    const {error} = await deleteReading(reading.id);
    if (error) {
      showError(error);
      return;
    }
    showSuccess(`Reading of "${reading.book.title}" on ${reading.dateRead} deleted.`);
    fetchReadings();
  };

  return {
    readings,
    loading,
    page,
    setPage,
    totalPages,
    filterKeyword,
    setFilterKeyword,
    filterDateTo,
    setFilterDateTo,
    filterDateFrom,
    setFilterDateFrom,
    sortField,
    sortDir,
    handleSearch,
    handleDelete,
    handleSort,
    handleEdit
  }
}