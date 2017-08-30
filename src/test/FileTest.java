/**
 * 
 */
package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * @author donghui
 * @since 6.4.0
 * @date 2017Äê6ÔÂ8ÈÕ
 */
public class FileTest
{
    @Test
    public void test()
    {
        File f = new File("tempic/a.png");
        File tempic = f.getParentFile();
        if (!tempic.exists())
        {
            tempic.mkdirs();
        }
        
        try
        {
            InputStream is = new FileInputStream("C:/Users/do_ng/Desktop/pic.jpg");
            byte[] bytes = new byte[1024];
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            while (is.read(bytes) != -1)
            {
                fileOutputStream.write(bytes);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println(f.exists());
    }
}
