<template>
  <div v-if="currentUser" class="site-top">
    <div class="site-header clearfix">
      <div class="container">
        <router-link to="/" class="a site-brand float-left">
          <h3>Visual <span class="green">Manager</span></h3></router-link
        >
      </div>
    </div>
    <!-- .site-header -->
    <div class="site-banner">
      <div class="container">
        <div class="row">
          <div class="offset-md-2 col-lg-8 text-center">
            <router-link
              active-class="active-class"
              exact
              to="/home"
              tag="button"
              class="btn btn-danger"
              ><font-awesome-icon icon="home" /> Anasayfa</router-link
            >
            <router-link
              active-class="active-class"
              to="/MyImages"
              tag="button"
              class="btn btn-info"
              ><font-awesome-icon icon="images" /> Galeri</router-link
            >
            <router-link
              active-class="active-class"
              to="/profile"
              tag="button"
              class="btn btn-warning"
              ><font-awesome-icon icon="user" /> Profil</router-link
            >
            <template
              v-if="
                currentUser.roles[0] == 'ROLE_ADMIN' ||
                  currentUser.roles[1] == 'ROLE_ADMIN'
              "
            >
              <router-link
                active-class="active-class"
                to="/admin"
                tag="button"
                class="btn btn-secondary"
                ><font-awesome-icon icon="user-shield" /> Admin</router-link
              >
            </template>
            <button @click.prevent="logOut" class="btn btn-dark">
              <font-awesome-icon icon="sign-out-alt" />Çıkış
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div v-else class="unauthorized text-center m-4">
    <router-link to="/" tag="h1">
      <h1>Visual <span class="green">Manager</span></h1></router-link
    >

  </div>
</template>
<script>
export default {
  props: ["currentUser"],
  methods: {
    logOut() {
      this.$store.dispatch("auth/logout");
      // this.$router.push("/login");
    }
  }
};
</script>
<style scoped>
button {
  margin-bottom: 5px;
}
.active-class {
  border: 1px solid white;
}
h1 {
  cursor: pointer;
  text-shadow: 1px 1px;
}
.blue {
  color: #6be;
}
.green {
  color: rgb(49, 207, 49);
}

.unauthorized {
  position: relative;
  z-index: 4;
}
.site-top {
  position: relative;
  z-index: 4;
  background: rgba(0, 0, 0, 0.6);
  margin-bottom: 40px;
  padding-bottom: 60px;
}

.site-header {
  padding-top: 40px;
}

.site-brand {
  color: #fff;
  text-transform: uppercase;
  font-size: 18px;
  text-decoration: none;
}

.site-brand strong {
  font-size: 24px;
  font-family: "robotobold";
}

@media screen and (max-width: 768px) {
  .site-brand {
    float: none !important;
    text-align: center;
    display: block;
    margin-bottom: 30px;
  }
}

.site-banner {
  padding-top: 60px;
}

.site-banner h2 {
  font-size: 32px;
  font-family: "robotobold";
  margin-bottom: 30px;
}

.subscribe-form {
  margin-top: 40px;
}

.subscribe-form #subscribe-email {
  width: 100%;
  padding: 10px;
  background: transparent;
  border: 1px solid #fff;
  outline: 0;
}

@media screen and (max-width: 768px) {
  .subscribe-form #subscribe-email {
    margin-bottom: 20px;
  }
}
</style>
