import { useEffect, useState } from "react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { Checkbox } from "@/components/ui/checkbox";
import { Card } from "@/components/ui/card";
import {
  AlertDialog,
  AlertDialogContent,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogFooter,
  AlertDialogCancel,
} from "@/components/ui/alert-dialog";
import ImagePreviewButton from "@/components/ImagePreview";
import { useDialog } from "@/context/DialogContext";
import api, { apiCall } from "../../lib/api";
import SelectGenreButton from "../genres/SelectGenreButton";
import SelectAuthorButton from "../../features/authors/components/SelectAuthorButton";
import SelectSagaButton from "../sagas/SelectSagaButton";
import LanguageSelect from "../LanguageSelect";

export default function BookForm({ initialData = null, onSubmit, onCancel, loading = false }) {
  const [title, setTitle] = useState("");
  const [language, setLanguage] = useState("");
  const [numberOfPages, setNumberOfPages] = useState("");
  const [publisherYear, setPublisherYear] = useState("");
  const [origTitle, setOrigTitle] = useState("");
  const [origLanguage, setOrigLanguage] = useState("");
  const [isbn, setIsbn] = useState("");
  const [urlImage, setUrlImage] = useState("");
  const [urlImageSmall, setUrlImageSmall] = useState("");
  const [genre, setGenre] = useState(null);
  const [author, setAuthor] = useState(null);
  const [saga, setSaga] = useState(null);
  const [sagaOrder, setSagaOrder] = useState("");
  const [sagaMainTitle, setSagaMainTitle] = useState(false);
  const [fetching, setFetching] = useState(false);

  const [searchResults, setSearchResults] = useState([]);
  const [openResultsDialog, setOpenResultsDialog] = useState(false);
  const [fetchingByName, setFetchingByName] = useState(false);

  const { showError, showSuccess } = useDialog();
  
  useEffect(() => {
    if (initialData) {
      setTitle(initialData.title || "");
      setLanguage(initialData.language || "");
      setNumberOfPages(initialData.numberOfPages ?? "");
      setPublisherYear(initialData.publisherYear ?? "");
      setOrigTitle(initialData.originalEdition?.title || "");
      setOrigLanguage(initialData.originalEdition?.language || "");
      setIsbn(initialData.isbn || "");
      setUrlImage(initialData.urlImage || "");
      setUrlImageSmall(initialData.urlImageSmall || "");
      setGenre(initialData?.genre || null);
      setAuthor(initialData?.author || null);
      setSaga(initialData?.saga?.saga || null);
      setSagaOrder(initialData?.saga?.order ?? "");
      setSagaMainTitle(initialData?.saga?.mainTitle || false);
    }
  }, [initialData]);

  const fetchByISBN = async () => {
    if (!isbn.trim()) return;
    setFetching(true);
    const res = await apiCall(() => api.get(`/book-information?isbn=${isbn.trim()}`));
    setFetching(false);

    if (res.error) {
      showError(res.error);
      return;
    }

    if (!res.data || res.data.length === 0) {
      showError("No book found for this ISBN");
      return;
    }

    const book = res.data[0]; 
    setTitle(book.title || "");
    setNumberOfPages(book.numberOfPages ?? "");
    setPublisherYear(book.publisherYear ?? "");
    setUrlImage(book.urlImage || "");
    setUrlImageSmall(book.urlImageSmall || "");
    setAuthor(book.author || author);
    setLanguage(book.language || "");
  };

  const fetchByTitle = async () => {
    if (!title.trim()) return;
    setFetchingByName(true);
    const query = new URLSearchParams({
      title: title.trim()
    });
    if (author?.name) query.append("author", author.name.trim());

    const res = await apiCall(() => api.get(`/book-information?${query.toString()}`));
    setFetchingByName(false);

    if (res.error) {
      showError(res.error);
      return;
    }

    if (!res.data || res.data.length === 0) {
      showError("No book found for this title");
      return;
    }

    setSearchResults(res.data);
    setOpenResultsDialog(true);
  };

  const handleSelectBookResult = (book) => {
    setTitle(book.title || "");
    setNumberOfPages(book.numberOfPages ?? "");
    setPublisherYear(book.publisherYear ?? "");
    setUrlImage(book.urlImage || "");
    setUrlImageSmall(book.urlImageSmall || "");
    setAuthor(book.author || author);
    setLanguage(book.language || "");
    setIsbn(book.isbn || "");
    setOpenResultsDialog(false);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!title.trim()) {
      onSubmit && onSubmit(null, { validationError: "Title is required" });
      return;
    }

    const payload = {
      title: title.trim(),
      language: language.trim() || null,
      numberOfPages: numberOfPages === "" ? null : Number(numberOfPages),
      publisherYear: publisherYear === "" ? null : Number(publisherYear),
      originalEdition: {
        title: origTitle.trim() || null,
        language: origLanguage.trim() || null,
      },
      isbn: isbn.trim() || null,
      urlImage: urlImage.trim() || null,
      urlImageSmall: urlImageSmall.trim() || null,
      genreId: genre?.id || null,
      authorId: author?.id || null,
      saga: saga?.id ? {
        id: saga?.id,
        order: sagaOrder  === "" ? null : Number(sagaOrder),
        mainTitle: sagaMainTitle || null,
      } : null,
    };

    onSubmit && onSubmit(payload);
  };

  return (
      <form onSubmit={handleSubmit} className="space-y-4">
        <div className="flex gap-2 items-end">
          <div className="flex-1">
            <Label>Title</Label>
            <Input value={title} onChange={(e) => setTitle(e.target.value)} placeholder="Book title" />
          </div>
          <Button type="button" onClick={fetchByTitle} disabled={fetchingByName || !title.trim()}>
            {fetchingByName ? "Searching..." : "Search by Title"}
          </Button>
        </div>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div className="flex gap-2 items-end">
            <div className="flex-1">
              <Label>ISBN</Label>
              <Input value={isbn} onChange={(e) => setIsbn(e.target.value)} placeholder="ISBN" />
            </div>
            <Button type="button" onClick={fetchByISBN} disabled={fetching || !isbn.trim()}>
              {fetching ? "Fetching..." : "Fetch by ISBN"}
            </Button>
          </div>
          <div>
            <Label>Language</Label>
            <LanguageSelect
                language={language}
                setLanguage={setLanguage}/>
          </div>
          <div>
            <Label>Original Title</Label>
            <Input value={origTitle} onChange={(e) => setOrigTitle(e.target.value)} placeholder="Original title" />
          </div>
          <div>
            <Label>Original Language</Label>
            <LanguageSelect
              language={origLanguage}
              setLanguage={setOrigLanguage}/>
          </div>
          <div className="flex gap-2 items-end"> 
            <SelectAuthorButton selectedAuthor={author} onSelect={setAuthor}/>
          </div>
          <div className="flex gap-2 items-end"> 
              <SelectGenreButton selectedGenre={genre} onSelect={setGenre} />
          </div>
          <div>
            <Label>Pages</Label>
            <Input
              type="number"
              min="0"
              value={numberOfPages}
              onChange={(e) => setNumberOfPages(e.target.value)}
              placeholder="Number of pages"
            />
          </div>
            
          
          <div>
            <Label>Publisher Year</Label>
            <Input
              type="number"
              min="0"
              value={publisherYear}
              onChange={(e) => setPublisherYear(e.target.value)}
              placeholder="e.g. 2022"
            />
          </div>
        </div>


        <Card className="p-4 space-y-4">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div className="flex gap-2 items-end"> 
            <SelectSagaButton selectedSaga={saga} onSelect={setSaga}/>
          </div>
          <div className="flex gap-2 items-end">
            <Button type="button" onClick={() => setSaga(null)} disabled={!saga}>
              Clear selection
            </Button>
          </div>
        </div>
        { saga &&
          <>
          <div className="pt-2">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <Label>Order in the Saga</Label>
                <Input
                  type="number"
                  min="0"
                  step="0.1"
                  value={sagaOrder}
                  onChange={(e) => setSagaOrder(e.target.value)}
                  placeholder="2.5"
                />
              </div>    
              <div className="flex items-center space-x-2">
                <Checkbox
                  id="maintitle"
                  checked={sagaMainTitle}
                  onCheckedChange={(checked) => setSagaMainTitle(!!checked)}
                />
                <Label>Main title in the Saga</Label>
              </div>
            </div>
          </div>
          </> 
        }
        </Card> 
        <div className="space-y-4">
          <div>
            <Label>Image URL (large)</Label>
            <Input value={urlImage} onChange={(e) => setUrlImage(e.target.value)} placeholder="http://..." />
          </div>
          <div>
            <Label>Image URL (small)</Label>
            <Input
              value={urlImageSmall}
              onChange={(e) => setUrlImageSmall(e.target.value)}
              placeholder="http://..."
            />              
          </div>
          <div>
            <ImagePreviewButton urlSmall={urlImageSmall} urlLarge={urlImage} />
          </div>
        </div> 
      
        <div className="flex gap-2">
          <Button type="submit" disabled={loading}>
            {loading ? "Saving..." : "Save"}
          </Button>
          <Button type="button" variant="outline" onClick={onCancel}>
            Cancel
          </Button>
        </div>

        {openResultsDialog && (
          <AlertDialog open={openResultsDialog} onOpenChange={setOpenResultsDialog}>
            <AlertDialogContent className="max-w-3xl max-h-[80vh] overflow-y-auto">
              <AlertDialogHeader>
                <AlertDialogTitle>Select Book Information</AlertDialogTitle>
              </AlertDialogHeader>

              <div className="space-y-3">
                {searchResults.map((book, idx) => (
                  <Card
                    key={idx}
                    className="p-3 flex items-center gap-4 cursor-pointer hover:bg-gray-100 transition"
                    onClick={() => handleSelectBookResult(book)}
                  >
                    {/* Miniatura da capa */}
                    {book.urlImageSmall ? (
                      <img
                        src={book.urlImageSmall}
                        alt={book.title}
                        className="w-16 h-24 object-cover rounded-md border shadow-sm"
                      />
                    ) : (
                      <div className="w-16 h-24 bg-gray-200 flex items-center justify-center rounded-md text-gray-500 text-xs">
                        No Image
                      </div>
                    )}

                    {/* Informações do livro */}
                    <div className="flex-1">
                      <p className="font-bold text-lg">{book.title}</p>
                      <p className="text-sm text-gray-600">
                        {book.author?.name ? `Author: ${book.author.name}` : "Author: Unknown"}
                      </p>
                      <p className="text-sm text-gray-600">
                        ISBN: {book.isbn || "-"} | Pages: {book.numberOfPages ?? "-"} | Year:{" "}
                        {book.publisherYear ?? "-"}
                      </p>
                    </div>
                  </Card>
                ))}

                {searchResults.length === 0 && (
                  <p className="text-center text-gray-500 py-4">No books found.</p>
                )}
              </div>

              <AlertDialogFooter>
                <AlertDialogCancel>Close</AlertDialogCancel>
              </AlertDialogFooter>
            </AlertDialogContent>
          </AlertDialog>
        )}

      </form>
  );
}
