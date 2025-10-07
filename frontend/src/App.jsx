import { BrowserRouter, Routes, Route } from "react-router-dom";

import Layout from "./components/Layout";
import AuthorsListPage from "./pages/authors/AuthorsListPage";
import AuthorFormPage from "./pages/authors/AuthorFormPage";
import GenresListPage from "./pages/genres/GenresListPage";
import GenreFormPage from "./pages/genres/GenreFormPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route path="/authors" element={<AuthorsListPage />} />
          <Route path="/genres" element={<GenresListPage />} />
          <Route path="/genres/new" element={<GenreFormPage />} />
          <Route path="/genres/:id/edit" element={<GenreFormPage />} />
          <Route path="/authors/new" element={<AuthorFormPage />} />
          <Route path="/authors/:id/edit" element={<AuthorFormPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
