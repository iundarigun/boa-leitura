import {
  AlertDialog,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import {Button} from "@/components/ui/button";
import StarRating from "@/components/StarRating";
import useReadingDetails from "@/features/reading/hooks/useReadingDetails.js";
import {getPlatformImage} from "@/lib/platform.js";


export default function ReadingDetailsDialog({ open, onClose, readingId }) {
  const {
    reading,
    loading,
    contentRef,
    exporting,
    backgroundImage,
    proxiedUrl,
    handleExportImage,
    handleRefreshBackground
  } = useReadingDetails(readingId);

  if (!open || !readingId) return null;

  if (loading)
    return (
      <div className="p-4 text-center text-gray-500">Loading reading...</div>
    );

  if (!reading)
    return (
      <div className="p-4 text-center text-gray-500">No data available.</div>
    );

  const { book } = reading;
  const bookInfo = book || {};
  const sagaName = bookInfo.saga?.saga?.name;
  const sagaOrder = bookInfo.saga?.order;
  const sagaDisplay =
    sagaName && sagaOrder ? `${sagaName} #${sagaOrder}` : sagaName || null;

  const title = bookInfo.title || "";
  const authorName = bookInfo.author?.name || "";
  const origTitle = bookInfo.originalEdition?.title || "";
  const rating =
    reading.myRating ?? reading.book?.readings?.[0]?.myRating ?? null;
  const readLanguage = reading.language || bookInfo.language;
  const languageImage = `/assets/languages/${readLanguage}.png`;
  const platform = reading.platform;
  const platformImage = getPlatformImage(platform, reading.format);
  const position = `#${reading.positionInYear} · ${reading.dateRead.substring(0, 4)}`;

  return (
    <AlertDialog open={open} onOpenChange={onClose}>
      <AlertDialogContent className="max-w-2xl justify-center items-center">
        <AlertDialogHeader>
          <AlertDialogTitle>Export {title}</AlertDialogTitle>
        </AlertDialogHeader>

        <div
          ref={contentRef}
          className="relative rounded-lg p-4 flex flex-col justify-between overflow-hidden"
          style={{
            width: "383px",
            height: "675px",
            backgroundColor: "rgb(255,255,255)",
          }}
        >
          <img
            id="bg-image"
            src={backgroundImage}
            alt="Background"
            className="absolute inset-0 w-full h-full object-cover"
          />

          <div className="relative z-10 flex flex-col justify-between h-full">
            {/* --- Top section --- */}
            <div>
              <div className="flex flex-col items-center text-center mb-4" />
              <div className="flex flex-col items-center text-center mb-4">
                <h1
                  style={{
                    color: "rgb(30,30,30)",
                    fontWeight: 800,
                    textTransform: "uppercase",
                    fontSize: "1.9rem",
                    textShadow: "2px 2px 4px rgba(0,0,0,0.1)",
                    letterSpacing: "0.05em",
                  }}
                >
                  Reading Finished
                </h1>
                <h2
                  style={{
                    color: "rgb(60,60,60)",
                    fontWeight: 600,
                    fontSize: "1.1rem",
                    marginTop: "0.25rem",
                    textShadow: "1px 1px 3px rgba(0,0,0,0.3)",
                  }}
                >
                  {position}
                </h2>
              </div>

              <div className="flex flex-col">
                {sagaDisplay ? (
                  <div
                    style={{
                      fontStyle: "italic",
                      color: "rgb(70,70,70)",
                      fontSize: "0.9rem",
                      marginBottom: "0.75rem",
                      textShadow: "1px 1px 2px rgba(0,0,0,0.25)",
                    }}
                  >
                    {sagaDisplay}
                  </div>
                ) : (
                  <div style={{ marginBottom: "0.75rem" }}>
                    <br />
                  </div>
                )}

                <div className="w-full flex gap-6">
                  <div className="flex flex-col items-center w-40">
                    {bookInfo.urlImage ? (
                      <img
                        src={proxiedUrl(bookInfo.urlImage)}
                        alt={title}
                        className="w-40 h-56 object-cover rounded"
                        style={{
                          boxShadow: "3px 3px 12px rgba(0,0,0,0.45)",
                        }}
                      />
                    ) : (
                      <div
                        style={{
                          width: "10rem",
                          height: "14rem",
                          backgroundColor: "rgb(230,230,230)",
                          borderRadius: "0.25rem",
                        }}
                      />
                    )}
                  </div>

                  <div className="flex-1 flex flex-col justify-between">
                    <div>
                      <h2
                        style={{
                          fontSize: "1.25rem",
                          fontWeight: 700,
                          color: "rgb(25,25,25)",
                          lineHeight: "1.3",
                          textShadow: "1px 1px 2px rgba(0,0,0,0.1)",
                        }}
                      >
                        {title}
                      </h2>
                      <p
                        style={{
                          fontSize: "1rem",
                          fontWeight: 500,
                          color: "rgb(45,45,45)",
                          marginTop: "0.25rem",
                          textShadow: "1px 1px 2px rgba(0,0,0,0.1)",
                        }}
                      >
                        {authorName}
                      </p>
                    </div>

                    {origTitle && origTitle !== title && (
                      <div style={{ marginTop: "1.5rem" }}>
                        <p
                          style={{
                            fontSize: "0.9rem",
                            color: "rgb(60,60,60)",
                            textShadow: "1px 1px 2px rgba(0,0,0,0.1)",
                          }}
                        >
                          <strong
                            style={{
                              fontWeight: 600,
                              color: "rgb(50,50,50)",
                              textShadow: "1px 1px 2px rgba(0,0,0,0.1)",
                            }}
                          >
                            Original title:
                          </strong>
                          <br />
                          {origTitle}
                        </p>
                      </div>
                    )}
                  </div>
                </div>

                <div className="mt-6 flex flex-col items-center">
                  <StarRating value={rating ?? 0} size={28} />
                </div>
              </div>
            </div>

            {/* --- Least section --- */}
            <div className="flex justify-around items-end mt-6 mb-8">
              <img
                src={languageImage}
                alt={readLanguage}
                width="60px"
                height="60px"
                className="object-cover rounded-sm"
                onError={(e) => (e.currentTarget.style.display = "none")}
              />
              <img
                src={platformImage}
                alt={platform}
                width="160px"
                // height="160px"
                className="object-contain  max-h-15"
                onError={(e) => (e.currentTarget.style.display = "none")}
              />

            </div>
          </div>
        </div>

        <AlertDialogFooter className="flex justify-end gap-3 mt-6">
          <Button
            variant="outline"
            size="sm"
            onClick={() => handleRefreshBackground()}
            alt="Refresh background"
          >
            Refresh
          </Button>
          <Button
            variant="outline"
            size="sm"
            onClick={() => handleExportImage({onClose})}
            disabled={exporting}
          >
            {exporting ? "Exporting..." : "Export image"}
          </Button>
          <AlertDialogCancel>Close</AlertDialogCancel>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
