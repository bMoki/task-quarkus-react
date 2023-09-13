import { ModeToggle } from '@/components/ModeToggle'
import { zodResolver } from "@hookform/resolvers/zod"
import { useForm, } from 'react-hook-form'
import { z } from 'zod'
import { FormTaskDialog } from '@/components/FormTaskDialog'
import { SearchForm } from '@/components/SearchForm'
import { TaskList } from '@/components/TaskList'
import { useState } from 'react'

export default function Home() {

  const searchTaskFormSchema = z.object({
    search: z.string()
  })

  type SearchTaskFormValues = z.infer<typeof searchTaskFormSchema>

  const form = useForm<SearchTaskFormValues>({
    resolver: zodResolver(searchTaskFormSchema)
  })

  const search = form.watch("search")

  const [createModalOpen, setCreateModalOpen] = useState(false)

  return (
    <main
      className='relative flex min-h-screen flex-col items-center'
    >
      <header className='sticky top-0  w-full bg-background/95 border-b border-border  flex flex-col items-center mb-12'>
        <div className='flex justify-between pt-20 pb-14 px-8 items-center w-full lg:w-2/4'>
          <h1 className='text-foreground md:text-3xl text-xl font-semibold'>Gerenciador de tarefas</h1>
          <ModeToggle />
        </div>
        <div className="flex gap-2 py-2 items-end lg:w-2/4 w-full -mb-8 px-8">

          <SearchForm form={form} />

          <FormTaskDialog
            handleModalOpen={setCreateModalOpen}
            modalOpen={createModalOpen}
          />
        </div>
      </header>

      <TaskList filter={search} />
    </main>
  )
}
