import { useNavigate } from "react-router-dom";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import AuthorTable from "../components/AuthorTable";
import Pagination from "@/components/Pagination";
import useAuthors from "@/features/authors/hooks/useAuthors.js";

export default function AuthorsListPage() {
  const {
    authors,
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
  } = useAuthors();

  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-6xl mx-auto p-8">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-3xl">✍️ Authors</CardTitle>
          <Button onClick={() => navigate("/authors/new")}>+ New Author</Button>
        </CardHeader>

        <div className="flex items-end gap-2 mb-4">
          <div>
            <Label>Name</Label>
            <Input
              placeholder="Search by name"
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              onKeyDown={(e) => e.key === "Enter" && handleSearch()}
            />
          </div>
          <Button onClick={handleSearch}>Search</Button>
        </div>

        <Pagination page={page} setPage={setPage} totalPages={totalPages} />

        <CardContent>
          {loading ? (
            <p className="text-center text-gray-500">Loading authors...</p>
          ) : (
            <AuthorTable
              authors={authors}
              onEdit={(author) => navigate(`/authors/${author.id}/edit`)}
              onDelete={handleDelete}
              onSort={handleSort}
              sortField={sortField}
              sortDir={sortDir}
            />
          )}
        </CardContent>

        <Pagination page={page} setPage={setPage} totalPages={totalPages} />
      </Card>
    </div>
  );
}
