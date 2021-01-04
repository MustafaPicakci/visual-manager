<template>
  <div class="container text-center">
    <Loader :status="loaderStatus"></Loader>
    <br />
    <div class="dropzone mb-4">
      <vue-dropzone
        :options="dropzoneOptions"
        :useCustomSlot="true"
        :duplicateCheck="true"
        @vdropzone-success="vsuccess"
        id="dropzone"
      >
        <div class="dropzone-custom-content pt-4">
          <h3 class="dropzone-custom-title">
            Görselleri Buraya sürükle ve bırak !
          </h3>
          <div class="subtitle">
            ...veya sadece tıkla ve istediğin görseli seç
          </div>
        </div>
      </vue-dropzone>
    </div>

    <div v-if="getImages.length" class="info text-center mt-4 mb-5">
      <h1>Etiketlenmeyi bekleyen görseller</h1>
    </div>
    <div v-else class="alert alert-danger text-center" role="alert">
      Etiketlenmeyi bekleyen görseliniz yok
    </div>
    <gallery :images="getImages"></gallery>

    <b-pagination
      v-if="getImages.length"
      align="center"
      v-model="pageNumber"
      :total-rows="getTotalElements"
      :per-page="pageSize"
      prev-text="Prev"
      next-text="Next"
      @change="handlePageChange"
    ></b-pagination>
  </div>
</template>
<script>
import { mapGetters, mapMutations, mapState } from "vuex";
import Gallery from "../components/Gallery.vue";
import vueDropzone from "vue2-dropzone";
import authHeader from "../services/auth-header";
import "vue2-dropzone/dist/vue2Dropzone.min.css";

export default {
  name: "Home",
  components: {
    Gallery,
    vueDropzone
  },
  data() {
    return {
      loaderStatus: true,
      pageNumber: 1,
      pageSize: 20,
      dropzoneOptions: {
        url: "http://localhost:3000/api/images/add",
        maxFilesize: 45.0,
        thumbnailWidth: 200,
        addRemoveLinks: false,

        headers: { ...authHeader() }
      }
    };
  },
  created() {
    this.retrieveImages();
  },

  computed: {
    ...mapGetters(["getImages", "getTotalElements"])
  },
  methods: {
    ...mapMutations(["addImage"]),

    vsuccess(file, response) {
      this.addImage(response);
    },
    getRequestParams(pageNumber, pageSize) {
      let params = {};
      if (pageNumber) {
        params["pageNumber"] = pageNumber - 1;
      }
      if (pageSize) {
        params["pageSize"] = pageSize;
      }
      this.loaderStatus = true;
      return params;
    },
    retrieveImages() {
      const params = this.getRequestParams(this.pageNumber, this.pageSize);
      this.$store
        .dispatch("getUntaggedImages", params)
        .then(() => {
          this.loaderStatus = false;
        })
        .catch(error => {
          this.errorAlert(error);
        });
    },
    handlePageChange(value) {
      this.pageNumber = value;
      this.retrieveImages();
    }
  }
};
</script>
<style scoped>
.info {
  position: relative;
  z-index: 6;
}
.info h1 {
  color: #00b782;
}
.dropzone-custom-content {
  position: relative;

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

#dropzone {
  position: relative;
  z-index: 99;
  background: rgba(155, 211, 156, 0.08);
  border: 5 px solid rgba(155, 211, 156, 0.1);
  border-radius: 15px;
}
</style>
