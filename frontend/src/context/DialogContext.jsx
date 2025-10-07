import { createContext, useContext, useState } from "react";
import FeedbackDialog from "@/components/FeedbackDialog";

const DialogContext = createContext();

export function DialogProvider({ children }) {
  const [open, setOpen] = useState(false);
  const [message, setMessage] = useState({
    title: "",
    description: "",
    type: "info",
  });

  const showDialog = (title, description, type = "info") => {
    setMessage({ title, description, type });
    setOpen(true);
  };  
  const showError = (description) => showDialog("Error", description, "error");
  const showSuccess = (description) => showDialog("Success", description, "success");
  const showInfo = (description) => showDialog("Info", description, "info");

  return (
    <DialogContext.Provider value={{ showDialog, showError, showSuccess, showInfo }}>
      {children}
      <FeedbackDialog
        open={open}
        onOpenChange={setOpen}
        title={message.title}
        description={message.description}
        type={message.type}
      />
    </DialogContext.Provider>
  );
}

export function useDialog() {
  return useContext(DialogContext);
}
