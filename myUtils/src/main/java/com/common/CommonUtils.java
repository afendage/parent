package com.common;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtils {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 得到UTC时间，类型为字符串，格式为"yyyy-MM-dd HH:mm"<br />
	 * 如果获取失败，返回null
	 *
	 * @return
	 */
	public static Date getUTCTime() {
		StringBuffer UTCTimeBuffer = new StringBuffer();
		// 1、取得本地时间：
		Calendar cal = Calendar.getInstance();
		// 2、取得时间偏移量：
		int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
		// 3、取得夏令时差：
		int dstOffset = cal.get(Calendar.DST_OFFSET);
		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		UTCTimeBuffer.append(year).append("-").append(month).append("-").append(day);
		UTCTimeBuffer.append(" ").append(hour).append(":").append(minute).append(":").append(second);
		try {
			return format.parse(UTCTimeBuffer.toString());
			// return UTCTimeBuffer.toString();
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * get time for day's 00:00:00
	 *
	 * @param day
	 *            witch day's,example : today:0,yesterday:-1,tomorrow:1; @
	 *            * @return
	 */
	public static Date getZeroUTC(int day) {
		Date utcToday = getUTCTime();

		long time = utcToday.getTime();// 当前时间毫秒数
		long zero = time / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();

		long zeorForDay = zero + day * 24 * 60 * 60 * 1000;

		return new Date(zeorForDay);

	}

	public static String formatDate(Date date, String format) {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (date != null) {
			result = sdf.format(date);
		}
		return result;
	}

	public final static String MD5(String pwd) {
		// 用于加密的字符
		char md5String[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			// 使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
			byte[] btInput = pwd.getBytes("utf-8");

			// 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
			MessageDigest mdInst = MessageDigest.getInstance("MD5");

			// MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
			mdInst.update(btInput);

			// 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
			byte[] md = mdInst.digest();

			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) { // i = 0
				byte byte0 = md[i]; // 95
				str[k++] = md5String[byte0 >>> 4 & 0xf]; // 5
				str[k++] = md5String[byte0 & 0xf]; // F
			}

			// 返回经过加密后的字符串
			return new String(str);

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 为字符串增加前缀
	 *
	 * @param p
	 * @param prefix
	 *            单个前缀值
	 * @param length
	 *            目标字符串总长度 例如：123456 增加前缀 6个0 则p:123456,prefix:0,length:12
	 * @return 增加前缀后的字符串
	 */
	public static String makPrefix(String p, String prefix, int length) {

		if (p == null || p.length() >= length || length <= 0) {
			return p;
		}
		StringBuilder sb = new StringBuilder();
		int acutalPreLength = length - p.length();
		for (int i = 0; i < acutalPreLength; i++) {

			sb.append(prefix);
		}
		sb.append(p);
		return sb.toString();
	}

}
