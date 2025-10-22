import {useEffect, useState} from "react";
import {useDialog} from "@/context/DialogContext.jsx";
import {getSummary} from "@/lib/api/statistics.js";

export default function useStatisticsSummary(currentYear) {
  const [year, setYear] = useState(currentYear.toString());
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(false);

  const { showError } = useDialog();

  useEffect(() => {
    fetchStatistics();
  }, []);

  const fetchStatistics = async () => {
    setLoading(true);
    const { data, error } = await getSummary(year);

    if (error) {
      showError(error);
    } else {
      setStats(data);
    }
    setLoading(false);
  };

  return {
    year,
    setYear,
    loading,
    stats,
    fetchStatistics
  };
}