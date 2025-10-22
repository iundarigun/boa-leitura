import PieChartCard from "@/components/PieChartCard.jsx";
import {getLanguageDisplay} from "@/lib/languages.js";

export default function StatisticsLanguageList({stats}) {
  return (
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
  );
}