import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import {Button} from "@/components/ui/button";
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue,} from "@/components/ui/select";
import useStatisticsAuthor from "@/features/statistics/hooks/useStatisticsAuthor.js";
import StatisticsAuthorList from "@/features/statistics/components/StatisticsAuthorList.jsx";

export default function StatisticsAuthorPage() {
  const currentYear = new Date().getFullYear();
  const {
    year,
    setYear,
    loading,
    stats,
    fetchStatistics
  } = useStatisticsAuthor(currentYear);

  const years = Array.from({ length: currentYear - 1999 }, (_, i) =>
    (currentYear - i).toString()
  );

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-5xl mx-auto p-8 space-y-6">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-2xl">✍️ Author Statistics</CardTitle>

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
        </CardHeader>

        {stats && (
          <CardContent className="space-y-8">
            <StatisticsAuthorList
              stats={stats}/>
          </CardContent>
        )}
      </Card>
    </div>
  );
}
