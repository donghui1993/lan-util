package com.lan.util.system;

import java.io.File;
import java.io.IOException;

/**
 * ��ȡ��·���й�����
 * 
 * @author donghui
 * @since 1.0
 */
public class Path
{
	/**
	 * ��ȡ��Ŀ��·��
	 * 
	 * @return
	 */
	public static String getRoot()
	{
		return System.getProperty("user.dir").replace("\\", "/") + "/";
	}

	/**
	 * ��ȡsource���ڵ�Ŀ¼
	 * 
	 * @return
	 */
	public static String getsrc()
	{
		return getRoot() + "src/";
	}
	
	/**
	 * ��ȡ���Դ��Ŀ¼
	 * 
	 * @return
	 */
	public static String getSrcClass(Class<?> clazz)
	{
		return getClassBuildPath(clazz).replaceFirst("/build/classes", "/src");
	}

	/**
	 * ��ȡbuild/classes/·��
	 * 
	 * @return
	 */
	public static String getBuildPath()
	{
		return Class.class.getResource("/").getPath().substring(1);
	}

	/**
	 * ��ȡClass���ڵ�·��
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getClassBuildPath(Class<?> clazz)
	{
		try
		{
			return new File(clazz.getResource("").getPath()).getCanonicalPath().replace("\\", "/") + "/";
        }
        catch (IOException e)
		{
			e.printStackTrace();
		}
		return "";
	}
}
