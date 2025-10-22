import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from "@/components/ui/select";
import CountrySelect from "@/components/CountrySelect";
import {AUTHOR_GENDER} from "@/lib/gender.js";

export default function AuthorForm({ onSubmit, editingAuthor, onCancel, loading }) {
  const [name, setName] = useState(null);
  const [gender, setGender] = useState("");
  const [nationality, setNationality] = useState(null);
  
  useEffect(() => {
    if (editingAuthor) {
      setName(editingAuthor.name);
      setGender(editingAuthor.gender || "");
      setNationality(editingAuthor.nationality || null);
    }
  }, [editingAuthor]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!name || !name.trim()) {
      onSubmit(null, { validationError: "Name is required." });
      return;
    }
    if (!gender || !gender.trim()) {
      onSubmit(null, { validationError: "Gender is required." });
      return;
    }

    onSubmit({
      name: name.trim(),
      gender: gender || null,
      nationality: nationality || null,
    });
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <Label>Name</Label>
        <Input
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="Author's name"
        />
      </div>

      <div>
        <Label>Gender</Label>
        <Select value={gender} onValueChange={setGender}>
          <SelectTrigger>
            <SelectValue placeholder="Select gender" />
          </SelectTrigger>
          <SelectContent>
            {AUTHOR_GENDER.map((lang) => (
              <SelectItem key={lang.code} value={lang.code}>
                {lang.label}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      </div>

      <div>
        <Label>Nationality</Label>
        <CountrySelect value={nationality} onChange={setNationality} />
      </div>

      <div className="flex gap-2">
        <Button type="submit" disabled={loading}>
          {loading ? "Saving..." : (editingAuthor ? "Save" : "Add")}</Button>
        <Button type="button" variant="outline" onClick={onCancel}>
          Cancel
        </Button>
      </div>
    </form>
  );
}
