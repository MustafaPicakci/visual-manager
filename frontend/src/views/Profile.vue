<template>
  <div class="container">
    <Loader :status="loaderStatus"></Loader>
    <div class="main-body">
      <!-- Breadcrumb -->

      <div class="row gutters-sm">
        <div class="col-md-12">
          <div class="card mb-3 col-md-6 offset-md-3">
            <div class="card-body">
              <div class="d-flex flex-column align-items-center text-center">
                <img
                  :src="'data:image/jpeg;base64,' + currentUser.profilePhoto"
                  alt="Admin"
                  class="rounded-circle "
                  width="120"
                  height="120"
                />
                <div>
                  <label for="profilePhoto">Profil fotoğrafını değiştir</label>
                  <input
                    id="profilePhoto"
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
                  <button class="btn btn-primary">
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
  mounted() {
    if (!this.currentUser) {
      this.$router.push("/login");
    }
    this.$store.dispatch("getTotalImages", this.currentUser.id);

    this.loaderStatus = false;
  },
  methods: {
    changeImage(event) {
      let file = event.target.files[0];
      if (file.size <= 8000000) {
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

                  this.$swal({
                    title: `Profil fotoğrafı değiştirildi`
                  });
                })
                .catch(error => {
                  this.$swal.showValidationMessage(`Request failed: ${error}`);
                });
            }
          });
      } else {
        file = null;
        alert("maksimum 80mb boyutunda görsel seçebilirsiniz.");
      }
    },
    editUsername() {
      console.log(this.currentUser);

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
          if (result.value) {
            UserService.changeUsername(
              this.currentUser.id,
              result.value[0],
              result.value[1]
            )
              .then(response => {
                this.$store.commit("auth/loginSuccess", response.data);
                let user = JSON.stringify(response.data);

                localStorage.setItem("user", user);

                this.$swal({
                  title: `Kullanıcı adını değiştirildi`
                });
              })
              .catch(error => {
                this.$swal.showValidationMessage(`Request failed: ${error}`);
              });
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
  border-radius: 0.25rem;
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
