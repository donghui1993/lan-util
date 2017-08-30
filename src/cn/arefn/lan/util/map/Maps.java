/**
 * 
 */
package cn.arefn.lan.util.map;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.arefn.lan.util.list.Lists;

/**
 * @author donghui
 * @date 2017��6��15��
 */
public class Maps
{
    /**
     * ������field�ֶ�תΪmap����
     * 
     * @param obj
     *            Ԫ�ض���
     * @return Map<String, Object>
     */
    public static Map<String, Object> field2Map(Object obj)
    {
        Field[] fields = obj.getClass().getFields();
        Map<String, Object> map = new HashMap<>();
        try
        {
            for (Field field : fields)
            {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * ͨ������getter������field�ֶ�תΪmap����<br>
     * getter ������϶�Ӧ���ֶ����ƣ�������ܻ�ȡ����ֵ<br>
     * �����ȡ�����Ǽ̳еĸ���getter
     * 
     * @param obj
     *            ��Ҫת���Ķ���
     * @return Map<String, Object>
     */
    public static Map<String, Object> method2Map(Object obj)
    {
        Map<String, Object> map = new HashMap<>();
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods)
        {
            try
            {
                String methodName = method.getName();
                if (methodName.startsWith("get") && !methodName.equals("getClass"))
                {
                    /* getFieldName --> fieldName */
                    char[] charArray = methodName.toCharArray();
                    charArray = Arrays.copyOfRange(charArray, 3, charArray.length);
                    charArray[0] = (char) (charArray[0] + 32);
                    
                    map.put(String.valueOf(charArray), method.invoke(obj));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return map;
    }
    
    /**
     * ͨ��map����list���� Ҫ��class��Ӧ��ʵ����Ϊ����Ϊkv��ʽ�� Ҫ��ʵ���������key��ǰ��value�ں�
     * 
     * @param map
     *            map����
     * @param clazz
     *            ָ�����Ͷ���class
     * @return list���ͼ���
     */
    public static <T> List<T> map2list(Map<?, ?> map, Class<T> clazz)
    {
        List<T> list = Lists.newList(clazz);
        if (map != null && !map.isEmpty())
        {
            Field[] fields = clazz.getDeclaredFields();
            if (fields.length != 2)
            {
                throw new RuntimeException("����Ķ����ʽ��û������key-value��ʽ�Ķ���");
            }
            Iterator<?> iterator = map.keySet().iterator();
            while (iterator.hasNext())
            {
                Object key = iterator.next();
                Object val = map.get(key);
                try
                {
                    T newInstance = clazz.newInstance();
                    fields[0].setAccessible(true);
                    fields[0].set(newInstance, key);
                    
                    fields[1].setAccessible(true);
                    fields[1].set(newInstance, val);
                    
                    list.add(newInstance);
                }
                catch (InstantiationException | IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
        }
        
        return list;
    }
}
