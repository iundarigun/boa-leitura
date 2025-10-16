import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from "@/components/ui/select";
import Pagination from "../../components/Pagination";
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
  
  const navigate = useNavigate();
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
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-6xl mx-auto p-8">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-3xl">📕 Books</CardTitle>
          <Button onClick={() => navigate("/books/new")}>+ New Book</Button>
        </CardHeader>
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

        <Pagination
          page={page}
          setPage={setPage}
          totalPages={totalPages}
          />

        <BookTable
          books={books}
          loading={loading}
          sortField={sortField}
          sortDir={sortDir}
          onSort={handleSort}
          onView={(book) => handleView(book.id)}
        />

        <Pagination
          page={page}
          setPage={setPage}
          totalPages={totalPages}
          />
        
        <BookDetailsDialog
          open={bookDetailsOpen}
          onClose={setBookDetailsOpen}
          book={selectedBook}
          onEdit={handleEdit}
          onDelete={handleDelete}
        />
      </Card>
    </div>
  );
}
