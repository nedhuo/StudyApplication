package com.example.lib_spider.meowtv.parser

import org.jsoup.Jsoup
import org.junit.Test
import io.mockk.every
import io.mockk.mockk
import kotlin.test.assertEquals

class MeowTvHtmlParserTest {

    @Test
    fun `正常解析导航数据`() {
        // 构造标准HTML结构
        val html = """
            <div class="nav-items">
                <a href="/api1" ext="v1" jar="jar1">name1</a>
                <a href="/api2" ext="v2" jar="jar2">name2</a>
            </div>
        """.trimIndent()

        val result = MeowTvHtmlParser.parseHomePage(html)

        assertEquals(2, result.size)
        assertEquals("name1", result[0].name)
        assertEquals("/api1", result[0].api)
        assertEquals("v1", result[0].ext)
        assertEquals("jar1", result[0].jar)
    }

    @Test
    fun `处理空HTML内容`() {
        val result = MeowTvHtmlParser.parseHomePage("")
        assertEquals(0, result.size)
    }

    @Test
    fun `解析异常标签结构`() {
        // 构造不完整HTML结构
        val brokenHtml = """
            <div class="nav-items">
                <a href="/api1">missing attributes
                <div>unexpected element</div>
        """.trimIndent()

        val result = MeowTvHtmlParser.parseHomePage(brokenHtml)
        
        // 验证能解析出有效数据并忽略异常部分
        assertEquals(1, result.size)
        assertEquals("missing attributes", result[0].name)
        assertEquals("/api1", result[0].api)
        assertEquals("", result[0].ext)  // 缺失属性应返回空字符串
        assertEquals("", result[0].jar)
    }

    @Test
    fun `解析带特殊字符的属性值`() {
        val html = """
            <div class="nav-items">
                <a href="/api?q=1&lang=zh" ext="v'1.0" jar="special#jar">名称&amp;标识</a>
            </div>
        """.trimIndent()

        val result = MeowTvHtmlParser.parseHomePage(html)
        
        assertEquals(1, result.size)
        assertEquals("名称&标识", result[0].name)
        assertEquals("/api?q=1&lang=zh", result[0].api)
        assertEquals("v'1.0", result[0].ext)
        assertEquals("special#jar", result[0].jar)
    }

    @Test
    fun `解析嵌套子元素结构`() {
        val html = """
            <div class="nav-items">
                <a href="/nested" ext="v3" jar="jar3">
                    主标题
                    <span class="sub">子标题</span>
                </a>
            </div>
        """.trimIndent()

        val result = MeowTvHtmlParser.parseHomePage(html)
        
        assertEquals(1, result.size)
        assertEquals("主标题", result[0].name.trim())
        assertEquals("/nested", result[0].api)
    }

    @Test
    fun `处理部分属性缺失`() {
        val html = """
            <div class="nav-items">
                <a href="/no-ext">无ext属性</a>
                <a href="/no-jar" ext="v4">无jar属性</a>
            </div>
        """.trimIndent()

        val result = MeowTvHtmlParser.parseHomePage(html)
        
        assertEquals(2, result.size)
        assertEquals("", result[0].ext)
        assertEquals("", result[0].jar)
        assertEquals("v4", result[1].ext)
        assertEquals("", result[1].jar)
    }
}