import { BrowserRouter, Routes, Route } from "react-router-dom";

import Layout from "./components/Layout";
import AuthorsListPage from "./pages/authors/AuthorsListPage";
import AuthorFormPage from "./pages/authors/AuthorFormPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route path="/authors" element={<AuthorsListPage />} />
          <Route path="/authors/new" element={<AuthorFormPage />} />
          <Route path="/authors/:id/edit" element={<AuthorFormPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
