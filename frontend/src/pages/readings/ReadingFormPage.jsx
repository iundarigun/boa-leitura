import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import ReadingForm from "../../components/readings/ReadingForm";
import { useDialog } from "../../context/DialogContext";
import api, { apiCall } from "../../lib/api";

const API_URL = "/readings";

export default function ReadingFormPage() {
  const [editingReading, setEditingReading] = useState(null);
  const [loading, setLoading] = useState(false);
  const { id } = useParams();
  const { bookId } = useParams();
  const navigate = useNavigate();
  const { showError, showSuccess } = useDialog();

  useEffect(() => {
    fechReading();
  }, [id, bookId]);

  const fechReading = async () => {
    setLoading(true);
    if (id) {
      const res = await apiCall(() => api.get(`${API_URL}/${id}`));
      if (res.data) {
        setEditingReading(res.data);
      } else {
        showError("Not possible to load the reading.");
      }
      setLoading(false);
    } else if (bookId) {
      const res = await apiCall(() => api.get(`/books/${bookId}`));
      if (res.data) {
        setEditingReading({"book": res.data});
      } else {
        showError("Not possible to load the book.");
      }
      setLoading(false);
    }
  }

  const handleSave = async (reading) => {
    if (!reading.dateRead || !reading.dateRead.trim()) {
      showError("Read date is required.");
      return;
    }

    const res = id
     ? await apiCall(() => api.put(`${API_URL}/${id}`, reading))
     : await apiCall(() => api.post(API_URL, reading));
    
    if (res.data) {
      showSuccess(`Reading ${id? "updated": "created"} successfully!`);
      const timer = setTimeout(() => {
        navigate("/reading");
      }, 2000);
      return () => clearTimeout(timer);
   } else {
      showError(res.error);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-3xl mx-auto p-8">
        <CardHeader>
          <CardTitle className="text-2xl">
            {id ? "✏️ Edit Reading" : "➕ New Reading"}
          </CardTitle>
        </CardHeader>
        <CardContent>
          {loading || !editingReading ? (
            <p className="text-center text-gray-500">Loading reading...</p>
          ) : (
            <>
            <ReadingForm
              onSubmit={handleSave}
              editingReading={editingReading}
              onCancel={() => navigate("/readings")}
              loading={loading}
            />
          </>
          )}
        </CardContent>
      </Card>
    </div>
  );
}
