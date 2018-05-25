package com.common;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.json.JSONException;
import org.json.JSONObject;

/***
 * 
 * 功能描述：字符串处理工具类
 * 
 * Version： 1.0
 * 
 * date： 2015-11-3
 * 
 * @author：Jimmy
 * 
 */
public class StringUtils
{
    public static final String EMPTY = "";
    public static final int INDEX_NOT_FOUND = -1;
    private static final String PADDING[];
    private final static String phoneRegex = "^1\\d{10}$";

    static
    {
        PADDING = new String[65535];
        PADDING[32] = "                                                          ";
    }

    /**
     * Object转为string
     * 
     * @param obj
     * @return
     */
    public static final String formatToString(Object obj)
    {
        if (obj == null)
        {
            return "";
        }
        return obj.toString();
    }

    public static final String getString(String key)
    {
        if (key == null || key.length() == 0)
        {
            return "";
        }
        else
        {
            return key;
        }
    }

    /**
     * 判断字符串是否为空或空字符串
     * 
     * @param str
     * @return
     */
    public static final boolean isEmpty(String str)
    {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断对象串是否为空
     * 
     * @param obj
     * @return
     */
    public static final boolean isEmpty(Object obj)
    {
        return obj == null || obj.toString().length() == 0;
    }

    /**
     * 判断对象串是否不为空
     * 
     * @param obj
     * @return
     */
    public static final boolean isNotEmpty(Object obj)
    {
        return obj != null && obj.toString().length() != 0;
    }

    /**
     * 判断字符串是否不为空或空字符串
     * 
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str)
    {
        return str != null && str.length() > 0;
    }

    /**
     * �ж��Ƿ�Ϊ����
     * 
     * @param str
     * @return
     */
    public static boolean isDigit(String str)
    {
        if (str == null)
            return false;
        int sz = str.length();
        for (int i = 0; i < sz; i++)
            if (!Character.isDigit(str.charAt(i)))
                return false;

        return true;
    }


    /**
     * 判断字符串是否相等
     * 
     * @param str
     * @param str2
     * @return
     */
    public static final boolean equals(String str, String str2)
    {
        if (null == str)
            return null == str2;
        else
            return str.equals(str2);
    }

    /**
     * 判断字符串是否相等（不区分大小写）
     * 
     * @param str
     * @param str2
     * @return
     */
    public static final boolean equalsIgnoreCase(String str, String str2)
    {
        if (null == str)
            return null == str2;
        else
            return str.equalsIgnoreCase(str2);
    }

    /**
     * 根据Class获取类名
     * 
     * @param cls
     * @return
     */
    public static final String getClassName(Class<? extends Object> cls)
    {
        String fullName = cls.getName();
        int index = fullName.lastIndexOf('.');
        if (index == -1)
            return fullName;
        else
            return fullName.substring(index + 1);
    }

    /**
     * 根据对象获取类名
     * 
     * @param obj
     * @return
     */
    public static final String getClassName(Object obj)
    {
        return getClassName(obj.getClass());
    }

    /**
     * 字符串转为boolean值
     * 
     * @param str
     * @return
     */
    public static boolean getBooleanFromString(String str)
    {
        return str != null && (str.equalsIgnoreCase("true") || str.equals("1"));
    }

    /**
     * 删除字符串空格
     * 
     * @param str
     * @return
     */
    public static String deleteWhitespace(String str)
    {
        if (isEmpty(str))
            return str;
        int sz = str.length();
        char chs[] = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++)
            if (!Character.isWhitespace(str.charAt(i)))
                chs[count++] = str.charAt(i);

        if (count == sz)
            return str;
        else
            return new String(chs, 0, count);
    }

    /**
     * 获取字符串len位置左边的字符串
     * 
     * @param str
     * @param len
     * @return
     */
    public static String left(String str, int len)
    {
        if (str == null)
            return null;
        if (len < 0)
            return "";
        if (str.length() <= len)
            return str;
        else
            return str.substring(0, len);
    }

    /**
     * 获取字符串len位置右边的字符串
     * 
     * @param str
     * @param len
     * @return
     */
    public static String right(String str, int len)
    {
        if (str == null)
            return null;
        if (len < 0)
            return "";
        if (str.length() <= len)
            return str;
        else
            return str.substring(str.length() - len);
    }

    /**
     * 字符串转为小写
     * 
     * @param str
     * @return
     */
    public static String lowerCase(String str)
    {
        if (str == null)
            return null;
        else
            return str.toLowerCase();
    }

    /**
     * 字符串转为大写
     * 
     * @param str
     * @return
     */
    public static String upperCase(String str)
    {
        if (str == null)
            return null;
        else
            return str.toUpperCase();
    }

