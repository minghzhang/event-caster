package com.lewis.event.eventcaster.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Louis
 * @ModuleOwner: Louis
 * @Date:03/25/2022 15:43
 * @Description:
 */
public class JsonUtil {

    private static ObjectMapper allowControlCharsObjectMapper = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());
        objectMapper.enable(new Feature[]{JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature()});
        objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }

    private JsonUtil() {
    }

    public static <T> T fromJson(String json, Class<T> beanClass) throws IOException {
        return allowControlCharsObjectMapper.readValue(json, beanClass);
    }

    public static <T> T fromJson(String json, TypeReference<T> reference) throws IOException {
        return allowControlCharsObjectMapper.readValue(json, reference);
    }

    public static <T> T fromJson(byte[] json, Class<T> beanClass) throws IOException {
        return allowControlCharsObjectMapper.readValue(json, beanClass);
    }

    public static String toJson(Object bean) throws JsonProcessingException {
        return allowControlCharsObjectMapper.writeValueAsString(bean);
    }

    public static byte[] toJsonAsBytes(Object bean) throws JsonProcessingException {
        return allowControlCharsObjectMapper.writeValueAsBytes(bean);
    }

    public static List fromJsonToList(String json, Class<?>... T) throws JsonParseException, JsonMappingException, IOException {
        JavaType javaType = allowControlCharsObjectMapper.getTypeFactory().constructParametricType(List.class, T);
        return (List) allowControlCharsObjectMapper.readValue(json, javaType);
    }

    public static Map<String, Object> toMap(String json) throws IOException {
        return (Map) allowControlCharsObjectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });
    }

    public static <K, V> Map<K, V> toMap(String json, Class<K> keyClass, Class<V> valueClass) throws IOException {
        return (Map) allowControlCharsObjectMapper.readValue(json, allowControlCharsObjectMapper.getTypeFactory().constructMapType(HashMap.class, keyClass, valueClass));
    }

}
