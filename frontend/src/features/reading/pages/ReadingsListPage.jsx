import {Button} from "@/components/ui/button";
import {Input} from "@/components/ui/input";
import {Card, CardHeader, CardTitle} from "@/components/ui/card";
import Pagination from "@/components/Pagination";
import ReadingTable from "@/features/reading/components/ReadingTable";
import DatePicker from "@/components/DatePicker";
import useReadings from "@/features/reading/hooks/useReadings.js";

export default function ReadingsListPage() {
  const {
    readings,
    loading,
    page,
    setPage,
    totalPages,
    filterKeyword,
    setFilterKeyword,
    filterDateTo,
    setFilterDateTo,
    filterDateFrom,
    setFilterDateFrom,
    sortField,
    sortDir,
    handleSearch,
    handleDelete,
    handleSort,
    handleEdit
  } = useReadings();

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-6xl mx-auto p-8">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-3xl">📖 Readings</CardTitle>
        </CardHeader>
        <div className="flex flex-wrap gap-3 items-center">
        <Input
          placeholder="Search by keyword..."
          value={filterKeyword}
          onChange={(e) => setFilterKeyword(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && handleSearch()}
          className="w-64"
        />
        <p>From:</p><DatePicker date={filterDateFrom} setDate={setFilterDateFrom} />
        <p>To:</p><DatePicker date={filterDateTo} setDate={setFilterDateTo} />
        <Button onClick={handleSearch}>Search</Button>
        </div>

        <Pagination
          page={page}
          setPage={setPage}
          totalPages={totalPages}
          />

        <ReadingTable
          readings={readings}
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
