package test.serial;

import org.junit.Test;

import com.lan.util.serial.Serial;
import com.lan.util.serial.Then;

public class Ser
{
	@Test
	public void test()
	{
		Serial.init("111").then(new Then()
		{
			Object[] args;

			@Override
			public void exec()
			{
				for (Object object : args)
				{
					System.out.println(object);
				}
			}

		});
	}
}
