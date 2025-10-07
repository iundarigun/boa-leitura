import { Card } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";

function GenreItem({ genre, onEdit, onDelete, level = 0 }) {
  return (
    <Card className="p-4 mb-2">
      <div className="flex justify-between items-center">
        <div className="flex-1" style={{ paddingLeft: `${level * 20}px` }}>
          <p className="font-semibold text-lg">{genre.name}</p>
        </div>

        <div className="flex gap-2">
          <Button variant="outline" onClick={() => onEdit(genre)}>
            Edit
          </Button>

          <AlertDialog>
            <AlertDialogTrigger asChild>
              <Button variant="destructive">Delete</Button>
            </AlertDialogTrigger>
            <AlertDialogContent>
              <AlertDialogHeader>
                <AlertDialogTitle>Confirm delete</AlertDialogTitle>
                <AlertDialogDescription>
                  Are you sure you want delete <b>{genre.name}</b>?  
                  This action can not be undone.
                </AlertDialogDescription>
              </AlertDialogHeader>
              <AlertDialogFooter>
                <AlertDialogCancel>Cancel</AlertDialogCancel>
                <AlertDialogAction
                  className="bg-red-600 hover:bg-red-700"
                  onClick={() => onDelete(genre.id, genre.name)}
                >
                  Delete
                </AlertDialogAction>
              </AlertDialogFooter>
            </AlertDialogContent>
          </AlertDialog>
        </div>
      </div>

      {genre.subGenres && genre.subGenres.length > 0 && (
        <div className="mt-2">
          {genre.subGenres.map((sub) => (
            <GenreItem
              key={sub.id}
              genre={sub}
              onEdit={onEdit}
              onDelete={onDelete}
              level={level + 1}
            />
          ))}
        </div>
      )}
    </Card>
  );
}

export default function GenreList({ genres, onEdit, onDelete }) {
  if (!genres || !genres.content || genres.content.length === 0) {
    return <p className="text-gray-500">No genre found.</p>;
  }

  return (
    <div className="space-y-2">
      {genres.content.map((genre) => (
        <GenreItem
          key={genre.id}
          genre={genre}
          onEdit={onEdit}
          onDelete={onDelete}
        />
      ))}
    </div>
  );
}
