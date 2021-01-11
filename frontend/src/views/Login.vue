<template>
  <div class="col-md-12">
    <Loader :status="loaderStatus"></Loader>
    <div class="card card-container">
      <img
        id="profile-img"
        src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
        class="profile-img-card"
      />
      <form name="form" @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="username">Kullanıcı Adı</label>
          <input
            v-model="username"
            type="text"
            class="form-control"
            name="username"
            placeholder="Kullanıcı adı"
          />
          <small v-if="!$v.username.minLength" class="form-text text-danger"
            >Kullanıcı adı en az
            {{ $v.username.$params.minLength.min }} karakterden
            oluşmalıdlır</small
          >
          <small v-if="!$v.username.maxLength" class="form-text text-danger"
            >Kullanıcı en fazla
            {{ $v.username.$params.maxLength.max }} karakterden
            oluşmalıdlır</small
          >
        </div>
        <div class="form-group">
          <label for="password">Şifre</label>
          <input
            v-model="password"
            type="password"
            class="form-control"
            name="password"
            placeholder="Şifre"
          />
          <small v-if="!$v.password.minLength" class="form-text text-danger"
            >şifre en az {{ $v.password.$params.minLength.min }} karakterden
            oluşmalıdlır</small
          >
          <small v-if="!$v.password.maxLength" class="form-text text-danger"
            >şifre en fazla {{ $v.password.$params.maxLength.max }} karakterden
            oluşmalıdlır</small
          >
        </div>
        <div class="form-group">
          <button class="btn btn-primary btn-block" :disabled="$v.$invalid">
            <span
              v-show="loading"
              class="spinner-border spinner-border-sm"
            ></span>
            <span>Giriş Yap</span>
          </button>
          <router-link to="/register"
            ><a class="nav-link" href="#">Kayıt ol</a>
          </router-link>
          <router-link to="/ResetPassword"
            ><a class="nav-link" href="#">Şifremi unuttum</a>
          </router-link>
        </div>
        <div class="form-group">
          <div v-if="message" class="alert alert-danger" role="alert">
            {{ message }}
          </div>
        </div>
      </form>
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
  name: "Login",
  data() {
    return {
      username: "",
      password: "",
      loading: false,
      message: "",
      loaderStatus: true
    };
  },
  validations: {
    username: {
      required,
      minLength: minLength(6),
      maxLength: maxLength(16)
    },
    password: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(16)
    }
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    }
  },
  created() {
    setTimeout(() => {
      this.loaderStatus = false;
    }, 300);
    if (this.loggedIn) {
      this.$router.push("/home");
    }
  },
  methods: {
    handleLogin() {
      this.loading = true;
      var user = new User(this.username, "", this.password);
      this.$store.dispatch("auth/login", user).then(
        () => {
          this.$router.push("/home");
        },
        error => {
          this.loading = false;
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
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
  max-height: 500px !important;
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
