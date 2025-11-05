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

  const proxiedUrl = (url) => `${api.defaults.baseURL}/proxy-image?url=${encodeURIComponent(url)}`

  useEffect(() => {
    if (!open || !readingId) return;
    fetchReading();
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

  return {
    reading,
    loading,
    contentRef,
    exporting,
    proxiedUrl,
    handleExportImage
  }
}