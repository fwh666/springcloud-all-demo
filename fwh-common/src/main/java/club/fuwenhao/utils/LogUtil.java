package club.fuwenhao.utils;

import club.fuwenhao.bean.LogInfo;
import org.slf4j.Logger;

/**
 * 日志封装
 *
 * @author fwh [2021/2/5 && 5:04 下午]
 * @return
 */
public class LogUtil {

    private LogUtil() {
    }

    public static void debug(Logger log, String message) {
        log.debug(new LogInfo(message, "debug").toString());
    }

    public static void debug(Logger log, String message, Object... param) {
        log.debug(new LogInfo(message, "debug").toString(), param);
    }

    public static void info(Logger log, String message) {
        log.info(new LogInfo(message).toString());
    }

    public static void info(Logger log, String message, Object... param) {
        log.info(new LogInfo(message).toString(), param);
    }

    public static void warn(Logger log, String message) {
        log.warn(new LogInfo(message, "debug").toString());
    }

    public static void warn(Logger log, String message, Object... param) {
        log.warn(new LogInfo(message, "debug").toString(), param);
    }

    public static void error(Logger log, String message) {
        log.error(new LogInfo(message, "error").toString());
    }

    public static void error(Logger log, String message, Object... param) {
        log.error(new LogInfo(message, "error").toString(), param);
    }

    public static void error(Logger log, String message, Exception e) {
        log.error(new LogInfo(message, "error").toString(), e);
    }
}
