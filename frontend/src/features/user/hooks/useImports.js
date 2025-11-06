import {useState} from "react";
import {useDialog} from "@/context/DialogContext.jsx";
import {importGoodreads} from "@/lib/api/imports.js";

export default function useImports() {
  const [file, setFile] = useState(null);
  const [read, setRead] = useState(false);
  const [tbr, setTbr] = useState(false);
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
    formData.append("read", read);
    formData.append("tbr", tbr);

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
    read,
    setRead,
    tbr,
    setTbr,
    loading,
    handleSubmit,
    handleFileChange
  }
}