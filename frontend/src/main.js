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
import * as firebase from "firebase/app";
import "firebase/storage";

var firebaseConfig = {
    apiKey: "AIzaSyA25rnJNs_q5KELyjwtbkfidT6X28M9rSY",
    authDomain: "fir-chat-7c99b.firebaseapp.com",
    databaseURL: "https://fir-chat-7c99b.firebaseio.com",
    projectId: "fir-chat-7c99b",
    storageBucket: "fir-chat-7c99b.appspot.com",
    messagingSenderId: "158051831435",
    appId: "1:158051831435:web:f9a46181db2bfe1ce80e8e",
    measurementId: "G-SQRQ354GKV"
  };

export default !firebase.apps.length ? firebase.initializeApp(firebaseConfig) : firebase.app();

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