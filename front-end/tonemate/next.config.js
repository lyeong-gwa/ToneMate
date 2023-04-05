/**
 * @type {import('next').NextConfig}
 */

const nextConfig = {
  images: {
    domains: ["search.pstatic.net"],
    loader: "imgix",
    path: "https://search.pstatic.net/",
    deviceSizes: [640, 750, 828, 1080, 1200, 1920, 2048, 3840],
    imageSizes: [16, 32, 48, 64, 96, 128, 256, 384],
  },
  reactStrictMode: true,
  output: 'standalone',
};

module.exports = nextConfig;
