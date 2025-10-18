import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from "@/components/ui/select";
import Pagination from "../../components/Pagination";
import ReadingTable from "../../components/readings/ReadingTable";
import BookDetailsDialog from "../../components/books/BookDetailsDialog";
import { useDialog } from "../../context/DialogContext";
import api, { apiCall } from "../../lib/api";
import DatePicker from "../../components/DatePicker";
import { format } from "date-fns";

const API_URL = "/readings";

export default function ReadingsListPage() {
  const [readings, setReadings] = useState({"content": []});
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading] = useState(false);

  const [filterKeyword, setFilterKeyword] = useState("");
  const [filterDateFrom, setFilterDateFrom] = useState(null);
  const [filterDateTo, setFilterDateTo] = useState(null);
  const [sortField, setSortField] = useState("DATE_READ");
  const [sortDir, setSortDir] = useState("desc");
  const [searchApplied, setSearchApplied] = useState("");
  
  const navigate = useNavigate();
  const { showError, showSuccess } = useDialog();

  const loadReadings = async () => {
    setLoading(true);
    setSearchApplied("");
    const query = new URLSearchParams({
      page: page.toString()
    });
    if (sortField) query.append("order", sortField);
    if (sortDir) query.append("directionAsc", sortDir === "asc")
    if (filterKeyword) query.append("keyword", filterKeyword);
    if (filterDateFrom) query.append("dateFrom", format(filterDateFrom, "yyyy-MM-dd"));
    if (filterDateTo) query.append("dateTo", format(filterDateTo, "yyyy-MM-dd"));

    const res = await apiCall(() => api.get(`${API_URL}?${query.toString()}`));

    if (!res.error) {
      setReadings(res.data.content);
      setPage(res.data.page);
      setTotalPages(res.data.totalPages);
    } else {
      showError(res.error);      
    }
    setLoading(false);
  };

  useEffect(() => {
    loadReadings();
  }, [page, sortField, sortDir, searchApplied]);

  const handleSearch = () => {
    setPage(1);
    setSearchApplied("search")
  };

  const handleSort = (field) => {
    if (sortField === field) {
      setSortDir((prev) => (prev === "asc" ? "desc" : "asc"));
    } else {
      setSortField(field);
      setSortDir("asc");
    }
  };

  const handleEdit = (reading) => {
    window.location.href = `/readings/${reading.id}/edit`;
  };

  const handleDelete = async (reading) => {
    const res = await apiCall(() => api.delete(`${API_URL}/${reading.id}`));
    if (res.error) {
      showError(res.error);
      return;
    }
    showSuccess(`Reading of "${reading.book.title}" on ${reading.dateRead} deleted.`);
    loadReadings();
  };

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-6xl mx-auto p-8">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-3xl">📖 Readings</CardTitle>
          <Button onClick={() => navigate("/readings/new")}>+ New Reading</Button>
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
          sortField={sortField}
          sortDir={sortDir}
          onSort={handleSort}
        />

        <Pagination
          page={page}
          setPage={setPage}
          totalPages={totalPages}
          />
        
        {/* <BookDetailsDialog
          open={bookDetailsOpen}
          onClose={setBookDetailsOpen}
          book={selectedBook}
          onEdit={handleEdit}
          onDelete={handleDelete}
        /> */}
      </Card>
    </div>
  );
}
