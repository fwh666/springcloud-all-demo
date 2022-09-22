//package club.fuwenhao.utils;
//
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//
//import java.io.IOException;
//
///**
// * @program: fwh-parent
// * @description: 国际化工具类
// * @author: fwh
// * @date: 2021-04-29 17:49
// **/
//public class I18nMessageUtil {
//    private static MessageSourceAccessor accessor;
//    private static MessageSource messageSource;
//    private static final String PATH_PARENT = "classpath:i18n/";
//    private static final String SUFFIX = ".properties";
//    private static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
//
//    private I18nMessageUtil() {
//    }
//
//    private static void initMessageSourceAccessor(String language) throws IOException {
//        Resource resource = resourcePatternResolver.getResource("classpath:i18n/" + language + ".properties");
//        String fileName = resource.getURL().toString();
//        int lastIndex = fileName.lastIndexOf(".");
//        String baseName = fileName.substring(0, lastIndex);
//        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
//        reloadableResourceBundleMessageSource.setBasename(baseName);
//        reloadableResourceBundleMessageSource.setCacheSeconds(5);
//        accessor = new MessageSourceAccessor(reloadableResourceBundleMessageSource);
//        messageSource = reloadableResourceBundleMessageSource;
//    }
//
//    public static String getMessage(String language, String message, String defaultMessage) throws IOException {
//        initMessageSourceAccessor(language);
//        return accessor.getMessage(message, defaultMessage, LocaleContextHolder.getLocale());
//    }
//}
