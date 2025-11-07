import {useDialog} from "@/context/DialogContext.jsx";
import {useState} from "react";
import {doPassword} from "@/lib/api/users.js";

export default function useChangePassword() {
  const [saving, setSaving] = useState(false);
  const { showError, showSuccess } = useDialog();

  const handleSubmit = async (payload, maybeError) => {
    if (maybeError?.validationError) {
      showError(maybeError.validationError);
      return;
    }
    setSaving(true);
    const {error} = await doPassword(payload)
    if (!error) {
      showSuccess(`Password changed!`);
    } else {
      showError(error);
    }
    setSaving(false);
  };

  return {
    saving,
    handleSubmit
  }
}