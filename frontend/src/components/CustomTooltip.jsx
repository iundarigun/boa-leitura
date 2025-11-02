import { Tooltip, TooltipContent, TooltipTrigger } from "@/components/ui/tooltip";

export default function CustomTooltip({content, tooltipContent}) {
  if (!tooltipContent) {
    return <span className="font-medium">{content}</span>;
  }

  return (
    <Tooltip>
      <TooltipTrigger asChild>
        <span className="cursor-help font-medium">{content}</span>
      </TooltipTrigger>
      <TooltipContent side="top">
        {tooltipContent}
      </TooltipContent>
    </Tooltip>
  );
}