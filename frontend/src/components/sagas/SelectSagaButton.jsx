import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import SagaTable from "./SagaTable"; // nossa tabela reutilizável
import {
  AlertDialog,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import api, { apiCall } from "../../lib/api";

export default function SelectSagaButton({ selectedSaga, onSelect }) {
  const [open, setOpen] = useState(false);
  const [sagas, setSagas] = useState({ content: [] });
  const [search, setSearch] = useState("");
  const [loading, setLoading] = useState(false);

  const fetchSagas = async (query = "") => {
    setLoading(true);
    const res = await apiCall(() => api.get(`/sagas?page=1&name=${query}`));
    if (res.data) {
      setSagas(res.data.content);
    } else {
      console.error(res.error);
    }
    setLoading(false);
  };

  const handleOpen = async () => {
    await fetchSagas("");
    setOpen(true);
  };

  const handleSelect = (saga) => {
    onSelect(saga);
    setOpen(false);
  };

  const handleSearch = async () => {
    await fetchSagas(search);
  };

  return (
    <>
      <div className="flex-1">
        <Label>Saga</Label>
        <Input
          value={selectedSaga ? selectedSaga.name : ""}
          disabled
          placeholder="Select Saga"
        />
      </div>
      <Button type="button" onClick={handleOpen}>
        Select Saga
      </Button>

      {open && (
        <AlertDialog open={open} onOpenChange={setOpen}>
          <AlertDialogContent className="max-w-4xl">
            <AlertDialogHeader>
              <AlertDialogTitle>Select Saga</AlertDialogTitle>
            </AlertDialogHeader>

            <div className="flex gap-2 mb-3">
              <Input
                placeholder="Search by name"
                value={search}
                onChange={(e) => setSearch(e.target.value)}
                onKeyDown={(e) => e.key === "Enter" && handleSearch()}
              />
              <Button onClick={handleSearch} disabled={loading}>
                {loading ? "Searching..." : "Search"}
              </Button>
            </div>

            <div className="space-y-2 max-h-80 overflow-y-auto">
              {loading ? (
                <p className="text-center text-gray-500">Loading sagas...</p>
              ) : (
                <SagaTable
                  sagas={sagas}
                  onSelect={handleSelect}
                  selectable
                />
              )}
            </div>

            <AlertDialogFooter>
              <AlertDialogCancel>Cancel</AlertDialogCancel>
            </AlertDialogFooter>
          </AlertDialogContent>
        </AlertDialog>
      )}
    </>
  );
}
