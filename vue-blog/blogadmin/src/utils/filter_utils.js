import Vue from 'vue'
import axios from 'axios'
import Element from 'element-ui'
import router from '../router'
import store from '../store'
Vue.filter("formatDate", function formatDate(value) {
  var date = new Date(value);
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var day = date.getDate();
  if (month < 10) {
    month = "0" + month;
  }
  if (day < 10) {
    day = "0" + day;
  }
  return year + "-" + month + "-" + day;
});
Vue.filter("formatDateTime", function formatDateTime(value) {
  var date = new Date(value);
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var day = date.getDate();
  var hours = date.getHours();
  var minutes = date.getMinutes();
  if (month < 10) {
    month = "0" + month;
  }
  if (day < 10) {
    day = "0" + day;
  }
  return year + "-" + month + "-" + day + " " + hours + ":" + minutes;
});

//拦截器
axios.interceptors.request.use(config => {
  return config
})

axios.interceptors.response.use(resp => {
  

  if (resp.data.code == 200) {
    return resp
  } 
  else if (resp.data.code == 20003) {
    store.commit("REMOVE_INFO")
    router.push("/")
    Element.Message.error(resp.data.msg);

  }
  else if(resp.data.code == 20004){
    store.commit("REMOVE_INFO")
    router.push("/")
    Element.Message.error(resp.data.msg);
  } else if(resp.data.code == 400){
    Element.Message.error(resp.data.msg);
  }
  else{
    Element.Message.error("操作失败");

    return Promise.reject(resp.data.msg)
  }
},
//其他错误情况
error=>{
  if (error.response.data) {
    error.message = error.response.data.msg;
  }
  if (error.response.status === 401) {
    store.commit("REMOVE_INFO")
    router.push("/")
  } 
  Element.Message.error(error.message);
  return Promise.reject(error)
}

)

router.beforeEach((to, from, next) => {

    
    const token = localStorage.getItem("token")

    if(token){
      if(to.path == '/'){
        next({ path: "/home" });
      } else{
        next();
      }
    } else{
      localStorage.setItem("token","")
      localStorage.setItem("userInfo", JSON.stringify(''))
      if(to.path == '/'){

        next();
      } else{
        next({ path: "/" });
      }
    }
    
  } 
  
);
