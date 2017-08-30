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
	 * ������������
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isBasicType(Object object)
	{
		return getSimpleClass(object.getClass()).isPrimitive();
	}

	/**
	 * ��ȡ�����ڲ��������ֶ�ֱ�����ݣ����ҷ���Map&lt;fieldName,fieldValue&gt;����
	 * 
	 * @param obj
	 *            ��Ҫ��ȡ�ֶε��Զ������
	 * @return Map&lt;fieldName,fieldValue&gt;����
	 */
	public static Map<String, Object> mapKeys(Object obj)
	{
		if (obj == null)// ��ֱ�ӷ���null
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
	 * ���������ֵ���л�ϴ�����������������ͬ������ͬ�����Խ��кϲ� ���ԭʼ�����Ѿ�����ֵ����������
	 * 
	 * @param t
	 *            ԭʼ����
	 * @param b
	 *            ��϶���
	 * @return t �����ɵĶ���
	 */
	public static <T, B> T mixin(T t, B b)
	{
		return mixin(t, b, false);
	}

	/**
	 * ���������ֵ���л�ϴ�����������������ͬ������ͬ�����Խ��кϲ�����allMixinΪtrueʱ������ԭʼ�����Ƿ���ֵ���û�϶�����и���
	 * 
	 * @param t
	 *            ԭʼ����
	 * @param b
	 *            ��϶���
	 * @param allMixin
	 *            ���Ա�������ֵ���л�� default:false
	 * @return t �����ɵĶ���
	 */
	public static <T, B> T mixin(T t, B b, boolean allMixin)
	{
		Map<String, Object> bmapKeys = mapKeys(b);// ��ȡֵ��Դ����������ֶ�����

		Class<? extends Object> tClass = t.getClass();// ��ȡ�ö��������

		Field[] fields = tClass.getDeclaredFields();// ��ȡ�����͵��ֶ�����
		try
		{
			for (Field field : fields)
			{
				String key = field.getName();// ��ȡ�ö������Ե�����

				field.setAccessible(true);
				Object targetField = bmapKeys.get(key);
				// allMixin = false ����Ҫ�ж�
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
	 * �ж�һ��field�Ƿ�����һ��Object������һ�� ����������ϵ
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
	 * ��ȡһ��class�İ�װ�࣬���������ֱ�ӷ��ظ�class
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
