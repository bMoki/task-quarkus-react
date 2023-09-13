import { UseFormReturn } from "react-hook-form";
import { Form, FormControl, FormField, FormItem, FormLabel } from "./Form";
import { Input } from "./Input";

interface Props {
  form: UseFormReturn<{
    search: string;
  }, any, undefined>
}

export function SearchForm({ form }: Props) {
  return (
    <Form {...form} >
      <form className='w-full'>
        <FormField
          control={form.control}
          name="search"
          render={({ field }) => (
            <FormItem  >
              <FormLabel>Pesquisar</FormLabel>
              <FormControl>
                <Input {...field} />
              </FormControl>
            </FormItem>
          )}
        />
      </form>
    </Form>
  )
}