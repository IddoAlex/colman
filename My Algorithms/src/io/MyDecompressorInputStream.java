package io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class MyDecompressorInputStream extends InputStream {
	InputStream in;

	public MyDecompressorInputStream(InputStream aIn) {
		in = aIn;
	}

	@Override
	public int read() throws IOException {
		return in.read();
	}
	
	@Override
	public int read(byte[] b) throws IOException {
		ByteBuffer buffer = ByteBuffer.wrap(b);

		int count=0;
		int num;
		int occurrences;
		
		while((num=read())!=-1) // -1 represents end of array
		{
			occurrences = read();
			for(int i=0;i<occurrences;i++) {
				buffer.put((byte)num);
			}
			count++;
		}
		
		return count;
	}
}
