import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import GenreList from "../../components/genres/GenreList";
import Pagination from "../../components/Pagination";
import { useDialog } from "../../context/DialogContext";
import api, { apiCall } from "../../lib/api";

const API_URL = "/genres";

export default function GenresListPage() {
  const [genres, setGenres] = useState({"content": []});
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const navigate = useNavigate();
  const { showError, showSuccess } = useDialog();

  useEffect(() => {
    fetchGenres();
  }, [page]);

  const fetchGenres = async () => {
    setLoading(true);
    const res = await apiCall(() => api.get(`${API_URL}?page=${page}`));
    if (!res.error) {
      setGenres(res.data);
      setPage(res.data.page);
      setTotalPages(res.data.totalPages);      
    } else {
      showError(res.error);
    }
    setLoading(false);
  };

  const handleDelete = async (id, name) => {
    const res = await apiCall(() => api.delete(`${API_URL}/${id}`));
    if (!res.error) {
      fetchGenres()
      showSuccess(`Genre "${name}" deleted successfully.`);
    }
    else {
      showError(res.error);
    }
  };

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
