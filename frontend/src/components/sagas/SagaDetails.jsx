import { Card } from "@/components/ui/card";

export default function SagaDetails({ saga }) {
  if (!saga) return null;

  return (
    <Card className="p-4 space-y-4">
      {saga.mainTitles && saga.mainTitles.length > 0 && (
        <div className="space-y-2">
          <p className="font-bold text-lg">Main titles:</p>
          {saga.mainTitles.map((title, idx) => (
            <p key={idx}>{title}</p>
          ))}
        </div>
      )}

      {saga.complementaryTitles && saga.complementaryTitles.length > 0 && (
        <div className="space-y-2">
          <p className="font-bold text-lg">Other titles:</p>
          {saga.complementaryTitles.map((title, idx) => (
            <p key={idx}>{title}</p>
          ))}
        </div>
      )}
    </Card>
  );
}
