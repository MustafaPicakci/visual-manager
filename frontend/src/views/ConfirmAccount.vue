<template>
  <div class="container h-100">
    <div
      class=" row h-1s00 justify-content-center align-items-center shadow-lg p-3 mb-5 bg-white rounded"
    >
      <div class="card">
        <div class="container">
          <div class=" row h-1s00 justify-content-center align-items-center">
            <div class="card-body text-center">
              <h5 class="card-title">Son bir adım....</h5>
              <p class="card-text">
                Aktivasyonu tamamlamak için lütfen tıklayınız.
              </p>
              <button @click="confirmAccount()" class="btn btn-success">
                Aktifleştir
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "confirmAccount",
  data() {
    return {};
  },
  methods: {
    confirmAccount() {
      let token = this.$route.query.token;
      this.$store
        .dispatch("auth/confirmAccount", token)
        .then(response => {
          this.successAlert(response.data.message);
          setTimeout(() => {
            this.$router.push("/login");
          }, 3000);
        })
        .catch(error => {
          this.errorAlert(error + "\n" + "\n" + error.response.data.message);
        });
    },
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
    }
  }
};
</script>

<style scoped>
.card {
  position: relative;
  display: flex;
  flex-direction: column;
  min-width: 0;
  max-width: 500px;

  word-wrap: break-word;
  background-color: #fff;
  background-clip: border-box;
  border: 0 solid rgba(0, 0, 0, 0.125);
  border-radius: 0.25rem;
}
</style>
