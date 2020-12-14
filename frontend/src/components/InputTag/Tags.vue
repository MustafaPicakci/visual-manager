<template>
  <div class="tag-container">
    <tag
      v-for="(tag, index) in value"
      :tagColor="color"
      :key="tag.id"
      :tag="tag"
      :index="index"
      @remove-one-tag-event="removeOneTag($event)"
    ></tag>
    <input type="text" @keydown.enter="addTag" @keydown.delete="removeTag" />
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
      required: false
    },
    imageId: {
      required: true
    },
    color: {
      type: String,
      required: false,
      default: "primary"
    }
  },
  components: {
    Tag
  },
  data() {
    return {
      tags: [],
      error: false
    };
  },
  methods: {
    ...mapGetters(["getTags", "getLastInsertedTag"]),
    addTag(event) {
      let text = event.target;
      let matchedTag = false;
      if (text.value.length > 0) {
        this.tags.forEach(tag => {
          if (tag.tagName.toLowerCase() === text.value.toLowerCase()) {
            matchedTag = true;
          }
        });

        if (!matchedTag) {
          let payload = {
            imageId: this.imageId,
            tagName: text.value
          };

          this.$store
            .dispatch("setTag", payload)
            .then(data => {
              this.tags.push(data.tags[data.tags.length - 1]);
            })
            .catch(data => {
              alert("etiket eklenemedi");
            });

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
          let payload = {
            images: this.imageId,
            tagId: this.tags[this.tags.length - 1].id
          };
          this.$store
            .dispatch("unlinkTag", payload)
            .then(data => {
              this.tags.splice(this.tags.length - 1, 1); // son elemanı siliyoruz
            })
            .catch(data => {
              alert("etiket silinemedi");
            });
        }
      }
    },
    removeOneTag(index) {
      let payload = {
        images: this.imageId,
        tagId: this.tags[index].id
      };

      this.$store
        .dispatch("unlinkTag", payload)
        .then(data => {
          this.tags.splice(index, 1);
        })
        .catch(data => {
          alert("etiket silinemedi");
        });
    }
  },
  watch: {
    tags() {
      this.$emit("input", this.tags); //arrayde bir değişiklik olduğunda v-model in de bu değişikliği yakalamasını sağladık
    }
  }
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
