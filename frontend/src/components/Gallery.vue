<template>
  <div class="gallery">
    <div class="row">
      <b-modal id="bv-modal-example" centered hide-footer>
        <template #modal-title
          ><h3 class="text-center">
            Burada etiketlerini düzenleyebilirsin
          </h3></template
        >
        <div class="d-block text-center">
          <app-tags
            :imageId="selectedImageId"
            v-model="imageTags"
            color="primary"
          ></app-tags>

          <button
            class="mt-4 btn btn-primary"
            @click="downloadImage(selectedImageId)"
          >
            Görseli indir
          </button>
        </div>
      </b-modal>

      <div
        class="col-md-4 col-sm-6 mb-4  item"
        v-for="(image, index) in images"
        :key="image.id"
      >
        <div class="thumbnail thumbnail-brightness">
          <img
            @click="imageInfo(image)"
            class="img-fluid img-thumbnail image"
            :src="'data:image/jpeg;base64,' + image.image"
            alt=""
          />
          <div class="image-panel">
            <button @click="deleteImage(index)" class="btn btn-danger">
              Sil
            </button>
            <button @click="imageInfo(image)" class="btn btn-warning">
              Düzenle
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from "axios";
import Tags from "./InputTag/Tags";
import tagService from "../services/tag.service";
import imageService from "../services/image.service";

export default {
  props: ["images"],
  data() {
    return {
      selectedImage: null,
      selectedImageId: null,
      imageTags: []
    };
  },
  mounted() {},
  components: {
    appTags: Tags
  },
  watch: {
    images(value) {
      if (this.images.length) {
      }
      return value;
    }
  },
  methods: {
    successAlert(message) {
      this.$swal({
        icon: "success",
        title: message,
        showConfirmButton: false,
        timer: 1500
      });
    },
    errorAlert(message) {
      this.$swal({
        icon: "error",
        title: message,
        showConfirmButton: false,
        timer: 1500
      });
    },
    downloadImage(imageId) {
      imageService.downloadImage(imageId);
    },
    deleteImage(index) {
      this.selectImage(this.images[index]);
      let payload = {
        imageId: this.selectedImage.id,
        index: index
      };
      // Use sweetalert2
      this.$swal({
        title: "Bu görseli silmek üzeresiniz !",
        showDenyButton: true,

        confirmButtonText: `Evet, Sil !`,
        denyButtonText: `Aah, Vazgeçtim !`
      }).then(result => {
        if (result.isConfirmed) {
          this.$store
            .dispatch("deleteImage", payload)
            .then(response => {
              this.successAlert("Görsel silindi !");
            })
            .catch(error => {
              this.errorAlert(error);
            });
        }
      });
    },
    imageInfo(image) {
      this.selectImage(image);
      this.$bvModal.show("bv-modal-example");
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
    },
    cancel(){
      console.log("qasfasfas");
    }
  }
};
</script>
<style>
.thumbnail {
  cursor: pointer;
}
/* Brightness-zoom Container */
.thumbnail-brightness img {
  transition: transform 0.5s, filter 1s ease-in-out;
  transform-origin: center center;
  filter: brightness(80%);
}

/* The Transformation */
.thumbnail-brightness:hover img {
  filter: brightness(100%);
  transform: scale(1.1);
}

.image {
  opacity: 1;
  width: 300px;
  height: 300px;
  object-fit: cover;
}

.item:hover .image-panel {
  opacity: 1;
}

.image-panel {
  transition: 0.5s ease;
  opacity: 0;
  position: absolute;
  bottom: -6%;
  left: 50%;
  transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  text-align: center;
}
</style>
