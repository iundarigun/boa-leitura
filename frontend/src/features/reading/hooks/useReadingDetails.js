import { useDialog } from "@/context/DialogContext.jsx";
import { useEffect, useRef, useState } from "react";
import { getReadingById } from "@/lib/api/readings.js";
import { exportImage } from "@/lib/exportImage.js";
import api from "@/lib/api.js";

export default function useReadingDetails(readingId) {
  const { showError, showSuccess } = useDialog();
  const [reading, setReading] = useState(null);
  const [loading, setLoading] = useState(false);
  const contentRef = useRef(null);
  const [exporting, setExporting] = useState(false);
  const [backgroundImage, setBackgroundImage] = useState("");
  const [mode, setMode] = useState("light");

  const proxiedUrl = (url) =>
    `${api.defaults.baseURL}/proxy-image?url=${encodeURIComponent(url)}`;

  const themes = {
    light: {
      background: "rgb(255,255,255)",
      titleColor: "rgb(30,30,30)",
      subtitleColor: "rgb(60,60,60)",
      sagaColor: "rgb(70,70,70)",
      authorColor: "rgb(45,45,45)",
      origTitleColor: "rgb(60,60,60)",
      labelColor: "rgb(50,50,50)",
      softShadow: "2px 2px 4px rgba(0,0,0,0.1)",
      textShadow: "1px 1px 3px rgba(0,0,0,0.25)",
    },
    dark: {
      background: "rgb(10,10,10)",
      titleColor: "rgb(245,245,245)",
      subtitleColor: "rgb(220,220,220)",
      sagaColor: "rgb(200,200,200)",
      authorColor: "rgb(230,230,230)",
      origTitleColor: "rgb(220,220,220)",
      labelColor: "rgb(240,240,240)",
      softShadow: "2px 2px 4px rgba(0,0,0,0.5)",
      textShadow: "1px 1px 3px rgba(0,0,0,0.6)",
    },
  };

  const theme= () => themes[mode];

  useEffect(() => {
    if (!readingId) return;
    fetchReading();
    handleRefreshBackground();
  }, [readingId, mode]);

  const fetchReading = async () => {
    setLoading(true);
    const { data, error } = await getReadingById(readingId);
    setLoading(false);
    if (error) {
      showError(error);
      return;
    }
    setReading(data);
  };

  const handleExportImage = async ({ onClose }) => {
    setExporting(true);
    const { data, error } = await exportImage(contentRef, reading?.book?.title);
    setExporting(false);
    if (data) {
      showSuccess(data);
      onClose(false);
    } else {
      showError(error);
    }
  };

  const getRandomInt = (max) => Math.floor(Math.random() * max);

  const handleRefreshBackground = () => {
    if (mode === "light") {
      setBackgroundImage(`/assets/backgrounds/light/instagram-bg-${getRandomInt(9) + 1}.png`);
    } else {
      setBackgroundImage(`/assets/backgrounds/dark/instagram-bg-${getRandomInt(6) + 1}.png`);
    }
  };

  const toggleMode = () => {
    setMode((prev) => (prev === "light" ? "dark" : "light"));
  };

  return {
    reading,
    loading,
    contentRef,
    exporting,
    backgroundImage,
    proxiedUrl,
    handleExportImage,
    handleRefreshBackground,
    theme,
    mode,
    toggleMode,
  };
}
