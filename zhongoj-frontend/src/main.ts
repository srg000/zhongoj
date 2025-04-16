import { createApp } from "vue";
import App from "./App.vue";
import ArcoVue from "@arco-design/web-vue";
import ArcoVueIcon from "@arco-design/web-vue/es/icon";
import "@arco-design/web-vue/dist/arco.css";
import "bytemd/dist/index.css";
import router from "./router";
import store from "./store";
import "@/access";
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import JsonViewer from "vue-json-viewer";
import "vue-json-viewer/style.css";

createApp(App)
  .use(JsonViewer)
  .use(ArcoVue)
  .use(ArcoVueIcon)
  .use(store)
  .use(router)
  .mount("#app");
