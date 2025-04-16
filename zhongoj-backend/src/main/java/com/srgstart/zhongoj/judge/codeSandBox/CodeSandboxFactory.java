package com.srgstart.zhongoj.judge.codeSandBox;

import com.srgstart.zhongoj.judge.codeSandBox.impl.ExampleCodeSandBoxImpl;
import com.srgstart.zhongoj.judge.codeSandBox.impl.RemoteCodeSandBoxImpl;
import com.srgstart.zhongoj.judge.codeSandBox.impl.ThirdPartyCodeSandBoxImpl;

/**
 * @author srgstart
 * @create 2025/03/27 15:18
 * @description 代码沙箱工厂（根据字符串参数创建指定的代码沙箱实例）
 */
public class CodeSandboxFactory {

    /**
     * 创建代码沙箱示例
     *
     * @param type 沙箱类型
     * @return 代码沙箱实例
     */
    public static CodeSandBox newInstance(String type) {
        switch (type) {
            case "remote":
                return new RemoteCodeSandBoxImpl();
            case "thirdParty":
                return new ThirdPartyCodeSandBoxImpl();
            default:
                return new ExampleCodeSandBoxImpl();
        }
    }

}
