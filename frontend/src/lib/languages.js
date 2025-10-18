export const LANGUAGES = [
  { code: "en", label: "English" },
  { code: "es", label: "Español" },
  { code: "ca", label: "Català" },
  { code: "pt", label: "Português" },
  { code: "fr", label: "Français" },
  { code: "ru", label: "Russian" },
  { code: "ja", label: "Japanese" },
];

export function getLanguageDisplay(code) {
  if (!code) return "-";
  const found = LANGUAGES.find((c) => c.code === code);
  return found ? `${found.label}` : code;
}