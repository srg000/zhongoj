<template>
  <div id="userRegisterPage">
    <h2 style="margin-bottom: 16px">用户注册</h2>
    <a-form
      :model="form"
      :style="{ width: '480px', margin: '0 auto' }"
      label-align="left"
      auto-label-width
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
      <a-form-item
        field="checkPassword"
        tooltip="确认密码不少于 8 位"
        label="确认密码"
      >
        <a-input-password
          v-model="form.checkPassword"
          placeholder="请输入确认密码"
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
            >注册
          </a-button>
          <a-link style="text-decoration: none" href="/user/login"
            >已有账号？去登录
          </a-link>
        </div>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import { Message } from "@arco-design/web-vue";
import { useRouter } from "vue-router";
import { UserControllerService, UserRegisterRequest } from "../../../generated";

const router = useRouter();

const form = reactive({
  userAccount: "",
  userPassword: "",
  checkPassword: "",
} as UserRegisterRequest);

/**
 * 提交
 */
const handleSubmit = async () => {
  console.log(form);
  const res = await UserControllerService.userRegisterUsingPost(form);
  if (res.data.code === 0) {
    Message.success("注册成功");
    await router.push({
      path: "/user/login",
      replace: true,
    });
  } else {
    Message.error("注册失败：" + res.data.message);
  }
};
</script>
