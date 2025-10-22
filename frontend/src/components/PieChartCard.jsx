import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { PieChart, Pie, Cell, Tooltip, Legend } from "recharts";

const COLORS = ["#2563eb", "#059669", "#f59e0b", "#dc2626", "#9333ea"];

export default function PieChartCard({ title, data }) {
  const total = data.reduce((acc, d) => acc + d.value, 0);

  const formattedData = data.map((d) => ({
    ...d,
    percent: ((d.value / total) * 100).toFixed(1),
  }));

  return (
    <Card className="w-full md:w-[95%] p-4">
      <CardHeader>
        <CardTitle>{title}</CardTitle>
      </CardHeader>

      <CardContent className="flex justify-center">
        <PieChart width={400} height={250}>
          <Pie
            data={formattedData}
            dataKey="value"
            nameKey="label"
            cx="50%"
            cy="50%"
            outerRadius={80}
            label={({ label, percent }) => `${label} (${percent}%)`}
          >
            {formattedData.map((entry, i) => (
              <Cell key={i} fill={COLORS[i % COLORS.length]} />
            ))}
          </Pie>
          <Tooltip
            formatter={(value, name, props) => [
              `${value} (${props.payload.percent}%)`,
              props.payload.label,
            ]}
          />
          <Legend />
        </PieChart>
      </CardContent>
    </Card>
  );
}
