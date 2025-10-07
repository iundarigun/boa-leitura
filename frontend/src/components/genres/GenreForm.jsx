import { useState, useEffect } from "react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
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
import GenreList from "./GenreList";

export default function GenreForm({ onSave, editingGenre, onCancel, allGenres, loading }) {
  const [name, setName] = useState(null);
  const [parent, setParent] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);

  useEffect(() => {
    if (editingGenre) {
      setName(editingGenre.name);
      setParent(editingGenre.parent);
    }
  }, [editingGenre]);

  const handleSubmit = (e) => {
    e.preventDefault();
    onSave({ 
      name,
      parentGenreId: parent ? parent.id : null,
     });
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <label className="block text-sm font-medium mb-1">Name</label>
        <Input
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="Enter genre name"
        />
      </div>
      <div>
        <label className="block text-sm font-medium mb-1">Parent Genre</label>
        <div className="flex items-center gap-2">
          <Input
            readOnly
            value={parent ? parent.name : "No parent selected"}
          />
          <AlertDialog open={dialogOpen} onOpenChange={setDialogOpen}>
            <AlertDialogTrigger asChild>
              <Button type="button" variant="outline">
                Select Parent
              </Button>
            </AlertDialogTrigger>
            <AlertDialogContent className="max-w-lg">
              <AlertDialogHeader>
                <AlertDialogTitle>Select Parent Genre</AlertDialogTitle>
              </AlertDialogHeader>
              <div className="space-y-2 max-h-64 overflow-y-auto">
              <GenreList
                genres={allGenres}
                onSelect={(g) => {
                  setParent(g);
                  setDialogOpen(false);
                }}
              />
              </div>
              <div className="pt-2">
                <AlertDialogFooter>
                  <AlertDialogCancel>Cancel</AlertDialogCancel>
                  <AlertDialogAction
                    variant="outline"
                    onClick={() => {
                      setParent(null);
                      setDialogOpen(false);
                    }}
                  >
                    Clear Parent
                  </AlertDialogAction>
                </AlertDialogFooter>
              </div>
            </AlertDialogContent>
          </AlertDialog>
        </div>
      </div>
      <div className="flex gap-2">
        <Button type="submit" disabled={loading}>
          {loading ? "Saving..." : (editingGenre ? "Save" : "Add") }
        </Button>
        <Button type="button" variant="outline" onClick={onCancel}>
          Cancel
        </Button>
      </div>
    </form>
  );
}
