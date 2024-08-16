package cn.watchdog.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class StringUtil {
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
