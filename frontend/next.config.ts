import type { NextConfig } from "next";
const backend = process.env.BACKEND_URL || "http://localhost:8888";
const nextConfig: NextConfig = { async rewrites() { return [{ source: "/api/backend/:path*", destination: `${backend}/api/:path*` }]; } };
export default nextConfig;
