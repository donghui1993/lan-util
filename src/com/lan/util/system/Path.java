package com.lan.util.system;

import java.io.File;
import java.io.IOException;

/**
 * 获取跟路径有关内容
 * 
 * @author donghui
 * @since 1.0
 */
public class Path
{
	/**
	 * 获取项目根路径
	 * 
	 * @return
	 */
	public static String getRoot()
	{
		return System.getProperty("user.dir").replace("\\", "/") + "/";
	}

	/**
	 * 获取source所在的目录
	 * 
	 * @return
	 */
	public static String getsrc()
	{
		return getRoot() + "src/";
	}
	
	/**
	 * 获取类的源码目录
	 * 
	 * @return
	 */
	public static String getSrcClass(Class<?> clazz)
	{
		return getClassBuildPath(clazz).replaceFirst("/build/classes", "/src");
	}

	/**
	 * 获取build/classes/路径
	 * 
	 * @return
	 */
	public static String getBuildPath()
	{
		return Class.class.getResource("/").getPath().substring(1);
	}

	/**
	 * 获取Class所在的路径
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
