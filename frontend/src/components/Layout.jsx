import { Link, Outlet, useLocation } from "react-router-dom";
import { Button } from "@/components/ui/button";

export default function Layout() {
  const location = useLocation();

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col">
      {/* Navbar */}
      <header className="bg-white border-b shadow-sm">
        <div className="max-w-6xl mx-auto px-6 py-4 flex items-center justify-between">
          <h1 className="text-xl font-bold text-gray-700">📖 Boa Leitura</h1>

          <nav className="flex gap-4">
            <Button
              variant={location.pathname.startsWith("/authors") ? "default" : "outline"}
              asChild
            >
              <Link to="/authors">Authors</Link>
            </Button>
            <Button
              variant={location.pathname.startsWith("/genres") ? "default" : "outline"}
              asChild
            >
              <Link to="/genres">Genres</Link>
            </Button>
            <Button
              variant={location.pathname.startsWith("/sagas") ? "default" : "outline"}
              asChild
            >
              <Link to="/sagas">Sagas</Link>
            </Button>
            <Button
              variant={location.pathname.startsWith("/books") ? "default" : "outline"}
              asChild
            >
              <Link to="/books">Books</Link>
            </Button>
            <Button
              variant={location.pathname.startsWith("/readings") ? "default" : "outline"}
              asChild
            >
              <Link to="/readings">Readings</Link>
            </Button>
            <Button
              variant={location.pathname.startsWith("/statistics") ? "default" : "outline"}
              asChild
            >
              <Link to="/statistics/summary">Statistics</Link>
            </Button>
          </nav>
        </div>
      </header>

      {/* route contains */}
      <main className="flex-1 p-6">
        <Outlet />
      </main>
    </div>
  );
}
