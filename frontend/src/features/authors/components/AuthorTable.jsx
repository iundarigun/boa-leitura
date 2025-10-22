import {getCountryDisplay} from "@/lib/countries.js";
import TableActionButtons from "@/components/TableActionButtons.jsx";
import {getGenderDisplay} from "@/lib/gender.js";
import SortableColumns from "@/components/SortableColumn.jsx";

export default function AuthorTable(
  { authors, onEdit, onDelete,
    sortField, sortDir, onSort,
    onSelect}) {

  if (!authors?.content?.length) {
    return <p className="text-gray-500 text-center py-4">No authors found.</p>;
  }

  return (
    <div className="overflow-x-auto">
      <table className="w-full border-collapse border border-gray-200">
        <thead className="bg-gray-100 text-left">
        <tr>
          <SortableColumns
              onSort={onSort}
              sortField={sortField}
              sortDir={sortDir}
              label="Name"
              orderFieldName="NAME"
            />
          {!onSelect && (
            <>
              <SortableColumns
                onSort={onSort}
                sortField={sortField}
                sortDir={sortDir}
                label="Gender"
                orderFieldName="GENDER"
              />
              <th className="p-3 border border-gray-200">Nationality</th>
            </>
          )}
          <th className="p-3 border border-gray-200 text-center">Actions</th>
        </tr>
        </thead>
        <tbody>
        {authors.content.map((author) => (
          <tr key={author.id} className="hover:bg-gray-50">
              <td className="p-3 border border-gray-200">{author.name}</td>

              {!onSelect && (
                <>
                <td className="p-3 border border-gray-200">{getGenderDisplay(author.gender)}</td>
                <td className="p-3 border border-gray-200">{getCountryDisplay(author.nationality) || "-"}</td>
                </>
              )}
              <td className="p-3 border border-gray-200 text-center">
                  <TableActionButtons
                    entity={author}
                    onEdit={onEdit}
                    onDelete={onDelete}
                    onSelect={onSelect}
                    warningProperty="name"
                    />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
