<template>
  <div class="blog-masonry masonry-true">
    <div
      class="post-masonry col-md-4 col-sm-6"
      v-for="image in images"
      :key="image.id"
    >
      <div class="post-thumb">
        <img :src="'data:image/jpeg;base64,' + image.image" alt="" />
        <div class="title-over">
          <h4><a href="#">Title one goes here</a></h4>
        </div>
        <div class="post-hover text-center">
          <div class="inside">
            <i class="fa fa-plus"></i>
            <span class="date">25 Jan 2084</span>
            <h4><a href="#">Title one goes here</a></h4>
            <p>Cum sociis natoque penatibus et magnis dis parturient</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["images"]
  /*computed: {
    imageList() {
      return this.$store.state.getters.getImages();
    }
  }*/
};
</script>
