import {useEffect, useRef, useState} from "react";
import {Link, Outlet, useLocation} from "react-router-dom";
import {Button} from "@/components/ui/button";
import {useAuth} from "@/context/AuthContext.jsx";

export default function Layout() {
  const location = useLocation();
  const [openStats, setOpenStats] = useState(false);
  const [openUserStats, setOpenUserStats] = useState(false);
  const statsRef = useRef(null);
  const userStatsRef = useRef(null);
  const {token, logout} = useAuth();

  useEffect(() => {
    function handleClickOutside(e) {
      if (statsRef.current && !statsRef.current.contains(e.target)) {
        setOpenStats(false);
      }
      if (userStatsRef.current && !userStatsRef.current.contains(e.target)) {
        setOpenUserStats(false);
      }
    }

    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  const handleNavClick = () => setOpenStats(false);
  const handleNavUserClick = () => setOpenUserStats(false);

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col">
      <header className="bg-white border-b shadow-sm">
        <div className="max-w-6xl mx-auto px-6 py-4 flex items-center justify-between">
          <h1 className="text-xl font-bold text-gray-700">📖 Boa Leitura</h1>
          {token ? (
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
                variant={location.pathname.startsWith("/tbr") ? "default" : "outline"}
                asChild
              >
                <Link to="/tbr">TBR</Link>
              </Button>
              <div className="relative" ref={statsRef}>
                <Button
                  variant={location.pathname.startsWith("/statistics") ? "default" : "outline"}
                  onClick={() => setOpenStats((o) => !o)}
                >
                  Statistics ▾
                </Button>

                {openStats && (
                  <div className="absolute top-full mt-1 right-0 bg-white border rounded shadow-md z-50 w-48">
                    <Link
                      to="/statistics/summary"
                      className="block px-4 py-2 text-sm hover:bg-gray-100"
                      onClick={handleNavClick}
                    >
                      Summary
                    </Link>
                    <Link
                      to="/statistics/language"
                      className="block px-4 py-2 text-sm hover:bg-gray-100"
                      onClick={handleNavClick}
                    >
                      Language
                    </Link>
                    <Link
                      to="/statistics/author"
                      className="block px-4 py-2 text-sm hover:bg-gray-100"
                      onClick={handleNavClick}
                    >
                      Authors
                    </Link>
                    <Link
                      to="/statistics/mood"
                      className="block px-4 py-2 text-sm hover:bg-gray-100"
                      onClick={handleNavClick}
                    >
                      Mood
                    </Link>
                  </div>
                )}
              </div>
              <div className="relative" ref={userStatsRef}>
                <Button
                  variant={location.pathname.startsWith("/user") ? "default" : "outline"}
                  onClick={() => setOpenUserStats((o) => !o)}
                >
                  User ▾
                </Button>

                {openUserStats && (
                  <div className="absolute top-full mt-1 right-0 bg-white border rounded shadow-md z-50 w-48">
                    <Link
                      to="/user/preferences"
                      className="block px-4 py-2 text-sm hover:bg-gray-100"
                      onClick={handleNavUserClick}
                    >
                      Preferences
                    </Link>
                    <Link
                      to="/user/import"
                      className="block px-4 py-2 text-sm hover:bg-gray-100"
                      onClick={handleNavUserClick}
                    >
                      Goodreads Import
                    </Link>
                  </div>
                    )}
              </div>
              <Button variant="outline" onClick={logout}>
                Logout
              </Button>
            </nav>
          ) : (
            <Button  variant={location.pathname.startsWith("/login") ? "default" : "outline"}>
              <Link to="/login">Login</Link>
            </Button>
          )}
        </div>
      </header>

      <main className="flex-1 p-6">
        <Outlet/>
      </main>
    </div>
  );
}
