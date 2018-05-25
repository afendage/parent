package com.common;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

/**
 * 通用方法工具类
 * @author afen
 *
 */
public class MethodUtil {

	/**
	 * 指定字符替换
	 * 
	 * @param str
	 *            需要替换的字符串
	 * @param index
	 *            需要替换第几个
	 * @param value
	 *            需要替换的字符是
	 * @param replaceChar
	 *            需要替换值
	 * @return
	 */
	public String replaceIndex(String str, int index, String value,
			String replaceChar) {
		int subIndex = 0;
		for (int i = 0; i < index; i++) {
			if (i == 0) {
				subIndex += str.indexOf(replaceChar);
			} else {
				subIndex = subIndex
						+ str.substring(subIndex + 1).indexOf(replaceChar) + 1;
			}
		}
		str = str.substring(0, subIndex) + value
				+ str.substring(subIndex + 1, str.length());
		return str;
	}

	@Test
	public void testReplaceIndex() {
		String sql = "aa=?,bb=?,ccccc=?";
		System.out.println(this.replaceIndex(sql, 3, "aa", "?"));
	}

	/**
	 * 判断当前日期是星期几
	 * 
	 * @param pTime
	 *            修要判断的时间
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */
	public static int dayForWeek(String pTime){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(pTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}
	
	@Test
	public void dayForWeekTest(){
		System.out.println(MethodUtil.dayForWeek("2017-04-06"));
	}

	/**
	 * 判断是否是同一周
	 */
	@Test
	public void weekLoginTime() {
		String today = "2014-04-10 00:11:11";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(today);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar nowCalendar = Calendar.getInstance();
		nowCalendar.setFirstDayOfWeek(Calendar.MONDAY);
		int nowWeek = nowCalendar.get(Calendar.WEEK_OF_YEAR);

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		int loginWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		System.out.println("nowWeek=" + nowWeek + "  loginWeek=" + loginWeek);
		if (nowWeek == loginWeek) {
			System.out.println("哈哈！我们是同一周内登录的");
		} else {
			System.out.println("o_o 不在同一周内登录...");
		}
	}

	/**
	 * 日期相减得到天数
	 */
	@Test
	public void dateCn() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date nowDate = fmt.parse(fmt.format(new Date()));
			Date loginDate = fmt.parse("2014-08-11 11:11:11");
			long day = (nowDate.getTime() - loginDate.getTime())
					/ (24 * 60 * 60 * 1000);
			if (day > 5) {

			}
			System.out.println(day);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通用读取配置文件
	 * 
	 * @param key
	 * @return
	 */
	public String readValue(String key) {
		Properties props = new Properties();
		try {
			InputStream in = MethodUtil.class.getClassLoader()
					.getResourceAsStream("dbConfig.properties");
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Test
	public void readValueTest(){
		System.out.println(readValue("username"));
	}
	
	/**
	 * JSON 格式
	 */
	@Test
	public void jsonTest() {
		try {
			JSONObject json = new JSONObject("{name:xiaomi,age:18}");
			System.out.println(json.getString("name"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * java生成随机数字和字母组合
	 * 
	 * @param length
	 *            [生成随机数的长度]
	 * @return
	 */
	public String getCharAndNumr(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			// 输出字母还是数字
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 字符串
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 取得大写字母还是小写字母
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
	
	@Test
	public void getCharAndNumrTest() {
		System.out.println(this.getCharAndNumr(6));
	}
	
	/**
	 * 随机生成简体中文
	 * 
	 * @param len
	 *            int
	 * @return String
	 */
	public String getRandomJianHan(int len) {
		String ret = "";
		for (int i = 0; i < len; i++) {
			String str = null;
			int hightPos, lowPos; // 定义高低位
			Random random = new Random();
			hightPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
			lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
			byte[] b = new byte[2];
			b[0] = (new Integer(hightPos).byteValue());
			b[1] = (new Integer(lowPos).byteValue());
			try {
				str = new String(b, "GBk"); // 转成中文
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
			}
			ret += str;
		}
		return ret;
	}

	@Test
	public void getRandomJianHanTest(){
		System.out.println(getRandomJianHan(10));
	}
	
	/**
	 * 获取对象的所有属性、方法和属性值
	 */
	public void eachObject(Object model) {
		try {
			Field[] fields = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
			Method[] methods = model.getClass().getMethods(); // 拿到函数成员

			for (Field f : fields) {
				String name = f.getName().substring(0, 1).toUpperCase()
						+ f.getName().substring(1); // 将属性的首字符大写，方便构造get，set方法
				Method m = model.getClass().getMethod("get" + name);
				Object value = m.invoke(model); // 调用getter方法获取属性值
				System.out.println("该类的内部变量有:" + f.getName() + "获取到get值为："
						+ value);
			}
			for (Method m : methods) {
				System.out.println("该类的方法有:" + m.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
