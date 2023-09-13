import { Button } from "@/components/Button"
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/DropDownMenu"
import { MoreHorizontal, MoreVertical, Pencil, Trash } from "lucide-react"
import { useState } from "react"

interface Props {
  openDeleteModal: () => void;
  openEditModal: () => void;
  openDetailsModal: () => void;
}

export function TaskActions({ openDeleteModal, openEditModal, openDetailsModal }: Props) {

  const [dropDownMenuOpen, setDropDownMenuOpen] = useState(false)

  return (
    <DropdownMenu open={dropDownMenuOpen} onOpenChange={setDropDownMenuOpen}>
      <DropdownMenuTrigger asChild>
        <Button
          variant="ghost"
          className="flex h-8 w-8 p-0 data-[state=open]:bg-muted"
        >
          <MoreVertical className="h-5 w-5" />
          <span className="sr-only">Open menu</span>
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent align="end" className="w-[160px]">
        <DropdownMenuItem className="flex justify-between" onClick={openDetailsModal}>
          Detalhes <MoreHorizontal className="h-4 w-4 text-foreground" />
        </DropdownMenuItem>
        <DropdownMenuSeparator />
        <DropdownMenuItem className="flex justify-between" onClick={openEditModal}>
          Editar <Pencil className="h-4 w-4 text-foreground" />
        </DropdownMenuItem>
        <DropdownMenuItem className="flex justify-between" onClick={openDeleteModal}>
          Deletar <Trash className="h-4 w-4 text-destructive dark:text-inherit" />
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  )
}