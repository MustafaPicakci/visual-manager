import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:3000/api/user/";

class UserService {
  confirmAccount(token) {
    console.log(token)
    return axios.post(
      "http://localhost:3000/api/auth/confirm-account/" + token
    );
  }
  resetPassword(email) {
    return axios.post(
      "http://localhost:3000/api/forgot_password/email/" + email
    );
  }
  newPassword(params) {
    return axios.post(
      "http://localhost:3000/api/forgot_password/reset_password/",
      params,
      {
        headers: {
          "Content-Type": "application/json"
        }
      }
    );
  }
  changePassword(userId, newPassword) {
    let user = new FormData();
    user.append("id", userId);
    user.append("password", newPassword);
    return axios.post(API_URL + "change/password", user, {
      headers: { ...authHeader(), "Content-Type": "application/json" }
    });
  }
  changeProfilePhoto(userId, username, password, file) {
    console.log(username);
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
