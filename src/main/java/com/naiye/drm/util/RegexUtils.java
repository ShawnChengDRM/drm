package com.naiye.drm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexUtils {

    private RegexUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static String matchGroup(String expression, String content, int group)
    {
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        if(matcher.find())
        {
            return matcher.group(group);
        }

        return "";
    }

    public static boolean find(String expression, String content)
    {
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        if(matcher.find())
        {
            return true;
        }

        return false;
    }

    /** 根据正则表达式返还匹配结果 */
    private static boolean matcherString(String value, String strRegex) {
        Pattern p = Pattern.compile(strRegex);
        Matcher m = p.matcher(value);
        return m.matches();
    }



    /**
     * 匹配非特殊字符
     */
    public static boolean matcherIllegal(String value) {
        String str = "^([a-zA-Z0-9]|[._]|[\\u4E00-\\u9FA5]){1,20}$";
        return matcherString(value, str);
    }

    /**
     * 字母开头，匹配字母、数字、下划线
     */
    public static boolean matcherPasswordCode(String value) {
        String str = "^[a-zA-Z][a-zA-Z0-9_]{6,16}$";
        return matcherString(value, str);
    }

    /**
     * 匹配字母、数字、下划线
     */
    public static boolean matcherPassword(String value) {
        String str = "[a-zA-Z0-9_]{6,16}$";
        return matcherString(value, str);
    }

    /**
     * 匹配邮箱
     */
    public static Boolean matcherEmail(String value) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{3})$";
        return matcherString(value, str);
    }

    /**
     * 匹配手机号码
     */
    public static Boolean matcherMobileNo(String value) {
        String str = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
        return matcherString(value, str);
    }

    /**
     * 匹配字母和数字
     */
    public static Boolean matcherNumberLetter(String value) {
        String str = "^[A-Za-z0-9]+$";
        return matcherString(value,str);
    }

    /**
     * 匹配数字
     */
    public static Boolean matcherNumber(String value) {
        String str = "^[0-9]+$";
        return matcherString(value,str);
    }

    /**
     * 验证银卡卡号
     */
    public static boolean matcherBankCard(String value) {
        String str = "^\\d{16,19}$|^\\d{6}[- ]\\d{10,13}$|^\\d{4}[- ]\\d{4}[- ]\\d{4}[- ]\\d{4,7}$";
        return matcherString(value,str);
    }

    /**
     * 身份证验证
     */
    public static boolean matcherIdCard(String value) {
        /**15位和18位身份证号码的正则表达式*/
        String str = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
        return matcherString(value,str);
    }

    /**
     * 匹配中文字符的正则表达式： [\u4e00-\u9fa5]
     评注：匹配中文还真是个头疼的事，有了这个表达式就好办了

     匹配双字节字符(包括汉字在内)：[^\x00-\xff]
     评注：可以用来计算字符串的长度（一个双字节字符长度计2，ASCII字符计1）

     匹配空白行的正则表达式：\n\s*\r
     评注：可以用来删除空白行

     匹配HTML标记的正则表达式： <(\S*?)[^>]*>.*? </\1> | <.*? />
     评注：网上流传的版本太糟糕，上面这个也仅仅能匹配部分，对于复杂的嵌套标记依旧无能为力

     匹配首尾空白字符的正则表达式：^\s* |\s*$
     评注：可以用来删除行首行尾的空白字符(包括空格、制表符、换页符等等)，非常有用的表达式

     匹配Email地址的正则表达式：\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*
     评注：表单验证时很实用

     匹配网址URL的正则表达式：[a-zA-z]+://[^\s]*
     评注：网上流传的版本功能很有限，上面这个基本可以满足需求

     匹配帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)：^[a-zA-Z][a-zA-Z0-9_]{4,15}$
     评注：表单验证时很实用

     匹配国内电话号码：\d{3}-\d{8} |\d{4}-\d{7}
     评注：匹配形式如 0511-4405222 或 021-87888822

     匹配腾讯QQ号：[1-9][0-9]{4,}
     评注：腾讯QQ号从10000开始

     匹配中国邮政编码：[1-9]\d{5}(?!\d)
     评注：中国邮政编码为6位数字

     匹配身份证：\d{15} |\d{18}
     评注：中国的身份证为15位或18位

     匹配ip地址：\d+\.\d+\.\d+\.\d+
     评注：提取ip地址时有用

     匹配特定数字：
     ^[1-9]\d*$　 　 //匹配正整数
     ^-[1-9]\d*$ 　 //匹配负整数
     ^-?[1-9]\d*$　　 //匹配整数
     ^[1-9]\d* |0$　 //匹配非负整数（正整数 + 0）
     ^-[1-9]\d* |0$　　 //匹配非正整数（负整数 + 0）
     ^[1-9]\d*\.\d* |0\.\d*[1-9]\d*$　　 //匹配正浮点数
     ^-([1-9]\d*\.\d* |0\.\d*[1-9]\d*)$　 //匹配负浮点数
     ^-?([1-9]\d*\.\d* |0\.\d*[1-9]\d* |0?\.0+ |0)$　 //匹配浮点数
     ^[1-9]\d*\.\d* |0\.\d*[1-9]\d* |0?\.0+ |0$　　 //匹配非负浮点数（正浮点数 + 0）
     ^(-([1-9]\d*\.\d* |0\.\d*[1-9]\d*)) |0?\.0+ |0$　　//匹配非正浮点数（负浮点数 + 0）
     评注：处理大量数据时有用，具体应用时注意修正

     匹配特定字符串：
     ^[A-Za-z]+$　　//匹配由26个英文字母组成的字符串
     ^[A-Z]+$　　//匹配由26个英文字母的大写组成的字符串
     ^[a-z]+$　　//匹配由26个英文字母的小写组成的字符串
     ^[A-Za-z0-9]+$　　//匹配由数字和26个英文字母组成的字符串
     ^\w+$　　//匹配由数字、26个英文字母或者下划线组成的字符串
     */

}
