package club.fuwenhao.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @program: fwh-parent
 * @description: 校验合规工具类
 * @author: fwh
 * @date: 2021-04-29 17:55
 **/
public class ValidateUtil {
    private static final int MOBILE_LENGTH = 11;
    private static final int OLD_IDNUMBER_LENGTH = 15;
    private static final int IDNUMBER_LENGTH = 18;
    private static final Pattern ISCHINAPHONE_PATTERN = Pattern.compile("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private static final Pattern DATEFORMAT_PATTERN = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("[0-9]*");
    private static final Pattern REGEX_USERNAME = Pattern.compile("^[a-zA-Z]\\w{5,17}$");
    private static final Pattern REGEX_PASSWORD = Pattern.compile("^[a-zA-Z0-9]{6,16}$");
    private static final Pattern REGEX_CHINESE = Pattern.compile("^[一-龥],{0,}$");
    private static final Pattern REGEX_ID_CARD = Pattern.compile("(^\\d{18}$)|(^\\d{15}$)");
    private static final Pattern REGEX_URL = Pattern.compile("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");

    public ValidateUtil() {
    }

    public static boolean isChinaPhone(String phone) throws PatternSyntaxException {
        return phone.length() != 11 ? false : ISCHINAPHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean checkEmail(String value) {
        return EMAIL_PATTERN.matcher(value).matches();
    }

    public static boolean idCardValidateSimple(String value) {
        return REGEX_ID_CARD.matcher(value).matches();
    }

    public static boolean userNameValidate(String value) {
        return REGEX_USERNAME.matcher(value).matches();
    }

    public static boolean passWordValidate(String value) {
        return REGEX_PASSWORD.matcher(value).matches();
    }

    public static boolean chineseValidate(String value) {
        return REGEX_CHINESE.matcher(value).matches();
    }

    public static boolean urlValidate(String value) {
        return REGEX_URL.matcher(value).matches();
    }

    public static boolean idCardValidate(String idNumber) throws ParseException {
        idNumber = idNumber.trim().toUpperCase();
        String[] valCodeArr = new String[]{"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
        String[] wi = new String[]{"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};
        String ai = "";
        if (idNumber.length() != 15 && idNumber.length() != 18) {
            return false;
        } else {
            if (idNumber.length() == 18) {
                ai = idNumber.substring(0, 17);
            } else if (idNumber.length() == 15) {
                ai = idNumber.substring(0, 6) + "19" + idNumber.substring(6, 15);
            }

            if (!isNumeric(ai)) {
                return false;
            } else {
                String strYear = ai.substring(6, 10);
                String strMonth = ai.substring(10, 12);
                String strDay = ai.substring(12, 14);
                if (!isDateFormat(strYear + "-" + strMonth + "-" + strDay)) {
                    return false;
                } else {
                    GregorianCalendar gc = new GregorianCalendar();
                    SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
                    if (gc.get(1) - Integer.parseInt(strYear) <= 150 && gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime() >= 0L) {
                        if (Integer.parseInt(strMonth) <= 12 && Integer.parseInt(strMonth) != 0) {
                            if (Integer.parseInt(strDay) <= 31 && Integer.parseInt(strDay) != 0) {
                                Hashtable h = getAreaCode();
                                if (h.get(ai.substring(0, 2)) == null) {
                                    return false;
                                } else {
                                    int totalmulAiWi = 0;

                                    int modValue;
                                    for(modValue = 0; modValue < 17; ++modValue) {
                                        totalmulAiWi += Integer.parseInt(String.valueOf(ai.charAt(modValue))) * Integer.parseInt(wi[modValue]);
                                    }

                                    modValue = totalmulAiWi % 11;
                                    String strVerifyCode = valCodeArr[modValue];
                                    ai = ai + strVerifyCode;
                                    if (idNumber.length() == 18) {
                                        return ai.equals(idNumber);
                                    } else {
                                        return true;
                                    }
                                }
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }
    }

    private static boolean isDateFormat(String str) {
        return DATEFORMAT_PATTERN.matcher(str).matches();
    }

    public static boolean isNumeric(String str) {
        return NUMERIC_PATTERN.matcher(str).matches();
    }

    private static Hashtable getAreaCode() {
        Hashtable<String, String> hashTable = new Hashtable();
        hashTable.put("11", "北京");
        hashTable.put("12", "天津");
        hashTable.put("13", "河北");
        hashTable.put("14", "山西");
        hashTable.put("15", "内蒙古");
        hashTable.put("21", "辽宁");
        hashTable.put("22", "吉林");
        hashTable.put("23", "黑龙江");
        hashTable.put("31", "上海");
        hashTable.put("32", "江苏");
        hashTable.put("33", "浙江");
        hashTable.put("34", "安徽");
        hashTable.put("35", "福建");
        hashTable.put("36", "江西");
        hashTable.put("37", "山东");
        hashTable.put("41", "河南");
        hashTable.put("42", "湖北");
        hashTable.put("43", "湖南");
        hashTable.put("44", "广东");
        hashTable.put("45", "广西");
        hashTable.put("46", "海南");
        hashTable.put("50", "重庆");
        hashTable.put("51", "四川");
        hashTable.put("52", "贵州");
        hashTable.put("53", "云南");
        hashTable.put("54", "西藏");
        hashTable.put("61", "陕西");
        hashTable.put("62", "甘肃");
        hashTable.put("63", "青海");
        hashTable.put("64", "宁夏");
        hashTable.put("65", "新疆");
        hashTable.put("71", "台湾");
        hashTable.put("81", "香港");
        hashTable.put("82", "澳门");
        hashTable.put("91", "国外");
        return hashTable;
    }
}
