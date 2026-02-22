import {useEffect, useState} from "react";
import {useDialog} from "@/context/DialogContext.jsx";
import {getAuthor} from "@/lib/api/statistics.js";

export default function useStatisticsAuthor(currentYear) {
  const [year, setYear] = useState(currentYear.toString());
  const [excludeRereading, setExcludeRereading] = useState(false);
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(false);

  const { showError } = useDialog();

  useEffect(() => {
    fetchStatistics();
  }, []);

  const fetchStatistics = async () => {
    setLoading(true);
    const { data, error } = await getAuthor({year: year, excludeRereading: excludeRereading});

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
    excludeRereading,
    setExcludeRereading,
    loading,
    stats,
    fetchStatistics
  };
}