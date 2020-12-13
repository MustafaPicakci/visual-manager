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
        console.log(response.data);
      });
    },
    setTag({ commit }, payload) {
      console.log("Settag");

      ImageService.SetTags(payload.imageId, payload.tagName).then(response => {
        commit("keepTag", response.data);
        commit("addNewTag", response.data);
      });
      /*return new Promise((resolve,reject)=>{
        resolve(payload);
      })*/
      //  return tag;
    }
  },
  mutations: {
    keepTag(state, payload) {
      state.tag = payload.tags[payload.tags.length - 1];
    },
    addNewTag(state, payload) {
      if (!state.images.length) {
        state.images = payload;
        console.log(state.images.length);
      } else {
        for (let i = 0; i < state.images.length; i++) {
          console.log("for");
          console.log(state.images[i]);
          if (state.images[i].id == payload.id) {
            Vue.set(state.images, i, payload);
          }
        }
      }

      state.images.forEach(image => {
        /*if (image.id === payload.id) {
          Vue.set(state.images, image, payload);
          console.log(state.images);
        }*/
      });
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
      console.log("getters  getImages");
      return state.images;
    },
    getTags(state, payload) {
      console.log("getterrss");
      let tags = [];
      state.images.forEach(image => {
        if (image.id === payload) {
          this.tags = image.tags[image.tags.length - 1];
          console.log("sadasf sooooon");
          console.log(this.tags);
          return tags;
        }
      });
    }
  }
};
