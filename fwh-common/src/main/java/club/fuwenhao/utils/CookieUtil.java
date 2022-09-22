package club.fuwenhao.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: fwh-parent
 * @description: Cookie工具类
 * @author: fwh
 * @date: 2021-04-29 17:47
 **/
public class CookieUtil {
    public CookieUtil() {
    }

    public static Cookie get(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            Cookie[] var3 = cookies;
            int var4 = cookies.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Cookie ck = var3[var5];
                if (StringUtils.equals(key, ck.getName())) {
                    return ck;
                }
            }
        }

        return null;
    }

    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            Cookie[] var3 = cookies;
            int var4 = cookies.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Cookie ck = var3[var5];
                if (StringUtils.equals(key, ck.getName())) {
                    return ck.getValue();
                }
            }
        }

        return null;
    }

    public static void setCookie(HttpServletResponse response, String domain, String key, String value, int maxAge) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setDomain(domain);
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }

        response.addCookie(cookie);
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String domain, String key) {
        Cookie cookie = get(request, key);
        if (cookie != null) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            cookie.setDomain(domain);
            response.addCookie(cookie);
        }

    }
}
