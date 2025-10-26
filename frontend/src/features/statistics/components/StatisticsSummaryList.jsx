import {useState} from "react";
import BookDetailsDialog from "@/features/books/components/BookDetailsDialog";
import StarRating from "@/components/StarRating.jsx";

export default function StatisticsSummaryList({stats}){
  const [bookDetailsOpen, setBookDetailsOpen] = useState(false);
  const [selectedBook, setSelectedBook] = useState(null);

  const handleBookView = (bookId) => {
    setSelectedBook(bookId);
    setBookDetailsOpen(true);
  };

  return (
    <>
      <div className="grid md:grid-cols-2 gap-4 text-center md:text-left">
        <div className="space-y-1">
          <p className="text-lg font-medium">
            Number of books:{" "}
            <span className="font-bold">{stats.amountOfTotalReading}</span>
          </p>
          <p className="text-lg font-medium">
            Total pages:{" "}
            <span className="font-bold">{stats.totalPages}</span>
          </p>
        </div>
        <div className="space-y-1">
          <p className="text-lg font-medium">
            Rereading:{" "}
            <span className="font-bold">{stats.amountOfRereading}</span>
          </p>
          <p className="text-lg font-medium">
            Average pages:{" "}
            <span className="font-bold">
                    {Math.round(stats.averagePages)}
                  </span>
          </p>
        </div>
        <div className="col-span-2 text-lg font-medium">
          Average rating:{" "}
          <span className="font-bold">
                  {stats.averageRating.toFixed(2)}
                </span>
        </div>
      </div>

      <div>
        <h3 className="text-xl font-semibold mb-2">
          ✨ Best books <StarRating value={stats.bestBooks.rating}/>
        </h3>
          <div className="grid grid-cols-2 sm:grid-cols-4 md:grid-cols-4 gap-3">
          {stats.bestBooks.bookList.map((book) => (
            <div
              key={book.id}
              className="flex flex-col items-center text-center"
            >
              <img
                src={book.urlImage}
                alt={book.title}
                className="w-28 h-40 object-cover rounded shadow cursor-pointer"
                onClick={() => handleBookView(book.id)}
              />
              <p className="text-xs mt-2 line-clamp-2">{book.title}</p>
            </div>
          ))}
        </div>
      </div>

      <div>
        <h3 className="text-xl font-semibold mb-2">
          💀 Worse books <StarRating value={stats.worseBooks.rating}/>
        </h3>
        <div className="grid grid-cols-2 sm:grid-cols-4 md:grid-cols-5 gap-3">
          {stats.worseBooks.bookList.map((book) => (
            <div
              key={book.id}
              className="flex flex-col items-center text-center"
            >
              <img
                src={book.urlImage}
                alt={book.title}
                className="w-28 h-40 object-cover rounded shadowcursor-pointer"
                onClick={() => handleBookView(book.id)}
              />
              <p className="text-xs mt-2 line-clamp-2">{book.title}</p>
            </div>
          ))}
        </div>
      </div>
      <BookDetailsDialog
        open={bookDetailsOpen}
        onClose={setBookDetailsOpen}
        bookId={selectedBook}
      />
    </>
  );
}