# WebView 模块

## 简介

WebView 模块提供了一个强大的 Android WebView 封装，实现了原生与 H5 之间的双向通信桥接功能。该模块主要解决以下问题：

1. WebView 基础配置的统一管理
2. JavaScript 与原生代码的双向通信
3. 安全性控制
4. 性能优化
5. 内存泄漏防护

## 功能特性

- [x] 统一的 WebView 配置管理
- [x] 双向通信桥接（Native ⟷ JavaScript）
- [x] 支持异步回调
- [x] 超时处理机制
- [x] 弱引用管理，防止内存泄漏
- [x] 统一的错误处理
- [x] TypeScript 类型支持（H5 端）

## 使用方法

### 1. 在布局中使用 BaseWebView

```xml
<com.example.lib_webview.view.BaseWebView
    android:id="@+id/webView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

### 2. 注册原生处理器

```kotlin
// 在 Activity 或 Fragment 中
webView.onCreate {
    // 注册分享处理器
    H5BridgeManager.registerHandler("share", ShareHandler())
}
```

### 3. H5 调用原生方法

```javascript
// 调用分享功能
window.h5Bridge.callNative(
    'share',
    {
        title: '分享标题',
        desc: '分享描述',
        link: 'https://example.com',
        image: 'https://example.com/image.jpg',
        platform: 'wechat'
    },
    function(response) {
        if (response.code === 0) {
            console.log('分享成功');
        } else {
            console.error('分享失败：' + response.message);
        }
    }
);
```

### 4. 原生调用 H5 方法

```kotlin
// 在原生代码中调用 H5 方法
H5BridgeManager.callH5(
    action = "updateTitle",
    params = mapOf("title" to "新标题"),
    callback = { response ->
        if (response.code == H5BridgeResponse.CODE_SUCCESS) {
            Log.d("WebView", "标题更新成功")
        }
    }
)
```

### 5. H5 注册处理器

```javascript
// 在 H5 中注册处理器
window.h5Bridge.registerHandler('updateTitle', function(params, callback) {
    document.title = params.title;
    callback({
        code: 0,
        message: '标题已更新',
        data: { title: params.title }
    });
});
```

## 错误码说明

| 错误码 | 说明 |
|--------|------|
| 0 | 成功 |
| -1 | 一般错误 |
| -2 | 超时 |
| -3 | 未找到处理器 |
| -4 | 取消操作 |

## 注意事项

1. **内存管理**
   - 在 Activity/Fragment 销毁时调用 `webView.onDestroy()`
   - 不使用时注销处理器：`H5BridgeManager.unregisterHandler("handler_name")`

2. **安全性**
   - 已禁用 file:// 协议的跨域访问
   - 仅允许可信域名的 JavaScript 执行
   - 敏感操作需要进行权限验证

3. **性能优化**
   - 使用弱引用管理 WebView 实例
   - 实现了请求超时机制（默认 30 秒）
   - 支持 WebView 复用

## 示例代码

### 自定义处理器

```kotlin
class CustomHandler : H5BridgeHandler {
    override fun handle(request: H5BridgeRequest, callback: (H5BridgeResponse) -> Unit) {
        try {
            // 处理请求
            callback(H5BridgeResponse.success(data = "处理结果"))
        } catch (e: Exception) {
            callback(H5BridgeResponse.error(e.message ?: "未知错误"))
        }
    }
}
```

### Activity 集成

```kotlin
class WebViewActivity : AppCompatActivity() {
    private lateinit var webView: BaseWebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        
        webView = findViewById(R.id.webView)
        
        // 注册处理器
        H5BridgeManager.registerHandler("customAction", CustomHandler())
        
        // 加载网页
        webView.loadUrl("https://example.com")
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }
}
```

## 依赖说明

```kotlin
dependencies {
    implementation("androidx.webkit:webkit:1.7.0")
    implementation("com.google.code.gson:gson:2.10.1")
    // ... 其他依赖
}
```

## 更新日志

### v1.0.0
- 基础 WebView 封装
- JavaScript 桥接实现
- 分享功能示例
- 完整的错误处理机制
- TypeScript 类型支持 