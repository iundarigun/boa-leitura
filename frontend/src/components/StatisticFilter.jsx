import {Checkbox} from "@/components/ui/checkbox.jsx";
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue} from "@/components/ui/select.jsx";
import {Button} from "@/components/ui/button.jsx";

export default function StatisticFilter({currentYear, year, setYear, excludeRereading, setExcludeRereading, fetchStatistics, loading}) {
  const years = Array.from({ length: currentYear - 1999 }, (_, i) =>
    (currentYear - i).toString()
  );

  return (
    <div className="flex gap-3 items-center">
      <div className="flex-1 items-center space-x-2">
        <Checkbox
          id="done"
          checked={excludeRereading}
          onCheckedChange={(checked) => setExcludeRereading(!!checked)}
        />
        <label htmlFor="done" className="text-sm font-medium">
          Exclude Rereading
        </label>
      </div>
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
  )
}