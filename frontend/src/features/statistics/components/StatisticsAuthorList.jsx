import PieChartCard from "@/components/PieChartCard.jsx";
import {getGenderDisplay} from "@/lib/gender.js";

export default function StatisticsAuthorList({stats}) {
  return (

    <>
      <div className="mt-6 grid gap-6 grid-cols-1 md:grid-cols-2">
        <PieChartCard
          title="Group readings by Author gender"
          data={Object.entries(stats.authorPerGender).map(([k, v]) => ({
            name: k.toUpperCase(),
            value: v,
            label: getGenderDisplay(k)
          }))}
        />
        <div className="space-y-6 mt-4">
          <p className="text-lg font-medium">
            Number of different authors:{" "}
            <span className="font-bold text-primary">{stats.totalDistinctAuthors}</span>
          </p>

          <div className="border rounded-lg p-4 shadow-sm bg-white">
            <h2 className="text-xl font-semibold text-gray-800 mb-3 border-b pb-2">
              🏆 Top Authors
            </h2>

            <table className="w-full text-left border-collapse text-sm">
              <thead className="bg-gray-100 text-gray-700 uppercase tracking-wide">
              <tr>
                <th className="p-3 w-2/3">Name</th>
                <th className="p-3 w-1/3 text-right">Amount</th>
              </tr>
              </thead>
              <tbody>
              {Object.entries(stats.topAuthors).map(([name, amount], index) => (
                <tr key={index} className="border-t hover:bg-gray-50">
                  <td className="p-3">{name}</td>
                  <td className="p-3 text-right font-medium">{amount}</td>
                </tr>
              ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div className="space-y-6 mt-4">
        <p className="text-lg font-medium">
          Number of new authors this year:{" "}
          <span className="font-bold text-primary">{Object.entries(stats.newAuthors).length}</span>
        </p>

        <div className="border rounded-lg p-4 shadow-sm bg-white">
          <h2 className="text-xl font-semibold text-gray-800 mb-3 border-b pb-2">
            🎉 New Authors
          </h2>

          <table className="w-full text-left border-collapse text-sm">
            <thead className="bg-gray-100 text-gray-700 uppercase tracking-wide">
            <tr>
              <th className="p-3 w-2/3">Name</th>
            </tr>
            </thead>
            <tbody>
            {Object.entries(stats.newAuthors).map(([name], index) => (
              <tr key={index} className="border-t hover:bg-gray-50">
                <td className="p-3">{name}</td>
              </tr>
            ))}
            </tbody>
          </table>
        </div>
      </div>
    </>
  );
}