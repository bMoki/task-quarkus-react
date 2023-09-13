import { z } from "zod";
import { Button } from "./Button";
import { Dialog, DialogTrigger, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle } from "./Dialog";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "./Form";
import { Input } from "./Input";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { Textarea } from "./TextArea";
import { api } from "@/lib/axios";
import { useChangeTaskMutation, useCreateTaskMutation } from "@/hooks/useTaskOptimisticMutations";
import { Task } from "@/interfaces/Task";

interface Props {
  task?: Task
  modalOpen: boolean
  handleModalOpen: (open: boolean) => void;
}

export function FormTaskDialog({ task, handleModalOpen, modalOpen }: Props) {

  const taskFormSchema = z.object({
    title: z.string().nonempty("Titulo deve ser informado"),
    description: z.string().nonempty("Descrição deve ser informada").max(200, "A descrição deve ter no máximo 200 caracteres"),
  })

  type TaskFormValues = z.infer<typeof taskFormSchema>
  type TaskData = TaskFormValues & {
    status: "PENDENTE" | "CONCLUIDA"
  }

  const defaultValues: Partial<TaskFormValues> = {
    title: task?.title ?? "",
    description: task?.description ?? ""
  }

  const form = useForm<TaskFormValues>({
    resolver: zodResolver(taskFormSchema),
    defaultValues
  })


  async function createTask(data: TaskData) {
    await api.post("task", data)
  }

  async function editTask(data: Task) {
    await api.put(`task/${data.id}`, data)
  }

  async function onSubmit(data: TaskFormValues) {

    if (task) {
      const taskDTO = {
        ...task,
        title: data.title,
        description: data.description
      }
      mutateEdit(taskDTO)


    } else {
      const taskDTO = {
        title: data.title,
        description: data.description,
        status: "PENDENTE" as const
      }
      mutateCreate(taskDTO)
    }

    handleModalOpen(false)
  }

  const { mutate: mutateCreate } = useCreateTaskMutation({
    mutationFn: createTask,
    onSettled: form.reset
  })

  const { mutate: mutateEdit } = useChangeTaskMutation({
    mutationFn: editTask
  })

  function handleCancelEdit() {
    handleModalOpen(false)
    form.reset()
  }

  return (
    <Dialog open={modalOpen} onOpenChange={handleModalOpen}>
      {
        !task &&
        <DialogTrigger asChild>
          <Button className="min-w-max">CRIAR TAREFA</Button>
        </DialogTrigger>
      }

      <DialogContent className="sm:max-w-[425px]">
        <Form {...form} >
          <form className='w-full space-y-5' onSubmit={form.handleSubmit(onSubmit)}>
            <DialogHeader>
              <DialogTitle>
                {
                  task ? "Editar tarefa" : "Criar tarefa"
                }
              </DialogTitle>
              <DialogDescription>
                {
                  task ?
                    "Edite os dados necessários. Quando terminar clique em Salvar"
                    :
                    "Insira os dados necessários. Quando terminar clique em Salvar"
                }
              </DialogDescription>
            </DialogHeader>
            <FormField
              control={form.control}
              name="title"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Titulo</FormLabel>
                  <FormControl>
                    <Input {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="description"
              render={({ field }) => (
                <FormItem  >
                  <FormLabel>Descrição</FormLabel>
                  <FormControl>
                    <Textarea {...field} className="max-h-32" />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <DialogFooter className="gap-2">
              {task &&
                <Button
                  type="button"
                  className="w-full"
                  variant={"outline"}
                  onClick={handleCancelEdit}
                >Cancelar</Button>
              }
              <Button type="submit" className="w-full">Salvar</Button>
            </DialogFooter>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  )
}