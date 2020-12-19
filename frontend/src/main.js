import Vue from "vue";
import App from "./App.vue";
import { router } from "./router";
import store from "./store/store";
import Vuex from "vuex";
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import VModal from "vue-js-modal";

import {
  faHome,
  faUser,
  faUserPlus,
  faSignInAlt,
  faSignOutAlt
} from "@fortawesome/free-solid-svg-icons";

import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
library.add(faHome, faUser, faUserPlus, faSignInAlt, faSignOutAlt);
import Loader from "./components/Loader";
Vue.use(VModal, { componentName: "v-modal" });
Vue.component("Loader", Loader);
Vue.use(Vuex);

// Install BootstrapVue
Vue.use(BootstrapVue);
Vue.component("font-awesome-icon", FontAwesomeIcon);
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
