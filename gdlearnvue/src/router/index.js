import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import BlogDetail from '../views/BlogDetail.vue'
import BlogEdit from '../views/BlogEdit.vue'
import BlogAdd from "@/views/BlogAdd";
import Gacha from "@/views/Gacha";
import Registry from "@/views/UserCenter/Registry.vue"
import RegistryP from "@/views/UserCenter/RegistryProcess.vue"

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Index',
        redirect: { name: 'Blogs' }
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/blogs',
        name: 'Blogs',
        // 懒加载
        component: () => import('../views/Blogs.vue')
    },
    {
        path: '/blog/add', // 注意放在 path: '/blog/:blogId'之前
        name: 'BlogAdd',
        meta: {
            requireAuth: true
        },
        component: BlogAdd
    },
    {
        path: '/blog/:blogId',
        name: 'BlogDetail',
        component: BlogDetail
    },
    {
        path: '/blog/:blogId/edit',
        name: 'BlogEdit',
        meta: {
            requireAuth: true
        },
        component: BlogEdit
    },
    {
        path: '/gacha',
        name: 'Gacha',
        component: Gacha
    },
    {
        path: '/registry',
        name: 'registry',
        component: Registry
    },
    {
        path: '/rgP',
        name: 'RegistryP',
        component: RegistryP
    },
];
const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})
export default router
