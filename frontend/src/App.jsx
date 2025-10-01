import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import AuthorsPage from "./pages/AuthorsPage";

export default function App() {
  return (
    <Router>
      <div className="p-6 max-w-lg mx-auto">
        {/* Menu de navegação */}
        <nav className="flex gap-4 mb-6">
          <Link to="/authors" className="text-blue-600 hover:underline">
            Autores
          </Link>
        </nav>

        {/* Definição das rotas */}
        <Routes>
          <Route path="/authors" element={<AuthorsPage />} />
        </Routes>
      </div>
    </Router>
  );
}