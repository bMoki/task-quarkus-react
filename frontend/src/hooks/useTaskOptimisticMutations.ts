import { toast } from "@/components/Toast/use-toast";
import { Task } from "@/interfaces/Task";
import { MutationFunction, useMutation, useQueryClient } from "@tanstack/react-query";

type CreateTaskData = {
  title: string
  description: string,
  status: "PENDENTE"
}

interface CreateTaskMutationProps {
  mutationFn: MutationFunction<unknown, CreateTaskData>;
  onSettled: () => void;
}

export function useCreateTaskMutation({ mutationFn, onSettled }: CreateTaskMutationProps) {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn,
    onMutate: async (newTask) => {
      await queryClient.cancelQueries({ queryKey: ["tasks"] });

      const previousTodos = queryClient.getQueryData(["tasks"]);

      queryClient.setQueryData(["tasks"], (old: any) => [
        {
          id: "new",
          title: newTask.title,
          description: newTask.description,
          status: newTask.status,
        },
        ...old,
      ]);

      return { previousTodos };
    },

    onError: (error: any, _variables, context) => {
      queryClient.setQueryData(["tasks"], context?.previousTodos);
      toast({
        variant: "destructive",
        title: "Uh oh! Alguma coisa deu errado.",
        description: "Teve algum problema com sua requisição.",
      })
    },
    onSettled: () => {
      onSettled()
      queryClient.invalidateQueries({ queryKey: ['tasks'] })
    },
  });

}


interface ChangeTaskMutationProps {
  mutationFn: MutationFunction<void, Task>
}

export function useChangeTaskMutation({ mutationFn }: ChangeTaskMutationProps) {
  const queryClient = useQueryClient()
  return useMutation({
    mutationFn,

    onMutate: async (changedTask) => {
      await queryClient.cancelQueries({ queryKey: ["tasks"] });

      const previousTasks = queryClient.getQueryData(["tasks"]);

      queryClient.setQueryData(["tasks"], (old: any) => {
        const tasks = old?.map((task: Task) => {
          if (task.id === changedTask.id) {
            return {
              ...task,
              status: status
            };
          }
          return task;
        });
        return tasks;
      });

      return { previousTasks };
    },

    onError: (error: any, _variables, context) => {
      queryClient.setQueryData(['tasks'], context?.previousTasks)
      toast({
        variant: "destructive",
        title: "Uh oh! Alguma coisa deu errado.",
        description: "Teve algum problema com sua requisição.",
      })
    },

    onSettled: () => {
      queryClient.invalidateQueries({ queryKey: ['tasks'] })
    },
  })
}

interface DeleteTaskMutationProps {
  mutationFn: MutationFunction<void, number>
}

export function useDeleteTaskMutation({ mutationFn }: DeleteTaskMutationProps) {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn,
    onMutate: async (id) => {
      await queryClient.cancelQueries({ queryKey: ["tasks"] });

      const previousTodos = queryClient.getQueryData(["tasks"]);

      queryClient.setQueryData(["tasks"], (old: any) => {
        const tasks = old.filter((task: Task) => task.id !== id);
        return tasks;
      });

      return { previousTodos };
    },

    onError: (error: any, _variables, context) => {
      queryClient.setQueryData(["tasks"], context?.previousTodos);
      toast({
        variant: "destructive",
        title: "Uh oh! Alguma coisa deu errado.",
        description: "Teve algum problema com sua requisição.",
      })
    },
    onSettled: () => {
      queryClient.invalidateQueries({ queryKey: ["tasks"] });
    },
  });
}