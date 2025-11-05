import { useEffect, useRef, useState } from "react";
import {
  AlertDialog,
  AlertDialogContent,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogCancel,
} from "@/components/ui/alert-dialog";
import { Button } from "@/components/ui/button";
import { Star } from "lucide-react";
import html2canvas from "html2canvas-oklch";
import api, { apiCall } from "@/lib/api";
import { useDialog } from "@/context/DialogContext";
import StarRating from "@/components/StarRating"; // o componente que já tens

const API_URL = "/readings";

function FlagImage({ code, alt, size = 40 }) {
  if (!code) return null;
  const src = `/assets/languages/${code}.png`;
  return (
    <img
      src={src}
      alt={alt || code}
      width={size}
      height={size}
      className="object-cover rounded-sm"
      onError={(e) => {
        e.currentTarget.style.display = "none";
      }}
    />
  );
}

function PlatformImage({ platform, format, alt, size = "160px" }) {
  if (!platform) return null;

  let imageName = "";
  switch (platform) {
    case "OWN": {
      if (format === "EBOOK") {
        imageName = "ebook";
      } else {
        imageName = "printed-book"
      }
      break;
    }
    default: imageName = platform.toLowerCase();
  }

  const src = `/assets/platforms/${imageName}.png`;
  return (
    <img
      src={src}
      alt={alt || platform}
      width={size}
      height={size}
      className="object-contain"
      onError={(e) => (e.currentTarget.style.display = "none")}
    />
  );
}

/**
 * ReadingDetailsDialog
 * Props:
 *  - open (bool)
 *  - onClose (fn)
 *  - readingId (number)
 *  - onEdit (fn) optional
 *  - onDelete (fn) optional
 *  - onRefresh (fn) optional
 */
