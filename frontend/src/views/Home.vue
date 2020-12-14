<template>
  <div class="container text-center">
    <br />
    <vue-dropzone
      :options="dropzoneOptions"
      :useCustomSlot="true"
      @vdropzone-success="vsuccess"
      id="dropzone"
    >
      <div class="dropzone-custom-content">
        <h3 class="dropzone-custom-title">
          Görselleri Buraya sürükle ve bırak !
        </h3>
        <div class="subtitle">
          ...veya sadece tıkla ve istediğin görseli seç
        </div>
      </div>
    </vue-dropzone>
    <br />
    <br />
    <br />
    <h1>Etiketlenmeyi bekleyen görseller</h1>
    <thumbnail :images="getImages"></thumbnail>
  </div>
</template>
<script>
import { mapGetters, mapMutations, mapState } from "vuex";
import Thumbnail from "../components/Thumbnail.vue";
import vueDropzone from "vue2-dropzone";
import authHeader from "../services/auth-header";
import "vue2-dropzone/dist/vue2Dropzone.min.css";

export default {
  name: "Home",
  components: {
    Thumbnail,
    vueDropzone
  },
  data() {
    return {
      dropzoneOptions: {
        url: "http://localhost:3000/api/images/add",
        thumbnailWidth: 200,
        addRemoveLinks: true,
        headers: { ...authHeader() }
      }
    };
  },
  created() {
    this.$store.dispatch("getUserImages");
  },
  computed: {
    ...mapGetters(["getImages"])
  },
  methods: {
    ...mapMutations(["addImage"]),
    vsuccess(file, response) {
      this.addImage(response);
    }
  }
};
</script>
<style scoped>
.dropzone-custom-content {
  position: absolute;

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
