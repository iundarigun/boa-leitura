import PieChartCard from "@/components/PieChartCard.jsx";
import {getReadingFormatDisplay} from "@/lib/format.js";

export default function StatisticsMoodList({stats}) {
  return (
    <>
      <div className="mt-6 grid gap-6 grid-cols-1 md:grid-cols-2">
        <PieChartCard
          title="Total by Format"
          data={Object.entries(stats.totalByFormat).map(([k, v]) => ({
            name: k.toUpperCase(),
            value: v,
            label: getReadingFormatDisplay(k)
          }))}
        />

        <PieChartCard
          title="Total by Origin"
          data={Object.entries(stats.totalByOrigin).map(
            ([k, v]) => ({
              name: k,
              value: v,
              label: k
            })
          )}
        />
      </div>
      <div className="mt-6 grid gap-6 grid-cols-1">
        <PieChartCard
          title="Group by book size"
          data={Object.entries(stats.totalByPageNumber).map(
            ([k, v]) => ({
              name: k,
              value: v,
              label: k
            })
          )}
        />
      </div>
    </>
  );
}