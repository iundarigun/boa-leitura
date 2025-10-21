export const READING_FORMATS = [
  { code: "PRINTED", label: "Printed book" },
  { code: "EBOOK", label: "eBook" },
  { code: "AUDIOBOOK", label: "Audiobook" },
];

export function getReadingFormatDisplay(code) {
  if (!code) return "-";
  const found = READING_FORMATS.find((c) => c.code === code);
  return found ? `${found.label}` : code;
}