import {useEffect, useState} from "react";
import {Button} from "@/components/ui/button";
import {Input} from "@/components/ui/input";
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue,} from "@/components/ui/select";
import {Plus, Trash2} from "lucide-react";
import {READING_PLATFORMS} from "@/lib/platform.js";
import {READING_FORMATS} from "@/lib/format.js";
import {LANGUAGES} from "@/lib/languages.js";

export default function GoodreadsPreferencesForm({
                                                   userPreferences,
                                                   onSubmit,
                                                   onCancel,
                                                   saving,
                                                 }) {
  const [languages, setLanguages] = useState([]);
  const [formats, setFormats] = useState([]);
  const [platforms, setPlatforms] = useState([]);

  useEffect(() => {
    if (userPreferences) {
      setLanguages(
        Object.entries(userPreferences.languageTags || {}).map(
          ([shelf, value]) => ({ shelf, value })
        )
      );
      setFormats(
        Object.entries(userPreferences.formatTags || {}).map(
          ([shelf, value]) => ({ shelf, value })
        )
      );
      setPlatforms(
        Object.entries(userPreferences.platformTags || {}).map(
          ([shelf, value]) => ({ shelf, value })
        )
      );
    }
  }, [userPreferences]);

  const handleChange = (setter, index, field, newValue) => {
    setter((prev) =>
      prev.map((item, i) =>
        i === index ? { ...item, [field]: newValue } : item
      )
    );
  };

  const handleAdd = (setter) =>
    setter((prev) => [...prev, { shelf: "", value: "" }]);

  const handleRemove = (setter, index) =>
    setter((prev) => prev.filter((_, i) => i !== index));

  const handleSave = () => {
    const toMap = (arr) =>
      arr.reduce((acc, { shelf, value }) => {
        if (shelf && value) acc[shelf] = value;
        return acc;
      }, {});

    const result = {
      languageTags: toMap(languages),
      formatTags: toMap(formats),
      platformTags: toMap(platforms),
    };

    onSubmit?.(result);
  };

  return (
    <div className="space-y-6">
      <div className="flex flex-col items-center text-center mb-4"/>
      <div className="flex flex-col mb-4">
        <h1 className="text-lg font-semibold">Configure preferences for Goodreads import</h1>
      </div>
      <MappingSection
        title="Languages"
        description="Associate Goodreads shelves to languages"
        rows={languages}
        options={LANGUAGES.map((v) => v.code)}
        onChange={(index, field, value) =>
          handleChange(setLanguages, index, field, value)
        }
        onAdd={() => handleAdd(setLanguages)}
        onRemove={(i) => handleRemove(setLanguages, i)}
      />

      <MappingSection
        title="Formats"
        description="Associate Goodreads shelves to reading formats"
        rows={formats}
        options={READING_FORMATS.map((v) => v.code)}
        onChange={(index, field, value) =>
          handleChange(setFormats, index, field, value)
        }
        onAdd={() => handleAdd(setFormats)}
        onRemove={(i) => handleRemove(setFormats, i)}
      />

      <MappingSection
        title="Platforms"
        description="Associate Goodreads shelves to reading platforms"
        rows={platforms}
        options={READING_PLATFORMS.map((v) => v.code)}
        onChange={(index, field, value) =>
          handleChange(setPlatforms, index, field, value)
        }
        onAdd={() => handleAdd(setPlatforms)}
        onRemove={(i) => handleRemove(setPlatforms, i)}
      />

      <div className="flex gap-2 pt-2">
        <Button onClick={handleSave} disabled={saving}>
          {saving ? "Saving..." : "Save preferences"}
        </Button>
        <Button type="button" variant="outline" onClick={onCancel}>
          Cancel
        </Button>
      </div>
    </div>
  );
}

function MappingSection({
                          title,
                          description,
                          rows,
                          options,
                          onChange,
                          onAdd,
                          onRemove,
                        }) {
  return (
    <div className="space-y-3">
      <div>
        <h2 className="text-m">{title}</h2>
        <p className="text-sm text-muted-foreground">{description}</p>
      </div>

      <div className="space-y-2">
        {rows.map((row, index) => (
          <div
            key={index}
            className="flex items-center gap-3 border p-2 rounded-lg bg-white"
          >
            <Input
              placeholder="Shelf do Goodreads"
              value={row.shelf}
              onChange={(e) => onChange(index, "shelf", e.target.value)}
              className="flex-1"
            />

            <Select
              value={row.value}
              onValueChange={(v) => onChange(index, "value", v)}
            >
              <SelectTrigger className="w-48">
                <SelectValue placeholder="Selecione" />
              </SelectTrigger>
              <SelectContent>
                {options.map((opt) => (
                  <SelectItem key={opt} value={opt}>
                    {opt}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>

            <Button variant="ghost" size="icon" onClick={() => onRemove(index)}>
              <Trash2 className="w-4 h-4" />
            </Button>
          </div>
        ))}
      </div>

      <Button variant="outline" size="sm" onClick={onAdd}>
        <Plus className="mr-2 h-4 w-4" /> Adicionar linha
      </Button>
    </div>
  );
}
