package cn.watchdog.utils.crypto;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

@Slf4j
public class Base64Util {
	private static final String charset = "utf-8";

	/**
	 * 解密
	 */
	public static String decode(String data) {
		try {
			if (null == data) {
				return null;
			}

			return new String(Base64.decodeBase64(data.getBytes(charset)), charset);
		} catch (UnsupportedEncodingException e) {
			log.error(String.format("String: %s, decryption exception", data), e);
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 加密
	 */
	public static String encode(String data) {
		try {
			if (null == data) {
				return null;
			}
			return new String(Base64.encodeBase64(data.getBytes(charset)), charset);
		} catch (UnsupportedEncodingException e) {
			log.error(String.format("String: %s, encryption exception", data), e);
			e.printStackTrace();
		}

		return null;
	}
}
