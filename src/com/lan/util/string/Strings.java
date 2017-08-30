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
	 * 将一个字符串与一些字符串比较，如果有相同的则返回true
	 * 
	 * @param source
	 *            需要比较的字符串 如果没有比较值则默认返回true
	 * @param prefix
	 *            需要添加的字符串前缀prefix 比较时会读取compre的每一个字符串，在前面添加上之指定的字符串之后再比较是否相同
	 * @param compare
	 *            被比较的一些字符串。如果没有比较值则默认返回true
	 * @return 比较结果
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
