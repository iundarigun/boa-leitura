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
import {Checkbox} from "@/components/ui/checkbox.jsx";

export default function ToBeReadForm({ editingReading: editingToBeRead = null, onSubmit, onCancel, loading = false }) {
  const [bought, setBought] = useState(false);
  const [done, setDone] = useState(false);
  const [platforms, setPlatforms] = useState([]);
  const [tags, setTags] = useState([]);
  const [notes, setNotes] = useState("");
  const [book, setBook] = useState(null);

  useEffect(() => {
    if (editingToBeRead) {
      setBought(editingToBeRead.bought || false);
      setDone(editingToBeRead.done || false);
      setPlatforms(editingToBeRead.platforms || []);
      setTags(editingToBeRead.tags || []);
      setNotes(editingToBeRead.notes || "");
      setBook(editingToBeRead.book || null);
    }
  }, [editingToBeRead]);

  const handleSubmit = (e) => {
    e.preventDefault();

    // if (!dateRead) {
    //   onSubmit && onSubmit(null, { validationError: "Date read is required" });
    //   return;
    // }
    //
    // if (!language || !language.trim()) {
    //   onSubmit && onSubmit(null, { validationError: "Language is required" });
    //   return;
    // }
    //
    // if (!myRating) {
    //   onSubmit && onSubmit(null, { validationError: "Rating is required" });
    //   return;
    // }

    const payload = {
      bookId: book.id,
      bought: bought,
      done: done,
      tags: tags,
      platforms: platforms,
      notes: notes
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
        <p className="text-gray-600 text-sm">{book?.author}</p>
      </div>
    </div>

    <div className="flex-1">
      <form onSubmit={handleSubmit} className="space-y-4">
        <div className="flex flex-col md:flex-row gap-2 items-end">
          <div className="flex-1">
            <Checkbox
              id="bought"
              checked={bought}
              onCheckedChange={(checked) => setBought(!!checked)}
            />
            <label htmlFor="bought" className="text-sm font-medium">
              Bought
            </label>
          </div>
          <div className="flex-1">
            <Checkbox
              id="done"
              checked={done}
              onCheckedChange={(checked) => setDone(!!checked)}
            />
            <label htmlFor="done" className="text-sm font-medium">
              Done
            </label>
          </div>
        </div>

        <div>
          <Label>Notes</Label>
          {/*<StarRatingInput value={myRating} onChange={setMyRating} />*/}
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