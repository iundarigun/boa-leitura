import { useState } from "react";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from "@/components/ui/select";
import useStatisticsSummary from "@/features/statistics/hooks/useStatisticsSummary.js";

export default function StatisticsSummaryPage() {
  const currentYear = new Date().getFullYear();
  const {
    year,
    setYear,
    loading,
    stats,
    fetchStatistics
  } = useStatisticsSummary(currentYear);

  const years = Array.from({ length: currentYear - 1999 }, (_, i) =>
    (currentYear - i).toString()
  );

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-5xl mx-auto p-8 space-y-6">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-2xl">📊 Reading Summary</CardTitle>
        </CardHeader>

        <div className="flex gap-3 items-center">
          <Select value={year} onValueChange={setYear}>
            <SelectTrigger className="w-40">
              <SelectValue placeholder="Select year" />
            </SelectTrigger>
            <SelectContent>
              {years.map((y) => (
                <SelectItem key={y} value={y}>
                  {y}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>

          <Button onClick={fetchStatistics} disabled={loading}>
            {loading ? "Loading..." : "Load"}
          </Button>
        </div>

        {stats && (
          <CardContent className="space-y-8">
            <div className="grid md:grid-cols-2 gap-4 text-center md:text-left">
              <div className="space-y-1">
                <p className="text-lg font-medium">
                  Number of books:{" "}
                  <span className="font-bold">{stats.amountOfTotalReading}</span>
                </p>
                <p className="text-lg font-medium">
                  Total pages:{" "}
                  <span className="font-bold">{stats.totalPages}</span>
                </p>
              </div>
              <div className="space-y-1">
                <p className="text-lg font-medium">
                  Rereading:{" "}
                  <span className="font-bold">{stats.amountOfRereading}</span>
                </p>
                <p className="text-lg font-medium">
                  Average pages:{" "}
                  <span className="font-bold">
                    {Math.round(stats.averagePages)}
                  </span>
                </p>
              </div>
              <div className="col-span-2 text-lg font-medium">
                Average rating:{" "}
                <span className="font-bold">
                  {stats.averageRating.toFixed(2)}
                </span>
              </div>
            </div>

            <div>
              <h3 className="text-xl font-semibold mb-2">
                ⭐ Best books (rating {stats.bestBooks.rating})
              </h3>
              <div className="grid grid-cols-2 sm:grid-cols-4 md:grid-cols-4 gap-3">
                {stats.bestBooks.bookList.map((book) => (
                  <div
                    key={book.id}
                    className="flex flex-col items-center text-center"
                  >
                    <img
                      src={book.urlImage}
                      alt={book.title}
                      className="w-28 h-40 object-cover rounded shadow"
                    />
                    <p className="text-xs mt-2 line-clamp-2">{book.title}</p>
                  </div>
                ))}
              </div>
            </div>

            <div>
              <h3 className="text-xl font-semibold mb-2">
                💀 Worse books (rating {stats.worseBooks.rating})
              </h3>
              <div className="grid grid-cols-2 sm:grid-cols-4 md:grid-cols-5 gap-3">
                {stats.worseBooks.bookList.map((book) => (
                  <div
                    key={book.id}
                    className="flex flex-col items-center text-center"
                  >
                    <img
                      src={book.urlImage}
                      alt={book.title}
                      className="w-28 h-40 object-cover rounded shadow"
                    />
                    <p className="text-xs mt-2 line-clamp-2">{book.title}</p>
                  </div>
                ))}
              </div>
            </div>
          </CardContent>
        )}
      </Card>
    </div>
  );
}
