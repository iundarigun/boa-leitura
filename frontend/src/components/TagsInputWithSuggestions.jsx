import { useState, useRef } from "react";
import { X } from "lucide-react";
import { Input } from "@/components/ui/input";
import { cn } from "@/lib/utils";

export default function TagsInputWithSuggestions({
                                                   value = [],
                                                   onChange,
                                                   suggestions = [],
                                                 }) {
  const [inputValue, setInputValue] = useState("");
  const [filteredSuggestions, setFilteredSuggestions] = useState([]);
  const [showSuggestions, setShowSuggestions] = useState(false);
  const inputRef = useRef(null);

  const handleInputChange = (e) => {
    const newValue = e.target.value;
    setInputValue(newValue);

    if (newValue.trim() === "") {
      setFilteredSuggestions([]);
      setShowSuggestions(false);
      return;
    }

    const filtered = suggestions
      .filter(
        (tag) =>
          tag.toLowerCase().includes(newValue.toLowerCase()) &&
          !value.includes(tag)
      )
      .slice(0, 5);

    setFilteredSuggestions(filtered);
    setShowSuggestions(filtered.length > 0);
  };

  const handleAddTag = (tag) => {
    if (!value.includes(tag)) {
      onChange([...value, tag]);
      setInputValue("");
      setFilteredSuggestions([]);
      setShowSuggestions(false);
      inputRef.current.focus();
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter" && inputValue.trim()) {
      e.preventDefault();
      handleAddTag(inputValue.trim());
    }
  };

  const handleRemoveTag = (tag) => {
    onChange(value.filter((t) => t !== tag));
  };

  return (
    <div className="relative">
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
              onClick={() => handleRemoveTag(tag)}
              className="hover:text-blue-900"
            >
              <X className="w-3 h-3" />
            </button>
          </div>
        ))}
        <Input
          ref={inputRef}
          value={inputValue}
          onChange={handleInputChange}
          onKeyDown={handleKeyDown}
          className="border-none shadow-none flex-1 min-w-[120px]"
          onFocus={() => {
            if (filteredSuggestions.length > 0) setShowSuggestions(true);
          }}
          onBlur={() => setTimeout(() => setShowSuggestions(false), 100)}
        />
      </div>

      {showSuggestions && (
        <ul className="absolute left-0 w-full bg-white border rounded-md mt-1 shadow-md z-10">
          {filteredSuggestions.map((suggestion) => (
            <li
              key={suggestion}
              className={cn(
                "px-3 py-2 cursor-pointer hover:bg-blue-50 hover:text-blue-700"
              )}
              onMouseDown={() => handleAddTag(suggestion)}
            >
              {suggestion}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
