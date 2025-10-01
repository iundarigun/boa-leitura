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


export default function AuthorList({ authors, onEdit, onDelete }) {;
  if (!authors || !authors.content) {
    return <p className="text-gray-500 text-center">Nenhum autor encontrado.</p>;
  }  
return (
    <div className="grid gap-4">
      {authors.content.map((author) => (
        <Card
          key={author.id}
          className="p-4 flex justify-between items-center"
        >
          {/* informações */}
          <div>
            <p className="font-bold text-lg">{author.name}</p>
            <p className="text-sm text-gray-600">
              Gênero: {author.gender} | Nacionalidade: {author.nationality}
            </p>
          </div>

          {/* ações */}
          <div className="flex gap-2">
            <Button variant="outline" onClick={() => onEdit(author)}>
              Editar
            </Button>

            <AlertDialog>
              <AlertDialogTrigger asChild>
                <Button variant="destructive">Deletar</Button>
              </AlertDialogTrigger>
              <AlertDialogContent>
                <AlertDialogHeader>
                  <AlertDialogTitle>Confirmar exclusão</AlertDialogTitle>
                  <AlertDialogDescription>
                    Tem certeza que deseja deletar <b>{author.name}</b>?  
                    Esta ação não poderá ser desfeita.
                  </AlertDialogDescription>
                </AlertDialogHeader>
                <AlertDialogFooter>
                  <AlertDialogCancel>Cancelar</AlertDialogCancel>
                  <AlertDialogAction
                    className="bg-red-600 hover:bg-red-700"
                    onClick={() => onDelete(author.id, author.name)}
                  >
                    Deletar
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
