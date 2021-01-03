<template>
  <div class="container">
    <Loader :status="loaderStatus"></Loader>
    <div class="main-body">
      <div class="row gutters-sm">
        <div class="col-md-12">
          <div class="card mb-3 col-md-6 offset-md-3">
            <div class="card-body">
              <div class="d-flex flex-column align-items-center text-center">
                <img
                  v-if="currentUser.profilePhoto"
                  :src="'data:image/jpeg;base64,' + currentUser.profilePhoto"
                  alt="profilePhoto"
                  class="rounded-circle "
                  width="120"
                  height="120"
                />
                <img
                  v-else
                  id="profile-img"
                  src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                  class="profile-img-card rounded-circle"
                  alt="profilePhoto"
                  width="120"
                  height="120"
                />
                <div>
                  <label
                    v-if="currentUser.profilePhoto"
                    class="mt-3 badge bg-primary p-2"
                    style=" cursor:pointer;  color:white"
                    for="profilePhoto"
                    >Profil fotoğrafını değiştir</label
                  >
                  <label
                    v-else
                    class="mt-3 badge bg-primary p-2"
                    style=" cursor:pointer; color:white"
                    for="profilePhoto"
                    >Profil fotoğrafı ekle</label
                  >
                  <input
                    id="profilePhoto"
                    style="display: none;"
                    type="file"
                    accept="image/*"
                    @change="changeImage($event)"
                  />
                </div>
                <div class="mt-3">
                  <h4>{{ currentUser.username }}</h4>
                </div>
              </div>
            </div>
          </div>
          <div class="card mb-3 card mb-3 col-md-6 offset-md-3">
            <div class="card-body">
              <div class="row">
                <div class="col-sm-4">
                  <h6 class="mb-0">Kullanıcı adı</h6>
                </div>
                <div class="col-sm-4 text-secondary text-center mb-1 mt-1">
                  {{ currentUser.username }}
                </div>
                <div class="col-sm-4 text-center">
                  <button
                    @click="editUsername()"
                    class="btn btn-primary"
                    data-toggle="tooltip"
                    data-placement="top"
                    title="Bu kadar sık kullanıcı adını değiştirmemeilisin."
                  >
                    <font-awesome-icon icon="edit" />
                  </button>
                </div>
              </div>
              <hr />
              <div class="row">
                <div class="col-sm-4">
                  <h6 class="mb-0">Email</h6>
                </div>
                <div class="col-sm-4 text-secondary text-center mb-1 mt-1">
                  {{ currentUser.email }}
                </div>
              </div>
              <hr />
              <div class="row">
                <div class="col-sm-4">
                  <h6 class="mb-0">Şifre</h6>
                </div>
                <div class="col-sm-4 text-secondary text-center">
                  ####
                </div>
                <div class="col-sm-4 text-center">
                  <button @click="changePassword()" class="btn btn-primary">
                    <font-awesome-icon icon="edit" />
                  </button>
                </div>
              </div>
              <hr />
            </div>
          </div>
          <div class="row gutters-sm">
            <div class="col-sm-12 mb-3">
              <div class="card h-100 col-md-6 offset-md-3">
                <div class="card-body">
                  <h6 class="d-flex align-items-center mb-3">
                    Visual Manager
                    <i class="material-icons text-info ml-2">Scorboard </i>
                    <span class="ml-2"> I</span>
                  </h6>
                  <small>Profil Fotoğrafına sahipsin</small>
                  <div class="progress mb-3" style="height: 5px">
                    <div
                      class="progress-bar bg-primary"
                      role="progressbar"
                      :style="{
                        width: currentUser.profilePhoto ? 100 + '%' : 0 + '%'
                      }"
                      aria-valuenow="80"
                      aria-valuemin="0"
                      aria-valuemax="100"
                    ></div>
                  </div>
                  <small>10 Görsel yükledin</small>
                  <div class="progress mb-3" style="height: 5px">
                    <div
                      class="progress-bar bg-primary"
                      role="progressbar"
                      :style="{ width: getTotalElements * 10 + '%' }"
                      aria-valuenow="72"
                      aria-valuemin="0"
                      aria-valuemax="100"
                    ></div>
                  </div>
                  <small>50 Görsel yükledin</small>
                  <div class="progress mb-3" style="height: 5px">
                    <div
                      class="progress-bar bg-primary"
                      role="progressbar"
                      :style="{ width: getTotalElements * 2 + '%' }"
                      aria-valuenow="89"
                      aria-valuemin="0"
                      aria-valuemax="100"
                    ></div>
                  </div>
                  <small>100 Görsel yükledin</small>
                  <div class="progress mb-3" style="height: 5px">
                    <div
                      class="progress-bar bg-primary"
                      role="progressbar"
                      :style="{ width: getTotalElements + '%' }"
                      aria-valuenow="55"
                      aria-valuemin="0"
                      aria-valuemax="100"
                    ></div>
                  </div>
                  <small>1000 Görsel yükledin</small>
                  <div class="progress mb-3" style="height: 5px">
                    <div
                      class="progress-bar bg-primary"
                      role="progressbar"
                      :style="{ width: getTotalElements / 5 + '%' }"
                      aria-valuenow="66"
                      aria-valuemin="0"
                      aria-valuemax="100"
                    ></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from "vuex";
