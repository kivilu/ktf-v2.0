package com.kivi.framework.util.kit;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.kivi.framework.constant.KtfConstant;
import com.vip.vjtools.vjkit.collection.ArrayUtil;
import com.vip.vjtools.vjkit.number.NumberUtil;
import com.vip.vjtools.vjkit.number.RandomUtil;

/**
 * 本工具类主要是对org.apache.commons.lang3.StringUtils类的封装，目的是为了兼容之前的代码
 * 
 * @author xueqi
 *
 */
public class StrKit {
    public static final String SPACE = " ";
    public static final String DOT = ".";
    public static final String SLASH = "/";
    public static final String BACKSLASH = "\\";
    public static final String EMPTY = "";
    public static final String CRLF = "\r\n";
    public static final String NEWLINE = "\n";
    public static final String UNDERLINE = "_";
    public static final String COMMA = ",";
    public static final String AND = "&";
    public static final String VERTICAL_BAR = "|";
    public static final String HYPHEN = "-";

    public static final String HTML_NBSP = "&nbsp;";
    public static final String HTML_AMP = "&amp";
    public static final String HTML_QUOTE = "&quote;";
    public static final String HTML_LT = "&lt;";
    public static final String HTML_GT = "&gt;";

    /**
     * 每个条目之间的分隔符
     */
    public static final String ITEM_SPLIT = ";";

    /**
     * 属性之间的分隔符
     */
    public static final String ATTR_SPLIT = ":";

    /**
     * 拼接字符串的id
     */
    public static final String MUTI_STR_ID = "ID";

    /**
     * 拼接字符串的key
     */
    public static final String MUTI_STR_KEY = "KEY";

    /**
     * 拼接字符串的value
     */
    public static final String MUTI_STR_VALUE = "VALUE";

    public static final String EMPTY_JSON = "{}";

    /**
     * 空字符串数组
     * 
     * @return
     */
    public static String[] emptyArray() {
        return new String[0];
    }

    /**
     * String-->bytes
     * 
     * @param str
     * @return
     */
    public static byte[] toBytes(String str) {
        return str.getBytes(KtfConstant.DEFAULT_CHARSET);
    }

    /**
     * bytes--->String
     * 
     * @param data
     * @return
     */
    public static String toString(byte[] data) {
        return new String(data, KtfConstant.DEFAULT_CHARSET);
    }

    /**
     * 将对象转为pretty json字符串
     * 
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        return JSON.toJSONString(object, true);
    }

    /**
     * 首字母变小写 <br>
     * {@link StringUtils#uncapitalize(String)}
     */
    public static String firstCharToLowerCase(String str) {
        return StringUtils.uncapitalize(str);
    }

    /**
     * 首字母变大写 <br>
     * {@link StringUtils#capitalize(String)}
     */
    public static String firstCharToUpperCase(String str) {
        return StringUtils.capitalize(str);
    }

    // ------------------------------------------------------------------------
    // Blank
    /**
     * 字符串是否为空白 空白的定义如下： <br>
     * StrKit.isBlank(null) = true <br>
     * StrKit.isBlank("") = true <br>
     * StrKit.isBlank(" ") = true <br>
     * StrKit.isBlank("bob") = false <br>
     * StrKit.isBlank(" bob ") = false<br>
     * 
     * @param str 被检测的字符串
     * @return 是否为空
     */
    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    /**
     * 字符串是否为非空白 空白的定义如下： <br>
     * StrKit.isNotBlank(null) = false <br>
     * StrKit.isNotBlank("") = false <br>
     * StrKit.isNotBlank(" ") = false <br>
     * StrKit.isNotBlank("bob") = true <br>
     * StrKit.isNotBlank(" bob ") = true <br>
     * 
     * @param str 被检测的字符串
     * @return 是否为非空
     */
    public static boolean isNotBlank(String str) {
        return StringUtils.isNotBlank(str);
    }

