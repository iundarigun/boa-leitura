import { Button } from "@/components/ui/button";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";

export default function SagaTable({
  sagas = [],
  onSelect,
  onEdit,
  onView,
  onDelete,
  selectable = false,
}) {
  if (!sagas || sagas.length === 0) {
    return <p className="text-gray-500 text-center py-4">No sagas found.</p>;
  }

  return (
    <div className="overflow-x-auto">
      <table className="w-full border-collapse border border-gray-200">
        <thead className="bg-gray-100 text-left">
          <tr>
            <th className="p-3 border border-gray-200">Name</th>
            <th className="p-3 border border-gray-200 text-center">Main Titles</th>
            <th className="p-3 border border-gray-200 text-center">Other Titles</th>
            <th className="p-3 border border-gray-200 text-center">Concluded</th>
            { !selectable ? (
              <th className="p-3 border border-gray-200 text-center">Actions</th>
            ) : (
              <></>
            )}
          </tr>
        </thead>
        <tbody>
          {sagas.map((saga) => (
            <tr key={saga.id} className="hover:bg-gray-50">
              <td className="p-3 border border-gray-200">{saga.name}</td>
              <td className="p-3 border border-gray-200 text-center">{saga.totalMainTitles ?? "-"}</td>
              <td className="p-3 border border-gray-200 text-center">{saga.totalComplementaryTitles ?? "-"}</td>
              <td className="p-3 border border-gray-200 text-center"> {saga.concluded ? "✅" : "❌"}</td>
              {!selectable ? (
                <td className="p-3 border border-gray-200 text-center">
                  <div className="flex justify-center gap-2">
                    {onView && (
                      <Button
                        size="sm"
                        variant="outline"
                        onClick={() => onView(saga)}
                      >
                        Details
                      </Button>
                    )}
                    {onEdit && (
                      <Button size="sm" variant="outline" onClick={() => onEdit(saga)}>
                        Edit
                      </Button>
                    )}
                    {onEdit && (
                      <AlertDialog>
                      <AlertDialogTrigger asChild>
                        <Button variant="destructive" size="sm">Delete</Button>
                      </AlertDialogTrigger>
                      <AlertDialogContent>
                        <AlertDialogHeader>
                          <AlertDialogTitle>Confirm delete</AlertDialogTitle>
                          <AlertDialogDescription>
                            Are you sure you want to delete <b>{saga.name}</b>?  
                            This action cannot be undone.
                          </AlertDialogDescription>
                        </AlertDialogHeader>
                        <AlertDialogFooter>
                          <AlertDialogCancel>Cancel</AlertDialogCancel>
                          <AlertDialogAction
                            className="bg-red-600 hover:bg-red-700"
                            onClick={() => onDelete(saga)}
                          >
                            Delete
                          </AlertDialogAction>
                        </AlertDialogFooter>
                      </AlertDialogContent>
                    </AlertDialog>
                    )}
                  </div>
                </td>
              ) :(
                <td className="p-3 border border-gray-200 text-center">
                  <Button size="sm" onClick={() => onSelect(saga)}>Select</Button>
                </td>
              )}
            </tr>
            )
          )}
        </tbody>
      </table>
    </div>
  );
}
