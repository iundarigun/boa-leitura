import {
  AlertDialog,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import ReadingTable from "@/features/reading/components/ReadingTable.jsx";
import useSelectReadingDialog from "@/features/reading/hooks/useSelectReadingDialog.js";
import {useEffect} from "react";

export default function SelectReadingDialog({open, onClose, year, month, onSelect}) {
  const {
    readings,
    loading,
    sortField,
    sortDir,
    handleSort,
    fetchReadings
  } = useSelectReadingDialog(year, month);

  useEffect(() => {
    if (open) fetchReadings();
  }, [open, year, month]);

  const handleSelect = (reading) => {
    onSelect(reading);
    onClose();
  };

  return (
    <>
      <AlertDialog open={open} onOpenChange={onClose}>
        <AlertDialogContent className="sm:max-w-4xl">
          <AlertDialogHeader>
            <AlertDialogTitle>Select Reading {month}-{year}</AlertDialogTitle>
          </AlertDialogHeader>
          <div className="space-y-2 max-h-80 overflow-y-auto">
            {loading ? (
              <p className="text-center text-gray-500">Loading readings...</p>
            ) : (
              <ReadingTable
                readings={readings}
                loading={loading}
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
    </>
  );
}
