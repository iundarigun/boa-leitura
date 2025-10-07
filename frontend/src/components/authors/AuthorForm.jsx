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

export default function AuthorForm({ onSave, editingAuthor, onCancel, loading }) {
  const [name, setName] = useState(null);
  const [gender, setGender] = useState("MALE");
  const [nationality, setNationality] = useState(null);
  
  useEffect(() => {
    if (editingAuthor) {
      setName(editingAuthor.name);
      setGender(editingAuthor.gender || "MALE");
      setNationality(editingAuthor.nationality);
    }
  }, [editingAuthor]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (name && !name.trim()) return;
    
    onSave({
      name,
      gender,
      nationality,
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
            <SelectItem value="MALE">Male</SelectItem>
            <SelectItem value="FEMALE">Female</SelectItem>
            <SelectItem value="COUPLE">Multiple authors</SelectItem>
          </SelectContent>
        </Select>
      </div>

      <div>
        <Label>Nationality</Label>
        <Input
          value={nationality}
          onChange={(e) => setNationality(e.target.value)}
          placeholder="Ex: Brazil"
        />
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
