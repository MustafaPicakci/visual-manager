<template>
  <div class="tag-container">
    <tag
      v-for="(tag, index) in tags"
      :tagColor="color"
      :key="tag"
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
export default {
  created() {
    if (this.value) {
      console.log(this.value);
      if (this.value.length > 0) {
        this.value = this.value.slice(0, -1); // en sondaki ',' ü silmek için
        this.tags = this.value.split(",");
      }
    }
  },
  props: {
    value: {
      required: false
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
    addTag(event) {
      let text = event.target;
      let matchedTag = false;
      if (text.value.length > 0) {
        this.tags.forEach(tag => {
          if (tag.toLowerCase() === text.value.toLowerCase()) {
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
      if (event.target.value.length <= 0) {
        this.tags.splice(this.tags.length - 1, 1); // son elemanı siliyoruz
      }
    },
    removeOneTag(index) {
      this.tags.splice(index, 1);
    }
  },
  watch: {
    tags() {
      this.$emit("input", this.tags.join(",")); //arrayde bir değişiklik olduğunda v-model in de bu değişikliği yakalamasını sağladık
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
