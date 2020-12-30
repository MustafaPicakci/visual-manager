import Vue from "vue";
import Router from "vue-router";
import Home from "./views/Home.vue";
import Login from "./views/Login.vue";
import ResetPassword from "./views/ResetPassword.vue";
import NewPassword from "./views/NewPassword.vue";
import Register from "./views/Register.vue";
import Profile from "./views/Profile.vue";
import BoardAdmin from "./views/BoardAdmin.vue";
import BoardModerator from "./views/BoardModerator.vue";
import MyImages from "./views/MyImages.vue";
import store from "./store/store";

Vue.use(Router);

export const router = new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "home",
      component: Home,
      beforeEnter(to, from, next) {
        if (store.getters.isLoggedIn) {
          next();
        } else {
          next("/login");
        }
      }
    },
    {
      path: "/home",
      component: Home
    },
    {
      path: "/login",
      component: Login
    },
    {
      path: "/register",
      component: Register
    },
    {
      path: "/resetPassword",
      component: ResetPassword
    },
    {
      path: "/newPassword",
      component: NewPassword,
      name: "NewPassword",
      props: route => ({ query: route.query.token })
    },
    {
      path: "/profile",
      name: "profile",

      component: Profile
    },
    {
      path: "/admin",
      name: "admin",

      component: BoardAdmin
    },
    {
      path: "/mod",
      name: "moderator",

      component: BoardModerator
    },
    {
      path: "/MyImages",
      name: "MyImages",
      component: MyImages
    }
  ]
});

// router.beforeEach((to, from, next) => {
//   const publicPages = ['/login', '/register', '/home'];
//   const authRequired = !publicPages.includes(to.path);
//   const loggedIn = localStorage.getItem('user');

//   // trying to access a restricted page + not logged in
//   // redirect to login page
//   if (authRequired && !loggedIn) {
//     next('/login');
//   } else {
//     next();
//   }
// });
