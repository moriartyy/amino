package darklight.amino.common.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * 简单封装Jackson实现JSON<->Java Object的Mapper.
 * 
 * 封装不同的输出风格, 使用不同的builder函数创建实例.
 */
public class JsonMapper {

	private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);

	private ObjectMapper mapper;

	public JsonMapper(Include inclusion) {
		mapper = new ObjectMapper();
        //设置输出时包含属性的风格
        mapper.setSerializationInclusion(inclusion);
		//设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//允许字段名不带引号
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
//		//禁止使用int代表Enum的order()來反序列化Enum,非常危險
//		mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
	}
	
	public JsonMapper(Include inclusion, boolean isAllowUnquotedControlChars) {
		mapper = new ObjectMapper();
		//设置输出时包含属性的风格
		mapper.setSerializationInclusion(inclusion);
		//设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//允许字段名不带引号
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		// allow JSON Strings to contain unquoted control characters
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,isAllowUnquotedControlChars);
	}

	/**
	 * 创建输出全部属性到Json字符串的Mapper.
	 */
	public static JsonMapper buildNormalMapper() {
		return new JsonMapper(Include.ALWAYS);
	}

	/**
	 * 创建只输出非空属性到Json字符串的Mapper.
	 */
	public static JsonMapper buildNonNullMapper() {
		return new JsonMapper(Include.NON_NULL);
	}

	/**
	 * 创建只输出初始值被改变的属性到Json字符串的Mapper.
	 */
	public static JsonMapper buildNonDefaultMapper() {
		return new JsonMapper(Include.NON_DEFAULT);
	}

    /**
     * 如果JSON字符串为Null或"null"字符串, 返回Null.
     * 如果JSON字符串为"[]", 返回空集合.
     * 
    	 * 如需读取集合如List/Map, 且不是List<String>这种简单类型时使用如下语句,使用後面的函數.
     */
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		if (Strings.isNullOrEmpty(jsonString)) {
			return null;
		}

		try {
			T t = mapper.readValue(jsonString, clazz);
			return t;
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

	/**
	 * 如果JSON字符串为Null或"null"字符串, 返回Null.
	 * 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需读取集合如List/Map, 且不是List<String>時,
	 * 先用constructParametricType(List.class,MyBean.class)構造出JavaType,再調用本函數.
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromJson(String jsonString, JavaType javaType) {
		if (Strings.isNullOrEmpty(jsonString)) {
			return null;
		}

		try {
			return mapper.readValue(jsonString, javaType);
		} catch (IOException e) {
            throw Throwables.propagate(e);
		}
	}
	
	
	/**
	 * 如果JSON字符串为Null或"null"字符串, 返回Null.
	 * 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需读取集合如List/Map, 且不是List<String>時,
	 * TypeReference 可以 通过 new 方法来解决：比如
	 * 
	 *  List<String>  通过 new TypeReference<List<String>>(){} 相对于JavaType 更直观明了些
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromJson(String jsonString, TypeReference<T> type) {
		if (Strings.isNullOrEmpty(jsonString)) {
			return null;
		}

		try {
            return mapper.readValue(jsonString, type);
		} catch (IOException e) {
            throw Throwables.propagate(e);
		}
	}

	/**
	 * 如果对象为Null, 返回"null".
	 * 如果集合为空集合, 返回"[]".
	 */
	public String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
            throw Throwables.propagate(e);
		}
	}


	/**
	 * 輸出JSONP格式數據.
	 */
	public String toJsonP(String functionName, Object object) {
		return toJson(new JSONPObject(functionName, object));
	}

	/**
	 * 取出Mapper做进一步的设置或使用其他序列化API.
	 */
	public ObjectMapper getMapper() {
		return mapper;
	}
}
