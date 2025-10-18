import { BrowserRouter, Routes, Route } from "react-router-dom";

import Layout from "./components/Layout";
import AuthorsListPage from "./pages/authors/AuthorsListPage";
import AuthorFormPage from "./pages/authors/AuthorFormPage";
import GenresListPage from "./pages/genres/GenresListPage";
import GenreFormPage from "./pages/genres/GenreFormPage";
import SagasListPage from "./pages/sagas/SagasListPage";
import SagaFormPage from "./pages/sagas/SagaFormPage";
import BooksListPage from "./pages/books/BookListPage";
import BookFormPage from "./pages/books/BookFormPage";
import ReadingsListPage from "./pages/readings/ReadingsListPage";

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
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
