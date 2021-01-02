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
        <b-table stickyHeader hover :items="getUsers" :fields="fields">
          <template #cell(roles)="row">
            {{ row.value[0].name }}
          </template>
          <template #cell(action)="row">
            <b-button
              size="sm"
              class="btn"
              @click="changeStatus(row.item, $event.target)"
              :class="[row.item.active ? activeClass : 'btn-secondary']"
            >
              <span v-if="row.item.active">Block</span>
              <span v-else>UnBlock</span>
            </b-button>
            <b-button
              size="sm"
              @click="info(row.item, row.index, $event.target)"
              class="mr-1 btn btn-danger"
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
          // Variant applies to the whole column, including the header and footer
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
    info(item, index, button) {
      console.log(item);
      this.infoModal.title = `Row index: ${index}`;
      this.infoModal.content = JSON.stringify(item, null, 2);
      this.$root.$emit("bv::show::modal", this.infoModal.id, button);
    },
    changeStatus(item, button) {
      if (item.active) {
        this.$store.dispatch("blockUser", item.id);
      } else {
        this.$store.dispatch("unBlockUser", item.id);
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
