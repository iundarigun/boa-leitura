import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import useStatisticsSummary from "@/features/statistics/hooks/useStatisticsSummary.js";
import StatisticsSummaryList from "@/features/statistics/components/StatisticsSummaryList.jsx";
import StatisticFilter from "@/components/StatisticFilter.jsx";

export default function StatisticsSummaryPage() {
  const date = new Date();
  date.setDate(date.getDate() - 30);
  const currentYear = date.getFullYear();
  const {
    year,
    setYear,
    excludeRereading,
    setExcludeRereading,
    loading,
    stats,
    fetchStatistics
  } = useStatisticsSummary(currentYear);

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-5xl mx-auto p-8 space-y-6">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-2xl">📊 Reading Summary</CardTitle>

          <StatisticFilter
            currentYear={currentYear}
            year={year}
            setYear={setYear}
            excludeRereading={excludeRereading}
            setExcludeRereading={setExcludeRereading}
            loading={loading}
            fetchStatistics={fetchStatistics}
          />
        </CardHeader>

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
