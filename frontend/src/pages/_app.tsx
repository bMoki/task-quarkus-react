import '@/styles/styles.css'
import 'tailwindcss/tailwind.css'
import 'dayjs/locale/pt-br'
import { ThemeProvider } from 'next-themes'
import type { AppProps } from 'next/app'
import { Inter } from 'next/font/google'
import { QueryClientProvider, QueryClient } from '@tanstack/react-query'
import { ReactQueryDevtools } from '@tanstack/react-query-devtools'
import { Toaster } from '@/components/Toast/Toaster'

const inter = Inter({
  subsets: ['latin'],
  weight: ['100', '200', '300', '400', '500', '600', '700', '800', '900'],
  variable: '--font-inter'
})

const queryClient = new QueryClient()

export default function App({ Component, pageProps }: AppProps) {
  return (
    <QueryClientProvider client={queryClient}>
      <ThemeProvider attribute="class" defaultTheme="system" enableSystem>
        <style jsx global>{`
        html {
          font-family: ${inter.style.fontFamily};
        }
        `}</style>
        <main className={`${inter.variable} font-sans`}>
          <Component {...pageProps} />
          <Toaster />
        </main>
      </ThemeProvider>
      <ReactQueryDevtools />
    </QueryClientProvider>
  )
}
