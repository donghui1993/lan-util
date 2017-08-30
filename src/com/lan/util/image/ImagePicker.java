/**
 * 
 */
package com.lan.util.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author donghui
 * @date 2017Äê5ÔÂ10ÈÕ
 */
public class ImagePicker
{
    private String path ;
    public ImagePicker(String path)
    {
        this.path = path;
    }
    
    public void getImagePixel(CallFunction call)
    {
        
        File file = new File(this.path);
        
        BufferedImage read = null;
        try
        {
            read = ImageIO.read(file);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        int width = read.getWidth();
        int height = read.getHeight();
        int minx = read.getMinX();
        int miny = read.getMinY();
        ColorEntity[] arr = new ColorEntity[width * height];
        int index = 0;
        for (int i = minx; i < width; i++)
        {
            for (int j = miny; j < height; j++)
            {
                ColorEntity colorEntity = new ColorEntity();
                long pixel = read.getRGB(i, j);
                colorEntity.r = (int) ((pixel & 0xff0000) >> 16);
                colorEntity.g = (int) ((pixel & 0xff00) >> 8);
                colorEntity.b = (int) (pixel & 0xff);
                arr[index++] = colorEntity;
            }
        }
        call.run(arr);
    }
}
