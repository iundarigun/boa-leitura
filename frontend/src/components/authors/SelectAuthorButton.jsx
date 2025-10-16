import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import AuthorTable from "./AuthorTable"; // nossa tabela reutilizável
import {
  AlertDialog,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import api, { apiCall } from "../../lib/api";

export default function SelectAuthorButton({ selectedAuthor, onSelect }) {
  const [open, setOpen] = useState(false);
  const [authors, setAuthors] = useState({ content: [] });
  const [search, setSearch] = useState("");
  const [loading, setLoading] = useState(false);

  const fetchAuthors = async (query = "") => {
    setLoading(true);
    const res = await apiCall(() => api.get(`/authors?page=1&name=${query}`));
    if (res.data) {
      setAuthors(res.data);
    } else {
      console.error(res.error);
    }
    setLoading(false);
  };

  const handleOpen = async () => {
    await fetchAuthors("");
    setOpen(true);
  };

  const handleSelect = (author) => {
    onSelect(author);
    setOpen(false);
  };

  const handleSearch = async () => {
    await fetchAuthors(search);
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
