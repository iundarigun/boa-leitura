import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from "@/components/ui/select";
import BookTable from "../../components/books/BookTable";
import BookDetailsDialog from "../../components/books/BookDetailsDialog";
import { useDialog } from "../../context/DialogContext";
import api, { apiCall } from "../../lib/api";


const API_URL = "/books";

export default function BooksListPage() {
  const [books, setBooks] = useState({"content": []});
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading] = useState(false);

  const [search, setSearch] = useState("");
  const [filterRead, setFilterRead] = useState("both");
  const [sortField, setSortField] = useState(null);
  const [sortDir, setSortDir] = useState(null);
  const [searchApplied, setSearchApplied] = useState("");
  
  const [bookDetailsOpen, setBookDetailsOpen] = useState(false);
  const [selectedBook, setSelectedBook] = useState(null);

  const { showError, showSuccess } = useDialog();

  const loadBooks = async () => {
    setLoading(true);
    setSearchApplied("");
    const query = new URLSearchParams({
      page: page.toString()
    });
    if (sortField) query.append("order", sortField);
    if (sortDir) query.append("directionAsc", sortDir === "asc")
    if (search) query.append("title", search);
    if (filterRead !== "both") query.append("read", filterRead === "read");

    const res = await apiCall(() => api.get(`${API_URL}?${query.toString()}`));

    if (!res.error) {
      setBooks(res.data.content);
      setPage(res.data.page);
      setTotalPages(res.data.totalPages);
    } else {
      showError(res.error);      
    }
    setLoading(false);
  };

  useEffect(() => {
    loadBooks();
  }, [page, sortField, sortDir, searchApplied]);

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
    const res = await apiCall(() => api.get(`${API_URL}/${bookId}`));
    if (res.error) {
      showError(res.error);
      return;
    }
    setSelectedBook(res.data);
    setBookDetailsOpen(true);
  };

  const handleEdit = (book) => {
    // navega para página de edição
    window.location.href = `/books/${book.id}/edit`;
  };

  const handleDelete = async (book) => {
    const res = await apiCall(() => api.delete(`${API_URL}/${book.id}`));
    if (res.error) {
      showError(res.error);
      return;
    }
    showSuccess(`Book "${book.title}" deleted.`);
    loadBooks();
  };

  return (
    <div className="p-6 space-y-6">
      <h1 className="text-2xl font-bold">📚 Books</h1>

      <div className="flex flex-wrap gap-3 items-center">
        <Input
          placeholder="Search by title..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && handleSearch()}
          className="w-64"
        />
        <Select value={filterRead} onValueChange={setFilterRead}>
          <SelectTrigger className="w-40">
            <SelectValue placeholder="Filter by status" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="both">Both</SelectItem>
            <SelectItem value="read">Read</SelectItem>
            <SelectItem value="non-read">Non-read</SelectItem>
          </SelectContent>
        </Select>
        <Button onClick={handleSearch}>Search</Button>
      </div>

      <div className="flex justify-center items-center gap-2">
        <Button
          variant="outline"
          onClick={() => setPage((p) => Math.max(p - 1, 1))}
          disabled={page <= 1}
        >
          Previous
        </Button>
        <span>
          Page {page} of {totalPages}
        </span>
        <Button
          variant="outline"
          onClick={() => setPage((p) => Math.min(p + 1, totalPages))}
          disabled={page >= totalPages}
        >
          Next
        </Button>
      </div>

      <BookTable
        books={books}
        loading={loading}
        sortField={sortField}
        sortDir={sortDir}
        onSort={handleSort}
        onView={(book) => handleView(book.id)}
      />

      <div className="flex justify-center items-center gap-2">
        <Button
          variant="outline"
          onClick={() => setPage((p) => Math.max(p - 1, 1))}
          disabled={page <= 1}
        >
          Previous
        </Button>
        <span>
          Page {page} of {totalPages}
        </span>
        <Button
          variant="outline"
          onClick={() => setPage((p) => Math.min(p + 1, totalPages))}
          disabled={page >= totalPages}
        >
          Next
        </Button>
      </div>
      
      <BookDetailsDialog
        open={bookDetailsOpen}
        onClose={setBookDetailsOpen}
        book={selectedBook}
        onEdit={handleEdit}
        onDelete={handleDelete}
      />
    </div>
  );
}
