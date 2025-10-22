import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import SagaForm from "@/features/sagas/components/SagaForm";
import useSagaForm from "@/features/sagas/hooks/useSagaForm.js";

export default function SagaFormPage() {
  const {
    isEdit,
    initialData,
    loading,
    saving,
    handleSubmit,
    handleCancel,
  } = useSagaForm();

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-lg mx-auto p-8">
        <CardHeader>
          <CardTitle className="text-2xl">
            {isEdit ? "✏️ Edit Saga" : "➕ New Saga"}
          </CardTitle>
        </CardHeader>
        <CardContent>
          {loading ? (
            <p className="text-center text-gray-500">Loading saga...</p>
          ) : (
            <SagaForm
              onSubmit={handleSubmit}
              editingSaga={initialData}
              onCancel={handleCancel}
              loading={saving}
            />
          )}
        </CardContent>
      </Card>
    </div>
  );
}
