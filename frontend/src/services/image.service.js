import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:3000/api/images/";

class ImageService {
  downloadImage(imageId) {
    return axios
      .get(
        API_URL + "downloadFile/" + imageId,

        { headers: authHeader(), responseType: "blob" }
      )
      .then(response => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", "image.jpg");
        document.body.appendChild(link);
        link.click();
      });
  }

  getUntagedImages(params) {
    return axios.get(API_URL + "list", { params, headers: authHeader() });
  }
  getAllUserImages(params) {
    return axios.get(API_URL + "getAll", { params, headers: authHeader() });
  }

  SetTags(payload) {
    let data = new FormData();
    data.append("imageId", payload.imageId);
    payload.insertedTagNames.forEach(element => {
      data.append("insertedTagNames[]", element);
    });
    payload.deletedTagNames.forEach(element => {
      data.append("deletedTagNames[]", element);
    });

    return axios.post(API_URL + "set/tag", data, {
      headers: authHeader(),
      "Content-Type": "multipart/form-data"
    });
  }

  deleteImage(imageId) {
    let data = new FormData();
    data.append("images", imageId);

    return axios.post(API_URL + "delete", data, {
      headers: authHeader(),
      "Content-Type": "multipart/form-data"
    });
  }
}

export default new ImageService();
