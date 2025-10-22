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
import StatisticsSummaryList from "@/features/statistics/components/StatisticsSummaryList.jsx";

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
            <StatisticsSummaryList
              stats={stats}/>
          </CardContent>
        )}
      </Card>
    </div>
  );
}
