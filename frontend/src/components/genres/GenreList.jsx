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

function GenreItem({ genre, onEdit, onDelete, level = 0 }) {
  return (
    <Card className="p-4 mb-2">
      <div className="flex justify-between items-center">
        {/* identação pelo nível da hierarquia */}
        <div className="flex-1" style={{ paddingLeft: `${level * 20}px` }}>
          <p className="font-semibold text-lg">{genre.name}</p>
        </div>

        <div className="flex gap-2">
          <Button variant="outline" onClick={() => onEdit(genre)}>
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
                  Tem certeza que deseja deletar <b>{genre.name}</b>?  
                  Isso também pode afetar os subgêneros.
                </AlertDialogDescription>
              </AlertDialogHeader>
              <AlertDialogFooter>
                <AlertDialogCancel>Cancelar</AlertDialogCancel>
                <AlertDialogAction
                  className="bg-red-600 hover:bg-red-700"
                  onClick={() => onDelete(genre.id, genre.name)}
                >
                  Deletar
                </AlertDialogAction>
              </AlertDialogFooter>
            </AlertDialogContent>
          </AlertDialog>
        </div>
      </div>

      {/* renderiza subgêneros recursivamente */}
      {genre.subGenres && genre.subGenres.length > 0 && (
        <div className="mt-2">
          {genre.subGenres.map((sub) => (
            <GenreItem
              key={sub.id}
              genre={sub}
              onEdit={onEdit}
              onDelete={onDelete}
              level={level + 1}
            />
          ))}
        </div>
      )}
    </Card>
  );
}

export default function GenreList({ genres, onEdit, onDelete }) {
  if (!genres || !genres.content || genres.content.length === 0) {
    return <p className="text-gray-500">Nenhum gênero cadastrado.</p>;
  }

  return (
    <div className="space-y-2">
      {genres.content.map((genre) => (
        <GenreItem
          key={genre.id}
          genre={genre}
          onEdit={onEdit}
          onDelete={onDelete}
        />
      ))}
    </div>
  );
}
