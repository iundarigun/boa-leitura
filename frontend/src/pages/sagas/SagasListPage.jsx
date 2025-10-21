import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import SagaTable from "../../components/sagas/SagaTable";
import Pagination from "../../components/Pagination";
import SagaDetails from "../../components/sagas/SagaDetails";
import { useDialog } from "../../context/DialogContext";
import api, { apiCall } from "../../lib/api";

const API_URL = "/sagas";

export default function SagasListPage() {
  const [sagas, setSagas] = useState({ content: [] });
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [searchApplied, setSearchApplied] = useState("");

  const navigate = useNavigate();
  const { showError, showSuccess, showDialog } = useDialog();

  useEffect(() => {
    fetchSagas();
  }, [page, searchApplied]);

  const fetchSagas = async () => {
    setLoading(true);
    const res = await apiCall(() =>
      api.get(`${API_URL}?page=${page}&name=${encodeURIComponent(searchApplied)}`)
    );
    if (!res.error) {
      setSagas(res.data);
      setPage(res.data.page);
      setTotalPages(res.data.totalPages);
    } else {
      showError(res.error);
    }
    setLoading(false);
  };

  const handleDelete = async (saga) => {
    const res = await apiCall(() => api.delete(`${API_URL}/${saga.id}`));
    if (!res.error) {
      fetchSagas();
      showSuccess(`Saga "${saga.name}" deleted successfully.`);
    } else {
      showError(res.error);
    }
  };

  const handleSearch = () => {
    setPage(1);
    setSearchApplied(search);
  };

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-6xl mx-auto p-8">
        <CardHeader className="flex flex-row items-center justify-between mb-4">
          <CardTitle className="text-3xl">📚 Sagas</CardTitle>
          <Button onClick={() => navigate("/sagas/new")}>+ New saga</Button>
        </CardHeader>

        {/* 🔍 Pesquisa */}
        <div className="flex items-center gap-2 mb-4">
          <Label htmlFor="search" className="text-sm font-medium">
            Name
          </Label>
          <Input
            id="search"
            className="max-w-xs"
            placeholder="Search by name"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && handleSearch()}
          />
          <Button onClick={handleSearch}>Search</Button>
        </div>

        {/* 📖 Paginação topo */}
        <Pagination page={page} setPage={setPage} totalPages={totalPages} />

        <CardContent>
          {loading ? (
            <p className="text-center text-gray-500">Loading sagas...</p>
          ) : (
            <SagaTable
              sagas={sagas.content}
              onEdit={(saga) => navigate(`/sagas/${saga.id}/edit`)}
              onDelete={handleDelete}
            />
          )}
        </CardContent>

        {/* 📖 Paginação base */}
        <Pagination page={page} setPage={setPage} totalPages={totalPages} />
      </Card>
    </div>
  );
}
