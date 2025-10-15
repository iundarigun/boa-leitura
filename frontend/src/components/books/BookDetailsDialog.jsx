import {
  AlertDialog,
  AlertDialogContent,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogCancel,
  AlertDialogAction,
} from "@/components/ui/alert-dialog";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";

export default function BookDetailsDialog({ open, onClose, book, onEdit, onDelete }) {
  if (!book) return null;

  const {
    title,
    author,
    genre,
    saga,
    isbn,
    read,
    numberOfPages,
    publisherYear,
    urlImage,
    originalEdition,
  } = book;

  return (
    <AlertDialog open={open} onOpenChange={onClose}>
      <AlertDialogContent className="max-w-2xl">
        <AlertDialogHeader>
          <AlertDialogTitle>{title}</AlertDialogTitle>
          <AlertDialogDescription>
            {author?.name} — {genre?.name || "Unknown genre"}
          </AlertDialogDescription>
        </AlertDialogHeader>

        <div className="flex flex-col md:flex-row gap-4 mt-4">
          {urlImage? (
          <img
            src={urlImage}
            alt={title}
            className="w-40 h-56 object-cover rounded shadow"
          />
          ): (
            <div className="w-40 h-56 bg-gray-200 rounded" />
          )}
          <div className="flex-1 space-y-2 text-sm">
            <p>
              <strong>Author:</strong>  {author?.id ? (
                <Link
                  to={`/authors/${author.id}/edit`}
                  className="text-blue-600 hover:underline"
                >
                  {author.name}
                </Link>
              ) : (
                "-"
              )}
            </p>
            <p>
              <strong>Genre:</strong> {genre?.id ? (
                <Link
                  to={`/genres/${genre.id}/edit`}
                  className="text-blue-600 hover:underline"
                >
                  {genre.name}
                </Link>
              ) : (
                "-"
              )}
            </p>
            <p>
              <strong>Saga:</strong>{" "}
              {saga?.saga?.id ? (
                <Link
                  to={`/sagas/${saga.saga.id}/edit`}
                  className="text-blue-600 hover:underline"
                >
                  {saga.saga.name}
                </Link>
              ) : (
                "-"
              )}
              {saga?.order && saga?.mainTitle ? ` (Book #${saga.order} of ${saga.saga.totalMainTitles})` : ""}
              {saga?.order && !saga?.mainTitle ? `(Part of)` : ""}
            </p>
            <p>
              <strong>Pages:</strong> {numberOfPages || "-"}
            </p>
            <p>
              <strong>Year:</strong> {publisherYear || "-"}
            </p>
            <p>
              <strong>ISBN:</strong> {isbn || "-"}
            </p>
            <p>
              <strong>Original Edition:</strong>{" "}
              {originalEdition?.title
                ? `${originalEdition.title} (${originalEdition.language})`
                : "-"}
            </p>
            <p>
              <strong>Status:</strong> {read ? "✅ Read" : "❌ Not read"}
            </p>
          </div>
        </div>

        <AlertDialogFooter className="flex justify-end gap-3 mt-6">
          <AlertDialogCancel asChild>
            <Button variant="outline" onClick={() => onEdit(book)}>
              Edit
            </Button>
          </AlertDialogCancel>
          <AlertDialog>
            <AlertDialogTrigger asChild>
              <Button variant="destructive">Delete</Button>
            </AlertDialogTrigger>
            <AlertDialogContent>
              <AlertDialogHeader>
                <AlertDialogTitle>Confirm delete</AlertDialogTitle>
                <AlertDialogDescription>
                  Are you sure you want delete <b>{title}</b>?  
                  This action can not be undone.
                </AlertDialogDescription>
              </AlertDialogHeader>
              <AlertDialogFooter>
                <AlertDialogCancel>Cancel</AlertDialogCancel>
                <AlertDialogAction
                  className="bg-red-600 hover:bg-red-700"
                  onClick={() => onDelete(book)}
                >
                  Delete
                </AlertDialogAction>
              </AlertDialogFooter>
            </AlertDialogContent>
          </AlertDialog>
          <AlertDialogCancel>Close</AlertDialogCancel>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
