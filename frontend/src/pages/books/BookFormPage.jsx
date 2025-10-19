import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import api, { apiCall } from "@/lib/api";
import { useDialog } from "@/context/DialogContext";
import BookForm from "@/components/books/BookForm";

const API_URL = "/books";

export default function BookFormPage() {
  const { id } = useParams();
  const isEdit = Boolean(id);
  const navigate = useNavigate();
  const { showError, showSuccess } = useDialog();

  const [initialData, setInitialData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    if (isEdit) fetchBook();
  }, [id]);

  const fetchBook = async () => {
    setLoading(true);
    const { data, error } = await apiCall(() => api.get(`${API_URL}/${id}`));
    if (data) {
      setInitialData(data);
    }
    if (error) showError("Could not load the book.");
    setLoading(false);
  };

  const handleSubmit = async (payload, maybeError) => {
    // if component signalled a validation error
    if (maybeError?.validationError) {
      showError(maybeError.validationError);
      return;
    }

    // payload is already prepared by the form
    // later we will merge authorId/genreId/saga here before sending
    setSaving(true);
    const { error } = isEdit
      ? await apiCall(() => api.put(`${API_URL}/${id}`, payload))
      : await apiCall(() => api.post(API_URL, payload));

    if (!error) {
      showSuccess("Success", `Book ${isEdit ? "updated" : "created"} successfully.`);
      navigate("/books");
    } else {
      showError(error);
    }
    setSaving(false);
  };

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-3xl mx-auto p-8">
        <CardHeader>
          <CardTitle className="text-2xl">
            {isEdit ? "✏️ Edit Book" : "➕ New Book"}</CardTitle>
        </CardHeader>
        <CardContent>
          <BookForm
            initialData={initialData}
            onSubmit={handleSubmit}
            onCancel={() => navigate("/books")}
            loading={saving || loading}
          />
        </CardContent>
      </Card>
    </div>
  );
}
