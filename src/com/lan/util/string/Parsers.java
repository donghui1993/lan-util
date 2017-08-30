package com.lan.util.string;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lan.util.list.Lists;
import com.lan.util.object.Objects;

public class Parsers
{
	// 匹配内容 {$word}
	private static final Pattern WORD_MATCH = Pattern.compile("\\{\\$([\\w\\d_-]+)\\}");

	/**
	 * 
	 * @param input
	 * @param params
	 * @return
	 */
	public static String paserList(String input, List<Object> params)
	{
		List<String> strs = new ArrayList<>();
		for (Object object : params)
		{
			if (Objects.isBasicType(object))
			{
				strs.add(object.toString());
			} else
			{
				input = paserObj(input, object);
			}
		}
		String[] arr = Lists.toArray(strs, String.class);
		input = paser(input, arr);
		return input;
	}

	/**
	 * <p>
	 * 替代 “+”的字符串处理工具。不需要冗余的“+”连接符，直接将字符内容书写至字符串内。
	 * 替换内容时，按照顺序依次替换，所以在使用时需要注意传入的参数顺序，否则得出的字符串内容将可能不正确。
	 * <p>
	 * 示例： there are some<b> {$desk} </b>,some <b>{$things}</b> on there.
	 * </p>
	 * <p>
	 * 示例值：{"my_desk","books"}
	 * </p>
	 * 替换完成：there are some <b>my_desk</b> ,some <b>books</b> on there.
	 * </p>
	 * 
	 * @param input
	 *            需要生成的目标字符串内容
	 * @param params
	 *            可变参数用于替换的元素
	 * @return 生成后的字符串内容
	 */
	public static String paser(String input, String... params)
	{
		Matcher matcher = WORD_MATCH.matcher(input);
		int count = 0;
		while (matcher.find())
		{
			String group = matcher.group(1);
			String val = "";
			try
			{
				val = params[count];
			} catch (IndexOutOfBoundsException e)
			{
				throw new RuntimeException("当前第 " + (count + 1) + " 个关键词 " + group + " 不能找到替换值，确认传入值的个数是否满足");
			}
			input = input.replaceAll("\\{\\$" + group + "\\}", val);
			count++;
		}
		return input;
	}

	/**
	 * 使用对象本身字段[不通过get方法获取]进行字符串内容的替换,仅用作自定义对象的元素替换。
	 * <p>
	 * 示例： there are some<b> {$desk} </b>,some <b>{$things}</b> on there.
	 * </p>
	 * <p>
	 * 示例值：MyObject { desk:"my_desk",things:"books" }
	 * </p>
	 * 替换完成：there are some <b>my_desk</b> ,some <b>books</b> on there.
	 * 
	 * @param input
	 *            需要生成的目标字符串内容
	 * @param param
	 *            用于替换的对象元素
	 * @return
	 */
	public static String paserObj(String input, Object param)
	{
		Map<String, Object> mapKeys = Objects.mapKeys(param);
		Matcher matcher = WORD_MATCH.matcher(input);
		while (matcher.find())
		{
			String key = matcher.group(1);
			Object object = mapKeys.get(key);
			if (object != null)
			{
				input = input.replaceAll("\\{\\$" + key + "\\}", object.toString());
			}
		}
		return input;
	}

}