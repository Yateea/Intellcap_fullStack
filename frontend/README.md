# Front-end INTELLCAP

Interface Next.js/TypeScript connectée au back-end Spring Boot existant.

```bash
pnpm install
pnpm dev
```

Le back-end doit être disponible sur `http://localhost:8080`. Pour une autre URL, copiez `.env.example` vers `.env.local` et modifiez `BACKEND_URL`.

Le proxy Next.js `/api/backend/*` évite toute modification CORS du back-end.
