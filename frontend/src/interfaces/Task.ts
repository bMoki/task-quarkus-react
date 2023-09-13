export interface Task {
  id: number
  title: string
  description: string
  status: "CONCLUIDA" | "PENDENTE"
  createdAt: string
  concludedAt: string
}