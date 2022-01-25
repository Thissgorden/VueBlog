import axios from 'axios'
import Element from 'element-ui'
import store from './store'
import router from './router'

axios.defaults.baseURL = 'http://127.0.0.1:8081'

//前置拦截
axios.interceptors.request.use(config => {
    config.headers['Authorization'] = localStorage.getItem("token");
    return config;
});
//后置拦截
axios.interceptors.response.use(response => {
        let res = response.data;

        if (res.code === 200 ||res.code === 203) {
            return response;
        } else {
            Element.Message.error(response.data.msg, {duration: 3 * 1000});

            return Promise.reject(response.data.msg);
        }
    },
        //后置全局error处理
        error => {
    if(error.response.data){//ERROR为空则先赋值
        error.message = error.response.data.msg
    }
            //401未授权
    if(error.response.status == 401){
        store.commit("REMOVE_INFO");
        router.push("/login");
    }
    else if(error.response.status == 403){
        router.push("/");
    }

    Element.Message.error(error.message,{duration: 3*1000});
    return Promise.reject(error);
    }
);