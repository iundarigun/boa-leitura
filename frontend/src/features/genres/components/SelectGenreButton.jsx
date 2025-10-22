import {useState} from "react";
import {Button} from "@/components/ui/button";
import {Label} from "@/components/ui/label";
import {Input} from "@/components/ui/input";
import GenreList from "@/features/genres/components/GenreList";
import {
  AlertDialog,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";
import useGenres from "@/features/genres/hooks/useGenres.js";

export default function SelectGenreButton({selectedGenre, onSelect}) {
  const [open, setOpen] = useState(false);

  const {
    genres,
    loading
  } = useGenres();

  const handleSelect = (genre) => {
    onSelect(genre);
    setOpen(false);
  };

  return (
    <>
      <div className="flex-1">
        <Label>Genre</Label>
        <Input value={selectedGenre ? selectedGenre.name : "Select Genre"}
               disabled={true} placeholder="Select the genre"/>
      </div>
      <Button type="button" onClick={() => setOpen(true)}>
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
              {loading ? (
                <p className="text-center text-gray-500">Loading authors...</p>
              ) : (<GenreList
                  genres={genres}
                  onSelect={handleSelect}
                />
              )}
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