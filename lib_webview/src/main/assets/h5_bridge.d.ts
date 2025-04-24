interface H5BridgeResponse {
    code: number;
    message: string;
    data?: any;
}

interface H5Bridge {
    /**
     * 注册 H5 处理器
     * @param name 处理器名称
     * @param handler 处理器函数
     */
    registerHandler(
        name: string,
        handler: (params: any, callback: (response: H5BridgeResponse) => void) => void
    ): void;

    /**
     * 调用原生方法
     * @param action 动作名称
     * @param params 参数对象
     * @param callback 回调函数
     */
    callNative(
        action: string,
        params?: any,
        callback?: (response: H5BridgeResponse) => void
    ): void;
}

declare global {
    interface Window {
        h5Bridge: H5Bridge;
    }
} 