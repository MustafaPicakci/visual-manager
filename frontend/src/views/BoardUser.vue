<template>
  <div class="container">
    <header class="jumbotron">
      <h3>{{ content }}</h3>

      <vue-dropzone
        :id="id1"
        :options="dropzoneOptions"
        :useCustomSlot="true"
        @vdropzone-success="vsuccess"
      >
        <div class="dropzone-custom-content">
          <h3 class="dropzone-custom-title">
            Drag and drop to upload content!
          </h3>
          <div class="subtitle">
            ...or click to select a file from your computer
          </div>
        </div>
      </vue-dropzone>
    </header>
  </div>
</template>

<script>
import UserService from "../services/user.service";

import vue2Dropzone from "vue2-dropzone";
import authHeader from "../services/auth-header";
import "vue2-dropzone/dist/vue2Dropzone.min.css";

export default {
  name: "User",
  data() {
    return {
      content: "",
      dropzoneOptions: {
        url: "http://localhost:3000/api/upload/image",
        thumbnailWidth: 200,
        addRemoveLinks: true,
        headers: { ...authHeader() }
      }
    };
  },
  components: {
    vueDropzone: vue2Dropzone
  },
  mounted() {
    UserService.getUserBoard().then(
      response => {
        this.content = response.data;
      },
      error => {
        this.content =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
      }
    );
  },
  methods: {
     vsuccess(file, response) {
     console.log(file);
     console.log(response)
     // this.$store.dispatch("getAllImages");
    },
  }
};
</script>
<style scoped>
.dropzone-custom-content {
  position: absolute;
  top: 26%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}

.dropzone-custom-title {
  margin-top: 0;
  color: #00b782;
}

.subtitle {
  color: #314b5f;
}
</style>
