import Axios from "axios";
import Vue from "vue";
import Vuex from "vuex";

import { auth } from "./auth.module";
import { imageOperations } from "./image.module";
import authHeader from "../services/auth-header";
Vue.use(Vuex);

export default new Vuex.Store({
  state: {},
  mutations: {},
  actions: {},
  getters: {},
  modules: {
    auth,
    imageOperations
  }
});
