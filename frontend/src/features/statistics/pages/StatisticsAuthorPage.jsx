import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import useStatisticsAuthor from "@/features/statistics/hooks/useStatisticsAuthor.js";
import StatisticsAuthorList from "@/features/statistics/components/StatisticsAuthorList.jsx";
import StatisticFilter from "@/components/StatisticFilter.jsx";

export default function StatisticsAuthorPage() {
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
  } = useStatisticsAuthor(currentYear);

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-5xl mx-auto p-8 space-y-6">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-2xl">✍️ Author Statistics</CardTitle>
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
            <StatisticsAuthorList
              stats={stats}/>
          </CardContent>
        )}
      </Card>
    </div>
  );
}
