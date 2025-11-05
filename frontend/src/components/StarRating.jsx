import { Star, StarHalf } from "lucide-react";

export default function StarRating({ value, max = 5, size = 20 }) {
  const stars = [];
  if (value > 0.0) {
    for (let i = 1; i <= max; i++) {
      if (value >= i) {
        stars.push(<Star key={i} fill="gold" stroke="gold" size={size} />);
      } else if (value >= i - 0.5) {
        stars.push(<div key={i} className="relative inline-block">
          {/* camada de contorno */}
          <Star
            fill="none"
            stroke="gold"
            strokeWidth={1.5}
            size={size}
            className="absolute top-0 left-0"
          />
          {/* metade preenchida */}
          <StarHalf
            fill="gold"
            stroke="gold"
            strokeWidth={1.5}
            size={size}
          />
        </div>);
      } else {
        stars.push(<Star key={i} stroke="gold" fill="none" size={size}/>);
      }
    }
  }
  return <span className="inline-flex items-center gap-1">{stars}</span>;
}
