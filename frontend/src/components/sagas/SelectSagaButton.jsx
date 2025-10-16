import SagaTable from "./SagaTable";

// ...

<div className="max-h-80 overflow-y-auto">
  {loading ? (
    <p className="text-center text-gray-500 py-3">Loading...</p>
  ) : (
    <SagaTable sagas={sagas} selectable onSelect={handleSelect} />
  )}
</div>
