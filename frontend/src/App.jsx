import { BrowserRouter, Routes, Route } from "react-router-dom";

import Layout from "@/components/Layout";
import AuthorsListPage from "@/features/authors/pages/AuthorsListPage";
import AuthorFormPage from "@/features/authors/pages/AuthorFormPage";
import GenresListPage from "@/features/genres/pages/GenresListPage";
import GenreFormPage from "@/features/genres/pages/GenreFormPage";
import SagasListPage from "@/features/sagas/pages/SagasListPage";
import SagaFormPage from "@/features/sagas/pages/SagaFormPage";
import BooksListPage from "@/features/books/pages/BookListPage";
import BookFormPage from "@/features/books/pages/BookFormPage";
import ReadingsListPage from "@/features/reading/pages/ReadingsListPage";
import ReadingFormPage from "@/features/reading/pages/ReadingFormPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route path="/authors" element={<AuthorsListPage />} />
          <Route path="/authors/new" element={<AuthorFormPage />} />
          <Route path="/authors/:id/edit" element={<AuthorFormPage />} />
          <Route path="/genres" element={<GenresListPage />} />
          <Route path="/genres/new" element={<GenreFormPage />} />
          <Route path="/genres/:id/edit" element={<GenreFormPage />} />
          <Route path="/sagas" element={<SagasListPage />} />
          <Route path="/sagas/new" element={<SagaFormPage />} />
          <Route path="/sagas/:id/edit" element={<SagaFormPage />} />
          <Route path="/books" element={<BooksListPage />} />
          <Route path="/books/new" element={<BookFormPage />} />
          <Route path="/books/:id/edit" element={<BookFormPage />} />
          <Route path="/readings" element={<ReadingsListPage />} />
          <Route path="/readings/:id/edit" element={<ReadingFormPage />} />
          <Route path="/readings/new/:bookId" element={<ReadingFormPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
