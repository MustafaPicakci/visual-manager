<template>
  <div class="container">
    <Loader :status="loaderStatus"></Loader>
    <div class="list row">
      <div class="col-md-8">
        <div class="input-group mb-3">
          <input
            type="text"
            class="form-control"
            placeholder="Search by tag"
            v-model="searchTag"
            @keypress.enter="
              pageNumber = 1;
              retrieveImages();
            "
          />
          <div class="input-group-append">
            <button
              class="btn btn-outline-secondary"
              type="button"
              @click="
                pageNumber = 1;
                retrieveImages();
              "
            >
              Search
            </button>
          </div>
        </div>
      </div>

      <div class="col-md-12" text-center>
        <thumbnail :images="getImages"></thumbnail>

        <b-pagination
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
import Thumbnail from "../components/Thumbnail.vue";
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
    Thumbnail
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
