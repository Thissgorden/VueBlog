import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token:'',
    userInfo:JSON.parse(sessionStorage.getItem("userInfo"))
  },
  mutations: {
    //set 可以通过该方式对state进行赋值
    SET_TOKEN:(state,token) =>{
      state.token = token
      localStorage.setItem("token",token)
    },
    SET_USERINFO:(state,userInfo) => {
      state.userInfo =userInfo
      sessionStorage.setItem("userInfo",JSON.stringify(userInfo))
    },
    REMOVE_INFO: (state) =>{
      state.token =''
      state.userInfo = {}
      localStorage.removeItem("token");
      sessionStorage.removeItem("userInfo");
    }
  },
  getters:{
    //get 同上 用于取值
    getUser: state => {
      return state.userInfo
    },
    getToken: state => {
      return state.token
    }
  },
  actions: {
  },
  modules: {
  }
})