    /**
     * 是否包含空字符串
     * 
     * {@link StringUtils#isAnyBlank(CharSequence...)}
     * 
     * @param strs 字符串列表
     * @return 是否包含空字符串
     */
    public static boolean hasBlank(String... strs) {
        return StringUtils.isAnyBlank(strs);
    }

    /**
     * 给定所有字符串是否为空白
     * 
     * StrKit.isAllBlank(null) = true <br>
     * StrKit.isAllBlank(null, "foo") = false <br>
     * StrKit.isAllBlank(null, null) = true <br>
     * StrKit.isAllBlank("", "bar") = false <br>
     * StrKit.isAllBlank("bob", "") = false <br>
     * StrKit.isAllBlank(" bob ", null) = false <br>
     * StrKit.isAllBlank(" ", "bar") = false <br>
     * StrKit.isAllBlank("foo", "bar") = false <br>
     * StrKit.isAllBlank(new String[] {}) = true <br>
     * 
     * @param strs 字符串
     * @return 所有字符串是否为空白
     */
    public static boolean isAllBlank(String... strs) {
        return StringUtils.isAllBlank(strs);
    }

    // ------------------------------------------------------------------------
    // Empty
    /**
     * 字符串是否为空，空的定义如下 <br>
     * StringUtils.isEmpty(null) = true <br>
     * StringUtils.isEmpty("") = true <br>
     * StringUtils.isEmpty(" ") = false <br>
     * StringUtils.isEmpty("bob") = false <br>
     * StringUtils.isEmpty(" bob ") = false <br>
     * 
     * @param str 被检测的字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    /**
     * 字符串是否为非空白 空白的定义如下： <br>
     * {@link StringUtils#isNoneEmpty(CharSequence...)}
     * 
     * @param str 被检测的字符串
     * @return 是否为非空
     */
    public static boolean isNotEmpty(String str) {
        return StringUtils.isNotEmpty(str);
    }

    /**
     * 当给定字符串为null时，转换为Empty <br>
     * 
     * {@link StringUtils#defaultString(String)}
     * 
     * @param str 被转换的字符串
     * @return 转换后的字符串
     */
    public static String nullToEmpty(String str) {
        return StringUtils.defaultString(str);
    }

    /**
     * 如果字符串是<code>null</code>，则返回指定默认字符串，否则返回字符串本身。
     * 
     * <pre>
     *{@link StringUtils#defaultString(String, String)}
     * </pre>
     * 
     * @param str 要转换的字符串
     * @param defaultStr 默认字符串
     * 
     * @return 字符串本身或指定的默认字符串
     */
    public static String nullToDefault(String str, String defaultStr) {
        return StringUtils.defaultString(str, defaultStr);
    }

    /**
     * 当给定字符串为空字符串时，转换为<code>null</code>
     * 
     * {@link StringUtils#defaultIfEmpty(String)}
     * 
     * @param str 被转换的字符串
     * @return 转换后的字符串
     */
    public static String emptyToNull(String str) {
        return StringUtils.defaultIfEmpty(str, null);
    }

    /**
     * 是否包含空字符串 <br>
     * 
     * {@link StringUtils#isAnyEmpty(CharSequence...)}
     * 
     * @param strs 字符串列表
     * @return 是否包含空字符串
     */
    public static boolean hasEmpty(String... strs) {
        return StringUtils.isAnyEmpty(strs);
    }

    /**
     * 是否全部为空字符串<br>
     * 
     * {@link StringUtils#isAllEmpty(CharSequence...)}
     * 
     * @param strs 字符串列表
     * @return 是否全部为空字符串
     */
    public static boolean isAllEmpty(String... strs) {
        return StringUtils.isAllEmpty(strs);
    }

    // ------------------------------------------------------------------------
    // Trim
    /**
     * 除去字符串头尾部的空白，如果字符串是<code>null</code>，依然返回<code>null</code>。
     * 
     * <p>
     * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
     * 
     * <pre>
     * StrKit.trim(null)          = null
     * StrKit.trim("")            = ""
     * StrKit.trim("     ")       = ""
     * StrKit.trim("abc")         = "abc"
     * StrKit.trim("    abc    ") = "abc"
     * </pre>
     * 
     * </p>
     * 
     * @param str 要处理的字符串
     * 
     * @return 除去空白的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
     */
    public static String trim(String str) {
        return StringUtils.trim(str);
    }

