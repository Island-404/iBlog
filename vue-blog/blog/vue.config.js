module.exports = {
  transpileDependencies: ["vuetify"],
  devServer: {
    proxy: {
      "/api": {
        target: "http://47.106.155.215:8081",
        changeOrigin: true,
        pathRewrite: {
          "^/api": ""
        }
      }
      
    },
    disableHostCheck: true
  }
};
