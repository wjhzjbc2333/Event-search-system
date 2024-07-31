import axios from 'axios'

const request = axios.create({
    baseURL: 'http://localhost:9090',  //后端接口
    timeout: 30000
})

//request 拦截器
request.interceptors.request.use(
    config => {
        config.headers['Content-Type'] = 'application/json;charset=utf-8';
        //let user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : null
        //config.headers['token'] = user?.token  //请求头参数
        
        return config
    },
    error => {
        console.error('request error: ' + error)
        return Promise.reject(error)
    }
);

//response 拦截器
request.interceptors.response.use(
    response => {
        let res = response.data;

        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        return res;
    },
    error => {
        console.error('response error: ' + error)
        return Promise.reject(error)
    }
)

export default request 