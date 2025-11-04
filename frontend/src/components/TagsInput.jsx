import {useState} from "react";
import {X} from "lucide-react";
import {Input} from "@/components/ui/input";

export default function TagsInput({ value = [], onChange }) {
  const [inputValue, setInputValue] = useState("");

  const handleKeyDown = (e) => {
    if (e.key === "Enter" && inputValue.trim()) {
      e.preventDefault();
      if (!value.includes(inputValue.trim())) {
        onChange([...value, inputValue.trim()]);
      }
      setInputValue("");
    }
  };

  const handleRemove = (tag) => {
    onChange(value.filter((t) => t !== tag));
  };

  return (
    <div>
      <label className="block text-sm font-medium mb-1">Tags</label>
      <div className="flex flex-wrap items-center gap-2 p-2 border rounded-md bg-white">
        {value.map((tag) => (
          <div
            key={tag}
            className="flex items-center gap-1 bg-blue-100 text-blue-700 px-2 py-1 rounded-full"
          >
            <span>{tag}</span>
            <button
              type="button"
              onClick={() => handleRemove(tag)}
              className="hover:text-blue-900"
            >
              <X className="w-3 h-3" />
            </button>
          </div>
        ))}
        <Input
          value={inputValue}
          onChange={(e) => setInputValue(e.target.value)}
          onKeyDown={handleKeyDown}
          placeholder="Add a tag and press Enter"
          className="border-none shadow-none flex-1 min-w-[120px]"
        />
      </div>
    </div>
  );
}
