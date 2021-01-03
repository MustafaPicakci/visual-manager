import axios from "axios";
import store from "../store/store";
const API_URL = "http://localhost:3000/api/auth/";

class AuthService {
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

          store.dispatch(
            "auth/setTimeoutTimer",
            user.expirationDate - new Date().getTime()
          );
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
  confirmAccount(token) {
    return axios.post(API_URL + "confirm-account/" + token);
  }
}

export default new AuthService();
