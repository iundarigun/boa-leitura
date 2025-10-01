import { Link, Outlet, useLocation } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { cn } from "@/lib/utils"; // helper do shadcn/ui (se não tiver, explico como criar)

export default function Layout() {
  const location = useLocation();

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col">
      {/* Navbar */}
      <header className="bg-white border-b shadow-sm">
        <div className="max-w-6xl mx-auto px-6 py-4 flex items-center justify-between">
          <h1 className="text-xl font-bold text-gray-700">📚 MyApp</h1>

          <nav className="flex gap-4">
            <Button
              variant={location.pathname.startsWith("/authors") ? "default" : "outline"}
              asChild
            >
              <Link to="/authors">Autores</Link>
            </Button>
          </nav>
        </div>
      </header>

      {/* Conteúdo da rota */}
      <main className="flex-1 p-6">
        <Outlet />
      </main>
    </div>
  );
}
