import {
  AlertDialog,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import {Link} from "react-router-dom";
import {Button} from "@/components/ui/button";
import {getLanguageDisplay} from "@/lib/languages";
import {getReadingFormatDisplay} from "@/lib/format";
import {getReadingPlatformsDisplay} from "@/lib/platform";
import useBookDetails from "@/features/books/hooks/useBookDetails.js";
import TableActionButtons from "@/components/TableActionButtons.jsx";

export default function BookDetailsDialog({open, onClose, bookId, onDelete}) {
  const {
    book,
    loading,
    handleNewReading,
    handleEdit,
  } = useBookDetails(bookId);

  if (!open || !bookId) return null;

  if (loading) {
    return (
      <div className="p-4 text-center text-gray-500">
        Loading book details...
      </div>
    );
  }

  if (!book) {
    return (
      <div className="p-4 text-center text-gray-500">
        No book data available.
      </div>
    );
  }

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
          {urlImage ? (
            <img
              src={urlImage}
              alt={title}
              className="w-40 h-56 object-cover rounded shadow"
            />
          ) : (
            <div className="w-40 h-56 bg-gray-200 rounded"/>
          )}
          <div className="flex-1 space-y-2 text-sm">
            <p>
              <strong>Author:</strong> {author?.id ? (
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
              {saga?.order && !saga?.mainTitle ? `(Book #${saga.order})` : ""}
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
        {book.readings && book.readings.length > 0 &&
          <div className="border rounded-lg space-y-2 text-sm">
            <table className="w-full text-left border-collapse">
              <thead className="bg-gray-100">
              <tr>
                <th className="p-3 ">Language</th>
                <th className="p-3 ">Format</th>
                <th className="p-3 ">Platform</th>
                <th className="p-3 ">Date read</th>
                <th className="p-3 "></th>
              </tr>
              </thead>
              <tbody>
              {book.readings.map((reading) => (
                <tr key={reading.id} className="border-t hover:bg-gray-50 ">
                  <td className="p-3">{getLanguageDisplay(reading.language)}</td>
                  <td className="p-3">{getReadingFormatDisplay(reading.format)}</td>
                  <td className="p-3">{getReadingPlatformsDisplay(reading.platform)}</td>
                  <td className="p-3">{reading.dateRead}</td>
                  <td className="p-3 text-center">
                    <a href={`/readings/${reading.id}/edit`} className="cursor-pointer">✏️</a>
                  </td>
                </tr>
              ))}
              </tbody>
            </table>
          </div>
        }

        <AlertDialogFooter className="flex justify-end gap-3 mt-6">
          <Button variant="outline" size="sm" onClick={() => handleNewReading(book)}>
            New reading
          </Button>
          <TableActionButtons
            entity={book}
            onEdit={handleEdit}
            onDelete={onDelete}
            warningProperty="title"
          />
          <AlertDialogCancel>Close</AlertDialogCancel>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
