package com.qr.utils;

import com.qr.exception.RRException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

/**
 *  生成appid
 * @Author wd
 * @since 10:51 2020/9/25
 **/
public class AppUtils {

	private AppUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 *  生成 app_secret 密钥
	 * @Author wd
	 * @since 10:49 2020/9/25
	 **/
	private static final String[] CHARS = new String[]{"a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z"};

	private static final int NUM = 8;
	/**
	 * @Description: <p>
	 * 短8位UUID思想其实借鉴微博短域名的生成方式，但是其重复概率过高，而且每次生成4个，需要随即选取一个。
	 * 本算法利用62个可打印字符，通过随机生成32位UUID，由于UUID都为十六进制，所以将UUID分成8组，每4个为一组，然后通过模62操作，结果作为索引取出字符，
	 * 这样重复率大大降低。
	 * 经测试，在生成一千万个数据也没有出现重复，完全满足大部分需求。
	 * </p>
	 */
	public static String getAppId() {
		StringBuilder shortBuffer = new StringBuilder();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < NUM; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(CHARS[x % 0x3E]);
		}
		return shortBuffer.toString();

	}

	/**
	 * <p>
	 * 通过appId和内置关键词生成APP Secret
	 * </P>
	 */
	public static String getAppSecret(String appId,String serverName) {
		try {
			String[] array = new String[]{appId, serverName};
			StringBuilder sb = new StringBuilder();
			// 字符串排序
			Arrays.sort(array);
			for (int i = 0; i < array.length; i++) {
				sb.append(array[i]);
			}
			String str = sb.toString();
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] digest = md.digest();

			StringBuilder hexstr = new StringBuilder();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RRException(e.getMessage());
		}
	}
}
