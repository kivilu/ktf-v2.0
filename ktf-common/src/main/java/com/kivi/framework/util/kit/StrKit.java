package com.kivi.framework.util.kit;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * 本工具类主要是对org.apache.commons.lang3.StringUtils类的封装，目的是为了兼容之前的代码
 * 
 * @author xueqi
 *
 */
public class StrKit {

	public static final String	SPACE			= " ";
	public static final String	DOT				= ".";
	public static final String	SLASH			= "/";
	public static final String	BACKSLASH		= "\\";
	public static final String	EMPTY			= "";
	public static final String	CRLF			= "\r\n";
	public static final String	NEWLINE			= "\n";
	public static final String	UNDERLINE		= "_";
	public static final String	COMMA			= ",";
	public static final String	AND				= "&";
	public static final String	VERTICAL_BAR	= "|";
	public static final String	HYPHEN			= "-";

	public static final String	HTML_NBSP		= "&nbsp;";
	public static final String	HTML_AMP		= "&amp";
	public static final String	HTML_QUOTE		= "&quote;";
	public static final String	HTML_LT			= "&lt;";
	public static final String	HTML_GT			= "&gt;";

	/**
	 * 每个条目之间的分隔符
	 */
	public static final String	ITEM_SPLIT		= ";";

	/**
	 * 属性之间的分隔符
	 */
	public static final String	ATTR_SPLIT		= ":";

	/**
	 * 拼接字符串的id
	 */
	public static final String	MUTI_STR_ID		= "ID";

	/**
	 * 拼接字符串的key
	 */
	public static final String	MUTI_STR_KEY	= "KEY";

	/**
	 * 拼接字符串的value
	 */
	public static final String	MUTI_STR_VALUE	= "VALUE";

	public static final String	EMPTY_JSON		= "{}";

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
	 * @param str        要转换的字符串
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
	 * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code>
	 * 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
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
	 * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code>
	 * 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
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
	 * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回
	 *         <code>null</code>
	 */
	public static String trimStart(String str) {
		return StringUtils.stripStart(str, null);
	}

	/**
	 * 除去字符串尾部的空白，如果字符串是<code>null</code>，则返回<code>null</code>。
	 * 
	 * <p>
	 * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code>
	 * 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
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
	 * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>""</code>，则返回
	 *         <code>null</code>
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
	 * @param str          被监测字符串
	 * @param prefix       开头字符串
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
	 * @param str          被监测字符串
	 * @param suffix       结尾字符串
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
	 * {@link StringUtils#containsIgnoreCase(CharSequence, CharSequence)}
	 * </pre>
	 * 
	 * @param str     被检测字符串
	 * @param testStr 被测试是否包含的字符串
	 * @return 是否包含
	 */
	public static boolean containsIgnoreCase(String str, String testStr) {
		return StringUtils.containsIgnoreCase(str, testStr);
	}

	/**
	 * 切分字符串<br>
	 * {@link StringUtils#split(String, char)}
	 * 
	 * @param str       被切分的字符串
	 * @param separator 分隔符字符
	 * @return 切分后的集合
	 */
	public static List<String> split(String str, char separator) {
		return Arrays.asList(StringUtils.split(str, separator));
	}

