import { router } from "../router";
import ImageService from "../services/image.service";
import Vue from "vue";

export const imageOperations = {
  state: {
    images: [],
    tag: null
  },
  actions: {
    getUserImages({ commit }) {
      ImageService.getUserImages().then(response => {
        commit("setImages", response.data);
      });
    },
    setTag({ commit }, payload) {
      return new Promise((resolve, reject) => {
        ImageService.SetTags(payload.imageId, payload.tagName)
          .then(response => {
            commit("keepTag", response.data);
            commit("addNewTag", response.data);
            resolve(response.data);
          })
          .catch(response => {
            reject(response);
          });
      });
    }
  },
  mutations: {
    keepTag(state, payload) {
      state.tag = payload.tags[payload.tags.length - 1];
    },
    addNewTag(state, payload) {
      if (!state.images.length) {
        state.images = payload;
      } else {
        for (let i = 0; i < state.images.length; i++) {
          if (state.images[i].id == payload.id) {
            Vue.set(state.images, i, payload);
          }
        }
      }
    },
    setImages(state, images) {
      state.images = images;
    },
    addImage(state, images) {
      state.images.push(images);
    }
  },
  getters: {
    getLastInsertedTag(state) {
      return state.tag;
    },
    getImages(state) {
      return state.images;
    },
    getTags(state, payload) {
      let tags = [];
      state.images.forEach(image => {
        if (image.id === payload) {
          this.tags = image.tags[image.tags.length - 1];

          return tags;
        }
      });
    }
  }
};
