<template>
  <div class="userLoginView">
    <h2 style="margin-bottom: 16px">用户登录</h2>
    <a-form
      style="max-width: 480px; margin: 0 auto"
      label-align="left"
      auto-label-width
      :model="form"
      @submit="handleSubmit"
    >
      <a-form-item field="userAccount" label="账号">
        <a-input v-model="form.userAccount" placeholder="请输入账号" />
      </a-form-item>
      <a-form-item field="userPassword" tooltip="密码不少于 8 位" label="密码">
        <a-input-password
          v-model="form.userPassword"
          placeholder="请输入密码"
        />
      </a-form-item>
      <a-form-item>
        <div
          style="
            display: flex;
            justify-content: space-between;
            width: 100%;
            align-items: center;
          "
        >
          <a-button type="primary" html-type="submit" style="width: 120px"
            >登录</a-button
          >
          <a-link style="text-decoration: none" href="/user/register"
            >新用户注册</a-link
          >
        </div>
      </a-form-item>
    </a-form>
  </div>
</template>
<script setup lang="ts">
import { ref } from "vue";
import { UserControllerService, UserLoginRequest } from "../../../generated";
import { Message } from "@arco-design/web-vue";
import { useRouter } from "vue-router";
import store from "@/store";

const form = ref<UserLoginRequest>({
  userAccount: "",
  userPassword: "",
});

const router = useRouter();

const handleSubmit = async () => {
  const res = await UserControllerService.userLoginUsingPost(form.value);
  if (res.code === 0) {
    Message.success("登录成功");
    // 重新设置 store中的信息
    await store.dispatch("user/getLoginUser", null);
    // 跳转到首页
    await router.push({ path: "/", replace: true });
  } else {
    Message.error("登录失败：" + res.message);
  }
};
</script>
