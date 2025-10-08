import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import SagaForm from "../../components/sagas/SagaForm";
import { useDialog } from "../../context/DialogContext";
import api, { apiCall } from "../../lib/api";

const API_URL = "/sagas";

export default function SagaFormPage() {
  const [editingSaga, setEditingSaga] = useState(null);
  const [loading, setLoading] = useState(false);
  const { id } = useParams();
  const navigate = useNavigate();
  const { showError, showSuccess } = useDialog();

  useEffect(() => {
    fechSaga();
  }, [id]);

  const fechSaga = async () => {
    if (id) {
      setLoading(true);
      const res = await apiCall(() => api.get(`${API_URL}/${id}`));
      if (res.data) {
        setEditingSaga(res.data);
      } else {
        showError("Not possible to load the saga.");
      }
      setLoading(false);
    }
  }

  const handleSave = async (saga) => {
    if (!saga.name || !saga.name.trim()) {
      showError("Saga name is required.");
      return;
    }
    if (saga.totalMainTitles == null || saga.totalMainTitles < 0) {
      showError("Main titles must be positive.");
      return;
    }
    if (saga.totalComplementaryTitles == null || saga.totalComplementaryTitles < 0) {
      showError("Other titles must be positive.");
      return;
    }

    const res = id
     ? await apiCall(() => api.put(`${API_URL}/${id}`, saga))
     : await apiCall(() => api.post(API_URL, saga));
    
    if (res.data) {
      showSuccess(`Saga ${id? "updated": "created"} successfully!`);
      const timer = setTimeout(() => {
        navigate("/sagas");
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
            {id ? "✏️ Edit Saga" : "➕ New Saga"}
          </CardTitle>
        </CardHeader>
        <CardContent>
          {loading ? (
            <p className="text-center text-gray-500">Loading saga...</p>
          ) : (
          <SagaForm
            onSave={handleSave}
            editingSaga={editingSaga}
            onCancel={() => navigate("/sagas")}
            loading={loading}
          />
          )}
        </CardContent>
      </Card>
    </div>
  );
}
