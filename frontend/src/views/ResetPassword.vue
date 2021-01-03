<template>
  <div class="col-md-12">
    <loader :status="loaderStatus"></loader>
    <div class="card card-container">
      <form name="form" @submit.prevent="handleForm">
        <div>
          <div class="form-group">
            <input
              v-model="email"
              type="email"
              class="form-control"
              :class="{ 'is-invalid': $v.email.$error }"
              name="email"
              placeholder="E-posta adresini giriniz"
            />
            <small v-if="!$v.email.required" class="form-text text-danger"
              >* Bu alan zorunludur</small
            >
            <small v-if="!$v.email.email" class="form-text text-danger"
              >Lütfen geçerli bir e-posta giriniz..</small
            >
          </div>

          <div class="form-group">
            <button
              class="btn btn-primary btn-block mb-2"
              :disabled="$v.$invalid"
            >
              <span
                v-show="loading"
                class="spinner-border spinner-border-sm"
              ></span>
              Gönder
            </button>
            <router-link to="/login"><a>Giriş Yap</a> </router-link>
          </div>
        </div>
      </form>

      <div v-if="message" class="alert alert-danger">
        {{ message }}
      </div>
    </div>
  </div>
</template>
<script>
import {
  required,
  email,
  minLength,
  maxLength,
  sameAs,
  between
} from "vuelidate/lib/validators";

export default {
  name: "ResetPassword",
  data() {
    return {
      email: "",
      loading: false,
      message: "",
      submitted: false,
      loaderStatus: true
    };
  },
  created() {
    setTimeout(() => {
      this.loaderStatus = false;
    }, 250);
  },
  validations: {
    email: {
      required,
      email
    }
  },
  methods: {
    handleForm() {
      this.message = "";
      this.submitted = true;
      this.loading = true;
      this.$store
        .dispatch("resetPassword", this.email)
        .then(response => {
          this.loading = false;
          this.$swal({
            icon: "success",
            title: response.data.message,
            showConfirmButton: false,
            timer: 1500
          });
          this.$router.push("/login");
        })
        .catch(error => {
          this.loading = false;
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
        });
    }
  }
};
</script>
<style scoped>
label {
  display: block;
  margin-top: 10px;
}

.card-container.card {
  max-width: 350px !important;
  padding: 40px 40px;
}

.card {
  background-color: #f7f7f7;
  padding: 20px 25px 30px;
  margin: 0 auto 25px;
  margin-top: 50px;
  -moz-border-radius: 2px;
  -webkit-border-radius: 2px;
  border-radius: 2px;
  -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
}

.profile-img-card {
  width: 96px;
  height: 96px;
  margin: 0 auto 10px;
  display: block;
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
  border-radius: 50%;
}
</style>
