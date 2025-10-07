import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import GenreList from "../../components/genres/GenreList";
import FeedbackDialog from "../../components/FeedbackDialog";

const API_URL = "http://localhost:1980/genres";

export default function GenresListPage() {
  const [genres, setGenres] = useState({"content": []});
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const [feedbackOpen, setFeedbackOpen] = useState(false);
  const [feedbackMessage, setFeedbackMessage] = useState({ title: "", description: "" });

  useEffect(() => {
    fetchGenres();
  }, []);

  const fetchGenres = async () => {
    setLoading(true);
    try {
      const res = await axios.get(API_URL);
      setGenres(res.data);
    } catch (err) {
      console.error("Erro ao buscar gêneros", err);
      setFeedbackMessage({
        title: "Erro",
        description: "Não foi possível carregar os gêneros."
      });
      setFeedbackOpen(true);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id, name) => {
    try {
      await axios.delete(`${API_URL}/${id}`);
      fetchGenres();
      setFeedbackMessage({
        title: "Sucesso",
        description: `Gênero "${name}" foi deletado com sucesso.`,
      });
    } catch (err) {
      let description = `Não foi possível deletar o gênero "${name}".`;
      if (err.response?.data?.message) {
        description = err.response.data.message;
      }
      setFeedbackMessage({
        title: "Erro",
        description,
      });
    } finally {
      setFeedbackOpen(true);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-6xl mx-auto p-8">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-3xl">📚 Gêneros</CardTitle>
          <Button onClick={() => navigate("/genres/new")}>+ Novo Gênero</Button>
        </CardHeader>
        <CardContent>
          {loading ? (
            <p className="text-center text-gray-500">Carregando gêneros...</p>
          ) : (
            <GenreList
              genres={genres}
              onEdit={(genre) => navigate(`/genres/${genre.id}/edit`)}
              onDelete={handleDelete}
            />
          )}
        </CardContent>
      </Card>
      <FeedbackDialog
        open={feedbackOpen}
        onOpenChange={setFeedbackOpen}
        title={feedbackMessage.title}
        description={feedbackMessage.description}
      />
    </div>
  );
}
