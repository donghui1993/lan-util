/**
 * 
 */
package com.lan.util.reg;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lan.util.list.Lists;

/**
 * @author donghui
 * @since 6.4.0
 * @date 2017Äê5ÔÂ17ÈÕ
 */
public class RegExps
{
    
    public static String[] byLength(String str, String reg)
    {
        Pattern compile = Pattern.compile(reg);
        Matcher matcher = compile.matcher(str);
        List<String> list = Lists.newList(String.class);
        int i = 0;
        while (matcher.find())
        {
            list.add(matcher.group(i));
        }
        return Lists.toArray(list, String.class);
    }
}
