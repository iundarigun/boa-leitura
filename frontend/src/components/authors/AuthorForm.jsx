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

export default function AuthorForm({ onSave, editingAuthor, onCancel }) {
  const [name, setName] = useState(null);
  const [gender, setGender] = useState("MALE");
  const [nationality, setNationality] = useState(null);
  
  useEffect(() => {
    if (editingAuthor) {
      setName(editingAuthor.name || "");
      setGender(editingAuthor.gender || "MALE");
      setNationality(editingAuthor.nationality || "");
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
        <Label>Nome</Label>
        <Input
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="Nome do autor"
        />
      </div>

      <div>
        <Label>Gênero</Label>
        <Select value={gender} onValueChange={setGender}>
          <SelectTrigger>
            <SelectValue placeholder="Selecione o gênero" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="MALE">Masculino</SelectItem>
            <SelectItem value="FEMALE">Feminino</SelectItem>
            <SelectItem value="COUPLE">Casal</SelectItem>
          </SelectContent>
        </Select>
      </div>

      <div>
        <Label>Nacionalidade</Label>
        <Input
          value={nationality}
          onChange={(e) => setNationality(e.target.value)}
          placeholder="Ex: Brasileiro"
        />
      </div>

      <div className="flex gap-2">
        <Button type="submit">{editingAuthor ? "Salvar" : "Adicionar"}</Button>
        <Button type="button" variant="outline" onClick={onCancel}>
          Cancelar
        </Button>
      </div>
    </form>
  );
}
