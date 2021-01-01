import Vue from "vue";
import App from "./App.vue";
import { router } from "./router";
import store from "./store/store";
import Vuex from "vuex";
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

import Vuelidate from "vuelidate";
import VueSweetalert2 from "vue-sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

import Loader from "./components/Loader";

import {
  faHome,
  faUser,
  faUserPlus,
  faSignInAlt,
  faSignOutAlt,
  faImages,
  faEdit,
  faMapMarkerAlt,
  faPhone,
  faEnvelope
} from "@fortawesome/free-solid-svg-icons";

library.add(
  faHome,
  faUser,
  faUserPlus,
  faSignInAlt,
  faSignOutAlt,
  faImages,
  faEdit,
  faMapMarkerAlt,
  faPhone,
  faEnvelope
);
Vue.use(Vuelidate);
Vue.use(BootstrapVue);
Vue.use(VueSweetalert2);

Vue.use(Vuex);
Vue.component("Loader", Loader);
Vue.component("font-awesome-icon", FontAwesomeIcon);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
