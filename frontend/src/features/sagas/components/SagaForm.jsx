import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Checkbox } from "@/components/ui/checkbox";
import { SagaDetails } from "./SagaDetailsDialog";

export default function SagaForm({ onSubmit, editingSaga, onCancel, loading }) {
  const [name, setName] = useState(null);
  const [totalMainTitles, setTotalMainTitles] = useState(0);
  const [totalComplementaryTitles, setTotalComplementaryTitles] = useState(0);
  const [concluded, setConcluded] = useState(false);
  
  useEffect(() => {
    if (editingSaga) {
      setName(editingSaga.name);
      setTotalMainTitles(editingSaga.totalMainTitles || 0);
      setTotalComplementaryTitles(editingSaga.totalComplementaryTitles || 0);
      setConcluded(editingSaga.concluded);
    }
  }, [editingSaga]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!name || !name.trim()) {
      onSubmit(null, { validationError: "Saga name is required."});
      return;
    }
    if (totalMainTitles == null || totalMainTitles < 0) {
      onSubmit(null, { validationError: "Main titles must be positive."});
      return;
    }
    if (totalComplementaryTitles == null || totalComplementaryTitles < 0) {
      onSubmit(null, { validationError: "Other titles must be positive."});
      return;
    }

    onSubmit({
      name,
      totalMainTitles,
      totalComplementaryTitles,
      concluded,
    });
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <Label>Name</Label>
        <Input
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="Saga's name"
        />
      </div>

      <div>
        <Label>Main titles</Label>
        <Input
          type="number"
          value={totalMainTitles}
          onChange={(e) => setTotalMainTitles(Number(e.target.value))}
          placeholder="Main titles"
        />
      </div>

      <div>
        <Label>Other titles</Label>
        <Input
          type="number"
          value={totalComplementaryTitles}
          onChange={(e) => setTotalComplementaryTitles(Number(e.target.value))}
          placeholder="Other titles"
        />
      </div>

      <div className="flex items-center space-x-2">
        <Checkbox
          id="concluded"
          checked={concluded}
          onCheckedChange={(checked) => setConcluded(!!checked)}
        />
        <label htmlFor="concluded" className="text-sm font-medium">
          Concluded
        </label>
      </div>

      <div className="flex gap-2">
        <Button type="submit" disabled={loading}>
          {loading ? "Saving..." : (editingSaga ? "Save" : "Add")}</Button>
        <Button type="button" variant="outline" onClick={onCancel}>
          Cancel
        </Button>
      </div>
      <div>
        <SagaDetails saga={editingSaga} />
      </div>
    </form>
  );
}
