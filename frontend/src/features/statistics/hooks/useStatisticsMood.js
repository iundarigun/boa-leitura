import {useEffect, useState} from "react";
import {getMood} from "@/lib/api/statistics.js";
import {useDialog} from "@/context/DialogContext.jsx";

export default function useStatisticsMood(currentYear) {
  const [year, setYear] = useState(currentYear);
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(false);

  const { showError } = useDialog();

  useEffect(() => {
    fetchStatistics();
  }, []);

  const fetchStatistics = async () => {
    setLoading(true);
    const {data, error} = await getMood(year);
    if (error) {
      showError(error);
    }
    else {
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