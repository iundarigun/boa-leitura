export default function SortableColumns({onSort, sortField, sortDir, label, orderFieldName}) {
  const SortIcon = ({ field }) => {
    if (sortField !== field) return null;
    return sortDir === "asc" ? "▲" : "▼";
  };

  return (
    <th className="p-3 cursor-pointer" onClick={() => onSort(orderFieldName)}>
      {label} <SortIcon field={orderFieldName}/>
    </th>
  );
}