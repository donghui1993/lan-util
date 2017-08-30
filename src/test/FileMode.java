package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class FileMode
{
	@Test

	public void hash()
	{
		String file = "F:/soft/ps/Photoshop.exe";
		String file1 = "C:/Users/do_ng/Desktop/ngrok-cp.cfg";
		System.out.println(getHash(file));
	}

	private static String getHash(String fileName)
	{
		String hashType = "SHA1";
		InputStream fis;
		try
		{
			fis = new FileInputStream(fileName);
			byte buffer[] = new byte[1024];
			MessageDigest md5 = MessageDigest.getInstance(hashType);
			for (int numRead = 0; (numRead = fis.read(buffer)) > 0;)
			{
				md5.update(buffer, 0, numRead);
			}
			fis.close();
			return toHexString(md5.digest());
		} catch (NoSuchAlgorithmException | IOException e)
		{
			e.printStackTrace();
		}
		return "";
	}

	private static String toHexString(byte[] byts)
	{
		StringBuffer sb = new StringBuffer();
		for (byte b : byts)
		{
			String s = Integer.toHexString(b & 0xFF);
			System.out.println(s);
			if (s.length() == 1)
			{
				sb.append("0" + s);
			} else
			{
				sb.append(s);
			}
		}
		return sb.toString();
		
	}
}
