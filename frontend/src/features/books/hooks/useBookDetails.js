import {useEffect, useState} from "react";
import {useDialog} from "@/context/DialogContext.jsx";
import {getBookById} from "@/lib/api/books.js";
import {addToBeRead} from "@/lib/api/tbr.js";

export default function useBookDetails(bookId) {
  const [book, setBook] = useState(null);
  const [loading, setLoading] = useState(false);
  const { showError, showSuccess } = useDialog();

  useEffect(() => {
    if (!bookId) return;
    fetchBook();
  }, [bookId]);

  const fetchBook = async () => {
    setLoading(true);
    const {data, error} = await getBookById(bookId);
    setLoading(false);

    if (error) {
      showError(error);
      return;
    }
    setBook(data);
  };

  const handleAddToBeRead = async (book) => {
    const { error} = await addToBeRead({bookId: book.id});
    if (error) {
      showError(error);
      return;
    }
    setBook({...book, inTbr: true})
    showSuccess("Add to TBR sucessfully!");
  }

  const handleEdit = (book) => {
    window.location.href = `/books/${book.id}/edit`;
  };

  const handleNewReading = (book) => {
    window.location.href = `/readings/new/${book.id}`;
  };

  return {
    book,
    loading,
    handleEdit,
    handleNewReading,
    handleAddToBeRead
  };
}