import StarRating from "@/components/StarRating";

const months = [
  "january", "february", "march",
  "april", "may", "june",
  "july", "august", "september",
  "october", "november", "december"
];

const monthLabels = {
  january: "January",
  february: "February",
  march: "March",
  april: "April",
  may: "May",
  june: "June",
  july: "July",
  august: "August",
  september: "September",
  october: "October",
  november: "November",
  december: "December"
};

export default function BestOfTheYearList({ stats }) {
  // Organiza os meses em grupos de 3
  const rows = [];
  for (let i = 0; i < months.length; i += 3) {
    rows.push(months.slice(i, i + 3));
  }

  return (
    <div className="flex flex-col gap-6">
      {rows.map((row, rowIndex) => (
        <div key={rowIndex} className="flex gap-6 justify-between">
          {row.map((month) => {
            const monthData = stats[month];
            if (!monthData || !monthData.book) {
              return (
                <div
                  key={month}
                  className="flex-1 h-64 rounded-lg flex flex-col items-center justify-center"
                >
                  <span className="h-60 w-40 bg-gray-200 text-center text-gray-400">{monthLabels[month]}</span>
                </div>
              );
            }

            const { book, myRating } = monthData;

            return (
              <div
                key={month}
                className="flex-1 bg-white rounded-lg shadow p-2 flex flex-col items-center"
              >
                <img
                  src={book.urlImage || book.urlImageSmall}
                  alt={book.title}
                  className="h-60 object-cover rounded"
                />
                <h3 className="mt-2 text-sm font-semibold text-center">
                  {book.title}
                </h3>
                <StarRating value={myRating ?? 0} size={20} className="mt-1" />
              </div>
            );
          })}
        </div>
      ))}
    </div>
  );
}