    /**
     * 给定字符串数组全部做去首尾空格
     * 
     * <pre>
     * StrKit.trim(null)             = null
     * StrKit.trim([])               = []
     * StrKit.trim(["abc", "  abc"]) = ["abc", "abc"]
     * StrKit.trim(["abc  ", null])  = ["abc", null]
     * </pre>
     * 
     * @param strs 字符串数组
     */
    public static void trim(String[] strs) {
        StringUtils.stripAll(strs);
    }

    /**
     * 除去字符串头部的空白，如果字符串是<code>null</code>，则返回<code>null</code>。
     * 
     * <p>
     * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
     * 
     * <pre>
     * StrKit.trimStart(null)        = null
     * StrKit.trimStart("")          = ""
     * StrKit.trimStart("abc" )      = "abc"
     * StrKit.trimStart("  abc" )    = "abc"
     * StrKit.trimStart("abc  " )    = "abc  "
     * StrKit.trimStart(" abc " )    = "abc "
     * </pre>
     * 
     * </p>
     * 
     * @param str 要处理的字符串
     * 
     * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回 <code>null</code>
     */
    public static String trimStart(String str) {
        return StringUtils.stripStart(str, null);
    }

    /**
     * 除去字符串尾部的空白，如果字符串是<code>null</code>，则返回<code>null</code>。
     * 
     * <p>
     * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
     * 
     * <pre>
     * StrKit.trimEnd(null)     = null
     * StrKit.trimEnd("")            = ""
     * StrKit.trimEnd("abc")        = "abc"
     * StrKit.trimEnd("abc", )      = "abc"
     * StrKit.trimEnd("  abc")    = "  abc"
     * StrKit.trimEnd("abc  ")    = "abc"
     * StrKit.trimEnd(" abc ")    = " abc"
     * 
     * </pre>
     * 
     * </p>
     * 
     * @param str 要处理的字符串
     * 
     * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回 <code>null</code>
     */
    public static String trimEnd(String str) {
        return StringUtils.stripEnd(str, null);
    }

    /**
     * 是否以指定字符串开头<br>
     * 
     * <pre>
     * StrKit.startsWith(null, null, false)      = true
     * StrKit.startsWith(null, "abc", false)     = false
     * StrKit.startsWith("abcdef", null, false)  = false
     * StrKit.startsWith("abcdef", "abc", false) = true
     * StringUtils.startsWith("ABCDEF", "abc", false) = true
     * 
     * StrKit.startsWith(null, null, true)      = true
     * StrKit.startsWith(null, "abc", true)     = false
     * StrKit.startsWith("abcdef", null, true)  = false
     * StrKit.startsWith("abcdef", "abc", true) = true
     * StringUtils.startsWith("ABCDEF", "abc", true) = true
     * 
     * </pre>
     * 
     * @param str 被监测字符串
     * @param prefix 开头字符串
     * @param isIgnoreCase 是否忽略大小写
     * @return 是否以指定字符串开头
     */
    public static boolean startWith(String str, String prefix, boolean isIgnoreCase) {
        if (isIgnoreCase) {
            return StringUtils.startsWithIgnoreCase(str, prefix);
        } else {
            return StringUtils.startsWith(str, prefix);
        }
    }

