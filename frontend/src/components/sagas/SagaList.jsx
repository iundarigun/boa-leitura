import SagaTable from "./SagaTable";

export default function SagaList({ sagas, onEdit, onDelete, onView }) {
  if (!sagas || !sagas.content) {
    return <p className="text-gray-500 text-center py-4">No sagas found.</p>;
  }

  const handleDelete = (saga) => {
    const confirmed = window.confirm(`Delete saga "${saga.name}"?`);
    if (confirmed) onDelete(saga.id, saga.name);
  };

  return (
    <SagaTable
      sagas={sagas.content}
      onEdit={onEdit}
      onDelete={handleDelete}
      onView={onView}
    />
  );
}
