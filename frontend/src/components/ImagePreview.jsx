import { useState } from "react";
import { Button } from "@/components/ui/button";
import { 
  AlertDialog, 
  AlertDialogContent, 
  AlertDialogHeader, 
  AlertDialogTitle, 
  AlertDialogTrigger, 
  AlertDialogFooter, 
  AlertDialogCancel, 
} from "@/components/ui/alert-dialog";

export default function ImagePreviewButton({ urlSmall, urlLarge }) {
  const [previewType, setPreviewType] = useState("small");

  const imageUrl = previewType === "small" ? urlSmall : urlLarge;

  if (!urlSmall && !urlLarge) return null;

  return (
    <AlertDialog>
      <AlertDialogTrigger asChild>
        <Button size="sm">Preview</Button>
      </AlertDialogTrigger>
      <AlertDialogContent className="max-w-lg">
        <AlertDialogHeader>
          <AlertDialogTitle>Image Preview</AlertDialogTitle>
        </AlertDialogHeader>
        <div className="flex flex-col items-center gap-2">
          <div className="flex gap-2">
            { urlSmall ? ( 
              <Button size="sm" variant={previewType === "small" ? "default" : "outline"} type="button" onClick={() => setPreviewType("small")}>
              Small
            </Button>
            ) : (
              ""
            )}
            { urlLarge ? (
            <Button size="sm" variant={previewType === "large" ? "default" : "outline"} type="button" onClick={() => setPreviewType("large")}>
              Large
            </Button>
            ) : (
              ""
            )}
          </div>
          {imageUrl && (
            <img src={imageUrl} alt="Preview" className="max-h-96 object-contain rounded-md mt-2" />
          )}
        </div>
        <AlertDialogFooter>
          <AlertDialogCancel>Close</AlertDialogCancel>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
