import { useEffect, useState } from "react";
import { Label } from "@/components/ui/label";
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from "@/components/ui/select";
import { Button } from "@/components/ui/button";

import { format as dateFormat } from "date-fns";

import { useDialog } from "@/context/DialogContext";
import LanguageSelect from "../LanguageSelect";
import DatePicker from "../DatePicker";
import StarRatingInput from "../StarRatingInput";
import { READING_PLATFORMS } from "../../lib/platform";
import { READING_FORMATS } from "../../lib/format";

export default function ReadingForm({ editingReading, onSubmit, onCancel, loading = false }) {
  const [myRating, setMyRating] = useState("");
  const [language, setLanguage] = useState("");
  const [platform, setPlatform] = useState("");
  const [format, setFormat] = useState("");
  const [dateRead, setDateRead] = useState(null);
  const [bookId, setBookId] = useState(null);
  const [book, setBook] = useState(null);

  const { showError, showSuccess } = useDialog();

  useEffect(() => {
    if (editingReading) {
      setMyRating(editingReading.myRating || "");
      setLanguage(editingReading.language || "");
      setPlatform(editingReading.platform || "");
      setFormat(editingReading.format || "");
      setDateRead(editingReading.dateRead || null);
      setBookId(editingReading.book?.id || null);
      setBook(editingReading.book || null);
    }
  }, [editingReading]);

  const handleSubmit = (e) => {
    e.preventDefault();

    const payload = {
      bookId: bookId,
      myRating: myRating === "" ? null : Number(myRating),
      dateRead: dateRead ? dateFormat(dateRead, "yyyy-MM-dd") : null,
      format: format || null,
      platform: platform || null,
      language: language 
    };

    onSubmit && onSubmit(payload);
  }

  return (
    <>
    <div className="flex flex-col md:flex-row gap-4 mt-4">
      <div>
        {book?.urlImage? (
        <img
          src={book.urlImage}
          alt={book.title}
          className="w-40 h-56 object-cover rounded shadow"
        />
        ): (
          <div className="w-40 h-56 bg-gray-200 rounded" />
        )}
        <p><strong>Title:</strong> {book?.title}</p>
        <p><strong>Author:</strong> {book?.author?.name}</p>
      </div>
      <div className="flex-1 space-y-2 text-sm">
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="flex gap-2 items-end">
              <div className="flex-1">
                <DatePicker label="Read Date" date={dateRead} setDate={setDateRead} />
              </div>
              <div className="flex-1">
                <Label>Language</Label>
                <LanguageSelect
                    language={language}
                    setLanguage={setLanguage}/>
              </div>
          </div>
          <div className="flex-1">
            <Label>My rating</Label>
            <StarRatingInput value={myRating} onChange={setMyRating} />
          </div>
          <div className="flex-1">
            <Label>Format</Label>
            <Select
              value={format}
              onValueChange={setFormat}>
              <SelectTrigger>
                <SelectValue placeholder="Select Format" />
              </SelectTrigger>
              <SelectContent>
                {READING_FORMATS.map((plat) => (
                  <SelectItem key={plat.code} value={plat.code}>
                    {plat.label}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
          <div className="flex-1">
            <Label>Platform</Label>
            <Select
              value={platform}
              onValueChange={setPlatform}>
              <SelectTrigger>
                <SelectValue placeholder="Select Platform" />
              </SelectTrigger>
              <SelectContent>
                {READING_PLATFORMS.map((plat) => (
                  <SelectItem key={plat.code} value={plat.code}>
                    {plat.label}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
           <div className="flex gap-2">
              <Button type="submit" disabled={loading}>
                {loading ? "Saving..." : "Save"}
              </Button>
              <Button type="button" variant="outline" onClick={onCancel}>
                Cancel
              </Button>
           </div>
        </form>
      </div>
    </div>
    </>
  );

}