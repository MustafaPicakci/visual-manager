import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:3000/api/user/";

class UserService {
  changePassword(userId, username) {
    console.log(authHeader())
    let data = new FormData();
    data.append("id", userId);
    data.append("username", username);

    return axios.post(API_URL + "change/username", data, {
      headers: authHeader()
    });
  }
}

export default new UserService();
