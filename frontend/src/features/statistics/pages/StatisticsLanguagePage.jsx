import {Card, CardHeader, CardTitle} from "@/components/ui/card";
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue,} from "@/components/ui/select";
import {Button} from "@/components/ui/button";
import useStatisticsLanguage from "@/features/statistics/hooks/useStatisticsLanguage.js";
import StatisticsLanguageList from "@/features/statistics/components/StatisticsLanguageList.jsx";

export default function StatsLanguagePage() {
  const currentYear = new Date().getFullYear();
  const {
    year,
    setYear,
    loading,
    stats,
    fetchStatistics
  } = useStatisticsLanguage(currentYear);

  const years = Array.from({ length: currentYear - 1999 }, (_, i) =>
    (currentYear - i).toString()
  );
  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-6xl p-6">
        <CardHeader className="flex flex-row justify-between items-center">
          <CardTitle className="text-2xl">📚 Language Statistics</CardTitle>

          <div className="flex gap-3 items-center">
            <Select value={String(year)} onValueChange={(v) => setYear(Number(v))}>
              <SelectTrigger className="w-36">
                <SelectValue placeholder="Year" />
              </SelectTrigger>
              <SelectContent>
                {years.map((y) => (
                  <SelectItem key={y} value={String(y)}>
                    {y}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>

            <Button onClick={fetchStatistics} disabled={loading}>
              {loading ? "Loading..." : "Load"}
            </Button>
          </div>
        </CardHeader>

        {!stats ? (
          <p className="text-center text-gray-500 mt-6">Select a year</p>
        ) : (
          <StatisticsLanguageList
            stats={stats}/>
        )}
      </Card>
    </div>
  );
}
