package cn.watchdog.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class StringUtil {
	public static String joinList(List<String> list, int number) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Math.min(list.size(), number); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(list.get(i));
		}
		if (list.size() > number) {
			sb.append(", ...");
		}
		return sb.toString();
	}

	public static String[] split(String string, String regex) {
		if (string.isEmpty()) {
			return new String[0];
		}
		return string.split(regex);
	}

	public static boolean endsWithIgnoreCase(String a, String b) {
		if (a == null || b == null) {
			return a == null && b == null;
		}
		return a.toLowerCase().endsWith(b.toLowerCase());
	}

	/**
	 * 合并一些字符串
	 *
	 * @param mergeIndex 可以合并的字符串的索引
	 * @param stringsNum 合并后的字符串数量
	 * @param strings    一些字符串
	 * @return 合并后的字符串, 合并的用空格分开
	 */
	public static String[] mergeStrings(int mergeIndex, int stringsNum, String... strings) {
		if (strings.length < stringsNum) {
			return strings;
		} else if (strings.length == stringsNum) {
			return strings;
		}
		List<String> rl = new ArrayList<>(Arrays.asList(strings).subList(0, mergeIndex));
		StringBuilder merge = new StringBuilder(strings[mergeIndex]);
		//2 3 +
		for (int i = mergeIndex + 1; i <= mergeIndex + strings.length - stringsNum; i++) {
			merge.append(" ").append(strings[i]);
		}
		rl.add(merge.toString());
		rl.addAll(Arrays.asList(strings).subList(mergeIndex + strings.length - stringsNum + 1, strings.length));
		return rl.toArray(new String[0]);
	}

	public static String mergeStrings(String separator, String[] strings) {
		if (strings.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder(strings[0]);
		for (int i = 1; i < strings.length; i++) {
			sb.append(separator);
			sb.append(strings[i]);
		}
		return sb.toString();
	}

	@SafeVarargs
	public static String replaceStrings(String raw, Map.Entry<String, String>... beforeAndAfter) {
		return replaceStrings(raw, ListUtil.toMap(Lists.newArrayList(beforeAndAfter)));
	}

	public static String replaceStrings(String raw, Map<String, String> beforeAndAfter) {
		String[] ss = {raw};
		for (Map.Entry<String, String> t : beforeAndAfter.entrySet()) {
			List<String> ts = new LinkedList<>();
			for (String s : ss) {
				boolean c = true;
				for (String k : beforeAndAfter.keySet()) {
					if (Objects.equals(t.getValue(), k)) {
						break;
					}
					if (Objects.equals(s, k)) {
						c = false;
						break;
					}
				}
				if (c) {
					String[] tts = (" " + s + " ").split(t.getKey());
					tts[0] = tts[0].substring(1);
					tts[tts.length - 1] = tts[tts.length - 1].substring(0, tts[tts.length - 1].length() - 1);
					for (String d : tts) {
						ts.add(d);
						ts.add(t.getValue());
					}
					ts.remove(ts.size() - 1);
				} else {
					ts.add(s);
				}
			}
			ss = ts.toArray(ss);
		}
		return mergeStrings(ss);
	}

	public static String mergeStrings(String... strings) {
		StringBuilder sb = new StringBuilder();
		for (String s : strings) {
			sb.append(s);
		}
		return sb.toString();
	}

	public static String codeString(String str) {
		StringBuilder sb = new StringBuilder();
		for (char c : str.toCharArray()) {
			switch (c) {
				case '\r':
					sb.append("\\r");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\0':
					sb.append("\\0");
					break;
				case '\"':
					sb.append("\\\"");
					break;
				case '\'':
					sb.append("\\'");
					break;
				default:
					sb.append(c);
					break;
			}
		}
		return '\"' + sb.toString() + '\"';
	}

	public static boolean containsIgnoreCase(List<String> list, String str) {
		for (String s : list) {
			if (s.equalsIgnoreCase(str)) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsAny(String str, List<String> ss) {
		for (String s : ss) {
			if (str.contains(s)) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsAnyIgnoreCase(String str, List<String> ss) {
		str = str.toLowerCase();
		for (String s : ss) {
			if (str.contains(s.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static boolean startsWithAny(String str, Collection<String> ss) {
		for (String s : ss) {
			if (str.startsWith(s)) {
				return true;
			}
		}
		return false;
	}

	public static boolean startsWithAnyIgnoreCase(String str, Collection<String> ss) {
		for (String s : ss) {
			if (StringUtil.startsWithIgnoreCase(str, s)) {
				return true;
			}
		}
		return false;
	}

	public static boolean startsWithIgnoreCase(String a, String b) {
		if (a == null || b == null) {
			return a == null && b == null;
		}
		return a.toLowerCase().startsWith(b.toLowerCase());
	}

	public static int sum(String str, List<String> ss) {
		int num = 0;
		for (String s : ss) {
			num += sum(str, s);
		}
		return num;
	}

	public static int sum(String str, String s) {
		int num = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.substring(i).startsWith(s)) {
				num++;
			}
		}
		return num;
	}

	public static int sumIgnoreCase(String str, List<String> ss) {
		int num = 0;
		for (String s : ss) {
			num += sumIgnoreCase(str, s);
		}
		return num;
	}

	public static int sumIgnoreCase(String str, String s) {
		int num = 0;
		for (int i = 0; i < str.length(); i++) {
			if (startsWithIgnoreCase(str.substring(i), s)) {
				num++;
			}
		}
		return num;
	}

	/**
	 * <p>
	 * Gets a substring from the specified String avoiding exceptions.
	 * </p>
	 *
	 * <p>
	 * A negative start position can be used to start/end <code>n</code>
	 * characters from the end of the String.
	 * </p>
	 *
	 * <p>
	 * The returned substring starts with the character in the <code>start</code>
	 * position and ends before the <code>end</code> position. All position counting is
	 * zero-based -- i.e., to start at the beginning of the string use
	 * <code>start = 0</code>. Negative start and end positions can be used to
	 * specify offsets relative to the end of the String.
	 * </p>
	 *
	 * <p>
	 * If <code>start</code> is not strictly to the left of <code>end</code>, ""
	 * is returned.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.substring(null, *, *)    = null
	 * StringUtils.substring("", * ,  *)    = "";
	 * StringUtils.substring("abc", 0, 2)   = "ab"
	 * StringUtils.substring("abc", 2, 0)   = ""
	 * StringUtils.substring("abc", 2, 4)   = "c"
	 * StringUtils.substring("abc", 4, 6)   = ""
	 * StringUtils.substring("abc", 2, 2)   = ""
	 * StringUtils.substring("abc", -2, -1) = "b"
	 * StringUtils.substring("abc", -4, 2)  = "ab"
	 * </pre>
	 *
	 * @param str   the String to get the substring from, may be null
	 * @param start the position to start from, negative means
	 *              count back from the end of the String by this many characters
	 * @param end   the position to end at (exclusive), negative means
	 *              count back from the end of the String by this many characters
	 * @return substring from start position to end position,
	 * <code>null</code> if null String input
	 */
	public static String substring(String str, int start, int end) {
		if (str == null) {
			return null;
		}

		// handle negatives
		if (end < 0) {
			end = str.length() + end; // remember end is negative
		}
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		// check length next
		if (end > str.length()) {
			end = str.length();
		}

		// if start is greater than end, return ""
		if (start > end) {
			return "";
		}

		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	public static String convertToRoman(int number) {
		if (number < 1 || number > 3999) {
			throw new IllegalArgumentException("The number must be between 1 and 3999.");
		}

		StringBuilder roman = new StringBuilder();

		// 定义罗马数字对应的字符和数值
		String[] romanNumerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
		int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

		// 遍历数值数组，从大到小尽可能多地减去对应的数值
		for (int i = 0; i < values.length; i++) {
			while (number >= values[i]) {
				roman.append(romanNumerals[i]);
				number -= values[i];
			}
		}

		return roman.toString();
	}

	public static String formatNumberWithCommas(int number) {
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		return decimalFormat.format(number);
	}

	public static String decodeUnicode(String str) {
		StringBuilder result = new StringBuilder();
		int i = 0;
		int len = str.length();
		while (i < len) {
			char c = str.charAt(i++);
			if (c == '\\' && i < len) {
				char next = str.charAt(i++);
				if (next == 'u' && i + 4 <= len) {
					String hex = str.substring(i, i + 4);
					result.append((char) Integer.parseInt(hex, 16));
					i += 4;
				} else {
					result.append(c).append(next);
				}
			} else {
				result.append(c);
			}
		}
		return result.toString();
	}

	public static String formatDate(Date date, boolean ignoredTime) {
		if (ignoredTime) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(date);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static String formatDate(Date date) {
		return formatDate(date, false);
	}

	public static String getRandomString(int length) {
		// 根据UUID生成随机字符串
		String uuid = UUID.randomUUID().toString();
		// 去掉“-”符号
		uuid = uuid.replace("-", "");
		// 截取指定长度的字符串,如果长度超过uuid的长度,则递归调用
		if (length > uuid.length()) {
			return uuid + getRandomString(length - uuid.length());
		}
		return uuid.substring(0, length);
	}

	public static String generateStringByTime() {
		// 获取当前日期时间
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = dateFormat.format(now);

		// 生成随机数，保证订单号的唯一性
		Random random = new Random();
		int randomNumber = random.nextInt(10000); // 在0~9999之间生成随机数

		// 组合订单号
		return timestamp + String.format("%04d", randomNumber);
	}

	/**
	 * 字符串相似度比较，返回相似度，0.0~1.0之间，参数越大，相似度越高
	 * original：原字符串
	 * target：目标字符串
	 */
	public static double similarity(String original, String target) {
		// 判断可能为null
		if (StringUtils.isAnyBlank(original, target)) {
			return 0;
		}
		int len1 = original.length();
		int len2 = target.length();
		int[][] dp = new int[len1 + 1][len2 + 1];
		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}
		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}
		for (int i = 1; i <= len1; i++) {
			for (int j = 1; j <= len2; j++) {
				if (original.charAt(i - 1) == target.charAt(j - 1)) {
					dp[i][j] = dp[i - 1][j - 1];
				} else {
					dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
				}
			}
		}
		return 1 - (double) dp[len1][len2] / Math.max(len1, len2);
	}
}
