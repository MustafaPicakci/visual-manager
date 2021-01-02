<template>
  <div class="container">
    <div class="col-md-12">
      <div
        v-if="
          currentUser.roles[0] == 'ROLE_ADMIN' ||
            currentUser.roles[1] == 'ROLE_ADMIN'
        "
        class="user-list text-center"
      >
        <h3 class="mb-3 mb-1">Kullanıcı Listesi</h3>
        <b-table
          stickyHeader
          hover
          :items="getUsers"
          :fields="fields"
          :tbody-tr-class="rowClass"
        >
          <template #cell(roles)="row" _rowVariant="danger">
            {{ row.value[0].name }},
            <template v-if="row.value.length > 1">{{
              row.value[1].name
            }}</template>
          </template>

          <template #cell(action)="row">
            <b-button
              size="sm"
              class="btn ml-1 mb-1 mr-1"
              @click="changeStatus(row.item, $event.target)"
              :class="[row.item.active ? activeClass : 'btn-secondary']"
            >
              <span v-if="row.item.active">Block</span>
              <span v-else>UnBlock</span>
            </b-button>
            <b-button
              @click="deleteUser(row.item, row.index, $event.target)"
              size="sm"
              class=" ml-1 mb-1 mr-1 btn btn-danger"
            >
              Delete
            </b-button>
          </template>
        </b-table>
      </div>
    </div>
  </div>
</template>

<script>
import UserService from "../services/user.service";
import { mapGetters } from "vuex";

export default {
  name: "Admin",
  data() {
    return {
      fields: [
        {
          key: "id",
          label: "UserId",
          sortable: true
        },
        {
          key: "username",
          sortable: true
        },
        {
          key: "email",
          sortable: false
        },
        {
          key: "roles",
          sortable: false
        },
        {
          key: "active",
          sortable: true
        },
        {
          key: "action"
        }
      ],
      activeClass: "btn-success"
    };
  },
  created() {
    this.$store.dispatch("listUsers");
  },
  computed: {
    ...mapGetters(["getUsers"]),

    currentUser() {
      return this.$store.state.auth.user;
    }
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
    changeStatus(item, button) {
      let changeable = true;
      item.roles.forEach(element => {
        if (element.name == "ROLE_ADMIN") {
          changeable = false;
        }
      });

      if (changeable) {
        this.$swal({
          title: item.active
            ? "Bu kullanıcıyı engellemek istediğinize emin misiniz?"
            : "Bu kullanıcının engellini kaldırmak istediğinize emin misiniz?",
          showDenyButton: true,

          confirmButtonText: `Evet !`,
          denyButtonText: `Hayır !`
        }).then(result => {
          if (result.isConfirmed) {
            if (item.active) {
              this.$store
                .dispatch("blockUser", item.id)
                .then(response => {
                  console.log(response);
                  this.successAlert("Kullanıcı engellendi!");
                })
                .catch(response => {
                  this.errorAlert("Kullanıcı engelleme işlemi başarısız !");
                });
            } else {
              this.$store
                .dispatch("unBlockUser", item.id)
                .then(response => {
                  this.successAlert("Kullanıcının engeli kaldırıldı !");
                })
                .catch(response => {
                  this.errorAlert(
                    "Kullanıcı engelini kaldırma işlemi başarısız !"
                  );
                });
            }
          }
        });
      } else {
        this.$swal({
          icon: "error",
          title: "Admin kendisini engelleyemez!",
          showConfirmButton: false,
          timer: 1500
        });
      }
    },
    rowClass(item, type) {
      if (!item || type !== "row") return;
      if (item.roles.length > 1) return "table-success";
    },
    deleteUser(item, index, button) {
      let deletable = true;
      item.roles.forEach(element => {
        if (element.name == "ROLE_ADMIN") {
          deletable = false;
        }
      });

      if (deletable) {
        let payload = {
          id: item.id,
          index: index
        };
        this.$swal({
          title: "Bu kullanıcıyı tamamen silmek istediğinize emin misiniz?",
          showDenyButton: true,

          confirmButtonText: `Evet !`,
          denyButtonText: `Hayır !`
        }).then(result => {
          if (result.isConfirmed) {
            this.$store
              .dispatch("deleteUser", payload)
              .then(response => {
                console.log(response);
                this.successAlert("Kullanıcı silindi !");
              })
              .catch(response => {
                this.errorAlert("Kullanıcı silme işlemi başarısız !");
              });
          }
        });
      } else {
        this.$swal({
          icon: "error",
          title: "Admin kendisini silemez!",
          showConfirmButton: false,
          timer: 1500
        });
      }
    }
  }
};
</script>
<style scoped>
.user-list {
  background-color: white;
}
</style>
