import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import SagaList from "../../components/sagas/SagaList";
import Pagination from "../../components/Pagination";
import SagaDetails from "../../components/sagas/SagaDetails";

import { useDialog } from "../../context/DialogContext";
import api, { apiCall } from "../../lib/api";

const API_URL = "/sagas";

export default function SagasListPage() {
  const [sagas, setSagas] = useState({"content": []});
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const { showError, showSuccess, showDialog } = useDialog();

  useEffect(() => {
    fetchSagas();
  }, [page]);

  const fetchSagas = async () => {
    setLoading(true);
    const res = await apiCall(() => api.get(`${API_URL}?page=${page}`));
    if (!res.error) {
      setSagas(res.data);
      setPage(res.data.page);
      setTotalPages(res.data.totalPages);
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

  const handleView = async (saga) => {
    const res = await apiCall(() => api.get(`${API_URL}/${saga.id}`));
    if (res.error) {
      showError(res.error);
      return;
    }
    showDialog("Details", <SagaDetails saga={res.data} />)
  };

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-6xl mx-auto p-8">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-3xl">📚 Sagas</CardTitle>
          <Button onClick={() => navigate("/sagas/new")}>+ New saga</Button>
        </CardHeader>
        <Pagination
          page={page}
          setPage={setPage}
          totalPages={totalPages}
          />
        <CardContent>
          {loading ? (
            <p className="text-center text-gray-500">Loading sagas...</p>
          ) : (
            <SagaList
              sagas={sagas}
              onEdit={(saga) => navigate(`/sagas/${saga.id}/edit`)}
              onDelete={handleDelete}
              onView={handleView}
            />
          )}
        </CardContent>
        <Pagination
          page={page}
          setPage={setPage}
          totalPages={totalPages}
          />
      </Card>      
    </div>
  );
}
