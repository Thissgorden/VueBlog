import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";
import axios from 'axios';
import Element from 'element-ui'
import router from './router/index'
import store from './store'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

import "element-ui/lib/theme-chalk/index.css"
import "./axios"
import "./permission"

Vue.use(mavonEditor)
Vue.use(Element)
Vue.prototype.$axios = axios;
Vue.use(VueRouter);

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");