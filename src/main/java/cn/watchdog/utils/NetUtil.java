package cn.watchdog.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

/**
 * 网络工具类
 */
@Slf4j
public class NetUtil {
	/**
	 * 获取客户端 IP 地址
	 */
	public static String getIpAddress(HttpServletRequest request) {
		if (request == null) {
			return "unknown";
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if (ip.equals("127.0.0.1")) {
				// 根据网卡取本机配置的 IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (Exception e) {
					log.error("getIpAddress error: {}", e.getMessage(), e);
				}
				if (inet != null) {
					ip = inet.getHostAddress();
				}
			}
		}
		// 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		if (ip == null) {
			return "127.0.0.1";
		}
		return ip;
	}

	/**
	 * 判断是否为移动端设备
	 *
	 * @return true为移动端设备，false为PC端设备
	 */
	public static boolean isMobileDevice(HttpServletRequest request) {
		String requestHeader = request.getHeader("user-agent");
		String[] deviceArray = {"android", "iphone", "ipod", "ipad", "windows phone", "mqqbrowser"};
		if (requestHeader == null) {
			return false;
		}
		requestHeader = requestHeader.toLowerCase();
		for (String aDeviceArray : deviceArray) {
			if (requestHeader.contains(aDeviceArray)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否为内网IP
	 *
	 * @param request HttpServletRequest
	 * @return true if the IP is an internal IP, false otherwise
	 */
	public static boolean isInternal(HttpServletRequest request) {
		String ipAddress = getIpAddress(request);
		// 判断是否为IPv6本地回环地址
		if (isLocalhostIPv6(ipAddress)) {
			return true;
		}

		// Convert IP address to long if it's IPv4
		if (ipAddress.contains(".")) {
			// Convert IP address to long
			long ipNum = ipToLong(ipAddress);
			// Define internal IP ranges
			long aBegin = ipToLong("10.0.0.0");
			long aEnd = ipToLong("10.255.255.255");
			long bBegin = ipToLong("172.16.0.0");
			long bEnd = ipToLong("172.31.255.255");
			long cBegin = ipToLong("192.168.0.0");
			long cEnd = ipToLong("192.168.255.255");
			// Check if the IP is within any of the internal IP ranges
			return isInRange(ipNum, aBegin, aEnd) || isInRange(ipNum, bBegin, bEnd) || isInRange(ipNum, cBegin, cEnd);
		}
		return false;
	}

	private static boolean isLocalhostIPv6(String ipAddress) {
		return "::1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress);
	}

	private static long ipToLong(String ipAddress) {
		String[] ipParts = ipAddress.split("\\.");
		return (Long.parseLong(ipParts[0]) << 24) + (Long.parseLong(ipParts[1]) << 16) + (Long.parseLong(ipParts[2]) << 8) + Long.parseLong(ipParts[3]);
	}

	private static boolean isInRange(long ip, long begin, long end) {
		return ip >= begin && ip <= end;
	}
}
