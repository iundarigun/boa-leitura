import {Card, CardHeader, CardTitle} from "@/components/ui/card";
import useStatisticsLanguage from "@/features/statistics/hooks/useStatisticsLanguage.js";
import StatisticsLanguageList from "@/features/statistics/components/StatisticsLanguageList.jsx";
import StatisticFilter from "@/components/StatisticFilter.jsx";

export default function StatsLanguagePage() {
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
  } = useStatisticsLanguage(currentYear);

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-6xl p-6">
        <CardHeader className="flex flex-row justify-between items-center">
          <CardTitle className="text-2xl">📚 Language Statistics</CardTitle>

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
