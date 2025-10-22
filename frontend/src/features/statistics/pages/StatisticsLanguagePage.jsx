import PieChartCard from "@/components/PieChartCard";
import { Card, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from "@/components/ui/select";
import { Button } from "@/components/ui/button";
import useStatisticsLanguage from "@/features/statistics/hooks/useStatisticsLanguage.js";
import {getLanguageDisplay} from "@/lib/languages.js";

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
          <div className="mt-6 grid gap-6 grid-cols-1 md:grid-cols-2">
            <PieChartCard
              title="Total by Language"
              data={Object.entries(stats.totalByLanguage).map(([k, v]) => ({
                name: k.toUpperCase(),
                value: v,
                label: getLanguageDisplay(k)
              }))}
            />

            <PieChartCard
              title="Original vs Translated"
              data={Object.entries(stats.originalPerTranslated).map(
                ([k, v]) => ({
                  name: k,
                  value: v,
                  label: k
                })
              )}
            />

            <PieChartCard
              title="Translated books by language"
              data={Object.entries(stats.totalByTranslated).map(
                ([k, v]) => ({
                  name: k.toUpperCase(),
                  value: v,
                  label: getLanguageDisplay(k)
                })
              )}
            />

            <PieChartCard
              title="Original books by language"
              data={Object.entries(stats.totalByOriginal).map(([k, v]) => ({
                name: k.toUpperCase(),
                value: v,
                label: getLanguageDisplay(k)
              }))}
            />
          </div>
        )}
      </Card>
    </div>
  );
}
