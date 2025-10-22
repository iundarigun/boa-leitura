import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogContent,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogDescription,
} from "@/components/ui/alert-dialog";
import { CheckCircle, XCircle, Info } from "lucide-react";

export default function FeedbackDialog({ open, onOpenChange, title, description, type }) {
  const getIcon = () => {
    switch (type) {
      case "success":
        return <CheckCircle className="text-green-600 w-6 h-6" />;
      case "error":
        return <XCircle className="text-red-600 w-6 h-6" />;
      default:
        return <Info className="text-blue-600 w-6 h-6" />;
    }
  };

  return (
    <AlertDialog open={open} onOpenChange={onOpenChange}>
      <AlertDialogContent>
        <AlertDialogHeader>
          {getIcon()}
          <AlertDialogTitle>{title}</AlertDialogTitle>
          <AlertDialogDescription>{description}</AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogAction onClick={() => onOpenChange(false)}>OK</AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
