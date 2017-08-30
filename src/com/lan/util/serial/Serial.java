package com.lan.util.serial;

import java.lang.reflect.Field;

public class Serial
{
	private Object[] args;

	private Serial(Object... args)
	{
		this.args = args;
	}

	public static Serial init(Object... args)
	{
		return new Serial(args);
	}

	public Serial then(Then then)
	{
		then.getClass().getDeclaredFields();
		try
		{
			Field field = then.getClass().getDeclaredField("args");
			field.setAccessible(true);
			if (field != null)
			{
				field.set(then, args);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		then.exec();
		return this;
	}
}
