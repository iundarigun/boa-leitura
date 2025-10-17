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
import { getCountryDisplay } from "../../lib/countries";

export default function AuthorTable({ authors, onEdit, onDelete, onSelect, selectable = false }) {
  const convertGender = (gender) => {
    switch (gender) {
      case "MALE": return "Male";
      case "FEMALE": return "Female";
      case "COUPLE": return "Multiple authors";
      default: return "-"
    }
  };

  if (!authors?.content?.length) {
    return <p className="text-gray-500 text-center py-4">No authors found.</p>;
  }

  return (
    <div className="overflow-x-auto">
      <table className="w-full border-collapse border border-gray-200">
        <thead className="bg-gray-100 text-left">
          <tr>
            <th className="p-3 border border-gray-200">Name</th>
            
            {!selectable ? (
              <>
                <th className="p-3 border border-gray-200">Gender</th>
                <th className="p-3 border border-gray-200">Nationality</th>
                <th className="p-3 border border-gray-200 text-center">Actions</th>
              </>
            ) : (
              <></>
            )}
          </tr>
        </thead>
        <tbody>
          {authors.content.map((author) => (
            <tr key={author.id} className="hover:bg-gray-50">
              <td className="p-3 border border-gray-200">{author.name}</td>

              {!selectable ? (
                <>
                <td className="p-3 border border-gray-200">{convertGender(author.gender)}</td>
                <td className="p-3 border border-gray-200">{getCountryDisplay(author.nationality) || "-"}</td>
                <td className="p-3 border border-gray-200 text-center">
                  <div className="flex justify-center gap-2">
                    <Button variant="outline" size="sm" onClick={() => onEdit(author)}>
                      Edit
                    </Button>

                    <AlertDialog>
                      <AlertDialogTrigger asChild>
                        <Button variant="destructive" size="sm">Delete</Button>
                      </AlertDialogTrigger>
                      <AlertDialogContent>
                        <AlertDialogHeader>
                          <AlertDialogTitle>Confirm delete</AlertDialogTitle>
                          <AlertDialogDescription>
                            Are you sure you want to delete <b>{author.name}</b>?  
                            This action cannot be undone.
                          </AlertDialogDescription>
                        </AlertDialogHeader>
                        <AlertDialogFooter>
                          <AlertDialogCancel>Cancel</AlertDialogCancel>
                          <AlertDialogAction
                            className="bg-red-600 hover:bg-red-700"
                            onClick={() => onDelete(author.id, author.name)}
                          >
                            Delete
                          </AlertDialogAction>
                        </AlertDialogFooter>
                      </AlertDialogContent>
                    </AlertDialog>
                  </div>
                </td>
                </>                
              ) : (
                <td className="p-3 border border-gray-200 text-center">
                  <Button size="sm" onClick={() => onSelect(author)}>Select</Button>
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
