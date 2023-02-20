/** @type {import('next').NextConfig} */
const nextConfig = {
    reactStrictMode: false,
    swcMinify: true,
    compiler: {
        styledComponents: true
    },
    experimental: {
        modularizeImports: {
            '@mui/icons-material': {
                transform: '@mui/icons-material/{{member}}',
            },
            '@mui/material': {
                transform: '@mui/material/{{member}}',
            },
        }
    },
    async rewrites() {
        return [
            {
                source: '/api/:path*',
                destination: 'http://localhost:8080/api/:path*',
            },
            {
                source: '/auth/:path*',
                destination: 'http://localhost:8080/auth/:path*',
            },
        ]
    }
}

module.exports = nextConfig
