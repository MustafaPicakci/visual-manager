<template>
  <div class="container">
    <div class="list row">
      <div class="col-md-8">
        <div class="input-group mb-3">
          <input
            type="text"
            class="form-control"
            placeholder="Search by title"
            v-model="searchTag"
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

      <div class="col-md-12">
        <div class="mb-3">
          Items per Page:
          <select v-model="pageSize" @change="handlePageSizeChange($event)">
            <option v-for="size in pageSizes" :key="size" :value="size">
              {{ size }}
            </option>
          </select>
        </div>

        <b-pagination
          v-model="pageNumber"
          :total-rows="count"
          :per-page="pageSize"
          prev-text="Prev"
          next-text="Next"
          @change="handlePageChange"
        ></b-pagination>
      </div>

      <div class="col-md-6">
        <h4>Image List</h4>
        <ul class="list-group" id="tutorials-list">
          <li
            class="list-group-item"
            :class="{ active: index == currentIndex }"
            v-for="(image, index) in images"
            :key="index"
            @click="setActiveImage(image, index)"
          >
            {{ image.imageName }}
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import UserService from "../services/user.service";

import authHeader from "../services/auth-header";
import imageService from "../services/image.service";

export default {
  name: "User",
  data() {
    return {
      images: [],
      currentImage: null,
      currentIndex: -1,
      searchTag: "",
      pageNumber: 1,
      count: 0,
      pageSize: 3,
      pageSizes: [3, 6, 9]
    };
  },
  components: {},
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
      return params;
    },
    retrieveImages() {
      const params = this.getRequestParams(
        this.searchTag,
        this.pageNumber,
        this.pageSize
      );
      imageService
        .getAllUserImages(params)
        .then(response => {
          this.images = response.data.content;
          this.count = response.data.totalElements;
          console.log(response.data);
        })
        .catch(e => {
          console.log(e);
        });
    },
    handlePageChange(value) {
      this.pageNumber = value;
      this.retrieveImages();
    },
    handlePageSizeChange(event) {
      this.pageSize = event.target.value;
      this.pageNumber = 1;
      this.retrieveImages();
    }
  }
};
</script>
<style scoped></style>
