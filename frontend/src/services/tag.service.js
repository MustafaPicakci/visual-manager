import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:3000/api/tags/";

class TagService {
  ListImageTags(data) {
    let image = new FormData();
    image.append("id", data.id);
    return axios.post(API_URL + "listImageTags", image, {
      headers: authHeader(),
      "Content-Type": "multipart/form-data"
    });
  }
}
axios.prototype;

export default new TagService();
