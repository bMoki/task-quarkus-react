import { useQueryTask } from "@/hooks/useQueryTask";
import { TaskCard } from "./TaskCard";
import { MoreVertical, ServerCrash, Sticker } from "lucide-react";
import { Skeleton } from "./Skeleton";

interface Props {
  filter: string
}

export function TaskList({ filter }: Props) {
  const { data, isLoading, error } = useQueryTask();
  const filteredTasks = data?.filter((task) => task.title.startsWith(filter ?? ""))

  if (isLoading || !data) {
    return (
      <div className="lg:w-2/4 w-full px-8 space-y-4 mt-8 pb-6">
        <div className="flex items-center space-x-4 rounded-md border border-border p-4 h-24" >
          <Skeleton className="w-6 h-6 rounded-sm" />
          <div className="flex-1 space-y-1 ">
            <Skeleton className="w-28 h-3" />
            <Skeleton className="w-64 h-3" />
          </div>
          <MoreVertical className="h-5 w-5" />
        </div>
        <div className="flex items-center space-x-4 rounded-md border border-border p-4 h-24" >
          <Skeleton className="w-6 h-6 rounded-sm" />
          <div className="flex-1 space-y-1 ">
            <Skeleton className="w-28 h-3" />
            <Skeleton className="w-64 h-3" />
          </div>
          <MoreVertical className="h-5 w-5" />
        </div>
      </div>

    )
  }

  if (data?.length < 1) {
    return (
      <div className="flex flex-col items-center gap-4">
        <Sticker className="w-12 h-12 text-gray-500" />
        <h2 className="text-foreground/60 text-3xl font-semibold">
          Nenhuma tarefa!
        </h2>
      </div>
    )
  }

  if (error) {
    return (
      <div className="flex flex-col items-center gap-4">
        <ServerCrash className="w-12 h-12 text-gray-500" />
        <h2 className="text-foreground/60 text-3xl font-semibold">
          Ocorreu um erro no servidor!
        </h2>
      </div>
    )
  }

  return (
    <section className='lg:w-2/4 w-full px-8 space-y-4 mt-8 pb-6'>
      {filteredTasks?.map(task => (
        <TaskCard key={task.id} task={task} />
      ))}
    </section>
  )
}