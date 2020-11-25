let proxyObj = {};
const CompressionPlugin = require("compression-webpack-plugin");
// proxyObj['/ws'] = {
//     ws: true,
//     target: "ws://localhost:8089/api"
// };
// proxyObj['/'] = {
//     ws: false,
//     target: 'http://localhost:8089/api',
//     changeOrigin: true,
//     pathRewrite: {
//         '^/': ''
//     }
// }


proxyObj['/ws'] = {
    ws: true,
    target: "ws://8.129.130.233:8085"
};
proxyObj['/'] = {
    ws: false,
    target: 'http://8.129.130.233:8085',
    changeOrigin: true,
    pathRewrite: {
        '^/': ''
    }
}


// proxyObj['/ws'] = {
//     ws: true,
//     target: "ws://localhost:8085"
// };
// proxyObj['/'] = {
//     ws: false,
//     target: 'http://localhost:8085',
//     changeOrigin: true,
//     pathRewrite: {
//         '^/': ''
//     }
// }

module.exports = {
    devServer: {
        host: 'localhost',
        port: 8080,
        proxy: proxyObj
    },
    configureWebpack: config => {
        if (process.env.NODE_ENV === 'production') {
            return {
                plugins: [
                    new CompressionPlugin({
                        test: /\.js$|\.html$|\.css/,
                        threshold: 1024,
                        deleteOriginalAssets: false
                    })
                ]
            }
        }
    }
}
