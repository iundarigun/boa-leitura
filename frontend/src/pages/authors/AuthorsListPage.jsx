import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import AuthorList from "../../components/authors/AuthorList";
import { useDialog } from "../../context/DialogContext";
import api, { apiCall } from "../../lib/api";

const API_URL = "/authors";

export default function AuthorsListPage() {
  const [authors, setAuthors] = useState({"content": []});
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const { showError, showSuccess } = useDialog();

  useEffect(() => {
    fetchAuthors();
  }, []);

  const fetchAuthors = async () => {
    setLoading(true);
    const res = await apiCall(() => api.get(API_URL));
    if (!res.error) {
      setAuthors(res.data);
    }else { 
      showError(res.error);
    }
    setLoading(false);
  };

  const handleDelete = async (id, name) => {
    const res = await apiCall(() => api.delete(`${API_URL}/${id}`));
    if (!res.error) {
      fetchAuthors()
      showSuccess(`Author "${name}" deleted successfully.`);
    }
    else {
      showError(res.error);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-6xl mx-auto p-8">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-3xl">✍️ Authors</CardTitle>
          <Button onClick={() => navigate("/authors/new")}>+ New Author</Button>
        </CardHeader>
        <CardContent>
          {loading ? (
            <p className="text-center text-gray-500">Loading authors...</p>
          ) : (
            <AuthorList
              authors={authors}
              onEdit={(author) => navigate(`/authors/${author.id}/edit`)}
              onDelete={handleDelete}
            />
          )}
        </CardContent>
      </Card>
    </div>
  );
}
