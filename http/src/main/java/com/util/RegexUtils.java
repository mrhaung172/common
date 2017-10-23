package com.util;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

public class RegexUtils {

    /**
     * 用户名是否符合规范（^[\u4E00-\u9FA5A-Za-z0-9_]+$）
     *
     * @return
     */
    public static boolean isValidUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return false;
        }

        return username.matches("^[\u4E00-\u9FA5A-Za-z0-9_]{3,10}$");
    }

    /**
     * 用户名是否符合规范是否中文
     *
     * @return
     */
    public static boolean isValidChina(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }

        return str.matches("^[\u4E00-\u9FA5]+$");
    }

    /**
     * 密码是否符合规范（[a-zA-Z\d]{6,20}）
     *
     * @return
     */
    public static boolean isValidPassword(String password) {
        if (null == password) {
            return false;
        }
        return password.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$");
//		return password.matches("^([^\\s'‘’]{6,20})$");
    }
//	public static boolean isValidPassword(String password) {
//		if (null == password) {
//			return false;
//		}
//
//		return password.matches("[a-zA-Z\\d]{6,20}");
//	}

    /**
     * 是否有效手机号码
     *
     * @param mobileNum
     * @return
     */
    public static boolean isMobileNum(String mobileNum) {
        if (null == mobileNum) {
            return false;
        }

        return mobileNum.matches("^((13[0-9])|(14[4,7])|(15[^4,\\D])|(17[0,6-8])|(18[0-9]))(\\d{8})$");
    }

    /**
     * 是否有效邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email) {
            return false;
        }

        return email.matches("^(\\w)+(\\.\\w+)*@(\\w|[_-])+((\\.(\\w|[_-])+)+)$");
//		return email.matches("^([a-zA-Z0-9])+([a-zA-Z0-9_.-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");
    }

    /**
     * 是否是QQ邮箱
     */
    public static boolean isQQEmail(String email) {
        if (null == email)
            return false;

        return email.matches("^[\\s\\S]*@qq.com$");
    }

    /**
     * 是否数字(小数||整数)
     *
     * @param number
     * @return
     */
    public static boolean isNumber(String number) {
        if (null == number) {
            return false;
        }

        return number.matches("^[+-]?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d)+)?$");
    }

    /**
     * 是否是身份证号码
     *
     * @param idNumber
     * @return
     */
    public static boolean isIdNumber(String idNumber) {
        if (null == idNumber) {

            return false;
        }

        return idNumber.matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}(\\d|X|x)$");
    }

    /**
     * 是否整数
     *
     * @param number
     * @return
     */
    public static boolean isInt(String number) {
        if (null == number) {
            return false;
        }

        return number.matches("^[+-]?(([1-9]{1}\\d*)|([0]{1}))$");
    }

    /**
     * 是否正整数
     *
     * @param number
     * @return
     */
    public static boolean isPositiveInt(String number) {
        if (null == number) {
            return false;
        }

        return number.matches("^[+-]?(([1-9]{1}\\d*)|([0]{1}))$");
    }

    /**
     * 是否日期yyyy-mm-dd(yyyy/mm/dd)
     *
     * @param date
     * @return
     */
    public static boolean isDate(String date) {
        if (null == date) {
            return false;
        }
        return date.matches("^([1-2]\\d{3})[\\/|\\-](0?[1-9]|10|11|12)[\\/|\\-]([1-2]?[0-9]|0[1-9]|30|31)$");
    }

    /**
     * 逗号分隔的正则表达式
     *
     * @param str
     * @return
     */
    public static String getCommaSparatedRegex(String str) {
        if (str == null) {
            return null;
        }

        return "^(" + str + ")|([\\s\\S]*," + str + ")|(" + str + ",[\\s\\S]*)|([\\s\\S]*," + str + ",[\\s\\S]*)$";
    }

    /**
     * 字符串包含
     *
     * @return
     */
    public static boolean contains(String str, String regex) {
        if (str == null || regex == null) {
            return false;
        }

        return str.matches(regex);
    }

    /**
     * 是否为纯数字
     *
     * @param bankAccount
     * @return
     */
    public static boolean isBankAccount(String bankAccount) {
        if (null == bankAccount) {
            return false;
        }

        return bankAccount.matches("^(\\d+)$");
    }

    /**
     * 是否是正浮点数或者正数 --判断金额
     *
     * @param amount
     * @return
     */
    public static boolean isAmount(String amount) {
        if (null == amount) {
            return false;
        }

        return amount.matches("^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$") || amount.matches("^[1-9]\\d*$");
    }

    /**
     * 判断输入的文本中是否含有emoji表情
     *
     * @param codePoint
     * @return
     */

    public static boolean containsEmoji(String codePoint) {

        Pattern unicodeOutliers = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CANON_EQ | Pattern.CASE_INSENSITIVE);
        return unicodeOutliers.matcher(codePoint).find();
    }

    public static void main(String[] args) {
        //Log4j.info("123@1qq.com".matches("^[\\s\\S]*@qq.com$")+"");
    }
}
