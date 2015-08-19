package com.imt.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class StringUtil {
	public static void main(String args[]){
		List<String> list = getNumersFromString("2,560,022");
		for(String str:list)
			System.out.print(str);
	}
	
	/**
	 * 从字符串中取得数字
	 * @param str
	 * @return
	 */
	public static List<String> getNumersFromString(String str){
        String s = "\\d+";
        //String s = "\\d+.\\d+|\\w+|\\d+";//取得字符串
        Pattern  pattern=Pattern.compile(s);  
        Matcher  ma=pattern.matcher(str);  
        List<String> list = new ArrayList<String>();
        while(ma.find()){
        	String num = ma.group();
        	if(StringUtils.isNotBlank(num))
        		list.add(num);
        }
        return list;
	}
	
	/**
	 * 从字符串中取得数字组合起来
	 * param:123,234,345 result: 123234345
	 * @param str
	 * @return
	 */
	public static long getNumerFromString(String str){
		try {
	        List<String> list = getNumersFromString(str);
			StringBuffer buffer = new StringBuffer();
			for(String nums:list){
				buffer.append(nums);
			}
			if(buffer.length()>0)
				return Long.parseLong(buffer.toString());
			else
				return 0;
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 截取mail地址
	 */
	public static String getMailServer(String mail) {
		if (mail != null && mail.indexOf("@") != -1) {
			return "http://mail."
					+ mail.substring(mail.indexOf("@") + 1, mail.length());
		}
		return "/";
	}
	
	public static boolean isEmail(String emailUrl){
		String reg = "([a-zA-Z0-9._-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})";
		//   /^([a-zA-Z0-9._-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$/
		//  /^[a-zA-Z\d]+[\w\.\-@]*$/;
		// \\w+@(\\w+.)+[a-z]{2,3}
		boolean isEmail   =   Pattern.matches(reg,emailUrl);
		return isEmail;
	}
	
	
	/**
	 * url  http://www.91jianshen.com  exchange <a href="http://www.91jianshen.com">http://www.91jianshen.com</a>
	 * @param Str
	 * @return
	 */
	public static String convertToHref(String Str){
		if (Str == null || Str.equals(""))
			return Str;
		Matcher matcher = null;
		Pattern pattern = null;
		String str2 = "";
		pattern = Pattern
				.compile(
						"(http://[A-Za-z0-9\\./=\\?%\\-&_~`@':+!]+)|(www\\.[A-Za-z0-9\\./=\\?%\\-&_~`@':+!]+)",
						Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(Str);
		StringBuffer stringbuffer = new StringBuffer();
		for (; matcher.find(); matcher.appendReplacement(stringbuffer, str2)){
			if (matcher.group(2) != null)

				str2 = "<a href=\"http://" + matcher.group(2)
						+ "\" target=\"_blank\">"
						+ matcher.group(2) + "</a>";
			else
				str2 = "<a href=\"" + matcher.group(1)
						+ "\" target=\"_blank\">"
						+ matcher.group(1) + "</a>";
		}
		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}
	
	/**
	 * 解决半个中文的问题
	 * @param source
	 * @param maxByteLen
	 * @param flag
	 * @return
	 */
	public static String leftStr(String source, int maxByteLen, int flag) {
		if (source == null || maxByteLen <= 0) {
			return "";
		}
		byte[] bStr = source.getBytes();
		if (maxByteLen >= bStr.length)
			return source;
		String cStr = new String(bStr, maxByteLen - 1, 2);
		if (cStr.length() == 1 && source.contains(cStr)) {
			maxByteLen += flag;
		}
		return new String(bStr, 0, maxByteLen);
	}
	
	public static String[] shortUrl(String url) {
		// 可以自定义生成 MD5 加密字符传前的混合 KEY
		String key = "wuguowei";
		// 要使用生成 URL 的字符
		String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h",
		"i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
		"u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
		"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",

		"U", "V", "W", "X", "Y", "Z"

		};

		// 对传入网址进行 MD5 加密
		String sMD5EncryptResult = CryptUtil.md5Encoder(key + url);
		String hex = sMD5EncryptResult;
		String[] resUrl = new String[4];
		for (int i = 0; i < 4; i++) {
			// 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
			String sTempSubString = hex.substring(i * 8, i * 8 + 8);
			// 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用
			// long ，则会越界
			long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
			String outChars = "";
			for (int j = 0; j < 6; j++) {
				// 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
				long index = 0x0000003D & lHexLong;
				// 把取得的字符相加
				outChars += chars[(int) index];
				// 每次循环按位右移 5 位
				lHexLong = lHexLong >> 5;
			}
			// 把字符串存入对应索引的输出数组
			resUrl[i] = outChars;
		}

		return resUrl;

	}
	
    /**  
     *   字符串定长截断函数。(按照字数截取) 
     *   @param   text   需要截断的字符串 
     *   @param   textMaxChar   需要留下的长度 
     *   @author   niko7@21cn.com       
     *   @return 
     */ 
   public static   String[]   CutString(String   text,   int   textMaxChar) 
   { 
       int   size,   index; 
       String[]   returnStringArray   =   new   String[2]; 

       if(textMaxChar   <=   0){ 
           returnStringArray[0]   =   text; 
           returnStringArray[1]   =   null; 
       } 
       else{ 
           for(size   =   0,   index   =   0;   index   <   text.length()   &&   size   <   textMaxChar; 
                   index++){ 
               size   +=   text.substring(index,   index   +   1).getBytes().length; 
           } 
           returnStringArray[0]   =   text.substring(0,   index); 
           returnStringArray[1]   =   text.substring(index); 
       } 

       return   returnStringArray; 
   }
    

/**
     * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
     * 
     * @param char
     *            c, 需要判断的字符
     * @return boolean, 返回true,Ascill字符
     */
    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     * 
     * @param String
     *            s ,需要得到长度的字符串
     * @return int, 得到的字符串长度
     */
    public static int length(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    /**
     * 截取一段字符的长度,( 按照字节截取)
     * @author patriotlml
     * @param String origin, 原始字符串
     * @param intlen, 截取长度(一个汉字长度按2算的)
     * @return String, 返回的字符串
     */
    public static String substring(String origin, int len) {
        if (origin == null || origin.equals("")||len<1)
            return "";
        byte[] strByte = new byte[len];
        if (len > length(origin)){
            return origin;}
        System.arraycopy(origin.getBytes(), 0, strByte, 0, len);
        int count = 0;
        for (int i = 0; i < len; i++) {
            int value = (int) strByte[i];
            if (value < 0) {
                count++;
            }
        }
        if (count % 2 != 0) {
            len = (len == 1) ? ++len : --len;
        }
        return new String(strByte, 0, len);
    }
    
    /**
     * 补齐不足长度
     * @param length 长度
     * @param number 数字
     * @return
     */
    private String addZeroForNum(int length, int number) {
        String f = "%0" + length + "d";
        return String.format(f, number);
    }
}
