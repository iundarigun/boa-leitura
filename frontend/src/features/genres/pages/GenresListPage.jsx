import { useNavigate } from "react-router-dom";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import GenreList from "../components/GenreList";
import Pagination from "@/components/Pagination";
import useGenres from "@/features/genres/hooks/useGenres.js";


export default function GenresListPage() {
  const {
    genres,
    page,
    setPage,
    totalPages,
    loading,
    handleDelete
  } = useGenres()
  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-6xl mx-auto p-8">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-3xl">🐉 Genres</CardTitle>
          <Button onClick={() => navigate("/genres/new")}>+ New genre</Button>
        </CardHeader>
        <Pagination
          page={page}
          setPage={setPage}
          totalPages={totalPages}
          />
        <CardContent>
          {loading ? (
            <p className="text-center text-gray-500">Loading genres...</p>
          ) : (
            <GenreList
              genres={genres}
              onEdit={(genre) => navigate(`/genres/${genre.id}/edit`)}
              onDelete={handleDelete}
            />
          )}
        </CardContent>
        <Pagination
          page={page}
          setPage={setPage}
          totalPages={totalPages}
          />
      </Card>      
    </div>
  );
}
