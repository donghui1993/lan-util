/**
 * 
 */
package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.lan.util.image.ColorCount;
import com.lan.util.image.OctreeColor;
import com.lan.util.map.Maps;

/**
 * @author donghui
 * @date 2017Äê5ÔÂ10ÈÕ
 */
public class BinaryTest
{
    @Test
    public void testImagePixel()
    {
        OctreeColor octreeColor = new OctreeColor();
        octreeColor.start("E:/theme-color-test/version3/pic.jpg", "", 16);
    }
    
    @Test
    public void test()
    {
        Map map = new HashMap();
        map.put("ff00ff", 10);
        List<ColorCount> map2list = Maps.map2list(map, ColorCount.class);
        System.out.println(map2list);
        System.out.println(Integer.toBinaryString(235));
    }
    
    @Test
    public void inttet()
    {
        System.out.println((int) 0.9);
    }
}
