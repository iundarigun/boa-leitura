import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from "@/components/ui/select";
import Pagination from "@/components/Pagination";
import BookTable from "../components/BookTable";
import BookDetailsDialog from "../components/BookDetailsDialog";
import useBooks from "@/features/books/hooks/useBooks.js";


export default function BooksListPage() {
  const {
    books,
    loading,
    page,
    setPage,
    totalPages,
    search,
    setSearch,
    filterRead,
    setFilterRead,
    sortField,
    sortDir,
    bookDetailsOpen,
    setBookDetailsOpen,
    selectedBook,
    handleSearch,
    handleDelete,
    handleSort,
    handleView
  } = useBooks();

  const navigate = useNavigate();

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
          onView={handleView}
        />

        <Pagination
          page={page}
          setPage={setPage}
          totalPages={totalPages}
          />
        
        <BookDetailsDialog
          open={bookDetailsOpen}
          onClose={setBookDetailsOpen}
          bookId={selectedBook}
          onDelete={handleDelete}
        />
      </Card>
    </div>
  );
}
