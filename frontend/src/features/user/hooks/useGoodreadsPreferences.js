import {useEffect, useState} from "react";
import {useDialog} from "@/context/DialogContext.jsx";
import {getUser, updateUserPreferences} from "@/lib/api/users.js";

export default function useGoodreadsPreferences() {
  const [initialData, setInitialData] = useState(null);
  const [saving, setSaving] = useState(false);

  const { showError, showSuccess } = useDialog();

  useEffect(() => {
    fetchPreferences();
  }, []);

  const fetchPreferences = async () => {
    const {data, error} = await getUser();
    if (data) setInitialData(data.preferences);
    if (error) showError(error);
  }

  const handleSubmit = async (payload, maybeError) => {
    if (maybeError?.validationError) {
      showError(maybeError.validationError);
      return;
    }
    setSaving(true);
    const {error} = await updateUserPreferences(payload);

    if (!error) {
      showSuccess(`User Preferences updated successfully!`);
    } else {
      showError(error);
    }
    setSaving(false);
  };

  const handleCancel = async () => await fetchPreferences();

  return {
    saving,
    initialData,
    handleSubmit,
    handleCancel,
  };
}