
import { Task } from "@/interfaces/Task";
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle } from "./Dialog";
import { Badge } from "./Badge";
import dayjs from 'dayjs'

interface Props {
  task: Task
  modalOpen: boolean
  handleModalOpen: (open: boolean) => void
}

export function DetailsModal({ handleModalOpen, modalOpen, task }: Props) {

  const createdAt = dayjs(task.createdAt)
    .locale('pt-br')
    .format('DD [de] MMMM YYYY')

  const concludedAt = (date: string) => {
    return dayjs(task.concludedAt)
      .locale('pt-br')
      .format('DD/MM/YY [às] HH:mm')
  }



  return (
    <Dialog open={modalOpen} onOpenChange={handleModalOpen} >
      <DialogContent className="max-w-md  min-h-[300px] max-h-fit flex flex-col">

        <DialogHeader className="border-b border-border pb-3">
          <div className="flex flex-col md:flex-row gap-2 items-center">
            <DialogTitle className="md:mr-auto">
              {task.title}
            </DialogTitle>
            <Badge variant={task.status === "CONCLUIDA" ? "success" : "alert"} className="md:mr-8 w-fit">
              {task.status}
            </Badge>
          </div>
          <DialogDescription>
            <time>
              {createdAt}
            </time>
          </DialogDescription>
        </DialogHeader>
        <div className="flex-1">
          {task.description}
        </div>
        <DialogFooter className="mt-auto flex-row mr-auto">
          <DialogDescription>
            {task.concludedAt &&
              <time>
                Concluído dia {concludedAt(task.concludedAt)}
              </time>
            }
          </DialogDescription>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  )
}