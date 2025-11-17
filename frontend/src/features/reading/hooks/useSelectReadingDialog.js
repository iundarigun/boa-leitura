import {useEffect, useState} from "react";
import {useDialog} from "@/context/DialogContext.jsx";
import {getReadings} from "@/lib/api/readings.js";

export default function useSelectReadingDialog(year, month) {
  const [readings, setReadings] = useState({"content": []});
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading] = useState(false);
  const [sortField, setSortField] = useState("DATE_READ");
  const [sortDir, setSortDir] = useState("asc");

  const filterDateFrom = `${year}-${month}-01`;
  const filterDateTo = `${year}-${month}-${new Date(year, Number(month), 0).getDate()}`;

  const {showError} = useDialog();

  const fetchReadings = async () => {
    setLoading(true);
    const {data, error} = await getReadings({
      page,
      filterDateTo,
      filterDateFrom,
      sortField,
      sortDir,
    });

    if (error) showError(error);

    if (data) {
      setReadings(data.content);
      setPage(data.page);
      setTotalPages(data.totalPages);
    }

    setLoading(false);
  };

  useEffect(() => {
    fetchReadings();
  }, [year, month, sortField, sortDir, page]);

  const handleSort = (field) => {
    if (sortField === field) {
      setSortDir((prev) => (prev === "asc" ? "desc" : "asc"));
    } else {
      setSortField(field);
      setSortDir("asc");
    }
  };

  return {
    readings,
    loading,
    page,
    setPage,
    totalPages,
    filterDateTo,
    filterDateFrom,
    sortField,
    sortDir,
    handleSort,
    fetchReadings
  }
}