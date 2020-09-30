import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import HNav from "./components/common/HNav.vue";
import HBreadcumb from "./components/common/HBreadcrumb.vue";
import vuetify from "./plugins/vuetify"
import VueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.css'
import 'vue-material/dist/theme/black-green-light.css'

Vue.filter("truncate", function(text, length, clamp) {
    if (text) {
        clamp = clamp || "...";
        return text.length > length ? text.slice(0, length) + clamp : text;
    }
});

new Vue({
    el: '#app',
    router,
    store,
    vuetify,
    render: h => h(App)
}).$mount("#app");

Vue.component("HNav", HNav);
Vue.component("HBreadcrumb", HBreadcumb);

var cors = require('cors')

App.use(cors());