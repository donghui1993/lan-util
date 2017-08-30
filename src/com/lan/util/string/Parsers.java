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
	// ƥ������ {$word}
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
	 * ��� ��+�����ַ��������ߡ�����Ҫ����ġ�+�����ӷ���ֱ�ӽ��ַ�������д���ַ����ڡ�
	 * �滻����ʱ������˳�������滻��������ʹ��ʱ��Ҫע�⴫��Ĳ���˳�򣬷���ó����ַ������ݽ����ܲ���ȷ��
	 * <p>
	 * ʾ���� there are some<b> {$desk} </b>,some <b>{$things}</b> on there.
	 * </p>
	 * <p>
	 * ʾ��ֵ��{"my_desk","books"}
	 * </p>
	 * �滻��ɣ�there are some <b>my_desk</b> ,some <b>books</b> on there.
	 * </p>
	 * 
	 * @param input
	 *            ��Ҫ���ɵ�Ŀ���ַ�������
	 * @param params
	 *            �ɱ���������滻��Ԫ��
	 * @return ���ɺ���ַ�������
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
				throw new RuntimeException("��ǰ�� " + (count + 1) + " ���ؼ��� " + group + " �����ҵ��滻ֵ��ȷ�ϴ���ֵ�ĸ����Ƿ�����");
			}
			input = input.replaceAll("\\{\\$" + group + "\\}", val);
			count++;
		}
		return input;
	}

	/**
	 * ʹ�ö������ֶ�[��ͨ��get������ȡ]�����ַ������ݵ��滻,�������Զ�������Ԫ���滻��
	 * <p>
	 * ʾ���� there are some<b> {$desk} </b>,some <b>{$things}</b> on there.
	 * </p>
	 * <p>
	 * ʾ��ֵ��MyObject { desk:"my_desk",things:"books" }
	 * </p>
	 * �滻��ɣ�there are some <b>my_desk</b> ,some <b>books</b> on there.
	 * 
	 * @param input
	 *            ��Ҫ���ɵ�Ŀ���ַ�������
	 * @param param
	 *            �����滻�Ķ���Ԫ��
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