	/**
	 * 切分字符串<br>
	 * {@link StringUtils#split(String, String)}
	 * 
	 * @param str       被切分的字符串
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
	 * @param values   参数值
	 * @return 格式化后的文本
	 */
	public static String format(String template, Object... values) {
		if (CollectionKit.isEmpty(values) || isBlank(template)) {
			return template;
		}

		final StringBuilder	sb			= new StringBuilder();
		final int			length		= template.length();

		int					valueIndex	= 0;
		char				currentChar;
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
	 * map = {a: "aValue", b: "bValue"} format("{a} and {b}", map) ----> aValue and
	 * bValue
	 * 
	 * @param template 文本模板，被替换的部分用 {key} 表示
	 * @param map      参数值对
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
	 * 编码字符串
	 * 
	 * @param str     字符串
	 * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
	 * @return 编码后的字节码
	 */
	public static byte[] bytes(String str, String charset) {
		return bytes(str, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
	}

	/**
	 * 编码字符串
	 * 
	 * @param str     字符串
	 * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
	 * @return 编码后的字节码
	 */
	public static byte[] bytes(String str, Charset charset) {
		if (str == null) {
			return null;
		}

		if (null == charset) {
			return str.getBytes();
		}
		return str.getBytes(charset);
	}

	/**
	 * 将byte数组转为字符串
	 * 
	 * @param bytes   byte数组
	 * @param charset 字符集
	 * @return 字符串
	 */
	public static String str(byte[] bytes, String charset) {
		return str(bytes, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
	}

	/**
	 * 解码字节码
	 * 
	 * @param data    字符串
	 * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
	 * @return 解码后的字符串
	 */
	public static String str(byte[] data, Charset charset) {
		if (data == null) {
			return null;
		}

		if (null == charset) {
			return new String(data);
		}
		return new String(data, charset);
	}

	/**
	 * 将编码的byteBuffer数据转换为字符串
	 * 
	 * @param data    数据
	 * @param charset 字符集，如果为空使用当前系统字符集
	 * @return 字符串
	 */
	public static String str(ByteBuffer data, String charset) {
		if (data == null) {
			return null;
		}

		return str(data, Charset.forName(charset));
	}

	/**
	 * 将编码的byteBuffer数据转换为字符串
	 * 
	 * @param data    数据
	 * @param charset 字符集，如果为空使用当前系统字符集
	 * @return 字符串
	 */
	public static String str(ByteBuffer data, Charset charset) {
		if (null == charset) {
			charset = Charset.defaultCharset();
		}
		return charset.decode(data).toString();
	}

	/**
	 * 字符串转换为byteBuffer
	 * 
	 * @param str     字符串
	 * @param charset 编码
	 * @return byteBuffer
	 */
	public static ByteBuffer byteBuffer(String str, String charset) {
		return ByteBuffer.wrap(StrKit.bytes(str, charset));
	}

	/**
	 * 以 conjunction 为分隔符将多个对象转换为字符串
	 * 
	 * @param conjunction 分隔符
	 * @param objs        数组
	 * @return 连接后的字符串
	 */
	public static String join(String conjunction, Object... objs) {
		return StringUtils.join(objs, conjunction);
	}

	public static String join(String conjunction, List<String> texts) {
		return StringUtils.join(texts, conjunction);
	}

	/**
	 * 以 conjunction 为分隔符将多个对象转换为字符串，忽略null
	 * 
	 * @param conjunction 分隔符
	 * @param objs        数组
	 * @return 连接后的字符串
	 */
	public static String joinIgnoreNull(String conjunction, Object... objs) {
		return StringUtils.join(objs, conjunction);
	}

	/**
	 * 将驼峰式命名的字符串转换为下划线方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HelloWorld->hello_world
	 *
	 * @param camelCaseStr 转换前的驼峰式命名的字符串
	 * @return 转换后下划线大写方式命名的字符串
	 */
	public static String toUnderlineCase(String camelCaseStr) {
		if (camelCaseStr == null) {
			return null;
		}

		final int		length			= camelCaseStr.length();
		StringBuilder	sb				= new StringBuilder();
		char			c;
		boolean			isPreUpperCase	= false;
		for (int i = 0; i < length; i++) {
			c = camelCaseStr.charAt(i);
			boolean isNextUpperCase = true;
			if (i < (length - 1)) {
				isNextUpperCase = Character.isUpperCase(camelCaseStr.charAt(i + 1));
			}
			if (Character.isUpperCase(c)) {
				if (!isPreUpperCase || !isNextUpperCase) {
					if (i > 0)
						sb.append(UNDERLINE);
				}
				isPreUpperCase = true;
			} else {
				isPreUpperCase = false;
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	/**
	 * 将下划线方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
	 * 例如：hello_world->HelloWorld
	 *
	 * @param name 转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String toCamelCase(String name) {
		if (name == null) {
			return null;
		}
		if (name.contains(UNDERLINE)) {
			name = name.toLowerCase();

			StringBuilder	sb			= new StringBuilder(name.length());
			boolean			upperCase	= false;
			for (int i = 0; i < name.length(); i++) {
				char c = name.charAt(i);

				if (c == '_') {
					upperCase = true;
				} else if (upperCase) {
					sb.append(Character.toUpperCase(c));
					upperCase = false;
				} else {
					sb.append(c);
				}
			}
			return sb.toString();
		} else
			return name;
	}

	/**
	 * 包装指定字符串
	 * 
	 * @param str    被包装的字符串
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return 包装后的字符串
	 */
	public static String wrap(String str, String prefix, String suffix) {
		return format("{}{}{}", prefix, str, suffix);
	}

	/**
	 * 指定字符串是否被包装
	 * 
	 * @param str    字符串
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return 是否被包装
	 */
	public static boolean isWrap(String str, String prefix, String suffix) {
		return str.startsWith(prefix) && str.endsWith(suffix);
	}

	/**
	 * 指定字符串是否被同一字符包装（前后都有这些字符串）
	 * 
	 * @param str     字符串
	 * @param wrapper 包装字符串
	 * @return 是否被包装
	 */
	public static boolean isWrap(String str, String wrapper) {
		return isWrap(str, wrapper, wrapper);
	}

	/**
	 * 指定字符串是否被同一字符包装（前后都有这些字符串）
	 * 
	 * @param str     字符串
	 * @param wrapper 包装字符
	 * @return 是否被包装
	 */
	public static boolean isWrap(String str, char wrapper) {
		return isWrap(str, wrapper, wrapper);
	}

	/**
	 * 指定字符串是否被包装
	 * 
	 * @param str        字符串
	 * @param prefixChar 前缀
	 * @param suffixChar 后缀
	 * @return 是否被包装
	 */
	public static boolean isWrap(String str, char prefixChar, char suffixChar) {
		return str.charAt(0) == prefixChar && str.charAt(str.length() - 1) == suffixChar;
	}

	/**
	 * 补充字符串以满足最小长度 StrUtil.padPre("1", 3, '0');//"001"
	 * 
	 * @param str       字符串
	 * @param minLength 最小长度
	 * @param padChar   补充的字符
	 * @return 补充后的字符串
	 */
	public static String padPre(String str, int minLength, char padChar) {
		if (str.length() >= minLength) {
			return str;
		}
		StringBuilder sb = new StringBuilder(minLength);
		for (int i = str.length(); i < minLength; i++) {
			sb.append(padChar);
		}
		sb.append(str);
		return sb.toString();
	}

	/**
	 * 补充字符串以满足最小长度 StrUtil.padEnd("1", 3, '0');//"100"
	 * 
	 * @param str       字符串
	 * @param minLength 最小长度
	 * @param padChar   补充的字符
	 * @return 补充后的字符串
	 */
	public static String padEnd(String str, int minLength, char padChar) {
		if (str.length() >= minLength) {
			return str;
		}
		StringBuilder sb = new StringBuilder(minLength);
		sb.append(str);
		for (int i = str.length(); i < minLength; i++) {
			sb.append(padChar);
		}
		return sb.toString();
	}

	/**
	 * 创建StringBuilder对象
	 * 
	 * @return StringBuilder对象
	 */
	public static StringBuilder builder() {
		return new StringBuilder();
	}

	/**
	 * 创建StringBuilder对象
	 * 
	 * @return StringBuilder对象
	 */
	public static StringBuilder builder(int capacity) {
		return new StringBuilder(capacity);
	}

	/**
	 * 创建StringBuilder对象
	 * 
	 * @return StringBuilder对象
	 */
	public static StringBuilder builder(String... strs) {
		final StringBuilder sb = new StringBuilder();
		for (String str : strs) {
			sb.append(str);
		}
		return sb;
	}

	/**
	 * 创建StringBuilder对象
	 *
	 * @return StringBuilder对象
	 */
	public static void builder(StringBuilder sb, String... strs) {
		for (String str : strs) {
			sb.append(str);
		}
	}

	/**
	 * 获得StringReader
	 * 
	 * @param str 字符串
	 * @return StringReader
	 */
	public static StringReader getReader(String str) {
		return new StringReader(str);
	}

	/**
	 * 获得StringWriter
	 * 
	 * @return StringWriter
	 */
	public static StringWriter getWriter() {
		return new StringWriter();
	}

	/**
	 * 编码字符串
	 * 
	 * @param str     字符串
	 * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
	 * @return 编码后的字节码
	 */
	public static byte[] encode(String str, String charset) {
		if (str == null) {
			return null;
		}

		if (isBlank(charset)) {
			return str.getBytes();
		}
		try {
			return str.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(format("Charset [{}] unsupported!", charset));
		}
	}

	/**
	 * 解码字节码
	 * 
	 * @param data    字符串
	 * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
	 * @return 解码后的字符串
	 */
	public static String decode(byte[] data, String charset) {
		if (data == null) {
			return null;
		}

		if (isBlank(charset)) {
			return new String(data);
		}
		try {
			return new String(data, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(format("Charset [{}] unsupported!", charset));
		}
	}

	/**
	 * 强转->string,并去掉多余空格
	 *
	 * @param str
	 * @return
	 */
	public static String toStr(Object str) {
		return toStr(str, "");
	}

	/**
	 * 强转->string,并去掉多余空格
	 *
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static String toStr(Object str, String defaultValue) {
		if (null == str) {
			return defaultValue;
		}
		return str.toString().trim();
	}

	/**
	 * String-->bytes
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] toBytes(String str) {
		return str.getBytes(Charset.forName("UTF-8"));
	}

	/**
	 * 解析一个组合字符串(例如: "1:启用;2:禁用;3:冻结" 这样的字符串)
	 *
	 */
	public static List<Map<String, String>> parseKeyValue(String mutiString) {
		if (StrKit.isEmpty(mutiString)) {
			return new ArrayList<>();
		} else {
			ArrayList<Map<String, String>>	results	= new ArrayList<>();
			String[]						items	= StrKit.split(StringUtils.removeEnd(mutiString, ITEM_SPLIT),
					ITEM_SPLIT);
			for (String item : items) {
				String[]				attrs	= item.split(ATTR_SPLIT);
				HashMap<String, String>	itemMap	= new HashMap<>();
				itemMap.put(MUTI_STR_KEY, attrs[0]);
				itemMap.put(MUTI_STR_VALUE, attrs[1]);
				results.add(itemMap);
			}
			return results;
		}
	}

	/**
	 * 解析id:key:value这样类型的字符串
	 * 
	 */
	public static List<Map<String, String>> parseIdKeyValue(String mutiString) {
		if (StrKit.isEmpty(mutiString)) {
			return new ArrayList<>();
		} else {
			ArrayList<Map<String, String>>	results	= new ArrayList<>();
			String[]						items	= StrKit.split(StringUtils.removeEnd(mutiString, ITEM_SPLIT),
					ITEM_SPLIT);
			for (String item : items) {
				String[]				attrs	= item.split(ATTR_SPLIT);
				HashMap<String, String>	itemMap	= new HashMap<>();
				itemMap.put(MUTI_STR_ID, attrs[0]);
				itemMap.put(MUTI_STR_KEY, attrs[1]);
				itemMap.put(MUTI_STR_VALUE, attrs[2]);
				results.add(itemMap);
			}
			return results;
		}
	}

}
