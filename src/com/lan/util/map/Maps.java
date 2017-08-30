package com.lan.util.map;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lan.util.list.Lists;

public class Maps
{

    /**
     * ͨ��map����list���� Ҫ��class��Ӧ��ʵ����Ϊ����Ϊkv��ʽ�� Ҫ��ʵ���������key��ǰ��value�ں�
     * @param map
     * @param clazz
     * @return
     * @since 6.4.0
     */
    public static <T> List<T> map2list(Map<?, ?> map, Class<T> clazz)
    {
        List<T> list = Lists.newList(clazz);
        if (map != null && !map.isEmpty())
        {
            Field[] fields = clazz.getDeclaredFields();
            if(fields.length != 2 ){
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
                } catch (InstantiationException | IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
        }
        
        return list;
    }
    
    /**
     * ������field�ֶ�תΪmap����
     * 
     * @param obj
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
     * ͨ������get������field�ֶ�תΪmap���� ,getMethod ������϶�Ӧ���ֶ����ƣ�������ܻ�ȡ����ֵ
     * 
     * @param obj
     * @return
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
                    /* getMethodName --> methodName */
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
    
    public static <K, V> Map<K, V> newMap(Class<K> key, Class<V> value)
    {
        return new HashMap<K, V>();
    }
    public static Maps.MapContainer get(Object target, String prop)
    {
        Map<String, Object> method2Map = field2Map(target);
        Object object = method2Map.get(prop);
        if (object == null || !(object instanceof Map))
        {
            method2Map = new HashMap<>();
        } else
        {
            method2Map = (Map<String, Object>) object;
        }
        return new Maps.MapContainer(method2Map.keySet().size() > 0, method2Map);
    }

    public static class MapContainer
    {
        public boolean state;
        public Map<String, Object> map;

        MapContainer(boolean state, Map<String, Object> map)
        {
            this.state = state;
            this.map = map;
        }
    }
}
