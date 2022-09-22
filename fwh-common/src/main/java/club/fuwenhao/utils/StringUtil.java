package club.fuwenhao.utils;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author zby
 *
 */
public class StringUtil {
	private static final String LAST_WORD_REGEX = "$";

	private StringUtil() {
	}

	/**
	 * 替换字符串的最后一位
	 * @param origStr     原始字符串
	 * @param lastWord    最后一位字符
	 * @param replaceWord 替换的字符
	 * @return
	 */
	public static String changeLastWord(String origStr, String lastWord, String replaceWord) {
		return origStr.replaceFirst(lastWord + LAST_WORD_REGEX, replaceWord);
	}

	/**
	 * 功能描述: <br> 〈验证是否为空字符串〉
	 * @param: [str]
	 * @return: boolean true为空
	 * @author: zhuochen7
	 * @Date: 2019/12/2 16:21
	 */
	public static boolean isNull(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 功能描述: <br> 〈判断对象是否为空〉
	 * @param: [obj]
	 * @return: boolean true == 空
	 * @author: zhuochen7
	 * @Date: 2019/12/2 16:24
	 */
	public static boolean isNull(Object obj) {
		return obj == null;
	}

	/**
	 * 功能描述: <br> 〈判断字符串是否为空，并去除空格〉
	 * @param: [str]
	 * @return: boolean true 为空
	 * @author: zhuochen7
	 * @Date: 2019/12/2 16:36
	 */
	public static boolean isEmpty(String str) {
		return null == str || "".equals(str.trim());
	}

	/**
	 * 功能描述: <br> 〈验证一个字符串是否完全由纯数字组成的字符串，当字符串为空时也返回false〉
	 * @param: [str 要判断的字符串]
	 * @return: boolean false为空或者不全为数字。
	 * @author: zhuochen7
	 * @Date: 2019/12/2 16:39
	 */
	public static boolean isNumeric(String str) {
		if (isEmpty(str)) {
			return false;
		} else {
			return str.matches("\\d*");
		}
	}

	/**
	 * 功能描述: <br> 〈计算采用utf-8编码方式时字符串所占字节数〉
	 * @param: [content 要计算的字符串]
	 * @return: int
	 * @author: zhuochen7
	 * @throws UnsupportedEncodingException 
	 * @Date: 2019/12/2 16:43
	 */
	public static int getByteSize(String content) throws UnsupportedEncodingException {
		int size = 0;
		if (null != content) {
			// 汉字采用utf-8编码时占3个字节
			size = content.getBytes("utf-8").length;
		}
		return size;
	}

	/**
	 * 功能描述: <br> 〈截取字符串转换List集合 ,逗号截取〉
	 * @param: [param]
	 * @return: java.util.List<java.lang.String>
	 * @author: zhuochen7
	 * @Date: 2019/12/2 16:46
	 */
	public static List<String> getInParam(String param) {
		List<String> list = null;
		if (isEmpty(param)) {
			return list;
		}
		boolean flag = param.contains(",");
		if (flag) {
			list = Arrays.asList(param.split(","));
		} else {
			list = new ArrayList<>();
			list.add(param);
		}
		return list;
	}

	/**
	 * 功能描述: <br> 〈将字符串解析成Map , 只适用于键值对拼接的字符串〉ssss=222&bbb=333&ccc=888
	 * @Param: [sourceString 要解析的Map, splitChar 字符串分割符（ssss=222&bbb=333&ccc=888）]
	 * @return: java.util.Map<java.lang.String,java.lang.Object>
	 * @author: zhuochen7
	 * @Date: 2019/12/2 16:52
	 */
	public static Map<String, Object> parseStringToMap(String sourceString, String splitChar) {
		if (isEmpty(sourceString)) {
			return null;
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String[] sourceArr = sourceString.split(splitChar);
		for (String s : sourceArr) {
			int firstIndex = s.indexOf('=');
			if (firstIndex > 0) {
				String key = s.substring(0, s.indexOf('='));
				String value = s.substring(s.indexOf('=') + 1);
				resultMap.put(key, value);
			}
		}
		return resultMap;
	}

	/**
	 * 功能描述: <br> 〈将字符串解析成Map , 只适用于键值对拼接的字符串,例如:ssss=222&bbb=333&ccc=888〉
	 * @Param: [sourceString]
	 * @return: java.util.Map<java.lang.String,java.lang.Object>
	 * @author: zhuochen7
	 * @Date: 2019/12/2 16:55
	 */
	public static Map<String, Object> parseStringToMap(String sourceString) {
		return parseStringToMap(sourceString, "&");
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}
