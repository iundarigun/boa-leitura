import {useEffect, useState} from "react";
import {useDialog} from "@/context/DialogContext.jsx";
import {getBestOfTheYear, patchBestOfTheYear} from "@/lib/api/bestOfTheYear.js";

export default function useBestOfTheYear(currentYear) {
  const [year, setYear] = useState(currentYear.toString());
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(false);

  const { showError } = useDialog();

  useEffect(() => {
    fetchBestOfTheYear();
  }, []);

  const fetchBestOfTheYear = async () => {
    setLoading(true);
    const { data, error } = await getBestOfTheYear(year);

    if (error) {
      showError(error);
    } else {
      setStats(data);
    }
    setLoading(false);
  };

  const updateBestOfTheYear = async (year, payload) => {
    setLoading(true);
    const { data, error } = await patchBestOfTheYear(year, payload);

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
    fetchBestOfTheYear,
    updateBestOfTheYear
  };
}