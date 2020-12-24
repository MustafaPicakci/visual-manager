<template>
  <div id="app">
    <div class="content-bg"></div>
    <!--  
    <nav v-if="currentUser" class="navbar navbar-expand navbar-dark bg-dark">
      <a href class="navbar-brand" @click.prevent>bezKoder</a>
      <div class="navbar-nav mr-auto">
        <li class="nav-item">
          <router-link to="/home" class="nav-link">
            <font-awesome-icon icon="home" />Home
          </router-link>
        </li>
        <li v-if="showAdminBoard" class="nav-item">
          <router-link to="/admin" class="nav-link">Admin Board</router-link>
        </li>
        <li v-if="showModeratorBoard" class="nav-item">
          <router-link to="/mod" class="nav-link">Moderator Board</router-link>
        </li>
        <li class="nav-item">
          <router-link v-if="currentUser" to="/MyImages" class="nav-link"
            >Görsellerim</router-link
          >
        </li>
      </div>

      <div v-if="!currentUser" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link to="/register" class="nav-link">
            <font-awesome-icon icon="user-plus" />Sign Up
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/login" class="nav-link">
            <font-awesome-icon icon="sign-in-alt" />Login
          </router-link>
        </li>
      </div>

      <div v-if="currentUser" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link to="/profile" class="nav-link">
            <font-awesome-icon icon="user" />
            {{ currentUser.username }}
          </router-link>
        </li>
        <li class="nav-item">
          <a class="nav-link" href @click.prevent="logOut">
            <font-awesome-icon icon="sign-out-alt" />LogOut
          </a>
        </li>
      </div>
    </nav>
-->
    <site-top :currentUser="currentUser"></site-top>
    <div>
      <router-view />
    </div>
  </div>
</template>

<script>
import SiteTop from "./components/SiteTop.vue";
export default {
  components: {
    SiteTop
  },

  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    showAdminBoard() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes("ROLE_ADMIN");
      }

      return false;
    },
    showModeratorBoard() {
      if (this.currentUser && this.currentUser.roles) {
        return this.currentUser.roles.includes("ROLE_MODERATOR");
      }

      return false;
    }
  },
  created() {
    this.$store.dispatch("auth/initAuth");
  },
  methods: {
    logOut() {
      this.$store.dispatch("auth/logout");
      this.$router.push("/login");
    }
  }
};
</script>
<style scoped>
.content-bg {
  top: 0;
  position: fixed;
  height: 100%;
  width: 100%;
  z-index: 0;
  background-image: url("../src/assets/images/bg.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-attachment: fixed;
}
#app {
  position: relative;
  z-index: 55;
}
</style>
