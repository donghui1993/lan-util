/**
 * 
 */
package com.lan.util.image;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lan.util.list.Lists;
import com.lan.util.list.SortMember;
import com.lan.util.map.Maps;
import com.lan.util.reg.RegExps;
import com.lan.util.string.Parsers;

/**
 * 八叉树算法提取图片主题色
 * 
 * @author donghui
 * @date 2017年5月10日
 */
public class OctreeColor
{
    int leafNum = 0;
    ColorNode[] reducible = new ColorNode[8];
    ColorNode root = new ColorNode();
    
    public void start(String image, String output, int max)
    {
        new ImagePicker(image).getImagePixel(new CallFunction()
        {
            @Override
            public void run(ColorEntity[] colorList)
            {
                List<ColorCount> buildOctree = buildOctree(colorList, max);
                
                System.out.println(buildOctree.size());
                File f = new File(output);
                
                try
                {
                    FileWriter fw = new FileWriter(f);
                    StringBuffer sb = new StringBuffer();
                    sb.append(Parsers.paser("<div style=\"width:100%\"><img src=\"{$image}\" style=\"float:left;margin-right: 5px;\"/></div>", image));
                    for (ColorCount colorCount : buildOctree)
                    {
                        Integer.parseInt(colorCount.color, 16);
                        
                        sb.append(Parsers.paserObj(
                                "<div title=\"{$count}\" style=\"background:#{$color};float:left;width: 50px;color: #fff;height: 21px; margin-right: 5px;margin-bottom: 5px;font-size: 12px;text-align: center;padding-top: 9px;\"></div>",
                                colorCount));
                    }
                    fw.write(sb.toString());
                    fw.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            
        });
    }
    
    /**
     * 
     * @param colorList
     *            颜色值，从图片中读取的rgb值
     * @param maxColor
     *            最大颜色值，保留图片提取出来的颜色值数量上限
     * @return
     */
    public List<ColorCount> buildOctree(ColorEntity[] colorList, int maxColor)
    {
        int length = colorList.length;
        for (int i = 0; i < length; i++)
        {
            addColor(root, colorList[i], 0);
            
            // 缩减叶子节点数
            while (leafNum > maxColor)
                reduceTree(i);
        }
        Map<String, Integer> colorsStats = colorsStats(root, new HashMap<>());
        List<ColorCount> map2list = Maps.map2list(colorsStats, ColorCount.class);
        return Lists.sort(map2list, new SortMember<ColorCount>()
        {
            
            @Override
            public boolean result(ColorCount one, ColorCount two)
            {
                String[] a = RegExps.byLength(one.color, "\\w{2}");
                String[] b = RegExps.byLength(two.color, "\\w{2}");
                double a3 = Integer.parseInt(a[0], 16) * 0.299 + Integer.parseInt(a[1], 16) * 0.587 + Integer.parseInt(a[2], 16) * 0.114;
                double b3 = Integer.parseInt(b[0], 16) * 0.299 + Integer.parseInt(b[1], 16) * 0.587 + Integer.parseInt(b[2], 16) * 0.114;
                
                return b3 < a3;
            }
            
        });
    }
    
    /**
     * 
     * @param node
     * @param colors
     * @return
     * @since 6.4.0
     */
    private Map<String, Integer> colorsStats(ColorNode node, Map<String, Integer> colors)
    {
       if(node.isLeaf){
           String r = Integer.toHexString(node.r/node.pixelCount);
           String g = Integer.toHexString(node.g/node.pixelCount);
           String b = Integer.toHexString(node.b/node.pixelCount);
            
            r = r.length() == 1 ? "0" + r : r;
            g = g.length() == 1 ? "0" + g : g;
            b = b.length() == 1 ? "0" + b : b;
            
            String color = r + g + b;
            
            if (colors.containsKey(color))
            {
                Integer integer = colors.get(color);
                colors.put(color, integer + node.pixelCount);
            } else
            {
                colors.put(color, node.pixelCount);
            }
            return colors;
        } else
        {
            for (int i = 0; i < 8; i++)
            {
                if (node.children[i] != null)
                {
                    colorsStats(node.children[i], colors);
                }
            }
       }
        return colors;
    }

    /**
     * @since 6.4.0
     */
    private void reduceTree(int index)
    {
        
        int lv = 6;// 叶子节点不放入缩减数组中，并且顶级节点不需要创建，所以当前的值实际最大深度为6.
        while (reducible[lv] == null)
        {
            lv--;
        }
        ColorNode colorNode = reducible[lv];
        reducible[lv] = colorNode.next;
        int r = 0;
        int b = 0;
        int g = 0;
        int count = 0;
        for(int i=0;i<8;i++){
            if (colorNode.children[i] != null)
            {
                r += colorNode.children[i].r;
                g += colorNode.children[i].g;
                b += colorNode.children[i].b;
                count += colorNode.children[i].pixelCount;
                leafNum--;
            }
            
        }
        colorNode.isLeaf = true;
        colorNode.r = r;
        colorNode.g = g;
        colorNode.b = b;
        colorNode.pixelCount = count;
        leafNum++;
    }
    
    /**
     * 向树节点添加颜色表
     * 
     * @param root
     *            迭代树的上一层，通常为树的root
     * @param color
     *            当前的颜色值
     * @param level
     *            树的深度 0-7
     * @since 6.4.0
     */
    private void addColor(ColorNode node, ColorEntity color, int level)
    {
        if (node.isLeaf)
        {// 如果是叶子节点，则合并当前色彩值
            node.pixelCount++;
            node.r += color.r;
            node.g += color.g;
            node.b += color.b;
        } else
        /*
         * 核心算法
         * 从颜色值中抽取每一位颜色值信息， 比如rgb(251,199,65)转为二进制标识法
         * r:1111 1101
         * g:1100 0111
         * b:0100 0001
         * --------------------- 每一个相近的颜色值，其颜色在八叉树中分布的节点范围类似
         * 其序列应该为  110[6]，111[7]，100[4]，100[4]，100[4]，110[6]，010[2]，111[7] 分别对应八叉树的0-7节点
         * 该颜色在树中节点的位置为67444627
         */
        {
            //采用移位换算
            int idx = getNodeIndexFromColor(color, level);
            if (node.children[idx] == null)
            {
                node.children[idx] = createNode(idx, level + 1);
            }
            // 迭代添加，直到添加到叶子节点
            addColor(node.children[idx], color, level + 1);
        }
    }
    
    static int[] lvs = { 7, 6, 5, 4, 3, 2, 1, 0 };
    /**
     * @param color
     * @return
     */
    private int getNodeIndexFromColor(ColorEntity color, int level)
    {
        level = lvs[level];
        String r = String.valueOf((color.r & (1 << level)) >> level);
        String g = String.valueOf((color.g & (1 << level)) >> level);
        String b = String.valueOf((color.b & (1 << level)) >> level);
        return Integer.parseInt(r + g + b, 2);
    }
    
    /**
     * 创建一个节点
     * 
     * @param nodeIndex
     *            当前需要创建的节点所在的位置
     * @param level
     *            当前节点深度
     * @return
     */
    private ColorNode createNode(int nodeIndex, int level)
    {
        ColorNode colorNode = new ColorNode();
        
        if (level == 7)
        {// 如果是叶子节点，则叶子数统计加一
            colorNode.isLeaf = true;
            leafNum++;
        }else{
            // 否则就是普通节点，普通节点数放入reducible可缩减
            colorNode.next = reducible[level];
            reducible[level] = colorNode;
        }
        
        return colorNode;
    }
}
