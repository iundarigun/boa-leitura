import {useEffect, useState} from "react";
import {useDialog} from "@/context/DialogContext.jsx";
import {deleteToBeRead, getToBeRead, reorderToBeRead} from "@/lib/api/tbr.js";
import {arrayMove} from "@dnd-kit/sortable";

export default function useToBeReads() {
  const [toBeReads, setToBeReads] = useState([]);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading] = useState(false);

  const [filterKeyword, setFilterKeyword] = useState("");
  const [sortField, setSortField] = useState("POSITION");
  const [sortDir, setSortDir] = useState("asc");
  const [searchApplied, setSearchApplied] = useState("");

  const {showError, showSuccess} = useDialog();

  useEffect(() => {
    fetchToBeReads();
  }, [page, sortField, sortDir, searchApplied]);


  const fetchToBeReads = async () => {
    setLoading(true);
    setSearchApplied("");
    const {data, error} = await getToBeRead({
      page: page,
      filterKeyword: filterKeyword,
      sortField: sortField,
      sortDir: sortDir
    })

    if (error) {
      showError(error);
    }
    if (data) {
      setToBeReads(data.content);
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
    window.location.href = `/tbr/${reading.id}/edit`;
  };

  const handleDelete = async (tbr) => {
    const {error} = await deleteToBeRead(tbr.id);
    if (error) {
      showError(error);
      return;
    }
    showSuccess(`Book "${tbr.book.title}" deleted from TBR.`);
    fetchToBeReads();
  };

  const handleDragEnd = async (event) => {
    const {active, over} = event;
    if (!over || active.id === over.id) return;

    const oldIndex = toBeReads.findIndex((i) => i.id === active.id);
    const newIndex = toBeReads.findIndex((i) => i.id === over.id);
    const direction = oldIndex < newIndex ? "DOWN" : "UP";

    const newItems = arrayMove(toBeReads, oldIndex, newIndex);
    setToBeReads(newItems);

    reorderToBeRead(active.id,
      {
        targetId: over.id,
        direction,
      });
  };


  return {
    toBeReads,
    loading,
    page,
    setPage,
    totalPages,
    filterKeyword,
    setFilterKeyword,
    sortField,
    sortDir,
    handleSearch,
    handleDelete,
    handleSort,
    handleEdit,
    handleDragEnd
  }
}