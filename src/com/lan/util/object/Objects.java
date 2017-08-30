package com.lan.util.object;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2017/2/28
 * @author donghui
 * @since 1.0
 */
public final class Objects
{
	/**
	 * 基础数据类型
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isBasicType(Object object)
	{
		return getSimpleClass(object.getClass()).isPrimitive();
	}

	/**
	 * 获取对象内部的所有字段直接内容，并且返回Map&lt;fieldName,fieldValue&gt;对象
	 * 
	 * @param obj
	 *            需要获取字段的自定义对象
	 * @return Map&lt;fieldName,fieldValue&gt;对象
	 */
	public static Map<String, Object> mapKeys(Object obj)
	{
		if (obj == null)// 不直接返回null
		{
			return new HashMap<String, Object>();
		}
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		Map<String, Object> kv = new HashMap<>();
		try
		{
			for (Field field : declaredFields)
			{
				field.setAccessible(true);
				Object val = field.get(obj);
				kv.put(field.getName(), val);

			}
		} catch (IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return kv;
	}

	/**
	 * 两个对象的值进行混合处理。两个对象名称相同类型相同的属性进行合并 如果原始对象已经存在值，则跳过。
	 * 
	 * @param t
	 *            原始对象
	 * @param b
	 *            混合对象
	 * @return t 混合完成的对象
	 */
	public static <T, B> T mixin(T t, B b)
	{
		return mixin(t, b, false);
	}

	/**
	 * 两个对象的值进行混合处理。两个对象名称相同类型相同的属性进行合并，当allMixin为true时，无论原始对象是否有值，用混合对象进行覆盖
	 * 
	 * @param t
	 *            原始对象
	 * @param b
	 *            混合对象
	 * @param allMixin
	 *            忽略本身属性值进行混合 default:false
	 * @return t 混合完成的对象
	 */
	public static <T, B> T mixin(T t, B b, boolean allMixin)
	{
		Map<String, Object> bmapKeys = mapKeys(b);// 获取值来源对象的所有字段属性

		Class<? extends Object> tClass = t.getClass();// 获取该对象的类型

		Field[] fields = tClass.getDeclaredFields();// 获取该类型的字段属性
		try
		{
			for (Field field : fields)
			{
				String key = field.getName();// 获取该对象属性的名称

				field.setAccessible(true);
				Object targetField = bmapKeys.get(key);
				// allMixin = false ，需要判断
				if (!allMixin && field.get(t) != null)
				{
					continue;
				}
				if (targetField != null && sameType(field, targetField))
				{
					field.set(t, targetField);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return t;
	}

	/**
	 * 判断一个field是否与另一个Object的类型一致 包括隶属关系
	 * 
	 * @param field
	 * @param obj
	 * @return
	 */
	public static boolean sameType(Field field, Object obj)
	{
		Class<? extends Object> objectClass = getWrapperClass(obj.getClass());
		Class<? extends Object> type = getWrapperClass(field.getType());
		return objectClass.isAssignableFrom(type) || type.isAssignableFrom(objectClass);
	}

	/**
	 * 获取一个class的包装类，如果不是则直接返回该class
	 * 
	 * @param clazz
	 * @return
	 */
	public static Class<?> getWrapperClass(Class<?> clazz)
	{
		switch (clazz.getName())
		{
		case "boolean":
			return Boolean.class;
		case "byte":
			return Byte.class;
		case "char":
			return Character.class;
		case "short":
			return Short.class;
		case "int":
			return Integer.class;
		case "long":
			return Long.class;
		case "float":
			return Float.class;
		case "double":
			return Double.class;
		default:
			return clazz;
		}
	}

	public static Class<?> getSimpleClass(Class<?> clazz)
	{
		String typeName = clazz.getName();
		if (typeName.equals(Boolean.class.getName()))
		{
			return Boolean.TYPE;
		}
		if (typeName.equals(Byte.class.getName()))
		{
			return Byte.TYPE;
		}
		if (typeName.equals(Character.class.getName()))
		{
			return Character.TYPE;
		}
		if (typeName.equals(Short.class.getName()))
		{
			return Short.TYPE;
		}
		if (typeName.equals(Integer.class.getName()))
		{
			return Integer.TYPE;
		}
		if (typeName.equals(Long.class.getName()))
		{
			return Long.TYPE;
		}
		if (typeName.equals(Float.class.getName()))
		{
			return Float.TYPE;
		}
		if (typeName.equals(Double.class.getName()))
		{
			return Double.TYPE;
		}
		return clazz;
	}
}
