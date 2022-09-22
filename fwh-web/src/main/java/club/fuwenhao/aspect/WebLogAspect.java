package club.fuwenhao.aspect;

import club.fuwenhao.utils.IpUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import org.apache.catalina.connector.RequestFacade;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @program: fwh-parent
 * @description: 日志切面类
 * @author: fwh
 * @date: 2021-04-29 15:37
 **/
@Aspect
@Component
public class WebLogAspect {
    private static final Logger log = LoggerFactory.getLogger(WebLogAspect.class);
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public WebLogAspect() {
    }

    @Pointcut("@within(club.fuwenhao.aspect.WebLog)||@annotation(club.fuwenhao.aspect.WebLog)")
    public void webLog() {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        boolean noNeedWebLog = this.checkNoNeedWebLog(proceedingJoinPoint);
        Map<String, Object> aspectLogMap = this.getAspectLogMap(proceedingJoinPoint);
        if (!noNeedWebLog) {
            String description = (String) aspectLogMap.get("description");
            boolean needReuestUrlLog = (Boolean) aspectLogMap.get("needReuestUrlLog");
            boolean needRequestLog = (Boolean) aspectLogMap.get("needRequestLog");
            log.info("========================================== Start ==========================================");
            if (needReuestUrlLog) {
                log.info("URL            : {}", request.getRequestURL().toString());
            }
            log.info("Description    : {}", description);
            log.info("HTTP Method    : {}", request.getMethod());
            log.info("Class Method   : {}.{}", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName());
            log.info("IP             : {}", IpUtil.getRemoteAddress(request));
            String contentTypeStr = request.getHeader("Content-Type");
            if (needRequestLog && !Strings.isNullOrEmpty(contentTypeStr) && contentTypeStr.contains("application/json")) {
                Object[] args = proceedingJoinPoint.getArgs();
                List<Object> argList = new ArrayList<>();
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    if (!(arg instanceof RequestFacade)) {
                        argList.add(arg);
                    }
                }
                log.info("Request Args   : {}", JSONObject.toJSONString(argList));
            }
        }
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        if (!noNeedWebLog) {
            boolean needResponseLog = (Boolean) aspectLogMap.get("needResponseLog");
            if (needResponseLog) {
                log.info("Response Args  : {}", JSONObject.toJSONString(result));
            }
            log.info("Time-Consuming : {} ms", System.currentTimeMillis() - startTime);
            log.info("=========================================== End ===========================================" + LINE_SEPARATOR);
        }
        return result;
    }

    /**
     * 校验日志
     *
     * @param joinPoint
     * @return boolean
     * @author fuwenhao
     * @date 2021/4/29 4:15 下午
     */
    public boolean checkNoNeedWebLog(JoinPoint joinPoint) throws ClassNotFoundException {
        String methodName = joinPoint.getSignature().getName();
        String targetName = joinPoint.getTarget().getClass().getName();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getDeclaredMethods();
        Method[] var6 = methods;
        int var7 = methods.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            Method method = var6[var8];
            if (method.getName().equals(methodName)) {
                NoNeedWebLog noNeedWebLogAnnotation = (NoNeedWebLog) method.getAnnotation(NoNeedWebLog.class);
                if (noNeedWebLogAnnotation != null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 遍历参数值
     *
     * @param proceedingJoinPoint
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author fuwenhao
     * @date 2021/4/29 4:00 下午
     */
    private Map<String, Object> getAspectLogMap(ProceedingJoinPoint proceedingJoinPoint) throws ClassNotFoundException {
        Map<String, Object> resultMap = new HashMap<>(4);
        String targetName = proceedingJoinPoint.getTarget().getClass().getName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();

        Class targetClass = Class.forName(targetName);
        Method[] declaredMethods = targetClass.getDeclaredMethods();
        Method[] tempMethods = declaredMethods;
        int methodSize = declaredMethods.length;
        for (int i = 0; i < methodSize; i++) {
            Method tempMethod = tempMethods[i];
            if (tempMethod.getName().equals(methodName)) {
                Class[] parameterTypes = tempMethod.getParameterTypes();
                if (parameterTypes.length == args.length) {
                    WebLog webLogAnnotation = (WebLog) tempMethod.getAnnotation(WebLog.class);
                    if (webLogAnnotation == null) {
                        webLogAnnotation = (WebLog) targetClass.getAnnotation(WebLog.class);
                    }
                    if (webLogAnnotation == null) {
                        return null;
                    }
                    String description = webLogAnnotation.description();
                    boolean needReuestUrlLog = webLogAnnotation.needReuestUrlLog();
                    boolean needRequestLog = webLogAnnotation.needRequestLog();
                    boolean needResponseLog = webLogAnnotation.needResponseLog();
                    resultMap.put("description", description);
                    resultMap.put("needReuestUrlLog", needReuestUrlLog);
                    resultMap.put("needRequestLog", needRequestLog);
                    resultMap.put("needResponseLog", needResponseLog);
                    break;
                }
            }
        }
        return resultMap;
    }
}
