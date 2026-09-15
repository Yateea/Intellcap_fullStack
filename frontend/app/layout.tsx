import type { Metadata } from "next";
import "./globals.css";
export const metadata: Metadata = { title: "INTELLCAP — Deep Tech Innovation", description: "L'innovation profonde, de l'ambition à l'impact." };
export default function RootLayout({children}:{children:React.ReactNode}) { return <html lang="fr"><body>{children}</body></html>; }
