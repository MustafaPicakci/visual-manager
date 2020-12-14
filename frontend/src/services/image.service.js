import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:3000/api/images/";

class ImageService {
  getUserImages() {
    return axios.get(API_URL + "list", { headers: authHeader() });
  }
  SetTags(payload) {
    let data = new FormData();
    data.append("images", payload.imageId);
    data.append("tagName", payload.tagName);
    console.log(data.images)
    return axios.post(API_URL + "set/tag", data, {
      headers: authHeader(),
      "Content-Type": "multipart/form-data"
    });
  }

  unlinkTag(payload) {
    console.log(payload)
    let data = new FormData();
    data.append("images", payload.images);
    data.append("tags", payload.tagId);
    console.log(data)
    return axios.post(API_URL + "unlinkTag", data, {
      headers: authHeader(),
      "Content-Type": "multipart/form-data"
    });
  }
}

export default new ImageService();
