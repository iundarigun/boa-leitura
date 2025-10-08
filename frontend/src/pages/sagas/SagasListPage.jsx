import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import SagaList from "../../components/sagas/SagaList";

import { useDialog } from "../../context/DialogContext";
import api, { apiCall } from "../../lib/api";

const API_URL = "/sagas";

export default function SagasListPage() {
  const [sagas, setSagas] = useState({"content": []});
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const { showError, showSuccess } = useDialog();

  useEffect(() => {
    fetchSagas();
  }, []);

  const fetchSagas = async () => {
    setLoading(true);
    const res = await apiCall(() => api.get(API_URL));
    if (!res.error) {
      setSagas(res.data);
    } else {
      showError(res.error);
    }
    setLoading(false);
  };

  const handleDelete = async (id, name) => {
    const res = await apiCall(() => api.delete(`${API_URL}/${id}`));
    if (!res.error) {
      fetchSagas()
      showSuccess(`Saga "${name}" deleted successfully.`);
    }
    else {
      showError(res.error);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-6xl mx-auto p-8">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-3xl">📚 Sagas</CardTitle>
          <Button onClick={() => navigate("/sagas/new")}>+ New saga</Button>
        </CardHeader>
        <CardContent>
          {loading ? (
            <p className="text-center text-gray-500">Loading sagas...</p>
          ) : (
            <SagaList
              sagas={sagas}
              onEdit={(saga) => navigate(`/sagas/${saga.id}/edit`)}
              onDelete={handleDelete}
            />
          )}
        </CardContent>
      </Card>      
    </div>
  );
}
