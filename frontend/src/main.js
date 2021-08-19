import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import ToggleButton from 'vue-js-toggle-button'

// Import Bootstrap an BootstrapVue CSS files (order is important)
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import VeeValidate from 'vee-validate';
import vueAwesomeCountdown from 'vue-awesome-countdown'

// FontAwesome
import { library } from '@fortawesome/fontawesome-svg-core'
import { faCog, faEnvelope, faPencilAlt, faPlus, faTint } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

// moment
import VueMoment from 'vue-moment' ;
import moment from 'moment-timezone' ;
import VueSimpleAlert from "vue-simple-alert";



// Alert




Vue.use(vueAwesomeCountdown, 'vac')

Vue.use(VeeValidate);

// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)
Vue.use(ToggleButton)

// Alert

Vue.use(VueSimpleAlert);
Vue.use(VueMoment, { moment, })

// FontAwesome
library.add(faCog, faEnvelope, faPencilAlt, faPlus, faTint)

Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
