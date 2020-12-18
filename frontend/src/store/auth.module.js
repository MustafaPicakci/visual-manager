import AuthService from "../services/auth.service";
import { router } from "../router";
const user = JSON.parse(localStorage.getItem("user"));
const initialState = user
  ? { status: { loggedIn: true }, user }
  : { status: { loggedIn: false }, user: null };

export const auth = {
  namespaced: true,
  state: initialState,
  actions: {
    login({ commit }, user) {
      return AuthService.login(user).then(
        user => {
          commit("loginSuccess", user);
          return Promise.resolve(user);
        },
        error => {
          commit("loginFailure");
          return Promise.reject(error);
        }
      );
    },
    logout({ commit }) {
      AuthService.logout();
      commit("logout");
      router.replace("/login");
    },
    register({ commit }, user) {
      return AuthService.register(user).then(
        response => {
          commit("registerSuccess");
          return Promise.resolve(response.data);
        },
        error => {
          commit("registerFailure");
          return Promise.reject(error);
        }
      );
    },
    initAuth({ dispatch, commit }) {
      if (user) {
        let expirationDate = user.expirationDate;
        let time = new Date().getTime(); //ms cinsinden şu anki zaman

        if (time >= expirationDate) {
          console.log("token süresi geçmiş -- " + time);

          dispatch("logout");
        } else {
          let timerSecond = +expirationDate - time;
          console.log("kalan süre: " + timerSecond);
          dispatch("setTimeoutTimer", timerSecond);
        }
      } else {
        return false;
      }
    },
    setTimeoutTimer({ dispatch }, expiresIn) {
      setTimeout(() => {
        dispatch("logout");
      }, expiresIn);
    }
  },
  mutations: {
    loginSuccess(state, user) {
      state.status.loggedIn = true;
      state.user = user;
    },
    loginFailure(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    logout(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    registerSuccess(state) {
      state.status.loggedIn = false;
    },
    registerFailure(state) {
      state.status.loggedIn = false;
    }
  },
  getters: {
    isLoggedIn(state) {
      return state.status.loggedIn;
    }
  }
};
