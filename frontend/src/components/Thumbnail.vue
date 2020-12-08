<template>
  <div>
    <!-- Grid row -->
    <div class="row">
      <!-- Grid column -->
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
      <!-- Grid column -->
    </div>
    <!-- Grid row -->

    <!-- Grid row -->
    <div class="gallery" id="gallery">
      <!-- Grid column -->
      <div
        class="mb-3 pics animation all 2"
        v-for="image in images"
        :key="image.id"
      >
        <img
          class="img-fluid"
          :src="'data:image/jpeg;base64,' + image.image"
          alt="Card image cap"
        />
      </div>
      <!-- Grid column -->

      <!-- Grid column -->
    </div>
    <!-- Grid row -->
  </div>
</template>

<script>
export default {
  props: ["images"]
};
</script>
<style scoped>
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
