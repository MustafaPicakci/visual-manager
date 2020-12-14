<template>
  <div class="com-thumb">
    <div class="row">
      <div class="col-md-12 d-flex justify-content-center mb-5">
        <button
          type="button"
          class="btn btn-outline-black waves-effect filter"
          data-rel="all"
        >
          All
        </button>
        <button
          type="button"
          class="btn btn-outline-black waves-effect filter"
          data-rel="1"
        >
          Mountains
        </button>
        <button
          type="button"
          class="btn btn-outline-black waves-effect filter"
          data-rel="2"
        >
          Sea
        </button>
      </div>
    </div>

    <div v-if="!images.length" class="alert alert-danger" role="alert">
      Hiç görsel yok :(
    </div>
    <div class="gallery" id="gallery">
      <v-modal name="imageInfo">
        <div>
          <app-tags
            :imageId="selectedImageId"
            v-model="imageTags"
            color="primary"
          ></app-tags>
        </div>
        This is a modal</v-modal
      >
      <div
        class="thumbnail mb-3 pics animation all 2"
        v-for="image in images"
        :key="image.id"
        @click="selectImage(image)"
      >
        <img
          class="image img-fluid"
          :src="'data:image/jpeg;base64,' + image.image"
          alt="Card image cap"
        />
        <div class="image-panel">
          <button class="btn btn-danger">Sil</button>
          <button
            @click="imageInfo()"
            class="btn btn-warning"
          >
            Düzenle
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
import Tags from "./InputTag/Tags";
import tagService from "../services/tag.service";

export default {
  props: ["images"],
  data() {
    return {
      selectedImage: null,
      selectedImageId: null,
      imageTags: []
    };
  },
  components: {
    appTags: Tags
  },
  watch: {
    images(value) {
      return value;
    }
  },
  methods: {
    imageInfo() {
      this.$modal.show("imageInfo");
    },
    selectImage(image) {
      this.imageTags = [];
      this.selectedImage = image;
      this.selectedImageId = image.id;
      let tags = [];
      image.tags.forEach(element => {
        tags.push(element);
      });
      this.imageTags = tags;
    }
  }
};
</script>
<style scoped>
.image {
  opacity: 1;
  transition: 0.5s ease;
  backface-visibility: hidden;
}
.thumbnail:hover .image {
  opacity: 0.3;
}
.thumbnail:hover .image-panel {
  opacity: 1;
}

.image-panel {
  transition: 0.5s ease;
  opacity: 0;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  text-align: center;
}
.gallery {
  -webkit-column-count: 3;
  -moz-column-count: 3;
  column-count: 3;
  -webkit-column-width: 33%;
  -moz-column-width: 33%;
  column-width: 33%;
}
.gallery .pics {
  -webkit-transition: all 350ms ease;
  transition: all 350ms ease;
}
.gallery .animation {
  -webkit-transform: scale(1);
  -ms-transform: scale(1);
  transform: scale(1);
}

@media (max-width: 450px) {
  .gallery {
    -webkit-column-count: 1;
    -moz-column-count: 1;
    column-count: 1;
    -webkit-column-width: 100%;
    -moz-column-width: 100%;
    column-width: 100%;
  }
}

@media (max-width: 400px) {
  .btn.filter {
    padding-left: 1.1rem;
    padding-right: 1.1rem;
  }
}
</style>
