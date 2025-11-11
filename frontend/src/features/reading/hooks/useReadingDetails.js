import {useDialog} from "@/context/DialogContext.jsx";
import {useEffect, useRef, useState} from "react";
import {getReadingById} from "@/lib/api/readings.js";
import {exportImage} from "@/lib/exportImage.js";
import api from "@/lib/api.js";

export default function useReadingDetails(readingId) {
  const { showError, showSuccess } = useDialog();
  const [reading, setReading] = useState(null);
  const [loading, setLoading] = useState(false);
  const contentRef = useRef(null);
  const [exporting, setExporting] = useState(false);
  const [backgroundImage, setBackgroundImage] = useState("");
  const [theme, setTheme] = useState(null);

  const proxiedUrl = (url) => `${api.defaults.baseURL}/proxy-image?url=${encodeURIComponent(url)}`

  useEffect(() => {
    if (!open || !readingId) return;
    fetchReading();
    handleRefreshBackground();
  }, [open, readingId, showError]);

  const fetchReading = async () => {
    setLoading(true);
    const {data, error} = await getReadingById(readingId);
    setLoading(false);
    if (error) {
      showError(error);
      return;
    }
    setReading(data);
  };

  const handleExportImage = async ({onClose}) => {
    setExporting(true);
    const {data, error} = await exportImage(contentRef, reading?.book?.title)
    setExporting(false);
    if (data) {
      showSuccess(data);
      onClose(false);
    } else {
      showError(error);
    }
  }

  const getRandomInt = (max) => Math.floor(Math.random() * max);

  const handleRefreshBackground = () => {
    if (getRandomInt(2) === 0) {
      setBackgroundImage(`/assets/backgrounds/light/instagram-bg-${getRandomInt(9) + 1}.png`);
      setTheme(lightTheme);
    } else {
      setBackgroundImage(`/assets/backgrounds/dark/instagram-bg-${getRandomInt(6) + 1}.png`);
      setTheme(darkTheme);
    }
  }

  const lightTheme = {
    background: "rgb(255,255,255)",
    titleColor: "rgb(25,25,25)",
    subtitleColor: "rgb(60,60,60)",
    sagaColor: "rgb(70,70,70)",
    authorColor: "rgb(45,45,45)",
    origTitleColor: "rgb(60,60,60)",
    labelColor: "rgb(50,50,50)",
    textShadow: "1px 1px 2px rgba(0,0,0,0.1)",
    softShadow: "2px 2px 4px rgba(0,0,0,0.1)"
  };

  const darkTheme = {
    background: "rgb(25,25,25)",
    titleColor: "rgb(240,240,240)",
    subtitleColor: "rgb(200,200,200)",
    sagaColor: "rgb(220,220,220)",
    authorColor: "rgb(220,220,220)",
    origTitleColor: "rgb(210,210,210)",
    labelColor: "rgb(235,235,235)",
    textShadow: "1px 1px 3px rgba(0,0,0,0.7)",
    softShadow: "2px 2px 6px rgba(0,0,0,0.8)"
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
    theme
  }
}