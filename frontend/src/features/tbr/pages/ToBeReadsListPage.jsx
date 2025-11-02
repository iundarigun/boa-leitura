import {Button} from "@/components/ui/button";
import {Input} from "@/components/ui/input";
import {Card, CardHeader, CardTitle} from "@/components/ui/card";
import Pagination from "@/components/Pagination";
import useToBeReads from "@/features/tbr/hooks/useToBeReads.js";
import ToBeReadTable from "@/features/tbr/components/ToBeReadTable.jsx";

export default function ToBeReadsListPage() {
  const {
    toBeReads,
    loading,
    page,
    setPage,
    totalPages,
    filterKeyword,
    setFilterKeyword,
    sortField,
    sortDir,
    handleSearch,
    handleDelete,
    handleSort,
    handleEdit
  } = useToBeReads();

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-6xl mx-auto p-8">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-3xl">📖 To Be Read</CardTitle>
        </CardHeader>
        <div className="flex flex-wrap gap-3 items-center">
          <Input
            placeholder="Search by keyword..."
            value={filterKeyword}
            onChange={(e) => setFilterKeyword(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && handleSearch()}
            className="w-64"
          />
          <Button onClick={handleSearch}>Search</Button>
        </div>

        <Pagination
          page={page}
          setPage={setPage}
          totalPages={totalPages}
        />

        <ToBeReadTable
          toBeReads={toBeReads}
          loading={loading}
          onEdit={handleEdit}
          onDelete={handleDelete}
          sortField={sortField}
          sortDir={sortDir}
          onSort={handleSort}
        />

        <Pagination
          page={page}
          setPage={setPage}
          totalPages={totalPages}
        />
      </Card>
    </div>
  );
}
