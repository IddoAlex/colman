package io;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MyDecompressorInputStream extends InputStream {
	InputStream in;

	public MyDecompressorInputStream(InputStream aIn) {
		in = aIn;
	}

	@Override
	public int read() throws IOException {
		return in.read();
	}

	public int[] readAll() throws IOException {
		ArrayList<Integer> arr = new ArrayList<>();
		int num;
		int occurrences;
		
		while((num=read())!=-1) // -1 represents end of array
		{
			occurrences = read();
			for(int i=0;i<occurrences;i++) {
				arr.add(num);
			}
		}

		int[] intArray = new int[arr.size()];
		for (int i = 0; i < intArray.length; i++) {
			intArray[i] = arr.get(i);
		}
		
		return intArray;
	}

}