<template>
  <div class="main-body">
    <Loader :status="loaderStatus"></Loader>
    <div class="row ">
      <div class="col-md-12">
        <div class="card mb-3 col-md-8 offset-md-2">
          <div class="card-body">
            <div class=" text-center">
              <h1>Sayfa Bulunamadı !</h1>
              <router-link to="/" tag="button" class="btn btn-info"
                ><font-awesome-icon icon="home" />Anasayfa</router-link
              >
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "pageNotFound",
  data() {
    return {
      loaderStatus: true
    };
  },
  created() {
    setTimeout(() => {
      this.loaderStatus = false;
    }, 300);
  }
};
</script>
<style scoped>
.card {
  background: rgba(0, 0, 0, 0.6);
  border-radius: 10px;
}
h1 {
  color: white;
}
</style>
