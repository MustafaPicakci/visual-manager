<template>
  <div id="app">
    <div class="content-bg"></div>
    <site-top :currentUser="currentUser"></site-top>
    <div>
      <router-view />
    </div>
    <site-footer :currentUser="currentUser"></site-footer>>
  </div>
</template>

<script>
import SiteTop from "./components/SiteTop.vue";
import SiteFooter from "./components/SiteFooter";
export default {
  components: {
    SiteTop,
    SiteFooter
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
