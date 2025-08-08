# Toast 模块

## 简介

Toast 模块提供了一个功能强大、高度可定制的 Toast 实现，支持自定义样式、动画效果和队列管理。

## 主要特性

- [x] 完全自定义的外观
  - 圆角大小
  - 背景颜色
  - 文字样式
  - 图标支持
  - 内边距配置
  
- [x] 预定义样式
  - 成功提示
  - 错误提示
  - 警告提示
  - 普通信息

- [x] 队列管理
  - 支持 Toast 排队显示
  - 可配置最大队列长度
  - 可选择是否允许排队

- [x] 动画效果
  - 支持自定义进入/退出动画
  - 默认提供淡入淡出效果

## 使用方法

### 1. 基本使用

```kotlin
// 显示普通 Toast
ToastManager.show(context, "这是一条普通消息")

// 显示成功 Toast
ToastManager.showSuccess(context, "操作成功")

// 显示错误 Toast
ToastManager.showError(context, "操作失败")

// 显示警告 Toast
ToastManager.showWarning(context, "请注意")

// 显示信息 Toast
ToastManager.showInfo(context, "提示信息")
```

### 2. 自定义配置

```kotlin
// 创建自定义配置
val config = ToastConfig(
    // 布局配置
    maxWidth = 300.dp,
    minHeight = 48.dp,
    cornerRadius = 24f.dp,
    horizontalPadding = 24.dp,
    verticalPadding = 12.dp,
    
    // 位置配置
    gravity = Gravity.CENTER,
    yOffset = 64.dp,
    
    // 背景配置
    backgroundColor = Color.parseColor("#CC000000"),
    
    // 文字配置
    textColor = Color.WHITE,
    textSize = 14f.sp,
    textMaxLines = 2,
    
    // 图标配置
    iconRes = R.drawable.ic_success,
    iconSize = 24.dp,
    
    // 动画配置
    animationDuration = 200L,
    
    // 行为配置
    duration = 2000,
    isAllowQueue = true,
    maxQueueSize = 3
)

// 使用自定义配置显示 Toast
ToastManager.show(context, "自定义样式的消息", config)

// 设置为默认配置
ToastManager.setDefaultConfig(config)
```

### 3. 队列管理

```kotlin
// 取消当前显示的 Toast
ToastManager.cancelCurrent()

// 清空等待队列
ToastManager.clearQueue()
```

## 注意事项

1. **内存管理**
   - Toast 使用弱引用管理 Context，不会造成内存泄漏
   - 队列中的 Toast 会在 Activity 销毁时自动清除

2. **自定义配置**
   - 所有尺寸单位都需要使用 dp/sp
   - 颜色值支持 RGB 和 ARGB 格式
   - 动画资源需要在 res/anim 目录下定义

3. **性能优化**
   - Toast 视图会被复用
   - 使用队列管理避免 Toast 重叠
   - 支持设置最大队列长度，防止队列过长

## 示例代码

### 1. 创建自定义样式

```kotlin
// 定义深色主题样式
val darkStyle = ToastConfig(
    backgroundColor = Color.parseColor("#CC000000"),
    textColor = Color.WHITE,
    cornerRadius = 24f.dp
)

// 定义浅色主题样式
val lightStyle = ToastConfig(
    backgroundColor = Color.parseColor("#CCFFFFFF"),
    textColor = Color.BLACK,
    cornerRadius = 24f.dp
)
```

### 2. 带图标的 Toast

```kotlin
val config = ToastConfig(
    iconRes = R.drawable.ic_success,
    iconSize = 24.dp,
    iconMarginEnd = 8.dp
)

ToastManager.show(context, "带图标的消息", config)
```

### 3. 自定义动画

```kotlin
val config = ToastConfig(
    enterAnimation = R.anim.slide_in,
    exitAnimation = R.anim.slide_out,
    animationDuration = 300L
)

ToastManager.show(context, "带动画的消息", config)
```

## 依赖说明

```kotlin
dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
}
```

## 更新日志

### v1.0.0
- 基础 Toast 实现
- 自定义样式支持
- 队列管理
- 动画效果
- 预定义样式 