<template>
  <div class="home">
    <MdEditor :value="mdValue" :handle-change="onMdChange" />
    <CodeEditor :value="codeValue" :handle-change="onCodeChange" />
  </div>
</template>

<script setup lang="ts">
import MdEditor from "@/components/MdEditor.vue";
import { ref } from "vue";
import CodeEditor from "@/components/CodeEditor.vue";

const mdValue = ref("");
const codeValue = ref("");

const onMdChange = (v: string) => {
  mdValue.value = v;
};

const onCodeChange = (v: string) => {
  codeValue.value = v;
};
</script>
