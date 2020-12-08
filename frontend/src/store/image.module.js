import { router } from "../router";
import ImageService from "../services/image.service";

export const imageOperations = {
  state: {
    images: []
  },
  actions: {
    getUserImages({ commit }) {
      ImageService.getUserImages().then(response => {
        commit("addImages", response.data);
        console.log(response.data);
      });
    }
  },
  mutations: {
    addImages(state, images) {
      state.images = images;
    }
  },
  getters: {
    getImages(state){
      return state.images;
    }
  }
};