    /**
     * 是否以指定字符串结尾
     * 
     * <pre>
     * StrKit.endsWith(null, null, false)      = true
     * StrKit.endsWith(null, "def", false)     = false
     * StrKit.endsWith("abcdef", null, false)  = false
     * StrKit.endsWith("abcdef", "def", false) = true
     * StrKit.endsWith("ABCDEF", "def", false) = false
     * StrKit.endsWith("ABCDEF", "cde", false) = false
     * StrKit.endsWith("ABCDEF", "", false)    = true
     * 
     * StrKit.endsWith(null, null, true)      = true
     * StrKit.endsWith(null, "def", true)     = false
     * StrKit.endsWith("abcdef", null, true)  = false
     * StrKit.endsWith("abcdef", "def", true) = true
     * StrKit.endsWith("ABCDEF", "def", true) = true
     * StrKit.endsWith("ABCDEF", "cde", true) = false
     * StrKit.endsWith("ABCDEF", "", true)    = true
     * </pre>
     * 
     * @param str 被监测字符串
     * @param suffix 结尾字符串
     * @param isIgnoreCase 是否忽略大小写
     * @return 是否以指定字符串结尾
     */
    public static boolean endWith(String str, String suffix, boolean isIgnoreCase) {
        if (isIgnoreCase) {
            return StringUtils.endsWithIgnoreCase(str, suffix);
        } else {
            return StringUtils.endsWith(str, suffix);
        }
    }

    /**
     * 是否包含特定字符，忽略大小写，如果给定两个参数都为<code>null</code>，返回false
     * 
     * <pre>
     * 
     * StrKit.contains(null, *)     = false
     * StrKit.contains(*, null)     = false
     * StrKit.contains("", "")      = true
     * StrKit.contains("abc", "")   = true
     * StrKit.contains("abc", "a")  = true
     * StrKit.contains("abc", "z")  = false
     * 
     * {@link StringUtils#contains(CharSequence, CharSequence)}
     * </pre>
     * 
     * @param str 被检测字符串
     * @param testStr 被测试是否包含的字符串
     * @return 是否包含
     */
    public static boolean contains(String str, String searchStr) {
        return StringUtils.contains(str, searchStr);
    }

    /**
     * 是否包含特定字符，忽略大小写，如果给定两个参数都为<code>null</code>，返回false
     * 
     * <pre>
     * 
     * {@link StringUtils#containsIgnoreCase(CharSequence, CharSequence)}
     * </pre>
     * 
     * @param str 被检测字符串
     * @param testStr 被测试是否包含的字符串
     * @return 是否包含
     */
    public static boolean containsIgnoreCase(String str, String searchStr) {
        return StringUtils.containsIgnoreCase(str, searchStr);
    }

    /**
     * 切分字符串<br>
     * {@link StringUtils#split(String, char)}
     * 
     * @param str 被切分的字符串
     * @param separator 分隔符字符
     * @return 切分后的集合
     */
    public static List<String> split(String str, char separator) {
        if (StrKit.isBlank(str))
            return null;
        return Arrays.asList(StringUtils.split(str, separator));
    }

    /**
     * 切分字符串<br>
     * {@link StringUtils#split(String, String)}
     * 
     * @param str 被切分的字符串
     * @param delimiter 分隔符
     * @return 字符串
     */
    public static String[] split(String str, String delimiter) {

        return StringUtils.split(str, delimiter);
    }

    /**
     * 比较两个字符串（大小写敏感）。
     * 
     * <pre>
     * equals(null, null)   = true
     * equals(null, "abc")  = false
     * equals("abc", null)  = false
     * equals("abc", "abc") = true
     * equals("abc", "ABC") = false
     * </pre>
     * 
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * 
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equals(String str1, String str2) {
        return StringUtils.equals(str1, str2);
    }

    /**
     * 比较两个字符串（大小写不敏感）。
     * 
     * <pre>
     * equalsIgnoreCase(null, null)   = true
     * equalsIgnoreCase(null, "abc")  = false
     * equalsIgnoreCase("abc", null)  = false
     * equalsIgnoreCase("abc", "abc") = true
     * equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     * 
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * 
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return StringUtils.equalsIgnoreCase(str1, str2);
    }

    /**
     * 比较两个字符串（大小写不敏感）。
     * 
     * <pre>
     * equalsIgnoreCase(null, null)   = false
     * equalsIgnoreCase(null, "abc")  = false
     * equalsIgnoreCase("abc", null)  = false
     * equalsIgnoreCase("abc", "abc") = true
     * equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     * 
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * 
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>false</code>
     */
    public static boolean equalsNotNull(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return false;
        }

