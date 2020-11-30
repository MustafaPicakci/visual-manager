import Axios from "axios";
import Vue from "vue";
import Vuex from "vuex";

import { auth } from "./auth.module";
import authHeader from "../services/auth-header";
Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    images: []
  },
  mutations: {
    getImages(state) {
      Axios.get("http://localhost:3000/api/upload/all", {
        headers: authHeader()
      }).then(response => {
        console.log(response.data)
        for (image in response.data) {
         // this.state.images.push(image);
        }
      });
    }
  },
  actions: {
    getAllImages({ dispatch, commit }) {
      commit("getImages");
    }
  },
  getters: {},
  modules: {
    auth
  }
});
