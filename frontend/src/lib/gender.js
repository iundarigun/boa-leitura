export const AUTHOR_GENDER = [
  { code: "MALE", label: "Male" },
  { code: "FEMALE", label: "Female" },
  { code: "COUPLE", label: "Multiple authors" },
];

export function getGenderDisplay(code) {
  if (!code) return "-";
  const found = AUTHOR_GENDER.find((c) => c.code === code);
  return found ? `${found.label}` : code;
}