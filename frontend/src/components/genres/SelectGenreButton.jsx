import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import GenreList from "./GenreList"; 
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";
import api, { apiCall } from "../../lib/api";

export default function SelectGenreButton({ selectedGenre, onSelect }) {
  const [open, setOpen] = useState(false);
  const [allGenres, setAllGenres] = useState([])

  const handleSelect = (genre) => {
    onSelect(genre);
    setOpen(false);
  };

  const clickButton = async () => {
    const res = await apiCall(() => api.get(`/genres`));
    if (res.data) {
      setAllGenres(res.data);
    } else {
       console.log(res.error);
    }
    setOpen(true);
  };

  return (
    <>
      <div className="flex-1">
        <Label>Genre</Label>
        <Input value={selectedGenre ? selectedGenre.name : "Select Genre"} 
               disabled={true} placeholder="Select the genre" />
      </div>
      <Button type="button" onClick={clickButton}>
        Select Genre
      </Button>

      {open && (
        <AlertDialog open={open} onOpenChange={setOpen}>
            <AlertDialogTrigger asChild>
              <Button type="button" variant="outline">
                Select Genre
              </Button>
            </AlertDialogTrigger>
            <AlertDialogContent className="max-w-lg">
              <AlertDialogHeader>
                <AlertDialogTitle>Select Genre</AlertDialogTitle>
              </AlertDialogHeader>
              <div className="space-y-2 max-h-64 overflow-y-auto">
              <GenreList
                genres={allGenres}
                onSelect={handleSelect}
              />
              </div>
              <div className="pt-2">
                <AlertDialogFooter>
                  <AlertDialogCancel>Cancel</AlertDialogCancel>
                </AlertDialogFooter>
              </div>
            </AlertDialogContent>
          </AlertDialog>
      )}
    </>
  );
}