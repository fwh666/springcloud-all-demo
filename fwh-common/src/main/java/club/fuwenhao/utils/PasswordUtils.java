package club.fuwenhao.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

/**
 * @program: fwh-parent
 * @description: 密码工具类
 * @author: fwh
 * @date: 2021-04-29 17:50
 **/
public class PasswordUtils {
    private PasswordUtils() {
    }

    public static String generateSaltPassword(String password) {
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(16);
        buffer.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        int length = buffer.length();
        if (length < 16) {
            for(int i = 0; i < 16 - length; ++i) {
                buffer.append("0");
            }
        }

        String salt = buffer.toString();
        password = DigestUtils.md5Hex(password + salt);
        char[] charArray = new char[48];

        for(int i = 0; i < 48; i += 3) {
            charArray[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            charArray[i + 1] = c;
            charArray[i + 2] = password.charAt(i / 3 * 2 + 1);
        }

        return new String(charArray);
    }

    public static boolean verify(String password, String md5) {
        if (password != null && md5 != null && md5.length() == 48) {
            char[] passwordCharArray = new char[32];
            char[] saltCharArray = new char[16];

            for(int i = 0; i < 48; i += 3) {
                passwordCharArray[i / 3 * 2] = md5.charAt(i);
                passwordCharArray[i / 3 * 2 + 1] = md5.charAt(i + 2);
                saltCharArray[i / 3] = md5.charAt(i + 1);
            }

            String salt = new String(saltCharArray);
            return DigestUtils.md5Hex(password + salt).equals(new String(passwordCharArray));
        } else {
            return false;
        }
    }
}
