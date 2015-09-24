package io;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {
	InputStream in;
	int currByte = 0;
	int repeat = 0;

	public MyDecompressorInputStream(InputStream aIn) {
		in = aIn;
	}

	@Override
	public int read() throws IOException {
		// if not end of file
		if (currByte >= 0) {
			if (repeat <= 0) {
				// if no more times to repeat, get next byte and next amout to repeat
				currByte = in.read();
				repeat = in.read();
			}
		}
		
		repeat--;
		return currByte;
	}
}
