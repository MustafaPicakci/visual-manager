import Vue from "vue";
import Vuex from "vuex";

import { auth } from "./auth.module";
import { imageOperations } from "./image.module";
import userService from "../services/user.service";
Vue.use(Vuex);

export default new Vuex.Store({
  state: {},
  mutations: {},
  actions: {
    resetPassword({ commit }, email) {
      return userService.resetPassword(email);
    },
    newPassword({ commit }, payload) {
      return userService.newPassword(payload);
    }
  },
  getters: {},
  modules: {
    auth,
    imageOperations
  }
});
