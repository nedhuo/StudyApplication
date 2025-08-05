package com.example.lib_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 站点表实体
 * 字段能力说明：
 * @property key           站点唯一标识
 * @property name          站点名称
 * @property type          站点类型（1=影视，2=动漫，3=云盘等）
 * @property api           解析器/爬虫类型（如 csp_xxx），用于动态加载爬虫实现
 * @property indexs        站点排序权重
 * @property searchable    是否支持搜索（1=支持，0=不支持）
 * @property quickSearch   是否支持快速搜索（1=支持，0=不支持）
 * @property filterable    是否支持筛选（1=支持，0=不支持）
 * @property changeable    是否支持换源（1=支持，0=不支持）
 * @property playerType    播放器类型（可选）
 * @property timeout       网络请求超时时间（毫秒，可选）
 * @property jar           依赖 jar 包路径（可选）
 * @property ext           额外扩展参数，通常为 json 字符串，包含站点 url、弹幕类型、云盘配置等
 */
@Entity(tableName = "tvbox_site")
data class TvBoxSiteEntity(
    @PrimaryKey val key: String,              // 站点唯一标识
    val name: String,                        // 站点名称
    val type: Int = 3,                       // 站点类型（1=影视，2=动漫，3=云盘等）
    val api: String,                         // 解析器/爬虫类型
    val indexs: Int? = null,                 // 站点排序权重
    val searchable: Int? = null,             // 是否支持搜索（1=支持，0=不支持）
    val quickSearch: Int? = null,            // 是否支持快速搜索（1=支持，0=不支持）
    val filterable: Int? = null,             // 是否支持筛选（1=支持，0=不支持）
    val changeable: Int? = null,             // 是否支持换源（1=支持，0=不支持）
    val playerType: Int? = null,             // 播放器类型（可选）
    val timeout: Int? = null,                // 网络请求超时时间（毫秒，可选）
    val jar: String? = null,                 // 依赖 jar 包路径（可选）
    val ext: String? = null                  // 额外扩展参数，通常为 json 字符串
) 