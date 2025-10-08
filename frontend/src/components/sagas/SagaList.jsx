import { Card } from "@/components/ui/card";
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


export default function SagaList({ sagas, onEdit, onDelete }) {;
  if (!sagas || !sagas.content) {
    return <p className="text-gray-500 text-center">No saga found.</p>;
  }  
return (
    <div className="grid gap-4">
      {sagas.content.map((saga) => (
        <Card
          key={saga.id}
          className="p-4 flex justify-between items-center"
        >
          
          <div>
            <p className="font-bold text-lg">{saga.name}</p>
            <p className="text-sm text-gray-600">
              Main titles: {saga.totalMainTitles} | Other titles: {saga.totalComplementaryTitles} | concluded: {saga.concluded? "yes": "no" }
            </p>
          </div>

          {/* ações */}
          <div className="flex gap-2">
            <Button variant="outline" onClick={() => onEdit(saga)}>
              Edit
            </Button>

            <AlertDialog>
              <AlertDialogTrigger asChild>
                <Button variant="destructive">Delete</Button>
              </AlertDialogTrigger>
              <AlertDialogContent>
                <AlertDialogHeader>
                  <AlertDialogTitle>Confirm delete</AlertDialogTitle>
                  <AlertDialogDescription>
                    Are you sure you want delete <b>{saga.name}</b>?  
                    This action can not be undone.
                  </AlertDialogDescription>
                </AlertDialogHeader>
                <AlertDialogFooter>
                  <AlertDialogCancel>Cancel</AlertDialogCancel>
                  <AlertDialogAction
                    className="bg-red-600 hover:bg-red-700"
                    onClick={() => onDelete(saga.id, saga.name)}
                  >
                    Delete
                  </AlertDialogAction>
                </AlertDialogFooter>
              </AlertDialogContent>
            </AlertDialog>
          </div>
        </Card>
      ))}
    </div>
  );
}
