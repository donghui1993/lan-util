package com.lan.util.object;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2017/2/28
 * @author donghui
 * @since 1.0
 */
public class DataStorageCache
{
	/**
	 * �洢�����ֶ����� String �� classname ��Field �� class field
	 */
	public static final Map<String, Field[]> TYPE_MAP = new HashMap<>();
}