    /**
     * ��ȡָ��λ�õ��ַ�
     * 
     * @param str
     * @param pos
     * @param len
     * @return
     */
    public static String mid(String str, int pos, int len)
    {
        if (str == null)
            return null;
        if (len < 0 || pos > str.length())
            return "";
        if (pos < 0)
            pos = 0;
        if (str.length() <= pos + len)
            return str.substring(pos);
        else
            return str.substring(pos, pos + len);
    }

    /**
     * 去除字符串特定字符
     * 
     * @param str
     * @param remove
     * @return
     */
    public static String remove(String str, char remove)
    {
        if (isEmpty(str) || str.indexOf(remove) == -1)
            return str;
        char chars[] = str.toCharArray();
        int pos = 0;
        for (int i = 0; i < chars.length; i++)
            if (chars[i] != remove)
                chars[pos++] = chars[i];

        return new String(chars, 0, pos);
    }

    /**
     * 去除字符串特定字符串
     * 
     * @param str
     * @param remove
     * @return
     */
    public static String remove(String str, String remove)
    {
        if (isEmpty(str) || isEmpty(remove))
            return str;
        else
            return replace(str, remove, "", -1);
    }

    /**
     * 在字符串末尾删除以特定字符串
     * 
     * @param str
     * @param remove
     * @return
     */
    public static String removeEnd(String str, String remove)
    {
        if (isEmpty(str) || isEmpty(remove))
            return str;
        if (str.endsWith(remove))
            return str.substring(0, str.length() - remove.length());
        else
            return str;
    }

    /**
     * 在字符串开始删除以特定字符串
     * 
     * @param str
     * @param remove
     * @return
     */
    public static String removeStart(String str, String remove)
    {
        if (isEmpty(str) || isEmpty(remove))
            return str;
        if (str.startsWith(remove))
            return str.substring(remove.length());
        else
            return str;
    }

    /**
     * 特定位置用字符串替换
     * 
     * @param str
     * @param repeat
     * @return
     */
    public static String repeat(String str, int repeat)
    {
        if (str == null)
            return null;
        if (repeat <= 0)
            return "";
        int inputLength = str.length();
        if (repeat == 1 || inputLength == 0)
            return str;
        if (inputLength == 1 && repeat <= 8192)
            return padding(repeat, str.charAt(0));
        int outputLength = inputLength * repeat;
        switch (inputLength)
        {
            case 1: // '\001'
                char ch = str.charAt(0);
                char output1[] = new char[outputLength];
                for (int i = repeat - 1; i >= 0; i--)
                    output1[i] = ch;

                return new String(output1);

            case 2: // '\002'
                char ch0 = str.charAt(0);
                char ch1 = str.charAt(1);
                char output2[] = new char[outputLength];
                for (int i = repeat * 2 - 2; i >= 0; i--)
                {
                    output2[i] = ch0;
                    output2[i + 1] = ch1;
                    i--;
                }

                return new String(output2);
        }
        StringBuffer buf = new StringBuffer(outputLength);
        for (int i = 0; i < repeat; i++)
            buf.append(str);

        return buf.toString();
    }

    /**
     * �ַ��滻
     * 
     * @param text
     * @param repl
     * @param with
     * @return
     */
    public static String replace(String text, String repl, String with)
    {
        return replace(text, repl, with, -1);
    }

    /**
     * �ַ��滻,maxΪ�滻����
     * 
     * @param text
     * @param repl
     * @param with
     * @param max
     * @return
     */
    public static String replace(String text, String repl, String with, int max)
    {
        if (text == null || isEmpty(repl) || with == null || max == 0)
            return text;
        StringBuffer buf = new StringBuffer(text.length());
        int start = 0;
        for (int end = 0; (end = text.indexOf(repl, start)) != -1;)
        {
            buf.append(text.substring(start, end)).append(with);
            start = end + repl.length();
            if (--max == 0)
                break;
        }

        buf.append(text.substring(start));
        return buf.toString();
    }

    /**
     * 字符串调转顺序
     * 
     * @param str
     * @return
     */
    public static String reverse(String str)
    {
        if (str == null)
            return null;
        else
            return (new StringBuffer(str)).reverse().toString();
    }

    /**
     * 字符串截取
     * 
     * @param str
     * @param start
     * @return
     */
    public static String substring(String str, int start)
    {
        if (str == null)
            return null;
        if (start < 0)
            start = str.length() + start;
        if (start < 0)
            start = 0;
        if (start > str.length())
            return "";
        else
            return str.substring(start);
    }

