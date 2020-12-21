import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:3000/api/images/";

class ImageService {
  getUntagedImages(params) {
    console.log(params);
    return axios.get(API_URL + "list", { params, headers: authHeader() });
  }
  getAllUserImages(params) {
    console.log(params);
    return axios.get(API_URL + "getAll", { params, headers: authHeader() });
  }

  SetTags(payload) {
    let data = new FormData();
    data.append("images", payload.imageId);
    data.append("tagName", payload.tagName);
    console.log(data.images);
    return axios.post(API_URL + "set/tag", data, {
      headers: authHeader(),
      "Content-Type": "multipart/form-data"
    });
  }

  unlinkTag(payload) {
    let data = new FormData();
    data.append("images", payload.images);
    data.append("tags", payload.tagId);

    return axios.post(API_URL + "unlinkTag", data, {
      headers: authHeader(),
      "Content-Type": "multipart/form-data"
    });
  }
  deleteImage(imageId) {
    let data = new FormData();
    data.append("images", imageId);

    return axios.post(API_URL + "delete", data, {
      headers: authHeader(),
      "Content-Type": "multipart/form-data"
    });
  }
}

export default new ImageService();
