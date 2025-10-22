import {useState} from "react";
import {Button} from "@/components/ui/button";
import {Label} from "@/components/ui/label";
import {Input} from "@/components/ui/input";
import SagaTable from "@/features/sagas/components/SagaTable";
import {
  AlertDialog,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import useSagas from "@/features/sagas/hooks/useSagas.js";

export default function SelectSagaButton({selectedSaga, onSelect}) {
  const [open, setOpen] = useState(false);
  const {
    loading,
    sagas,
    search,
    setSearch,
    sortField,
    sortDir,
    handleSearch,
    handleSort,
  } = useSagas();

  const handleSelect = (saga) => {
    onSelect(saga);
    setOpen(false);
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
      <Button type="button" onClick={() => setOpen(true)}>
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
                  onSort={handleSort}
                  sortField={sortField}
                  sortDir={sortDir}
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
