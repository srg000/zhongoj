import ACCESS_ENUM from "@/access/accessEnum";
import checkAccess from "@/access/checkAccess";
import router from "@/router";
import store from "@/store";

router.beforeEach(async (to, from, next) => {
  // 在进入页面之前再获取一遍 登录用户信息
  await store.dispatch("user/getLoginUser", null);
  const loginUser = store.state.user.loginUser;
  console.log("登陆用户信息", loginUser);
  const needAccess = (to.meta?.access as string) ?? ACCESS_ENUM.NOT_LOGIN;
  // 要跳转的页面必须要登陆
  if (needAccess !== ACCESS_ENUM.NOT_LOGIN) {
    // 如果没登陆，跳转到登录页面
    if (
      !loginUser ||
      !loginUser.userRole ||
      loginUser.userRole === ACCESS_ENUM.NOT_LOGIN
    ) {
      next(`/user/login?redirect=${to.fullPath}`);
      return;
    }
    // 如果已经登陆了，但是权限不足，那么跳转到无权限页面
    if (!checkAccess(loginUser, needAccess)) {
      next("/noAuth");
      return;
    }
  }
  next();
});
