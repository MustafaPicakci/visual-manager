<template>
  <div class="tag-container">
    <tag
      v-for="(tag, index) in value"
      :tagColor="color"
      :key="index"
      :tag="tag"
      :index="index"
      @remove-one-tag-event="removeOneTag($event)"
    ></tag>
    <input
      class="mt-4"
      type="text"
      @keydown.enter="addTag"
      @keydown.delete="removeTag"
    />
    <small class="form-text text-muted mt-2"
      >Etiket eklemek için 'enter' tuşunu, silmek için ise 'X' butonunu veya
      'Backspace' tuşunu kullanabilirsiniz.
    </small>
    <button @click="saveChanges()" class="btn btn-success center">
      Kaydet
    </button>
    <div v-if="error" class="error">Bu etiket daha önceden eklenmiş!!</div>
  </div>
</template>
<script>
import Tag from "./Tag";
import tagService from "../../services/image.service";
import imageService from "../../services/image.service";
import { mapGetters } from "vuex";

export default {
  created() {
    if (this.value) {
      if (this.value.length > 0) {
        this.tags = this.value;
      }
    }
  },
  props: {
    value: {
      required: false,
    },
    imageId: {
      required: true,
    },
    color: {
      type: String,
      required: false,
      default: "primary",
    },
  },
  components: {
    Tag,
  },
  data() {
    return {
      tags: [],
      error: false,
      deletedTags: [],
    };
  },
  methods: {
    ...mapGetters(["getTags", "getLastInsertedTag"]),
    saveChanges() {
      let tagNames = [];
      let deletedTagNames = [];
      this.tags.forEach((tag) => {
        if (tag.tagName) {
          tagNames.push(tag.tagName);
        } else {
          tagNames.push(tag);
        }
      });

      this.deletedTags.forEach((tag) => {
        if (tag.tagName) {
          deletedTagNames.push(tag.tagName);
        } else {
          deletedTagNames.push(tag);
        }
      });

      let payload = {
        imageId: this.imageId,
        insertedTagNames: tagNames,
        deletedTagNames: deletedTagNames,
      };
      this.$store
        .dispatch("setTag", payload)
        .then((data) => {
          this.successAlert("başarılı !");
          this.deletedTags = [];
        })
        .catch((data) => {
          this.errorAlert("Etiket eklenemedi!");
        });
    },
    addTag(event) {
      let text = event.target;
      text.value = text.value.trim();
      let matchedTag = false;
      if (text.value.length > 0) {
        this.tags.forEach((tag) => {
          if (
            tag.tagName &&
            tag.tagName.toLocaleLowerCase("tr") ===
              text.value.toLocaleLowerCase("tr")
          ) {
            matchedTag = true;
          } else if (
            !tag.tagName &&
            tag.toLocaleLowerCase("tr") === text.value.toLocaleLowerCase("tr")
          ) {
            matchedTag = true;
          }
        });

        if (!matchedTag) {
          this.tags.push(text.value);
          text.value = "";
        } else {
          this.error = true;

          setTimeout(() => {
            this.error = false;
          }, 2000);
        }
      }
    },
    removeTag(event) {
      if (this.tags.length > 0) {
        if (event.target.value.length <= 0) {
          let deletedTag = this.tags.splice(this.tags.length - 1, 1); // son elemanı siliyoruz

          this.deletedTags.push(deletedTag[0]);
        }
      }
    },
    removeOneTag(index) {
      let deletedTag = this.tags.splice(index, 1);
      this.deletedTags.push(deletedTag[0]);
    },
    successAlert(message, time) {
      this.$swal({
        icon: "success",
        title: message,
        showConfirmButton: false,
        timer: 500,
      });
    },
    errorAlert(message) {
      this.$swal({
        icon: "error",
        title: message,
        showConfirmButton: false,
        timer: 1500,
      });
    },
  },
  watch: {
    tags() {
      if (this.tags.tagName) {
        this.$emit("input", this.tags.tagName);
      } else if (!this.tags.tagName) {
        this.$emit("input", this.tags); //arrayde bir değişiklik olduğunda v-model in de bu değişikliği yakalamasını sağladık
      }
    },
  },
};
</script>

<style scoped>
.tag-container {
  border: 1px solid #ccc;
  padding: 20px;
}

input {
  outline: none;
  height: 30px;
  width: 100px;
}
.error {
  font-size: 12px;
  color: red;
  margin: 5px;
}
</style>
