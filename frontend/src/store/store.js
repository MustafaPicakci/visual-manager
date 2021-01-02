import Vue from "vue";
import Vuex from "vuex";

import { auth } from "./auth.module";
import { imageOperations } from "./image.module";
import userService from "../services/user.service";
import adminService from "../services/admin.service";
Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    users: []
  },
  actions: {
    deleteUser({ commit }, payload) {
      return adminService.deleteUser(payload.id).then(response => {
        if (response.status == 200) {
          commit("deleteUser", payload.index);
        }
      });
    },
    blockUser({ commit }, payload) {
      return adminService.blockUser(payload).then(response => {
        if (response.status == 200) {
          commit("changeUserStatus", response.data);
        }
      });
    },
    unBlockUser({ commit }, payload) {
      return adminService.unBlockUser(payload).then(response => {
        if (response.status == 200) {
          commit("changeUserStatus", response.data);
        }
      });
    },
    listUsers({ commit }) {
      adminService.listUsers().then(response => {
        commit("setUsers", response.data);
      });
    },
    resetPassword({ commit }, email) {
      return userService.resetPassword(email);
    },
    newPassword({ commit }, payload) {
      return userService.newPassword(payload);
    }
  },
  mutations: {
    deleteUser(state, payload) {
      Vue.delete(state.users, payload);
    },
    setUsers(state, payload) {
      console.log(payload);
      this.state.users = payload;
    },
    changeUserStatus(state, payload) {
      console.log(payload);
      for (let i = 0; i < state.users.length; i++) {
        if (state.users[i].id == payload.id) {
          Vue.set(state.users, i, payload);
        }
      }
    }
  },

  getters: {
    getUsers(state) {
      return state.users;
    }
  },
  modules: {
    auth,
    imageOperations
  }
});
