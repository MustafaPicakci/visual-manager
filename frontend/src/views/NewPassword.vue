<template>
  <div class="col-md-12">
    <Loader :status="loaderStatus"></Loader>
    <div class="card card-container">
      <form name="form" @submit.prevent="handleForm">
        <div>
          <div class="form-group">
            <input
              v-model="password"
              type="password"
              class="form-control"
              name="password"
              placeholder="Şifre tekrar"
            />
            <small v-if="!$v.password.required" class="form-text text-danger"
              >Bu alan zorunludur</small
            >
            <small v-if="!$v.password.minLength" class="form-text text-danger"
              >şifre en az {{ $v.password.$params.minLength.min }} karakterden
              oluşmalıdlır</small
            >
            <small v-if="!$v.password.maxLength" class="form-text text-danger"
              >şifre en fazla
              {{ $v.password.$params.maxLength.max }} karakterden
              oluşmalıdlır</small
            >
          </div>
          <div class="form-group">
            <input
              v-model="$v.repassword.$model"
              type="password"
              class="form-control"
              name="repassword"
              placeholder="Şifre tekrar"
            />
            <small v-if="!$v.repassword.required" class="form-text text-danger"
              >Bu alan zorunludur</small
            >
            <small v-if="!$v.repassword.minLength" class="form-text text-danger"
              >şifre en az {{ $v.repassword.$params.minLength.min }} karakterden
              oluşmalıdlır</small
            >
            <small v-if="!$v.repassword.maxLength" class="form-text text-danger"
              >şifre en fazla
              {{ $v.repassword.$params.maxLength.max }} karakterden
              oluşmalıdlır</small
            >
            <small v-if="!$v.repassword.sameAs" class="form-text text-danger"
              >Şifreler eşleşmiyor...</small
            >
          </div>
          <div class="form-group">
            <button class="btn btn-primary btn-block" :disabled="$v.$invalid">
              Gönder
            </button>
            <router-link to="/login">Giriş Yap </router-link>
          </div>
        </div>
      </form>

      <div
        v-if="message"
        class="alert"
        :class="successful ? 'alert-success' : 'alert-danger'"
      >
        {{ message }}
      </div>
    </div>
  </div>
</template>

<script>
import {
  required,
  minLength,
  maxLength,
  sameAs
} from "vuelidate/lib/validators";

export default {
  name: "NewPassword",
  data() {
    return {
      password: "",
      repassword: "",
      submitted: false,
      successful: false,
      message: "",
      token: this.$route.params.token,
      loaderStatus: true
    };
  },
  created() {
    this.loaderStatus = false;
  },
  validations: {
    password: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(16)
    },
    repassword: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(16),
      sameAs: sameAs("password")
    }
  },

  methods: {
    handleForm() {
      this.message = "";
      this.submitted = true;
      let payload = { token: this.$route.query.token, password: this.password };
      this.$store
        .dispatch("newPassword", payload)
        .then(response => {
          this.message = response.data.message;
          this.$swal({
            icon: "success",
            title: this.message,
            showConfirmButton: false,
            timer: 1500
          });
          setTimeout(() => {
            this.$router.push("/login");
          }, 1000);
        })
        .catch(error => {
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
