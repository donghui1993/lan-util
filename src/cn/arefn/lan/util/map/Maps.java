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
 * @date 2017年6月15日
 */
public class Maps
{
    /**
     * 将对象field字段转为map集合
     * 
     * @param obj
     *            元素对象
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
     * 通过对象getter方法将field字段转为map集合<br>
     * getter 必须符合对应的字段名称，否则可能获取不到值<br>
     * 这里获取可以是继承的父级getter
     * 
     * @param obj
     *            需要转化的对象
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
     * 通过map创建list集合 要求class对应的实体类为必须为kv形式。 要求实体类必须是key在前，value在后
     * 
     * @param map
     *            map集合
     * @param clazz
     *            指定范型对象class
     * @return list范型集合
     */
    public static <T> List<T> map2list(Map<?, ?> map, Class<T> clazz)
    {
        List<T> list = Lists.newList(clazz);
        if (map != null && !map.isEmpty())
        {
            Field[] fields = clazz.getDeclaredFields();
            if (fields.length != 2)
            {
                throw new RuntimeException("错误的对象格式，没有满足key-value形式的对象");
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