import UserService from "../services/user.service";
export default {
  name: "Profile",
  data() {
    return {
      loaderStatus: true
    };
  },
  computed: {
    ...mapGetters(["getTotalElements"]),
    currentUser() {
      return this.$store.state.auth.user;
    }
  },
  created() {
    if (!this.currentUser) {
      this.$router.push("/login");
    }

    setTimeout(() => {
      this.loaderStatus = false;
    }, 350);
  },
  methods: {
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
    },
    changeImage(event) {
      let file = event.target.files[0];
      if (file.size <= 20000000) {
        this.$swal
          .mixin({
            input: "text",
            confirmButtonText: "Next &rarr;",
            showCancelButton: true,
            progressSteps: ["1"]
          })
          .queue([
            {
              title: "Şifrenizi giriniz."
            }
          ])
          .then(result => {
            if (result.value) {
              UserService.changeProfilePhoto(
                this.currentUser.id,
                this.currentUser.username,
                result.value[0],
                file
              )
                .then(response => {
                  this.$store.commit("auth/loginSuccess", response.data);
                  let user = JSON.stringify(response.data);

                  localStorage.setItem("user", user);

                  this.successAlert(
                    "Profil fotoğrafınız başarı ile değiştirildi."
                  );
                })
                .catch(error => {
                  this.errorAlert(error);
                });
            }
          });
      } else {
        file = null;
        this.errorAlert("maksimum 20mb boyutunda görsel seçebilirsiniz.");
      }
    },
    editUsername() {
      this.$swal
        .mixin({
          input: "text",
          confirmButtonText: "Next &rarr;",
          showCancelButton: true,
          progressSteps: ["1", "2"]
        })
        .queue([
          {
            title: "Yeni kullanıcı adınızı giriniz."
          },
          "Şifrenizi giriniz"
        ])
        .then(result => {
          if (result.value[0].length > 5) {
            UserService.changeUsername(
              this.currentUser.id,
              result.value[0],
              result.value[1]
            )
              .then(response => {
                this.$store.commit("auth/loginSuccess", response.data);
                let user = JSON.stringify(response.data);

                localStorage.setItem("user", user);

                this.successAlert("Kullanıcı adını değiştirildi.");
              })
              .catch(error => {
                this.errorAlert(error);
              });
          } else {
            this.errorAlert(
              "Kullanıcı adı minimum '6' karakterden oluşmalıdır."
            );
          }
        });
    },
    changePassword() {
      this.$swal
        .mixin({
          input: "text",
          confirmButtonText: "Next &rarr;",
          showCancelButton: true,
          progressSteps: ["1", "2"]
        })
        .queue([
          {
            title: "Yeni Şifrenizi giriniz."
          },
          "Yeni Şifrenizi tekrar giriniz."
        ])
        .then(result => {
          if (
            result.value[0] === result.value[1] &&
            result.value[0].length > 3
          ) {
            UserService.changePassword(this.currentUser.id, result.value[0])
              .then(response => {
                this.$store.commit("auth/loginSuccess", response.data);
                let user = JSON.stringify(response.data);

                localStorage.setItem("user", user);

                this.successAlert("Şifreniz başarı ile değiştirildi.");
              })
              .catch(error => {
                this.errorAlert(error);
              });
          } else if (result.value[0] != result.value[1]) {
            this.errorAlert("Şifreler biri ile eşleşmiyor!");
          } else {
            this.errorAlert("Şifre minimum '4' karakterden oluşmalıdır.");
          }
        });
    }
  }
};
</script>
<style scoped>
.main-body {
  padding: 15px;
}
.card {
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
}

.card {
  position: relative;
  display: flex;
  flex-direction: column;
  min-width: 0;
  word-wrap: break-word;
  background-color: #fff;
  background-clip: border-box;
  border: 0 solid rgba(0, 0, 0, 0.125);
    border-radius: 10px;
}

.card-body {
  flex: 1 1 auto;
  min-height: 1px;
  padding: 1rem;
}

.gutters-sm {
  margin-right: -8px;
  margin-left: -8px;
}

.gutters-sm > .col,
.gutters-sm > [class*="col-"] {
  padding-right: 8px;
  padding-left: 8px;
}
.mb-3,
.my-3 {
  margin-bottom: 1rem !important;
}

.bg-gray-300 {
  background-color: #e2e8f0;
}
.h-100 {
  height: 100% !important;
}
.shadow-none {
  box-shadow: none !important;
}
</style>
