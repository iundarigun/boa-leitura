import {useState} from "react";
import {useDialog} from "@/context/DialogContext.jsx";
import {getByParams} from "@/lib/api/bookInformation.js";

export default function useBookInformation() {
  const { showError } = useDialog();

  const [fetching, setFetching] = useState(false);
  const [searchResults, setSearchResults] = useState([]);
  const [fetchingByName, setFetchingByName] = useState(false);

  const fetchByISBN = async (isbn) => {
    if (!isbn.trim()) return;
    setFetching(true);
    const {data, error} = await getByParams({isbn: isbn});
    setFetching(false);

    if (error) {
      showError(error);
      return;
    }
    if (!data || data.length === 0) {
      showError("No book found for this ISBN");
      return;
    }
    return { data };
  }

  const fetchByTitleAndAuthor = async (title, author) => {
    if (!title.trim()) return;
    setFetchingByName(true);

    const {data, error} = await getByParams({title: title, author: author?.name});

    setFetchingByName(false);

    if (error) {
      showError(error);
      return;
    }

    if (!data || data.length === 0) {
      showError("No book found for this title");
      return;
    }
    setSearchResults(data);
    return true;
  };

  return {
    fetching,
    fetchingByName,
    searchResults,
    fetchByISBN,
    fetchByTitleAndAuthor
  };
}