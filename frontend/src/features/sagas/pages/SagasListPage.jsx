import {useNavigate} from "react-router-dom";
import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import {Input} from "@/components/ui/input";
import {Label} from "@/components/ui/label";
import {Button} from "@/components/ui/button";
import Pagination from "@/components/Pagination";
import SagaTable from "@/features/sagas/components/SagaTable";
import useSagas from "@/features/sagas/hooks/useSagas.js";

export default function SagasListPage() {
  const {
    sagas,
    loading,
    page,
    setPage,
    totalPages,
    search,
    setSearch,
    sortField,
    sortDir,
    handleSearch,
    handleDelete,
    handleSort
  } = useSagas();

  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-6xl mx-auto p-8">
        <CardHeader className="flex flex-row items-center justify-between mb-4">
          <CardTitle className="text-3xl">📚 Sagas</CardTitle>
          <Button onClick={() => navigate("/sagas/new")}>+ New saga</Button>
        </CardHeader>

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

        <Pagination page={page} setPage={setPage} totalPages={totalPages} />

        <CardContent>
          {loading ? (
            <p className="text-center text-gray-500">Loading sagas...</p>
          ) : (
            <SagaTable
              sagas={sagas}
              onEdit={(saga) => navigate(`/sagas/${saga.id}/edit`)}
              onDelete={handleDelete}
              onSort={handleSort}
              sortField={sortField}
              sortDir={sortDir}
            />
          )}
        </CardContent>

        <Pagination page={page} setPage={setPage} totalPages={totalPages} />
      </Card>
    </div>
  );
}
