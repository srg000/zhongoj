import { StoreOptions } from "vuex";
import accessEnum from "@/access/accessEnum";
import { UserControllerService } from "../../generated";

export default {
  namespaced: true,
  state: () => ({
    loginUser: {
      userName: "未登录",
    },
  }),
  // 调用 mutations方法
  actions: {
    async getLoginUser({ commit, state }, payload) {
      const res = await UserControllerService.getLoginUserUsingGet();
      if (res.code === 0) {
        commit("updateLocalUser", res.data);
      } else {
        commit("updateLocalUser", {
          ...state.loginUser,
          userRole: accessEnum.NOT_LOGIN,
        });
      }
    },
  },
  // 修改 state
  mutations: {
    updateLocalUser(state, payload) {
      state.loginUser = payload;
    },
  },
} as StoreOptions<any>;
