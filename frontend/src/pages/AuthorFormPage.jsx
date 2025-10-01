import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import AuthorForm from "../components/AuthorForm";
import FeedbackDialog from "../components/FeedbackDialog";

const API_URL = "http://localhost:1980/authors";

export default function AuthorFormPage() {
  const [editingAuthor, setEditingAuthor] = useState(null);
  const { id } = useParams();
  const navigate = useNavigate();

  const [dialogOpen, setDialogOpen] = useState(false);
  const [dialogMessage, setDialogMessage] = useState({ title: "", description: "" });

  useEffect(() => {
    if (id) {
      axios
        .get(`${API_URL}/${id}`)
        .then((res) => setEditingAuthor(res.data))
        .catch(() => {
          setDialogMessage({
            title: "Erro",
            description: "Não foi possível carregar o autor.",
          });
          setDialogOpen(true);
        });
    }
  }, [id]);

  const handleSave = async (author) => {
    try{
      if (id) {
        await axios.put(`${API_URL}/${id}`, author);
        setDialogMessage({
            title: "Sucesso",
            description: "Autor atualizado com sucesso!",
            });
      } else {
        await axios.post(API_URL, author);
        setDialogMessage({
            title: "Sucesso",
            description: "Autor criado com sucesso!",
            });
      }
      setDialogOpen(true);

      const timer = setTimeout(() => {
        navigate("/authors");
      }, 2000);
      return () => clearTimeout(timer);
    } catch (err) {
      setDialogMessage({
        title: "Erro",
        description: "Não foi possível salvar o autor.",
      });
      setDialogOpen(true);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-lg mx-auto p-8">
        <CardHeader>
          <CardTitle className="text-2xl">
            {id ? "✏️ Editar Autor" : "➕ Novo Autor"}
          </CardTitle>
        </CardHeader>
        <CardContent>
          <AuthorForm
            onSave={handleSave}
            editingAuthor={editingAuthor}
            onCancel={() => navigate("/authors")}
          />
        </CardContent>
      </Card>

      <FeedbackDialog
        open={dialogOpen}
        onOpenChange={setDialogOpen}
        title={dialogMessage.title}
        description={dialogMessage.description}
      />
    </div>
  );
}
