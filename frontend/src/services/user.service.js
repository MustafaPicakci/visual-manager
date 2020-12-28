import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:3000/api/user/";

class UserService {
  changeProfilePhoto(userId, username, password, file) {
    console.log(username)
    let data = new FormData();
    data.append("id", userId);
    data.append("username", username), data.append("password", password);
    data.append("file", file);
    return axios.post(API_URL + "change/profilePhoto", data, {
      headers: authHeader(),
      "Content-Type": "multipart/form-data"
    });
  }
  changeUsername(userId, username, password) {
    let data = new FormData();
    data.append("id", userId);
    data.append("username", username);
    data.append("password", password);

    return axios.post(API_URL + "change/username", data, {
      headers: authHeader()
    });
  }
  getTotalImages(userId) {
    let params = {
      userId
    };
    return axios.get(API_URL + "totalImages", {
      params,
      headers: authHeader()
    });
  }
}

export default new UserService();
