package com.goshine.gscenter.gskit.base;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 *
 * 尽量使用Charsets.UTF8而不是"UTF-8"，减少JDK里的Charset查找消耗.
 *
 * 使用JDK7的StandardCharsets，同时留了标准名称的字符串
 *
 */
public class Charsets {
    public static final Charset UTF_8 = StandardCharsets.UTF_8;
    public static final String UTF_8_NAME = StandardCharsets.UTF_8.name();
}
