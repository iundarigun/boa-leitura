import { createContext, useContext, useState } from "react";
import FeedbackDialog from "@/components/FeedbackDialog";

const DialogContext = createContext();

export function DialogProvider({ children }) {
  const [open, setOpen] = useState(false);
  const [message, setMessage] = useState({ title: "", description: "" });

  const showDialog = (title, description) => {
    setMessage({ title, description });
    setOpen(true);
  };

  return (
    <DialogContext.Provider value={{ showDialog }}>
      {children}
      <FeedbackDialog
        open={open}
        onOpenChange={setOpen}
        title={message.title}
        description={message.description}
      />
    </DialogContext.Provider>
  );
}

export function useDialog() {
  return useContext(DialogContext);
}
