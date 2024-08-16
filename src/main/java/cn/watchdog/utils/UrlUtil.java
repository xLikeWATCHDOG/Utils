package cn.watchdog.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.TreeMap;

@Slf4j
public class UrlUtil {
	public static String buildQueryString(Map<String, String> data) {
		StringBuilder queryStr = new StringBuilder();
		for (Map.Entry<String, String> entry : data.entrySet()) {
			if (!queryStr.isEmpty()) {
				queryStr.append('&');
			}
			queryStr.append(entry.getKey()).append('=').append(entry.getValue());
		}
		return queryStr.toString();
	}

	public static String buildUrl(String baseUrl, Map<String, String> params) {
		Map<String, String> sortedParam = new TreeMap<>(params);
		StringBuilder urlBuilder = new StringBuilder(baseUrl);
		urlBuilder.append("?");
		for (Map.Entry<String, String> entry : sortedParam.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			urlBuilder.append(key).append("=").append(value).append("&");
		}
		// Remove the trailing '&' character
		urlBuilder.deleteCharAt(urlBuilder.length() - 1);
		return urlBuilder.toString();
	}
}
