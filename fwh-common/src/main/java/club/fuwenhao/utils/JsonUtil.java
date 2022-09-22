package club.fuwenhao.utils;

import club.fuwenhao.constant.CommonConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 核心思想方便后续版本升级统一管理
 *
 * @author fwh [2021/2/5 && 5:04 下午]
 * @return
 */
public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private JsonUtil() {
    }

    /**
     * 功能描述: <br>
     * 〈不做处理转换String〉
     *
     * @param object
     * @return java.lang.String
     * @author zhuochen7
     * @date 2020/4/20 11:36
     */
    public static String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }

    /*
     * 功能描述: <br>
     * 〈将实体转换为Json格式字符串 〉
     * @Param: [object  , 需要格式化的对象]
     * @Return: void
     * @Author: zhuochen7
     * @Date: 2019/12/2 14:32
     */
    public static String objectConvertJsonString(Object object) {
        return objectConvertJsonString(object, null, null, null);
    }

    /*
     * 功能描述: <br>
     * 〈将实体转换为Json格式字符串 〉
     * @Param: [dataFomat  , 日期格式]
     * @Param: [object  , 需要格式化的对象]
     * @Return: void
     * @Author: zhuochen7
     * @Date: 2019/12/2 14:32
     */
    public static String objectConvertJsonString(Object object, String dateFormat) {
        return objectConvertJsonString(object, dateFormat, null, null);
    }

    /*
     * 功能描述: <br>
     * 〈将实体转换为Json格式字符串 〉
     * @Param: [dataFomat  , 日期格式]
     * @Param: [object  , 需要格式化的对象]
     * @Param: [dateType  , 需要格式化的日期类型]
     * @Param: [returnDateType , 需要返回的日期格式
     * 目前是两种JSON_DATE_RETURN_TYPE_NULL = "" 和 JSON_DATE_RETURN_TYPE_1 = "-1"
     * 默认是JSON_DATE_RETURN_TYPE_1]
     * @Return: void
     * @Author: zhuochen7
     * @Date: 2019/12/2 14:32
     */
    public static String objectConvertJsonString(Object object, String dateFormat,
                                                 Class<?> dateType, String returnDateType) {
        if (null == object) {
            return "";
        }
        String jsonStr = null;
        try {
            //如果值为null，则返回空串
            ValueFilter dateFilter = (Object sourceOject, String field, Object fieldValue) -> {
                try {
                    Field declaredField = getDeclaredField(sourceOject, field);
                    boolean valueObject = sourceOject != null && declaredField != null;
                    if ((dateType == null && fieldValue == null && valueObject &&
                            (Date.class.isAssignableFrom(declaredField.getType()) || LocalTime.class.isAssignableFrom(declaredField.getType()))) || (
                            dateType != null && valueObject && dateType.isAssignableFrom(declaredField.getType())
                    )
                    ) {
                        return getReturnValue(returnDateType);
                    }
                    return fieldValue;
                } catch (Exception e) {
                    LogUtil.error(log, "日期null处理错误", e);
                }
                return fieldValue;
            };
            SerializeFilter[] serializeFilters = new SerializeFilter[1];
            if (!StringUtil.isNull(dateFilter)) {
                serializeFilters[0] = dateFilter;
            }
            jsonStr = JSON.toJSONString(object, SerializeConfig.globalInstance, serializeFilters,
                    dateFormat, JSON.DEFAULT_GENERATE_FEATURE,
                    SerializerFeature.WriteNullStringAsEmpty,
                    SerializerFeature.WriteNullBooleanAsFalse,
                    SerializerFeature.WriteNullListAsEmpty,
                    SerializerFeature.UseSingleQuotes);
            jsonStr = jsonStr.replaceAll("'", "\"");
        } catch (Exception e) {
            LogUtil.error(log, "entity转json字符串异常", e);
        }
        return jsonStr;
    }

    /*
     * 功能描述: <br>
     * 〈json转object〉
     * @Param: [jsonString json字符串
     *  pojoCalss 需要转换的类型]
     * @Return: T
     * @Author: zhuochen7
     * @Date: 2019/12/2 14:28
     */
    public static <T> T jsonToObject(String jsonString, Class<T> pojoCalss) {
        if (StringUtil.isNull(jsonString)) return null;
        T parseObject = null;
        try {
            parseObject = JSONObject.parseObject(jsonString, pojoCalss);
        } catch (Exception e) {
            LogUtil.error(log, "json转换对象异常", e);
        }
        return parseObject;
    }

    /*
     * 功能描述: <br>
     * 〈json转 List〉
     * @Param: [jsonArray  json字符串数组, pojoCalss 需要转换的集合]
     * @Return: java.util.List<T>
     * @Author: zhuochen7
     * @Date: 2019/12/2 14:29
     */
    public static <T> List<T> jsonToListObject(String jsonArray, Class<T> pojoCalss) {
        if (StringUtil.isNull(jsonArray)) return Collections.emptyList();
        List<T> ts = null;
        try {
            ts = JSONArray.parseArray(jsonArray, pojoCalss);
        } catch (Exception e) {
            LogUtil.error(log, "转化出现异常：{}", e);
        }
        return ts;
    }

    private static Object getReturnValue(String returnType) {
        if (null == returnType) {
            return -1L;
        }
        switch (returnType) {
            case CommonConstant.JSON_DATE_RETURN_TYPE_NULL:
                return "";
            case CommonConstant.JSON_DATE_RETURN_TYPE_1:
                return -1L;
            default:
                return -1L;
        }
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */

    private static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;

        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }

        return null;
    }

    public static Object getAttribute(String json, String attr) {
        JSONObject obj = JSONObject.parseObject(json);
        return obj != null ? obj.get(attr) : null;
    }

    public static String getStringAttribute(String json, String attr) {
        JSONObject obj = JSONObject.parseObject(json);
        return obj != null ? obj.getString(attr) : "";
    }
}
