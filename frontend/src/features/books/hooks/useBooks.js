import {useEffect, useState} from "react";
import {useDialog} from "@/context/DialogContext.jsx";
import {deleteBook, getBooks} from "@/lib/api/books.js";

export default function useBooks() {
  const [books, setBooks] = useState({"content": []});
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [filterRead, setFilterRead] = useState("both");
  const [sortField, setSortField] = useState("CREATED_AT");
  const [sortDir, setSortDir] = useState("desc");
  const [searchApplied, setSearchApplied] = useState("");
  const [bookDetailsOpen, setBookDetailsOpen] = useState(false);
  const [selectedBook, setSelectedBook] = useState(null);

  const { showError, showSuccess } = useDialog();


  useEffect(() => {
    fetchBooks();
  }, [page, sortField, sortDir, searchApplied]);

  const fetchBooks = async () => {
    setLoading(true);
    setSearchApplied("");

    const {data, error} = await getBooks({
      page: page,
      title: search,
      filterRead: filterRead,
      sortField: sortField,
      sortDir: sortDir
    });
    if(error) {
      showError(error);
    }
    if (data) {
      setBooks(data.content);
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

  const handleView = async (bookId) => {
    setSelectedBook(bookId);
    setBookDetailsOpen(true);
  };


  const handleDelete = async (book) => {
    const {error} = await deleteBook(book.id);
    if (error) {
      showError(error);
    } else {
      fetchBooks();
      showSuccess(`Book "${book.title}" deleted.`);
      setBookDetailsOpen(false);
    }
  };

  return {
    books,
    loading,
    page,
    setPage,
    totalPages,
    filterRead,
    setFilterRead,
    search,
    setSearch,
    sortField,
    sortDir,
    bookDetailsOpen,
    setBookDetailsOpen,
    selectedBook,
    handleSearch,
    handleDelete,
    handleSort,
    handleView
  }
}