import axios from "axios";
import store from "../store/store";
const API_URL = "http://localhost:3000/api/auth/";

class AuthService {
  resetPassword(email) {
    return axios.post(API_URL + "forgot_password", {
      email
    });
  }
  login(user) {
    return axios
      .post(API_URL + "signin", {
        username: user.username,
        password: user.password
      })
      .then(response => {
        if (response.data.accessToken) {
          localStorage.setItem("user", JSON.stringify(response.data));
          let user = JSON.parse(localStorage.getItem("user"));
          console.log(user);
          store.dispatch(
            "auth/setTimeoutTimer",
            user.expirationDate - new Date().getTime()
          );
          // console.log((user.expirationDate - (new Date().getTime()) ))
        }

        return response.data;
      });
  }

  logout() {
    localStorage.removeItem("user");
  }

  register(user) {
    return axios.post(API_URL + "signup", {
      username: user.username,
      email: user.email,
      password: user.password
    });
  }
}

export default new AuthService();
