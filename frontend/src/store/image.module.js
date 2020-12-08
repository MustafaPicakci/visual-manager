import { router } from "../router";
import ImageService from "../services/image.service";

export const imageOperations = {
  state: {
    images: []
  },
  actions: {
    getUserImages({ commit }) {
      ImageService.getUserImages().then(response => {
        commit("setImages", response.data);
        console.log(response.data);
      });
    }
  },
  mutations: {
    setImages(state, images) {
      state.images = images;
    },
    addImage(state, images) {
      state.images.push(images);
    }
  },
  getters: {
    getImages(state) {
      return state.images;
    }
  }
};
