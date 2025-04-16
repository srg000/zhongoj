<template>
  <div class="basicLayouts">
    <a-layout style="height: 400px">
      <a-layout-header class="header">
        <GlobalHeader />
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
      <a-layout-footer class="footer">
        <a
          href="http://localhost:8080"
          style="text-decoration: none; color: dodgerblue"
          target="_blank"
        >
          OJ 在线判题系统 By 柊泓迅
        </a>
      </a-layout-footer>
    </a-layout>
  </div>
</template>

<script setup lang="ts">
import GlobalHeader from "@/components/GlobalHeader.vue";
</script>

<style scoped>
.basicLayouts {
}

.basicLayouts .header {
  margin-bottom: 16px;
}

.basicLayouts .content {
  margin-bottom: 16px;
  background: linear-gradient(to right, #fefefe, #fff);
  //background: linear-gradient(to right, #fefefe, #fff);
  padding: 20px;
}

.basicLayouts .footer {
  padding: 16px;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
}
</style>
