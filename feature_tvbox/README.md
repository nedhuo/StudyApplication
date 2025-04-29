# feature_tvbox 模块说明

## 模块定位

`feature_tvbox` 负责 TV 盒子相关的业务功能，包括影视数据解析、展示、播放等。

## 目录结构（推荐分层）

```
feature_tvbox/
├── data/           # 数据层（如本地数据源、远程数据源、仓库）
│   ├── TvBoxRepository.kt
│   └── model/
│       └── TvBoxVideo.kt
├── domain/         # 业务逻辑层（如用例/UseCase）
│   └── FetchAndParseMeowTvUseCase.kt
├── ui/             # 界面层
│   ├── list/
│   │   ├── TvBoxListFragment.kt
│   │   ├── TvBoxListViewModel.kt
│   │   └── TvBoxVideoAdapter.kt
│   ├── data/
│   │   └── TvBoxDataFragment.kt
│   └── player/
│       ├── TvBoxPlayerActivity.kt
│       └── TvBoxPlayerViewModel.kt
├── di/             # 依赖注入（如 Hilt Module）
│   └── TvBoxModule.kt
└── navigation/     # 导航相关
    └── tvbox_nav_graph.xml
```

## 主要功能
- 解析 http://www.meowtv.top 影视数据
- 数据入库与本地缓存
- 影视列表展示、下拉刷新
- 视频播放
- 通用 loading 弹框

## 开发约定
- 严格分层，UI 不直接依赖数据源
- 依赖注入推荐使用 Hilt/Koin
- 业务流程通过 UseCase 调度
- 所有网络、数据库操作均在 Repository 层完成 