    /**
     * 字符串截取
     * 
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static String substring(String str, int start, int end)
    {
        if (str == null)
            return null;
        if (end < 0)
            end = str.length() + end;
        if (start < 0)
            start = str.length() + start;
        if (end > str.length())
            end = str.length();
        if (start > end)
            return "";
        if (start < 0)
            start = 0;
        if (end < 0)
            end = 0;
        return str.substring(start, end);
    }

    /**
     * 去除字符串结尾空格
     * 
     * @param str
     * @return
     */
    public static String trim(String str)
    {
        return str != null ? str.trim() : null;
    }

    /**
     * 
     * @param repeat
     * @param padChar
     * @return
     */
    private static String padding(int repeat, char padChar)
    {
        String pad = PADDING[padChar];
        if (pad == null)
            pad = String.valueOf(padChar);
        for (; pad.length() < repeat; pad = pad.concat(pad));
        PADDING[padChar] = pad;
        return pad.substring(0, repeat);
    }

    /**
     * 字符串是否空格
     * 
     * @param str
     * @return
     */
    public static boolean isBlank(String str)
    {
        int strLen;
        if (str == null || (strLen = str.length()) == 0)
            return true;
        for (int i = 0; i < strLen; i++)
            if (!Character.isWhitespace(str.charAt(i)))
                return false;

        return true;
    }

    /**
     * 字符串转为double
     * 
     * @param str
     * @return
     */
    public static Double toDouble(String str)
    {
        if (isEmpty(str))
        {
            return Double.valueOf("0.00");
        }

        return Double.valueOf(str);
    }

    /**
     * 字符串转为BigDecimal
     * 
     * @param str
     * @return
     */
    public static BigDecimal toBigDecimal(String str)
    {
        if (isEmpty(str))
        {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(str);
    }

    /**
     * 日期转为字符串（默认yyyy-MM-dd）
     * 
     * @param str
     * @return
     */
    public static Date toDate(String str)
    {
        if (isEmpty(str))
        {
            return null;
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;

        try
        {
            date = df.parse(str);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 字符串转为int
     * 
     * @param str
     * @return
     */
    public static int toInt(String str)
    {
        if (isEmpty(str))
        {
            return new Integer("0").intValue();
        }

        return new Integer(str).intValue();
    }

    /**
     * 数组转换成sql in后的字符串
     * 
     * @param ids
     * @return
     */
    public static String arrayToSqlStr(Object[] ids)
    {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < ids.length; i++)
        {
            if (i > 0)
            {
                str.append(",");
            }
            str.append("'").append(ids[i]).append("'");
        }
        return str.toString();
    }


    /**
     * 汉字转换位汉语拼音首字母，英文字符不变
     * 
     * @param chines 汉字
     * @return 拼音
     */
    public static String converterToFirstSpell(String chines)
    {
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++)
        {
            if (nameChar[i] > 128)
            {
                try
                {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);
                }
                catch (BadHanyuPinyinOutputFormatCombination e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }

    /**
     * 判断是否为手机号 格式1开头，13位数就满足了
     * 
     * @param mobile
     * @return
     */
    public static boolean isMobileNO(String mobile)
    {
        Pattern p = Pattern.compile(phoneRegex);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * BigDecimal按一定精度转为double
     * 
     * @param value 值
     * @param scale 精度
     * @return
     */
    public static Double formatBigDecimal(Object value, int scale)
    {
        Double returnValue = Double.valueOf("0");
        if (value != null && value instanceof BigDecimal)
        {
            returnValue = ((BigDecimal) value).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return returnValue;
    }

    /**
     * 根据key获取json的value
     * 
     * @param jsonObject
     * @param key
     * @return
     */
    public static String getJsonString(JSONObject jsonObject, String key)
    {
        String value = "";
        try
        {
            if (jsonObject.has(key))
            {
                String jsonValue = jsonObject.getString(key);
                if (StringUtils.isNotEmpty(jsonValue) && !"null".equals(jsonValue))
                {
                    value = jsonValue;
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * 根据特定字符生成size位的随机数
     * 
     * @param size
     * @param sources 为空时默认值为0123456789ABCDEFGHJKLMNOPQRSTUVWXYZ
     * @return
     */
    public static String getRandomString(int size, String sources)
    {
        if (StringUtils.isEmpty(sources))
        {
            sources = "0123456789ABCDEFGHJKLMNOPQRSTUVWXYZ";
        }
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(size);
        for (int i = 0; i < size; i++)
        {
            verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
        }
        return verifyCode.toString();
    }

    /**
     * 根据key获取map的string值
     * 
     * @param map
     * @param key
     * @return
     */
    public static String getMapString(Map<String, Object> map, String key)
    {
        String value = "";

        if (map.containsKey(key))
        {
            Object object = map.get(key);
            if (StringUtils.isNotEmpty(object))
            {
                String mapValue = object.toString();
                if (!"null".equals(mapValue))
                {
                    value = mapValue;
                }
            }
        }
        return value;
    }
}
