import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import AuthorForm from "../../components/authors/AuthorForm";
import { useDialog } from "../../context/DialogContext";
import api, { apiCall } from "../../lib/api";

const API_URL = "/authors";

export default function AuthorFormPage() {
  const [editingAuthor, setEditingAuthor] = useState(null);
  const [loading, setLoading] = useState(false);
  const { id } = useParams();
  const navigate = useNavigate();
  const { showError, showSuccess } = useDialog();

  useEffect(() => {
    fechAuthor();
  }, [id]);

  const fechAuthor = async () => {
    if (id) {
      setLoading(true);
      const res = await apiCall(() => api.get(`${API_URL}/${id}`));
      if (res.data) {
        setEditingAuthor(res.data);
      } else {
        showError("Not possible to load the author.");
      }
      setLoading(false);
    }
  }

  const handleSave = async (author) => {
    const res = id
     ? await apiCall(() => api.put(`${API_URL}/${id}`, author))
     : await apiCall(() => api.post(API_URL, author));
    
    if (res.data) {
      showSuccess(`Author ${id? "updated": "created"} successfully!`);
      const timer = setTimeout(() => {
        navigate("/authors");
      }, 2000);
      return () => clearTimeout(timer);
   } else {
      showError(res.error);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-lg mx-auto p-8">
        <CardHeader>
          <CardTitle className="text-2xl">
            {id ? "✏️ Edit Author" : "➕ New Author"}
          </CardTitle>
        </CardHeader>
        <CardContent>
          {loading ? (
            <p className="text-center text-gray-500">Loading author...</p>
          ) : (
          <AuthorForm
            onSave={handleSave}
            editingAuthor={editingAuthor}
            onCancel={() => navigate("/authors")}
            loading={loading}
          />
          )}
        </CardContent>
      </Card>
    </div>
  );
}
