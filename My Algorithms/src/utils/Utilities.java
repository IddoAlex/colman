package utils;

public class Utilities {
	
	public static final byte[] intToByteArray(int value) {
		byte[] b = new byte[] {
			            (byte)(value >>> 24),
			            (byte)(value >>> 16),
			            (byte)(value >>> 8),
			            (byte)value};
	    return b;
	}
}
