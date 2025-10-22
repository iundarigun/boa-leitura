import {useEffect, useState} from "react";
import {Label} from "@/components/ui/label";
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue,} from "@/components/ui/select";
import {Button} from "@/components/ui/button";

import {format as dateFormat} from "date-fns";
import LanguageSelect from "@/components/LanguageSelect";
import DatePicker from "@/components/DatePicker";
import StarRatingInput from "@/components/StarRatingInput";
import {READING_PLATFORMS} from "@/lib/platform";
import {READING_FORMATS} from "@/lib/format";

export default function ReadingForm({ editingReading = null, onSubmit, onCancel, loading = false }) {
  const [myRating, setMyRating] = useState("");
  const [language, setLanguage] = useState("");
  const [platform, setPlatform] = useState("");
  const [format, setFormat] = useState("");
  const [dateRead, setDateRead] = useState(null);
  const [bookId, setBookId] = useState(null);
  const [book, setBook] = useState(null);

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

    if (!dateRead) {
      onSubmit && onSubmit(null, { validationError: "Date read is required" });
      return;
    }

    if (!language || !language.trim()) {
      onSubmit && onSubmit(null, { validationError: "Language is required" });
      return;
    }

    if (!myRating) {
      onSubmit && onSubmit(null, { validationError: "Rating is required" });
      return;
    }

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
  <div className="flex flex-col md:flex-row gap-6 mt-4 w-full max-w-5xl mx-auto">
    <div className="flex flex-col items-center md:items-start min-w-[200px] md:w-1/3 lg:w-1/4">
      {book?.urlImage ? (
        <img
          src={book.urlImage}
          alt={book.title}
          className="w-40 h-56 object-cover rounded shadow"
        />
      ) : (
        <div className="w-40 h-56 bg-gray-200 rounded" />
      )}
      <div className="mt-3 text-center md:text-left">
        <p className="font-semibold text-base">{book?.title}</p>
        <p className="text-gray-600 text-sm">{book?.author?.name}</p>
      </div>
    </div>

    <div className="flex-1">
      <form onSubmit={handleSubmit} className="space-y-4">
        <div className="flex flex-col md:flex-row gap-2 items-end">
          <div className="flex-1">
            <DatePicker label="Read Date" date={dateRead} setDate={setDateRead} />
          </div>
          <div className="flex-1">
            <Label>Language</Label>
            <LanguageSelect language={language} setLanguage={setLanguage} />
          </div>
        </div>

        <div>
          <Label>My rating</Label>
          <StarRatingInput value={myRating} onChange={setMyRating} />
        </div>

        <div className="flex flex-col md:flex-row gap-2 items-end">
          <div className="flex-1">
            <Label>Format</Label>
            <Select key={format} value={format} onValueChange={setFormat}>
              <SelectTrigger>
                <SelectValue placeholder="Select Format" />
              </SelectTrigger>
              <SelectContent>
                {READING_FORMATS.map((fmt) => (
                  <SelectItem key={fmt.code} value={fmt.code}>
                    {fmt.label}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>

          <div className="flex-1">
            <Label>Platform</Label>
            <Select key={platform} value={platform} onValueChange={setPlatform}>
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
        </div>
        <div className="flex gap-2 pt-2">
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
);

}