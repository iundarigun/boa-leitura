export const SAGA_STATUS = [
  { code: "READ_AS_STANDALONE", label: "Read as standalone" },
  { code: "DNF", label: "DNF" },
  { code: "ON_GOING", label: "On going" },
  { code: "STANDBY", label: "Stand by" },
  { code: "FINISHED", label: "Finished" },
];

export function getSagaStatusDisplay(code) {
  if (!code) return "-";
  const found = SAGA_STATUS.find((c) => c.code === code);
  return found ? `${found.label}` : code;
}