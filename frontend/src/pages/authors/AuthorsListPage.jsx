import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";
import AuthorList from "../../components/authors/AuthorList";
import Pagination from "../../components/Pagination";
import { useDialog } from "../../context/DialogContext";
import api, { apiCall } from "../../lib/api";

const API_URL = "/authors";

export default function AuthorsListPage() {
  const [authors, setAuthors] = useState({"content": []});
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [search, setSearch] = useState("");
  const [searchApplied, setSearchApplied] = useState("");

  const navigate = useNavigate();
  const { showError, showSuccess } = useDialog();

  useEffect(() => {
    fetchAuthors();
  }, [page, searchApplied]);

  const fetchAuthors = async () => {
    setLoading(true);
    const res = await apiCall(() => api.get(`${API_URL}?page=${page}&name=${search}`));
    if (!res.error) {
      setAuthors(res.data);
      setPage(res.data.page);
      setTotalPages(res.data.totalPages);
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

  const handleSearch = () => {
    setPage(1); 
    setSearchApplied(search);
  }; 

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-6xl mx-auto p-8">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-3xl">✍️ Authors</CardTitle>
          <Button onClick={() => navigate("/authors/new")}>+ New Author</Button>
        </CardHeader>
        <div className="flex space-x-2">
          <Label>Name</Label>
          <Input
            placeholder="Search by name"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && handleSearch()}
          />
          <Button onClick={handleSearch}>Search</Button>
        </div>
        <Pagination
          page={page}
          setPage={setPage}
          totalPages={totalPages}
          />
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
        <Pagination
          page={page}
          setPage={setPage}
          totalPages={totalPages}
          />
      </Card>
    </div>
  );
}
