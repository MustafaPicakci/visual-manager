<template>
  <span class="tag" :class="selectedTheme">
    <span class="content">{{ tag.tagName }}</span>
    <span @click="$emit('remove-one-tag-event', index)" class="close-btn"
      ><span class="text-danger">X</span>
    </span>
  </span>
</template>
<script>
export default {
  props: ["tag", "index", "tagColor"],
  data() {
    return {
      selectedTheme: "null"
    };
  },
  created() {
    this.selectedTheme = this.tagColor;
  }
};
</script>
<style scoped>
.primary {
  color: #004085;
  background-color: #cce5ff;
  border-color: #b8daff;
}
.danger {
  color: #721c24;
  background-color: #f8d7da;
  border-color: #f5c6cb;
}
.warning {
  color: #856404;
  background-color: #fff3cd;
  border-color: #ffeeba;
}
.info {
  color: #0c5460;
  background-color: #d1ecf1;
  border-color: #bee5eb;
}
.secondary {
  color: #383d41;
  background-color: #e2e3e5;
  border-color: #d6d8db;
}
.success {
  color: #155724;
  background-color: #d5eddb;
  border-color: #c3e6cb;
}

.tag {
  padding: 10px;
  margin: 5px;
  cursor: default;

  display: inline-block;
  border-radius: 5px;
}
.tag .close-btn {
  cursor: pointer;
}
</style>