export default function ReadingDetailsDialog({
                                               open,
                                               onClose,
                                               readingId,
                                             }) {
  const { showError, showSuccess } = useDialog();
  const [reading, setReading] = useState(null);
  const [loading, setLoading] = useState(false);
  const contentRef = useRef(null);
  const [exporting, setExporting] = useState(false);

  const proxiedUrl = (url) => `http://localhost:1980/proxy-image?url=${encodeURIComponent(url)}`

  useEffect(() => {
    if (!open || !readingId) return;
    const load = async () => {
      setLoading(true);
      const res = await apiCall(() => api.get(`${API_URL}/${readingId}`));
      setLoading(false);
      if (res.error) {
        showError(res.error);
        return;
      }
      setReading(res.data);
    };
    load();
  }, [open, readingId, showError]);

  if (!open || !readingId) return null;

  if (loading) {
    return (
      <div className="p-4 text-center text-gray-500">Loading reading...</div>
    );
  }

  if (!reading) {
    return (
      <div className="p-4 text-center text-gray-500">No data available.</div>
    );
  }

  // extrai o book dentro do reading (segundo teu JSON)
  const { book } = reading;
  const bookInfo = book || {};
  const sagaName = bookInfo.saga?.saga?.name;
  const sagaOrder = bookInfo.saga?.order;
  const sagaDisplay =
    sagaName && sagaOrder ? `${sagaName} #${sagaOrder}` : sagaName || null;
  const title = bookInfo.title || "";
  const authorName = bookInfo.author?.name || "";
  const origTitle = bookInfo.originalEdition?.title || "";
  const rating = reading.myRating ?? reading.book?.readings?.[0]?.myRating ?? null;
  const readLanguage = reading.language || bookInfo.language;
  const platform = reading.platform;

  // Export to image handler:
  const handleExportImage = async () => {
    if (!contentRef.current) return;
    setExporting(true);
    try {
      // target dimensions (px)
      const targetW = 1533;
      const targetH = 2700;

      // get current element size
      const el = contentRef.current;
      const rect = el.getBoundingClientRect();
      const scale = Math.min(targetW / rect.width, targetH / rect.height);

      // temporarly apply background white and fixed size to make capture consistent
      const originalStyle = {
        width: el.style.width,
        height: el.style.height,
        transform: el.style.transform,
        background: el.style.background,
      };

      // set explicit pixel size for rendering (we scale via html2canvas option)
      el.style.width = `${Math.round(rect.width)}px`;
      el.style.height = `${Math.round(rect.height)}px`;
      el.style.background = "#ffffff";

      // html2canvas with scale to reach desired output resolution
      const canvas = await html2canvas(el, {
        scale: scale,
        useCORS: true, // tenta carregar imagens cross-origin (se permitido)
        allowTaint: true,
        logging: false,
        backgroundColor: null,
        // width/height not necessary since we use scale
      });

      // restore styles
      el.style.width = originalStyle.width;
      el.style.height = originalStyle.height;
      el.style.transform = originalStyle.transform;
      el.style.background = originalStyle.background;

      // create a full-sized canvas with exact target dims (center original)
      const finalCanvas = document.createElement("canvas");
      finalCanvas.width = targetW;
      finalCanvas.height = targetH;
      const ctx = finalCanvas.getContext("2d");

      // Fill white background
      ctx.fillStyle = "#ffffff";
      ctx.fillRect(0, 0, targetW, targetH);

      // compute placement: center the captured canvas in the final (or top-align)
      const dx = Math.round((targetW - canvas.width) / 2);
      const dy = Math.round((targetH - canvas.height) / 2);

      ctx.drawImage(canvas, dx, dy);

      const dataUrl = finalCanvas.toDataURL("image/png");
      // trigger download
      const a = document.createElement("a");
      a.href = dataUrl;
      a.download = `${(title || "reading").replace(/\s+/g, "_")}.png`;
      document.body.appendChild(a);
      a.click();
      a.remove();

      showSuccess("Image exported");
    } catch (err) {
      console.error(err);
      showError("Failed to export image. See console.");
    } finally {
      setExporting(false);
    }
  };

  return (
    <AlertDialog open={open} onOpenChange={onClose}>
      <AlertDialogContent className="max-w-2xl">
        <AlertDialogHeader>
          <AlertDialogTitle>
            Export {title}
          </AlertDialogTitle>
        </AlertDialogHeader>

        {/* content area we will capture */}
        <div
          ref={contentRef}
          className="relative bg-white shadow rounded-lg p-4 flex flex-col justify-between"
          style={{
            width: "383px",
            height: "675px",
          }}
        >
          {/* --- Parte superior --- */}
          <div>
            <div className="flex flex-col items-center text-center mb-4">
              <h1 className="text-2xl font-extrabold text-gray-800 tracking-wide uppercase">
                Reading Finished
              </h1>
              <h2 className="text-lg text-gray-500 font-medium mt-1">
                #{reading.positionInYear} · {reading.dateRead.substring(0, 4)}
              </h2>
            </div>
            <div className="flex flex-col">
              {sagaDisplay ? (
                <div className="text-sm text-gray-500 mb-2 italic">
                  {sagaDisplay}
                </div>
              ) : (
                <div className="text-sm font-semibold mb-2 text-center">
                  <br/>
                </div>
              )}

              <div className="w-full flex gap-6">
                <div className="flex flex-col items-center w-40">
                  {bookInfo.urlImage ? (
                    <img
                      src={proxiedUrl(bookInfo.urlImage)}
                      alt={title}
                      className="w-40 h-56 object-cover rounded shadow"
                    />
                  ) : (
                    <div className="w-40 h-56 bg-gray-200 rounded"/>
                  )}
                </div>

                <div className="flex-1 flex flex-col justify-between">
                  <div>
                    <h2 className="text-xl font-bold">{title}</h2>
                    <p className="text-sm text-gray-600 mt-1">{authorName}</p>
                  </div>
                  {origTitle && origTitle !== title && (
                    <div className="mt-6">
                      <p className="text-sm text-gray-500">
                        <strong>Original title:</strong><br/> {origTitle}
                      </p>
                    </div>
                  )}
                </div>
              </div>

              <div className="mt-6 flex flex-col items-center">
                <StarRating value={rating ?? 0} size={28}/>
              </div>
            </div>
          </div>

          {/* --- Parte inferior --- */}
          <div className="flex justify-around items-end mt-6">
            <FlagImage code={readLanguage} alt={readLanguage} size="60px"/>
            <PlatformImage platform={platform} alt={platform} size="160px"/>
          </div>
        </div>


        <AlertDialogFooter className="flex justify-end gap-3 mt-6">
          <Button variant="outline" size="sm" onClick={handleExportImage} disabled={exporting}>
            {exporting ? "Exporting..." : "Export image (IG)"}
          </Button>
          <AlertDialogCancel>Close</AlertDialogCancel>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
