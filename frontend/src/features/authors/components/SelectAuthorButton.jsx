import {useState} from "react";
import {Button} from "@/components/ui/button";
import {Label} from "@/components/ui/label";
import {Input} from "@/components/ui/input";
import AuthorTable from "./AuthorTable";
import {
  AlertDialog,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import useAuthors from "@/features/authors/hooks/useAuthors.js";

export default function SelectAuthorButton({selectedAuthor, onSelect}) {
  const [open, setOpen] = useState(false);

  const {
    loading,
    authors,
    search,
    setSearch,
    sortField,
    sortDir,
    handleSearch,
    handleSort,
  } = useAuthors();

  const handleOpen = async () => {
    setOpen(true);
  };

  const handleSelect = (author) => {
    onSelect(author);
    setOpen(false);
  };

  return (
    <>
      <div className="flex-1">
        <Label>Author</Label>
        <Input
          value={selectedAuthor ? selectedAuthor.name : ""}
          disabled
          placeholder="Select Author"
        />
      </div>
      <Button type="button" onClick={handleOpen}>
        Select Author
      </Button>

      {open && (
        <AlertDialog open={open} onOpenChange={setOpen}>
          <AlertDialogContent className="max-w-4xl">
            <AlertDialogHeader>
              <AlertDialogTitle>Select Author</AlertDialogTitle>
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
                <p className="text-center text-gray-500">Loading authors...</p>
              ) : (
                <AuthorTable
                  authors={authors}
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
