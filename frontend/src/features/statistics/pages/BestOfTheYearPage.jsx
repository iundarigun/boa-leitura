import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import {Button} from "@/components/ui/button";
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue,} from "@/components/ui/select";
import useBestOfTheYear from "@/features/statistics/hooks/useBestOfTheYear.js";
import BestOfTheYearList from "@/features/statistics/components/BestOfTheYearList.jsx";

export default function BestOfTheYearPage() {
  const date = new Date();
  date.setDate(date.getDate() - 30);
  const currentYear = date.getFullYear();
  const {
    year,
    setYear,
    loading,
    stats,
    fetchBestOfTheYear,
    updateBestOfTheYear
  } = useBestOfTheYear(currentYear);

  const years = Array.from({length: currentYear - 1999}, (_, i) =>
    (currentYear - i).toString()
  );

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-5xl mx-auto p-8 space-y-6">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-2xl">🏆 Best of the year</CardTitle>

          <div className="flex gap-3 items-center">
            <Select value={year} onValueChange={setYear}>
              <SelectTrigger className="w-40">
                <SelectValue placeholder="Select year"/>
              </SelectTrigger>
              <SelectContent>
                {years.map((y) => (
                  <SelectItem key={y} value={y}>
                    {y}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>

            <Button onClick={fetchBestOfTheYear} disabled={loading}>
              {loading ? "Loading..." : "Load"}
            </Button>
          </div>
        </CardHeader>

        {stats && (
          <CardContent className="space-y-8">
            <BestOfTheYearList
              stats={stats}
              year={year}
              onSelect={updateBestOfTheYear}/>
          </CardContent>
        )}
      </Card>
    </div>
  );
}
