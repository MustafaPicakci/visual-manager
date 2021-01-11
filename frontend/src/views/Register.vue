<template>
  <div class="col-md-12">
    <Loader :status="loaderStatus"></Loader>
    <div class="card card-container">
      <img
        id="profile-img"
        src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
        class="profile-img-card"
      />
      <form name="form" @submit.prevent="handleRegister">
        <div v-if="!successful">
          <div class="form-group">
            <input
              v-model="username"
              type="text"
              class="form-control"
              name="username"
              placeholder="Kullanıcı adı"
            />
            <small v-if="!$v.username.required" class="form-text text-danger"
              >Bu alan zorunludur</small
            >
            <small v-if="!$v.username.minLength" class="form-text text-danger"
              >kullanıcı adı en az
              {{ $v.username.$params.minLength.min }} karakterden
              oluşmalıdlır</small
            >
            <small v-if="!$v.username.maxLength" class="form-text text-danger"
              >kullanıcı adı en fazla
              {{ $v.username.$params.maxLength.max }} karakterden
              oluşmalıdlır</small
            >
          </div>
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
              <span
                v-show="loading"
                class="spinner-border spinner-border-sm"
              ></span>
              Kaydol
            </button>
            <router-link to="/login"
              ><a class="nav-link" href="#">Hesabım var</a>
            </router-link>
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
import User from "../models/user";
import {
  required,
  email,
  minLength,
  maxLength,
  sameAs,
  between
} from "vuelidate/lib/validators";

export default {
  name: "Register",
  data() {
    return {
      username: "",
      password: "",
      email: "",
      repassword: "",
      submitted: false,
      successful: false,
      message: "",
      loading: false,
      loaderStatus: true
    };
  },
  validations: {
    username: {
      required,
      minLength: minLength(6),
      maxLength: maxLength(16)
    },
    email: {
      required,
      email
    },
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
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    }
  },
  mounted() {
    setTimeout(() => {
      this.loaderStatus = false;
    }, 300);
    if (this.loggedIn) {
      this.$router.push("/profile");
    }
  },
  methods: {
    handleRegister() {
      this.loading = true;
      this.message = "";
      this.submitted = true;
      var user = new User(this.username, this.email, this.password);
      this.$store.dispatch("auth/register", user).then(
        data => {
          this.message = data.message;
          this.successful = true;
          this.loading = false;
          setTimeout(() => {
            this.$router.push("/login");
          }, 2500);
        },
        error => {
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
          this.successful = false;
        }
      );
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
  max-height: 550px !important;
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
