import {useEffect, useState} from "react";
import {Button} from "@/components/ui/button";
import {Input} from "@/components/ui/input";
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue} from "@/components/ui/select";
import {Plus, Trash2} from "lucide-react";

export default function GoodreadsPreferencesForm({userPreferences, onSubmit, onCancel, saving}) {
  const [languages, setLanguages] = useState([]);
  const [formats, setFormats] = useState([]);
  const [platforms, setPlatforms] = useState([]);

  useEffect(() => {
    if (userPreferences) {
      // userPreferences.languageTags.map((language) => setLanguages());
      // setBought(editingToBeRead.bought || false);
      // setDone(editingToBeRead.done || false);
      // setPlatforms(editingToBeRead.platforms || []);
      // setTags(editingToBeRead.tags || []);
      // setNotes(editingToBeRead.notes || "");
      // setBook(editingToBeRead.book || null);
    }
  }, [userPreferences]);

  const handleChange = (setter, index, field, newValue) => {
    setter((prev) =>
      prev.map((item, i) =>
        i === index ? { ...item, [field]: newValue } : item
      )
    );
  };

  const handleAdd = (setter) => setter((prev) => [...prev, { shelf: "", value: "" }]);
  const handleRemove = (setter, index) =>
    setter((prev) => prev.filter((_, i) => i !== index));

  const handleSave = () => {
    const result = {
      languages,
      formats,
      platforms,
    };

    console.log("Mapeamentos finais:", result);
    onSubmit(result);
  };

  return (
    <div className="space-y-4">
      <div>
        {/* LANGUAGES */}
        <MappingSection
          title="Languages"
          description="Associe shelves do Goodreads aos idiomas (ISO 639-1)."
          rows={languages}
          options={["en", "es", "pt", "fr", "de", "it"]}
          onChange={(index, field, value) =>
            handleChange(setLanguages, index, field, value)
          }
          onAdd={() => handleAdd(setLanguages)}
          onRemove={(i) => handleRemove(setLanguages, i)}
        />
      </div>
      <div>
        {/* FORMATS */}
        <MappingSection
          title="Formats"
          description="Associe shelves do Goodreads aos formatos de leitura."
          rows={formats}
          options={["EBOOK", "AUDIOBOOK", "PRINTED"]}
          onChange={(index, field, value) =>
            handleChange(setFormats, index, field, value)
          }
          onAdd={() => handleAdd(setFormats)}
          onRemove={(i) => handleRemove(setFormats, i)}
        />
      </div>
      <div>
        {/* PLATFORMS */}
        <MappingSection
          title="Platforms"
          description="Associe shelves do Goodreads às plataformas usadas."
          rows={platforms}
          options={[
            "KINDLE",
            "KOBO",
            "AUDIBLE",
            "BIBLIO",
            "EBIBLIO",
            "UNLIMITED",
          ]}
          onChange={(index, field, value) =>
            handleChange(setPlatforms, index, field, value)
          }
          onAdd={() => handleAdd(setPlatforms)}
          onRemove={(i) => handleRemove(setPlatforms, i)}
        />
      </div>
      <div className="flex gap-2">
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
        <h2 className="text-lg font-semibold">{title}</h2>
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
                <SelectValue placeholder="Selecione"/>
              </SelectTrigger>
              <SelectContent>
                {options.map((opt) => (
                  <SelectItem key={opt} value={opt}>
                    {opt}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>

            <Button
              variant="ghost"
              size="icon"
              onClick={() => onRemove(index)}
            >
              <Trash2 className="w-4 h-4"/>
            </Button>
          </div>
        ))}
      </div>

      <Button variant="outline" size="sm" onClick={onAdd}>
        <Plus className="mr-2 h-4 w-4"/> Adicionar linha
      </Button>
    </div>
  );
}
