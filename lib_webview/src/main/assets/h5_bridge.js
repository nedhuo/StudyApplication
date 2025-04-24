;(function() {
    if (window.h5Bridge) {
        return;
    }

    var callbackId = 0;
    var callbacks = {};
    var handlers = {};

    var bridge = {
        // 注册 H5 处理器
        registerHandler: function(name, handler) {
            handlers[name] = handler;
        },

        // 调用原生方法
        callNative: function(action, params, callback) {
            var callbackName = null;
            
            if (callback) {
                callbackName = 'h5_callback_' + (++callbackId);
                callbacks[callbackName] = callback;
            }
            
            window.nativeBridge.handleCall(
                action,
                JSON.stringify(params || {}),
                callbackName
            );
        },

        // 处理原生调用
        onNativeCall: function(action, params, callback) {
            var handler = handlers[action];
            
            if (!handler) {
                console.error('未找到处理器：' + action);
                if (callback) {
                    window[callback]({
                        code: -3,
                        message: '未找到处理器：' + action
                    });
                }
                return;
            }
            
            try {
                handler(params, function(response) {
                    if (callback) {
                        window[callback](response);
                    }
                });
            } catch (e) {
                console.error('处理原生调用失败：', e);
                if (callback) {
                    window[callback]({
                        code: -1,
                        message: e.message || '未知错误'
                    });
                }
            }
        }
    };

    // 注册全局对象
    window.h5Bridge = bridge;
})(); 