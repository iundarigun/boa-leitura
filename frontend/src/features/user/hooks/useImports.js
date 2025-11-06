import {useState} from "react";
import {useDialog} from "@/context/DialogContext.jsx";
import {importGoodreads} from "@/lib/api/imports.js";

export default function useImports() {
  const [file, setFile] = useState(null);
  const [loading, setLoading] = useState(false);
  const {showError, showSuccess} = useDialog();

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleSubmit = async () => {
    if (!file) {
      showError("Select a file!");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    setLoading(true);
    const {data, error} = await importGoodreads(formData);
    if (data) {
      showSuccess("Imported " + data.length + " rows!");
    }
    if (error) {
      showError(error);
    }
    setFile(null);
    setLoading(false);
  };
  return {
    file,
    loading,
    handleSubmit,
    handleFileChange
  }
}