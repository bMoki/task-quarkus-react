import { Task } from "@/interfaces/Task";
import { api } from "@/lib/axios";
import { useQuery } from '@tanstack/react-query'

async function getTasks() {
  const { data } = await api.get("task");

  const tasks: Task[] = data

  return tasks
}

export function useQueryTask() {
  return useQuery(['tasks'], () => getTasks(), {
    staleTime: 1000 * 60  //1min
  })
}