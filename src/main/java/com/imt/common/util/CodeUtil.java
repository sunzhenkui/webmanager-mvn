package com.imt.common.util;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class CodeUtil {
	public static byte[] unsignedInt2Byte(int n) {
		byte[] b = new byte[4];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		b[2] = (byte) (n >> 16 & 0xff);
		b[3] = (byte) (n >> 24 & 0xff);
		return b;
	}

	public static byte[] unsignedShort2Byte(short n) {
		byte[] b = new byte[2];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		return b;
	}

	public static byte[] int2Byte(int intValue) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (intValue >> 8 * (3 - i) & 0xFF);
		}
		return b;
	}

	public static int byte2Int(byte[] b) {
		int intValue = 0;
		for (int i = 0; i < b.length; i++) {
			intValue += (b[i] & 0xFF) << (8 * (3 - i));
		}
		return intValue;
	}

	public static byte[] long2Bytes(long value) {
		if (value != 0) {
			int zeros = Long.numberOfLeadingZeros(value);
			int length = 8 - zeros / 8;
			byte[] rawValue = new byte[length];
			for (int i = 0; i < length; i++) {
				rawValue[i] = (byte) (value >>> ((i) * 8));
			}
			return rawValue;
		} else {
			return new byte[] { (byte) 0 };
		}
	}

	public static long bytes2Long(byte[] value) {
		if (value.length > 8)
			return -1;
		long[] temp = new long[value.length];
		for (int i = 0; i < value.length; i++) {
			temp[i] = 0x000000000000FF & value[i];
		}
		long ret = 0;
		for (int i = 0; i < temp.length; i++) {
			ret = ((long) (ret | temp[i] << (i * 8)));
		}
		return ret;
	}

	public static String bytes2String(byte[] value) {
		StringBuilder sb = new StringBuilder();
		for (byte b : value) {
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}

	/**
	 * 16进制的字符串表示转成字节数组
	 * 
	 * @param hexString
	 *            16进制格式的字符串
	 * @return 转换后的字节数组
	 **/
	public static byte[] toByteArray(String hexString) {
		hexString = hexString.toLowerCase();
		final byte[] byteArray = new byte[hexString.length() / 2];
		int k = 0;
		for (int i = 0; i < byteArray.length; i++) {
			// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
			byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
			byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
			byteArray[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return byteArray;
	}

	public static byte[] hexToBytes(String str) {

		if (null != str && str.length() >= 2) {
			int len = str.length() / 2;
			byte[] buffer = new byte[len];
			for (int i = 0; i < len; i++) {
				buffer[i] = (byte) Integer.parseInt(
						str.substring(i * 2, i * 2 + 2), 16);
			}
			return buffer;
		}
		return null;
	}

	public static String escape(String str) {
		String callbackvalue= null;
		try {
			ScriptEngineManager sem = new ScriptEngineManager();
			ScriptEngine se = sem.getEngineByName("javascript");
			//直接JAVASCRIPT语句
			String command = "function toEscape(){ return escape(\""+str+"\");}";
			se.eval(command);
			Invocable invocableEngine = (Invocable) se;
			callbackvalue=(String) invocableEngine.invokeFunction("toEscape");
		} catch (Exception e) {
		}
		return callbackvalue;
	}
}
