import Vue from "vue"
import VueRouter from "vue-router"
// 준호 라우터
import DirectQuery from "../views/Sidebar/DirectQuery.vue"
import MyAlerts from "../views/Sidebar/MyAlerts.vue"
import Settings from "../views/Sidebar/Settings.vue"
import Message from "../views/Message/Message.vue"
// import Received from "../views/Message/Received.vue"
// import Sent from "../views/Message/Sent.vue"

// 기하 라우터
import Login from "../views/Login/Login.vue"
import Signup from "../views/Login/Signup.vue"
import SignupNext from "../views/Login/SignupNext.vue"
import FindId from "../views/Login/FindId.vue"
import FindIdNext from "../views/Login/FindIdNext.vue"
import FindPassword from "../views/Login/FindPassword.vue"
import Profile from "../views/Profile/Profile.vue"
import UserModify from "../views/Profile/UserModify.vue"

Vue.use(VueRouter)

const routes = [
  // 수정 라우터
  
  {
    path: "/",
    name: "Main",
    component: () =>
    import("../views/Main.vue"),
  },
  {
    path: "/admin",
    name: "Admin",
    component: () => import("../views/Admin.vue"),
    children : [
      {
        path: "/admin/users",
        name: "AdminUserList",
        component: () => import("../components/Admin/UserList.vue")
      },
      {
        path: "/admin/posts",
        name: "AdminPostList",
        component: () => import("../components/Admin/PostList.vue")
      },
      {
        path: "/admin/qna",
        name: "AdminQnAList",
        component: () => import("../components/Admin/QnAList.vue")
      },
    ],
    redirect: () => {
      return "/admin/users";
    },
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
    path: '/message',
    name: 'Message',
    component: Message,
  },
  // {
  //   path: '/received',
  //   name: 'Received',
  //   component: Received,
  // },
  // {
  //   path: '/sent',
  //   name: 'Sent',
  //   component: Sent,
  // },

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
  {
    path: '/user/modify',
    name: 'UserModify',
    component: UserModify,
  },
    // 용직
    
  {
    path: '/mycrop',
    name: 'MyCrop',
    component: () =>
    import("../views/MyCrop/MyCrop.vue"),
    children : [
      {
        path: "/mycrop/list",
        name: "MyCropList",
        component: () => import("../components/MyCrop/MyCropList.vue")
      },
      {
        path: "/mycrop/detail/:no",
        name: "MyCropDetail",
        component: () => import("../components/MyCrop/MyCropDetail.vue")
      },
      {
        path: "/mycrop/insert",
        name: "MyCropInsert",
        component: () => import("../components/MyCrop/MyCropInsert.vue")
      },
    ],
    redirect: () => {
      return "/mycrop/list";
    },
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