        return equalsIgnoreCase(str1, str2);
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 例如：format("aaa {} ccc", "bbb") ----> aaa bbb ccc
     * 
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param values 参数值
     * @return 格式化后的文本
     */
    public static String format(String template, Object... values) {
        if (CollectionKit.isEmpty(values) || isBlank(template)) {
            return template;
        }

        final StringBuilder sb = new StringBuilder();
        final int length = template.length();

        int valueIndex = 0;
        char currentChar;
        for (int i = 0; i < length; i++) {
            if (valueIndex >= values.length) {
                sb.append(StringUtils.substring(template, i, length));
                break;
            }

            currentChar = template.charAt(i);
            if (currentChar == '{') {
                final char nextChar = template.charAt(++i);
                if (nextChar == '}') {
                    sb.append(values[valueIndex++]);
                } else {
                    sb.append('{').append(nextChar);
                }
            } else {
                sb.append(currentChar);
            }

        }

        return sb.toString();
    }

    /**
     * 格式化文本，使用 {varName} 占位<br>
     * map = {a: "aValue", b: "bValue"} format("{a} and {b}", map) ----> aValue and bValue
     * 
     * @param template 文本模板，被替换的部分用 {key} 表示
     * @param map 参数值对
     * @return 格式化后的文本
     */
    public static String format(String template, Map<?, ?> map) {
        if (null == map || map.isEmpty()) {
            return template;
        }

        for (Entry<?, ?> entry : map.entrySet()) {
            template = template.replace("{" + entry.getKey() + "}", entry.getValue().toString());
        }
        return template;
    }

    /**
     * 连接成字符串
     * 
     * @param objs
     * @return
     */
    public static String join(Object... objs) {
        return StringUtils.join(objs);
    }

    /**
     * 连接成字符串
     * 
     * @param objs
     * @return
     */
    public static String join(final List<?> objs) {
        return StringUtils.join(objs);
    }

    /**
     * 以 conjunction 为分隔符将多个对象转换为字符串
     * 
     * @param conjunction 分隔符
     * @param objs 数组
     * @return 连接后的字符串
     */
    public static String joinWith(String conjunction, Object... objs) {
        return StringUtils.join(objs, conjunction);
    }

    public static String joinWith(String conjunction, final List<?> list) {
        return StringUtils.join(list, conjunction);
    }

    /**
     * 以 conjunction 为分隔符将多个对象转换为字符串，忽略null
     * 
     * @param conjunction 分隔符
     * @param objs 数组
     * @return 连接后的字符串
     */
    public static String joinIgnoreNull(String conjunction, Object... objs) {
        return StringUtils.join(objs, conjunction);
    }

    /**
     * 将字符串数组转换为Long数组
     * 
     * @param values
     * @return
     */
    public static Long[] toLongArray(String[] values) {
        if (values == null)
            return new Long[0];

        List<Long> list = Arrays.asList(values).stream().map(NumberUtil::toLong).collect(Collectors.toList());
        return ArrayUtil.toArray(list, Long.class);
    }

    public static String mask(String str) {
        final String MASK = "****";
        if (isBlank(str))
            return str;

        int length = str.length();

        if (length == 1)
            return StringUtils.join(str, MASK);
        else if (length < 5)
            return StringUtils.join(StringUtils.substring(str, 0, 1), MASK, StringUtils.substring(str, length - 1));
        else if (length < 9)
            return StringUtils.join(StringUtils.substring(str, 0, 2), MASK, StringUtils.substring(str, length - 2));
        else
            return StringUtils.join(StringUtils.substring(str, 0, 4), MASK, StringUtils.substring(str, length - 4));

    }

    public static String random(int num) {
        return RandomUtil.randomStringFixLength(num);
    }

    public static String getExceptionToString(Throwable e) {
        if (e == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * 驼峰名称字符串转换为下划线
     * 
     * @param str
     * @return
     */
    public static String camel2Underline(String str) {
        String[] items = StringUtils.splitByCharacterTypeCamelCase(str);

        return StringUtils.join(items, "_").toLowerCase();
    }

}
