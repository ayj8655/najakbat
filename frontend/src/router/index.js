import Vue from "vue"
import VueRouter from "vue-router"
import Home from "../views/Home.vue"
import MyAlerts from "../views/Sidebar/MyAlerts.vue"
import DirectQuery from "../views/Sidebar/DirectQuery.vue"
import Settings from "../views/Sidebar/Settings.vue"
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
  // 준호 라우터
  {
    path: '/MyAlerts',
    name: 'MyAlerts',
    component: MyAlerts,
  },
  {
    path: '/directquery',
    name: 'DirectQuery',
    component: DirectQuery,
  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
  },
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
