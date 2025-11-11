export const READING_PLATFORMS = [
  { code: "OWN", label: "Bought/gifted" },
  { code: "KINDLE", label: "Kindle" },
  { code: "KOBO", label: "Kobo Books" },
  { code: "EBIBLIO", label: "Biblio Digital" },
  { code: "BIBLION", label: "BibliON" },
  { code: "UNLIMITED", label: "Unlimited" },
  { code: "BIBLIO", label: "Biblioteca" },
  { code: "AUDIBLE", label: "Audible" },
  { code: "VIVLIO", label: "Vivlio" },
  { code: "BOOKBEAT", label: "BookBeat" },
  { code: "PLAY_BOOKS", label: "Play books" },  
];

export function getReadingPlatformsDisplay(code) {
  if (!code) return "-";
  const found = READING_PLATFORMS.find((c) => c.code === code);
  return found ? `${found.label}` : code;
}

export function getPlatformImage(platform, format, mode) {
  if (!platform) return null;

  let imageName = "";
  switch (platform) {
    case "OWN":
      imageName = format === "EBOOK" ? "ebook" : "printed-book";
      break;
    default:
      imageName = platform.toLowerCase();
  }

  return `/assets/platforms/${mode}/${imageName}.png`;
}