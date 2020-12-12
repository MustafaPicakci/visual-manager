import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:3000/api/images/";

class ImageService {
  getUserImages() {
    return axios.get(API_URL + "list", { headers: authHeader() });
  }
  SetTags(imageId, tagName) {
    var data = new FormData();
    data.append("images", imageId);
    data.append("tagName", tagName);
    console.log(imageId + tagName);
    return axios.post(API_URL + "set/tag", data, {
      headers: authHeader(),
      "Content-Type": "multipart/form-data"
    });
  }
}

export default new ImageService();
