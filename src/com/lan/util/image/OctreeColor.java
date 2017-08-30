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
 * �˲����㷨��ȡͼƬ����ɫ
 * 
 * @author donghui
 * @date 2017��5��10��
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
     *            ��ɫֵ����ͼƬ�ж�ȡ��rgbֵ
     * @param maxColor
     *            �����ɫֵ������ͼƬ��ȡ��������ɫֵ��������
     * @return
     */
    public List<ColorCount> buildOctree(ColorEntity[] colorList, int maxColor)
    {
        int length = colorList.length;
        for (int i = 0; i < length; i++)
        {
            addColor(root, colorList[i], 0);
            
            // ����Ҷ�ӽڵ���
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
        
        int lv = 6;// Ҷ�ӽڵ㲻�������������У����Ҷ����ڵ㲻��Ҫ���������Ե�ǰ��ֵʵ��������Ϊ6.
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
     * �����ڵ������ɫ��
     * 
     * @param root
     *            ����������һ�㣬ͨ��Ϊ����root
     * @param color
     *            ��ǰ����ɫֵ
     * @param level
     *            ������� 0-7
     * @since 6.4.0
     */
    private void addColor(ColorNode node, ColorEntity color, int level)
    {
        if (node.isLeaf)
        {// �����Ҷ�ӽڵ㣬��ϲ���ǰɫ��ֵ
            node.pixelCount++;
            node.r += color.r;
            node.g += color.g;
            node.b += color.b;
        } else
        /*
         * �����㷨
         * ����ɫֵ�г�ȡÿһλ��ɫֵ��Ϣ�� ����rgb(251,199,65)תΪ�����Ʊ�ʶ��
         * r:1111 1101
         * g:1100 0111
         * b:0100 0001
         * --------------------- ÿһ���������ɫֵ������ɫ�ڰ˲����зֲ��Ľڵ㷶Χ����
         * ������Ӧ��Ϊ  110[6]��111[7]��100[4]��100[4]��100[4]��110[6]��010[2]��111[7] �ֱ��Ӧ�˲�����0-7�ڵ�
         * ����ɫ�����нڵ��λ��Ϊ67444627
         */
        {
            //������λ����
            int idx = getNodeIndexFromColor(color, level);
            if (node.children[idx] == null)
            {
                node.children[idx] = createNode(idx, level + 1);
            }
            // ������ӣ�ֱ����ӵ�Ҷ�ӽڵ�
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
     * ����һ���ڵ�
     * 
     * @param nodeIndex
     *            ��ǰ��Ҫ�����Ľڵ����ڵ�λ��
     * @param level
     *            ��ǰ�ڵ����
     * @return
     */
    private ColorNode createNode(int nodeIndex, int level)
    {
        ColorNode colorNode = new ColorNode();
        
        if (level == 7)
        {// �����Ҷ�ӽڵ㣬��Ҷ����ͳ�Ƽ�һ
            colorNode.isLeaf = true;
            leafNum++;
        }else{
            // ���������ͨ�ڵ㣬��ͨ�ڵ�������reducible������
            colorNode.next = reducible[level];
            reducible[level] = colorNode;
        }
        
        return colorNode;
    }
}
