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
/*
Vue.config.productionTip = false;
*/
library.add(faHome, faUser, faUserPlus, faSignInAlt, faSignOutAlt);

Vue.use(VModal, { componentName: "v-modal" });

Vue.use(Vuex);

Vue.component("font-awesome-icon", FontAwesomeIcon);
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
