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


export default function AuthorList({ authors, onEdit, onDelete }) {;
  if (!authors || !authors.content) {
    return <p className="text-gray-500 text-center">No author found.</p>;
  }  
return (
    <div className="grid gap-4">
      {authors.content.map((author) => (
        <Card
          key={author.id}
          className="p-4 flex justify-between items-center"
        >
          
          <div>
            <p className="font-bold text-lg">{author.name}</p>
            <p className="text-sm text-gray-600">
              Gender: {author.gender} | Nationality: {author.nationality}
            </p>
          </div>

          {/* ações */}
          <div className="flex gap-2">
            <Button variant="outline" onClick={() => onEdit(author)}>
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
                    Are you sure you want delete <b>{author.name}</b>?  
                    This action can not be undone.
                  </AlertDialogDescription>
                </AlertDialogHeader>
                <AlertDialogFooter>
                  <AlertDialogCancel>Cancel</AlertDialogCancel>
                  <AlertDialogAction
                    className="bg-red-600 hover:bg-red-700"
                    onClick={() => onDelete(author.id, author.name)}
                  >
                    Delete
                  </AlertDialogAction>
                </AlertDialogFooter>
              </AlertDialogContent>
            </AlertDialog>
          </div>
        </Card>
      ))}
    </div>
  );
}
