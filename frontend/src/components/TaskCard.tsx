import { Checkbox } from "./Checkbox";
import { TaskActions } from "./TaskActions";
import { Task } from "@/interfaces/Task";
import { api } from "@/lib/axios";
import { cn } from "@/lib/utils";
import { useChangeTaskMutation, useDeleteTaskMutation } from "@/hooks/useTaskOptimisticMutations";
import { DeleteModal } from "./DeleteModal";
import { useState } from "react";
import { FormTaskDialog } from "./FormTaskDialog";
import { DetailsModal } from "./DetailsModal";


interface Props {
  task: Task
}

export function TaskCard({ task }: Props) {
  const checked = task.status === "CONCLUIDA";

  async function changeStatus(data: Task) {
    await api.patch(`task/status/${data.id}`)
  }

  async function deleteTask(id: number) {
    await api.delete(`task/${id}`)
  }

  const { mutate: mutateStatus, isLoading: isChangingStatus } = useChangeTaskMutation({
    mutationFn: changeStatus
  })

  const { mutate: taskDeletion } = useDeleteTaskMutation({
    mutationFn: deleteTask
  })

  const [deleteModalOpen, setDeleteModalOpen] = useState(false)

  function handleDeleteModalOpen() {
    setDeleteModalOpen(state => !state)
  }

  const [editModalOpen, setEditModalOpen] = useState(false)
  const [detailsModalOpen, setDetailsModalOpen] = useState(false)

  return (
    <div className="flex items-center space-x-4 rounded-md border border-border p-4 h-24">
      <Checkbox
        className={cn("w-6 h-6", checked && "opacity-50")}
        checked={checked}
        onClick={() => mutateStatus({ ...task, status: task.status === "CONCLUIDA" ? "PENDENTE" : "CONCLUIDA" })}
        disabled={isChangingStatus}
      />
      <div className="flex-1 space-y-1 ">
        <p className={cn("text-sm font-medium leading-none",
          checked && "line-through opacity-50"
        )} >
          {task.title}
        </p>
        <p className={cn("text-sm text-muted-foreground max-h-16 line-clamp-2", checked && "opacity-50")} >
          {task.description}
        </p>
      </div>
      <TaskActions
        openDeleteModal={() => setDeleteModalOpen(true)}
        openEditModal={() => setEditModalOpen(true)}
        openDetailsModal={() => setDetailsModalOpen(true)}
      />
      <DeleteModal
        modalOpen={deleteModalOpen}
        handleModalOpen={handleDeleteModalOpen}
        onConfirm={() => taskDeletion(task.id)}

      />
      <FormTaskDialog
        handleModalOpen={setEditModalOpen}
        modalOpen={editModalOpen}
        task={task}
      />
      <DetailsModal
        handleModalOpen={setDetailsModalOpen}
        modalOpen={detailsModalOpen}
        task={task}
      />
    </div>
  )
}