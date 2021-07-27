import Vue from "vue"
import VueRouter from "vue-router"
import Home from "../views/Home.vue"

import SidebarMain from "../views/Sidebar/SidebarMain.vue"
Vue.use(VueRouter)

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
    import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
  {
    path: "/community",
    name: "Community",
    component: () =>
    import(/* webpackChunkName: "about" */ "../views/Community.vue"),
  },
  // 준호 라우터

  {
    path: '/sidebarmain',
    name: 'SidebarMain',
    component: SidebarMain,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
