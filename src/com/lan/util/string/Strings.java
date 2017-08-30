package com.lan.util.string;

public class Strings
{
	/**
	 * 
	 * @param source
	 * @param compre
	 * @return
	 */
	public static boolean eqIgenorIn(String source, String... compre)
	{
		return eqIgenorIn(source, null, compre);
	}
	/**
	 * ��һ���ַ�����һЩ�ַ����Ƚϣ��������ͬ���򷵻�true
	 * 
	 * @param source
	 *            ��Ҫ�Ƚϵ��ַ��� ���û�бȽ�ֵ��Ĭ�Ϸ���true
	 * @param prefix
	 *            ��Ҫ��ӵ��ַ���ǰ׺prefix �Ƚ�ʱ���ȡcompre��ÿһ���ַ�������ǰ�������ָ֮�����ַ���֮���ٱȽ��Ƿ���ͬ
	 * @param compare
	 *            ���Ƚϵ�һЩ�ַ��������û�бȽ�ֵ��Ĭ�Ϸ���true
	 * @return �ȽϽ��
	 */
	public static boolean eqIgenorIn(String source, String prefix, String... compare)
	{
		if (compare == null || source == null)
		{
			return true;
		}
		if (prefix == null)
		{
			prefix = "";
		}
		for (String str : compare)
		{
			if (source.equalsIgnoreCase(prefix + str))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean isNull(Object... obj)
	{
		if (obj == null)
			return true;

		for (Object object : obj)
		{
			if (object == null || "".equals(object))
			{
				return true;
			}
		}
		return false;
	}

}
