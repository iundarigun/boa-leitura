import {useState} from "react";
import SagaDetailsDialog from "@/features/sagas/components/SagaDetailsDialog";
import SortableColumns from "@/components/SortableColumn.jsx";
import TableActionButtons from "@/components/TableActionButtons.jsx";

export default function SagaTable(
  {
    sagas, onEdit, onDelete,
    sortField, sortDir, onSort,
    onSelect,
  }) {
  const [sagaDetailsOpen, setSagaDetailsOpen] = useState(false);
  const [selectedSaga, setSelectedSaga] = useState(null);

  const handleSagaView = (sagaId) => {
    setSelectedSaga(sagaId);
    setSagaDetailsOpen(true);
  };

  if (!sagas || sagas.length === 0) {
    return <p className="text-gray-500 text-center py-4">No sagas found.</p>;
  }

  return (
    <>
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
            <th className="p-3 border border-gray-200 text-center">Main Titles</th>
            {!onSelect && (
              <>
                <th className="p-3 border border-gray-200 text-center">Other Titles</th>
                <th className="p-3 border border-gray-200 text-center">Concluded</th>
                <th className="p-3 border border-gray-200 text-center">Actions</th>
              </>
            )}
          </tr>
          </thead>
          <tbody>
          {sagas.content.map((saga) => (
              <tr key={saga.id} className="hover:bg-gray-50">
                <td className="p-3 border border-gray-200">{saga.name}</td>
                <td className="p-3 border border-gray-200 text-center">{saga.totalMainTitles ?? "-"}</td>
                {!onSelect && (
                  <>
                    <td className="p-3 border border-gray-200 text-center">{saga.totalComplementaryTitles ?? "-"}</td>
                    <td className="p-3 border border-gray-200 text-center"> {saga.concluded ? "✅" : "❌"}</td>
                  </>
                )}
                <td className="p-3 border border-gray-200 text-center">
                  <TableActionButtons
                    onDetails={() => handleSagaView(saga.id)}
                    entity={saga}
                    onEdit={onEdit}
                    onDelete={onDelete}
                    onSelect={onSelect}
                    warningProperty="name"
                  />
                </td>
              </tr>
            )
          )}
          </tbody>
        </table>
      </div>
      <SagaDetailsDialog
        open={sagaDetailsOpen}
        onClose={setSagaDetailsOpen}
        sagaId={selectedSaga}
      />
    </>
  );
}
