import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:3000/api/images/";

class ImageService {
  getUserImages() {
    return axios.get(API_URL + "list", { headers: authHeader() });
  }
}

export default new ImageService();
