import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import AuthorList from "../components/AuthorList";
import FeedbackDialog from "../components/FeedbackDialog";

const API_URL = "http://localhost:1980/authors";

export default function AuthorsListPage() {
  const [authors, setAuthors] = useState({"content": []});
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const [feedbackOpen, setFeedbackOpen] = useState(false);
  const [feedbackMessage, setFeedbackMessage] = useState({ title: "", description: "" });

  useEffect(() => {
    fetchAuthors();
  }, []);

  const fetchAuthors = async () => {
    setLoading(true);
    try {
      const res = await axios.get(API_URL);
      setAuthors(res.data);
    } catch (err) {
      console.error("Erro ao buscar autores", err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id, name) => {
    try {
      await axios.delete(`${API_URL}/${id}`);
      fetchAuthors()
      setFeedbackMessage({
        title: "Sucesso",
        description: `Autor "${name}" foi deletado com sucesso.`,
      });
    } catch (err) {
      var description = `Não foi possível deletar o autor "${name}".`
      if (err.response && err.response.data && err.response.data.message) {
         description = err.response.data.message
      }
      setFeedbackMessage({
        title: "Erro",
        description: description,
      });
    } finally {
      setFeedbackOpen(true); // abre feedback
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-6xl mx-auto p-8">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-3xl">✍️ Autores</CardTitle>
          <Button onClick={() => navigate("/authors/new")}>+ Novo Autor</Button>
        </CardHeader>
        <CardContent>
          {loading ? (
            <p className="text-center text-gray-500">Carregando autores...</p>
          ) : (
            <AuthorList
              authors={authors}
              onEdit={(author) => navigate(`/authors/${author.id}/edit`)}
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
