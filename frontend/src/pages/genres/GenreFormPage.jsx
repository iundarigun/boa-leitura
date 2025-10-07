import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import api, { apiCall } from "../../lib/api";
import { useDialog } from "../../context/DialogContext";
import GenreForm from "../../components/genres/GenreForm";

const API_URL = "/genres";

export default function GenreFormPage() {
  const [editingGenre, setEditingGenre] = useState(null);
  const [loading, setLoading] = useState(false);
  const [allGenres, setAllGenres] = useState([]);
  const { id } = useParams();
  const navigate = useNavigate();
  
  const { showError, showSuccess } = useDialog();

  // Load genre when editing
  useEffect(() => {
    fetchAllGenres();
    fetchGenre();
  }, [id]);

  const fetchGenre = async () => {
    if (id) {
      setLoading(true);
      const res = await apiCall(() => api.get(`${API_URL}/${id}`));
      if (res.data) {
        setEditingGenre(res.data);
      } else {
        showError(res.error);
      }
      setLoading(false);
    }
  };
  
  const fetchAllGenres = async () => {
    const res = await apiCall(() => api.get(API_URL));
    if (res.data) {
      setAllGenres(res.data.content);
    } else {
       showError(res.error);
    }
  };

  const handleSave = async (genre) => {
    if (!genre.name && !genre.name.trim()) {
      showError("Genre name is required.");
      return;
    }
    setLoading(true);
    const res = id
      ? await apiCall(() => api.put(`${API_URL}/${id}`, genre))
      : await apiCall(() => api.post(API_URL, genre));

    if (res.data) {
      showSuccess(`Genre ${id ? "updated" : "created"} successfully.`);
      const timer = setTimeout(() => {
        navigate("/genres");
      }, 2000);
      return () => clearTimeout(timer);
    } else {
      showError(res.error);
    }
    setLoading(false);
  };

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-2xl mx-auto p-8">
        <CardHeader>
          <CardTitle className="text-2xl">
            {id ? "✏️ Edit Genre" : "➕ New Genre"}
          </CardTitle>
        </CardHeader>
        <CardContent>
          <GenreForm
            onSave={handleSave}
            editingGenre={editingGenre}
            onCancel={() => navigate("/genres")}
            allGenres={allGenres}
            loading={loading}
          />
        </CardContent>
      </Card>
    </div>
  );
}
