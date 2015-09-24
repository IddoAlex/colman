package io;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

	OutputStream out;
	int last;
	int count;
	boolean first = true;

	public MyCompressorOutputStream(OutputStream aOut) {
		this.out = aOut;
	}

	@Override
	public void write(int b) throws IOException {
		if (first) {
			first = false;
			last = b;
			count=1;
		} else {
			if (b != last) {
				out.write(last); // writes the byte (0/1)
				out.write(count); // writes how many occurrences
				last = b;
				count = 1;
			} else {
				count++;
			}
		}
		
		if(b==-1) {
			out.write(b);
		}
	}
	
	@Override
	public void write(byte[] arr) throws IOException {
		this.first = true;
		
		for (int i = 0; i < arr.length; i++) {
			write(arr[i]);
		}
		write(-1);
	}
}
