import Vue from "vue"
import VueRouter from "vue-router"
import Home from "../views/Home.vue"
// 준호 라우터
import SidebarMain from "../views/Sidebar/SidebarMain.vue"
import DirectQuery from "../views/Sidebar/DirectQuery.vue"
import MyAlerts from "../views/Sidebar/MyAlerts.vue"
import Settings from "../views/Sidebar/Settings.vue"

// 기하 라우터
import Login from "../views/Login/Login.vue"
import Signup from "../views/Login/Signup.vue"
import SignupNext from "../views/Login/SignupNext.vue"
import FindId from "../views/Login/FindId.vue"
import FindIdNext from "../views/Login/FindIdNext.vue"
import FindPassword from "../views/Login/FindPassword.vue"
import Profile from "../views/Profile/Profile.vue"

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
  // 수정 라우터
  
  {
    path: "/main",
    name: "Main",
    component: () =>
    import("../views/Main.vue"),
  },
  {
    path: "/community",
    name: "Community",
    component: () => import("../views/Community.vue"),
    children : [
      {
        path: "/community/list",
        name: "CommunityList",
        component: () => import("../components/Community/CommunityList.vue")
      },
      {
        path: "/community/write",
        name: "CommunityWrite",
        component: () => import("../components/Community/CommunityWrite.vue")
      },
      {
        path: "/community/modify/:no",
        name: "CommunityModify",
        component: () => import("../components/Community/CommunityModify.vue")
      },
      {
        path: "/community/detail/:no",
        name: "CommunityDetail",
        component: () => import("../components/Community/CommunityDetail.vue")
      },
    ],
    redirect: () => {
      return "/community/list";
    },
  },
  {
    path: "/dict",
    name: "Dictionary",
    component: () => import("../views/Dictionary.vue"),
    children : [
      {
        path: "/dict/list",
        name: "DictionaryList",
        component: () => import("../components/Dictionary/DictList.vue")
      },
      {
        path: "/dict/detail/:no",
        name: "DictionaryDetail",
        component: () => import("../components/Dictionary/DictDetail.vue")
      },
    ],
    redirect: () => {
      return "/dict/list";
    },
  },
  // 준호 라우터

  {
    path: '/directquery',
    name: 'DirectQuery',
    component: DirectQuery,
  },
  {
    path: '/myalerts',
    name: 'MyAlerts',
    component: MyAlerts,
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

  // 기하 라우터
  {
    path: '/login',
    name: 'Login',
    component: Login,
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup,
  },
  {
    path: '/signupnext',
    name: 'SignupNext',
    component: SignupNext,
  },
  {
    path: '/findid',
    name: 'FindId',
    component: FindId,
  },
  {
    path: '/findidnext',
    name: 'FindIdNext',
    component: FindIdNext,
  },
  {
    path: '/findpassword',
    name: 'FindPassword',
    component: FindPassword,
  },
  {
    path: '/profile/:usernumber',
    name: 'Profile',
    component: Profile,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
