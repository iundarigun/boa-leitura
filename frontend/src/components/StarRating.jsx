import { Star, StarHalf } from "lucide-react";

export default function StarRating({ value, max = 5, size = 20 }) {
  const stars = [];
  if (value > 0.0) {
    for (let i = 1; i <= max; i++) {
      if (value >= i) {
        stars.push(<Star key={i} fill="gold" stroke="gold" size={size} />);
      } else if (value >= i - 0.5) {
        stars.push(<StarHalf key={i} fill="gold" stroke="gold" size={size} />);
      } else {
        stars.push(<Star key={i} stroke="gold" fill="none" size={size} />);
      }
    }
  }
  return <span className="inline-flex items-center gap-1">{stars}</span>;
}
