import axios from "axios";
import store from "../store/store";
import authHeader from "./auth-header";
const API_URL = "http://localhost:3000/api/admin/";

class AdminService {
  listUsers() {
    return axios.get(API_URL + "list/users", {
      headers: authHeader()
    });
  }

  blockUser(Id) {
    let user = {
      id: Id
    };
    return axios.post(API_URL + "block/user", user, {
      headers: authHeader()
    });
  }
  unBlockUser(Id) {
    let user = {
      id: Id
    };
    return axios.post(API_URL + "unblock/user", user, {
      headers: authHeader()
    });
  }
  deleteUser(Id) {
    let user = {
      id: Id
    };
    return axios.post(API_URL + "delete/user", user, {
      headers: authHeader()
    });
  }
}

export default new AdminService();
