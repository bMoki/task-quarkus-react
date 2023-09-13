import { Button } from "./Button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "./Dialog";

interface Props {
  modalOpen: boolean
  handleModalOpen: () => void
  onConfirm: () => void;
}

export function DeleteModal({ handleModalOpen, modalOpen, onConfirm }: Props) {

  return (
    <Dialog open={modalOpen} onOpenChange={handleModalOpen} >
      <DialogContent className="sm:max-w-[425px]">

        <DialogHeader>
          <DialogTitle>Deletar Tarefa</DialogTitle>
          <DialogDescription>
            Tem certeza que deseja deletar tarefa? Essa ação sera irreversível
          </DialogDescription>
        </DialogHeader>

        <DialogFooter className="flex gap-2">
          <Button
            className="w-full"
            variant={"outline"}
            onClick={handleModalOpen}
          >Cancelar</Button>
          <Button
            className="w-full"
            variant={"destructive"}
            onClick={onConfirm}
          >Deletar</Button>
        </DialogFooter>

      </DialogContent>
    </Dialog>
  )
}