<template>
  <div class="container text-center">
    <Loader :status="loaderStatus"></Loader>
    <div class="list row">
      <div class="col-md-12 ">
        <div class="input-group mb-4 mt-5 d-flex justify-content-center">
          <div class="col-xs-4">
            <input
              type="text"
              class="form-control center"
              placeholder="Etiket ile ara"
              v-model="searchTag"
              @keypress.enter="
                pageNumber = 1;
                retrieveImages();
              "
            />
          </div>
          <div class="input-group-append">
            <button
              class="btn btn-outline-dark pl-3 pr-3 ml-2"
              type="button"
              @click="
                pageNumber = 1;
                retrieveImages();
              "
            >
              Ara
            </button>
          </div>
        </div>

        <div
          v-if="!getImages.length"
          class="alert alert-danger text-center"
          role="alert"
        >
          <p>Hiç görsel Yüklememişsiniz (-_-)</p>
          <p>Birkaç görsel yüklemeyi denemelisiniz !</p>
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
    </div>
  </div>
</template>

<script>
import UserService from "../services/user.service";

import authHeader from "../services/auth-header";
import imageService from "../services/image.service";
import Gallery from "../components/Gallery.vue";
import { mapGetters, mapMutations, mapState } from "vuex";

export default {
  name: "MyImages",
  data() {
    return {
      images: [],
      loaderStatus: true,
      searchTag: "",
      pageNumber: 1,
      totalElements: 0,
      pageSize: 20
    };
  },
  components: {
    Gallery
  },
  computed: {
    ...mapGetters(["getImages", "getTotalElements"])
  },
  mounted() {
    this.retrieveImages();
  },
  methods: {
    getRequestParams(searchTag, pageNumber, pageSize) {
      let params = {};
      if (searchTag) {
        params["tag"] = searchTag;
      }
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
      const params = this.getRequestParams(
        this.searchTag,
        this.pageNumber,
        this.pageSize
      );

      this.$store.dispatch("getAllUserImages", params).then(() => {
        this.loaderStatus = false;
      });
    },
    handlePageChange(value) {
      this.pageNumber = value;
      this.retrieveImages();
    }
  }
};
</script>
<style scoped></style>
