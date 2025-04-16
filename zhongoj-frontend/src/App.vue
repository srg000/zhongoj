<template>
  <div class="app">
    <template v-if="route.path.startsWith('/user')">
      <router-view />
    </template>
    <template v-else>
      <BasicLayouts />
    </template>
  </div>
</template>

<style>
.app {
}
</style>
<script setup lang="ts">
import BasicLayouts from "@/layouts/BasicLayouts.vue";
import { useRoute } from "vue-router";

const route = useRoute();
</script>
