import { useState } from "react";
import { Star } from "lucide-react";

export default function StarRatingInput({ value = 0, max = 5, size = 24, onChange }) {
  const [hover, setHover] = useState(null);

  const handleClick = (rating) => {
    if (onChange) onChange(rating);
  };

  const handleMouseMove = (e, index) => {
    const { left, width } = e.currentTarget.getBoundingClientRect();
    const x = e.clientX - left;
    const half = width / 2;
    const hoverValue = x < half ? index - 0.5 : index;
    setHover(hoverValue);
  };

  const handleMouseLeave = () => setHover(null);

  const renderStar = (index) => {
    const current = hover !== null ? hover : value;
    const filled = current >= index;
    const half = !filled && current >= index - 0.5;

    return (
      <div
        key={index}
        className="relative cursor-pointer"
        onMouseMove={(e) => handleMouseMove(e, index)}
        onMouseLeave={handleMouseLeave}
        onClick={() => handleClick(half ? index - 0.5 : index)}
        style={{ width: size, height: size }}
      >
        {/* Fundo (estrela vazia) */}
        <Star
          size={size}
          stroke="gray"
          fill="none"
        />

        {/* Estrela cheia */}
        {(filled || half) && (
          <Star
            size={size}
            fill="gold"
            stroke="gold"
            className="absolute top-0 left-0"
            style={half ? { clipPath: "inset(0 50% 0 0)" } : {}}
          />
        )}
      </div>
    );
  };

  return <div className="flex items-center gap-1">{[...Array(max)].map((_, i) => renderStar(i + 1))}</div>;
}
