package com.softicar.platform.common.math.integer;

/**
 * Utility methods to convert between integers and byte arrays.
 *
 * @author Oliver Richers
 */
public class IntegerBytes {

	/**
	 * Decodes the integer into bytes in little endian format.
	 *
	 * @param value
	 *            the integer
	 * @return the byte array
	 */
	public static byte[] toBytes_LE(int value) {

		byte[] bytes = new byte[4];
		toBytes_LE(value, bytes, 0);
		return bytes;
	}

	/**
	 * Decodes the integer into bytes in little endian format.
	 *
	 * @param value
	 *            the integer
	 * @param bytes
	 *            the output array
	 * @param index
	 *            the index into the array where to output
	 */
	public static void toBytes_LE(int value, byte[] bytes, int index) {

		bytes[index + 0] = (byte) (value & 255);
		value >>= 8;
		bytes[index + 1] = (byte) (value & 255);
		value >>= 8;
		bytes[index + 2] = (byte) (value & 255);
		value >>= 8;
		bytes[index + 3] = (byte) (value & 255);
	}

	/**
	 * Decodes the integer into bytes in big endian format.
	 *
	 * @param value
	 *            the integer
	 * @return the byte array
	 */
	public static byte[] toBytes_BE(int value) {

		byte[] bytes = new byte[4];
		toBytes_BE(value, bytes, 0);
		return bytes;
	}

	/**
	 * Decodes the integer into bytes in big endian format.
	 *
	 * @param value
	 *            the integer
	 * @param bytes
	 *            the output array
	 * @param index
	 *            the index into the array where to output
	 */
	public static void toBytes_BE(int value, byte[] bytes, int index) {

		bytes[index + 3] = (byte) (value & 255);
		value >>= 8;
		bytes[index + 2] = (byte) (value & 255);
		value >>= 8;
		bytes[index + 1] = (byte) (value & 255);
		value >>= 8;
		bytes[index + 0] = (byte) (value & 255);
	}

	/**
	 * Decodes the given bytes into an integer in little endian format.
	 *
	 * @param bytes
	 *            the byte array
	 * @return the decoded integer
	 */
	public static int toInt_LE(byte[] bytes) {

		return toInt_LE(bytes, 0);
	}

	/**
	 * Decodes the given bytes into an integer in little endian format.
	 *
	 * @param bytes
	 *            the byte array
	 * @param index
	 *            the index into the byte array
	 * @return the decoded integer
	 */
	public static int toInt_LE(byte[] bytes, int index) {

		return toInt_LE(bytes[index + 0], bytes[index + 1], bytes[index + 2], bytes[index + 3]);
	}

	/**
	 * Decodes the given bytes into an integer in big endian format.
	 *
	 * @param bytes
	 *            the byte array
	 * @return the decoded integer
	 */
	public static int toInt_BE(byte[] bytes) {

		return toInt_BE(bytes, 0);
	}

	/**
	 * Decodes the given bytes into an integer in big endian format.
	 *
	 * @param bytes
	 *            the byte array
	 * @param index
	 *            the index into the byte array
	 * @return the decoded integer
	 */
	public static int toInt_BE(byte[] bytes, int index) {

		return toInt_LE(bytes[index + 3], bytes[index + 2], bytes[index + 1], bytes[index + 0]);
	}

	/**
	 * Converts the specified bytes into an integer, where the first byte is the
	 * least significant byte and the last byte is the most significant byte.
	 *
	 * @param b0
	 *            the least significant byte
	 * @param b1
	 *            the second-least significant byte
	 * @param b2
	 *            the second-most significant byte
	 * @param b3
	 *            the most significant byte
	 * @return an integer constructed from the bytes in little endian
	 */
	public static int toInt_LE(byte b0, byte b1, byte b2, byte b3) {

		return (1 << 3 * 8) * getUnsignedByte(b3) + (1 << 2 * 8) * getUnsignedByte(b2) + (1 << 1 * 8) * getUnsignedByte(b1)
				+ (1 << 0 * 8) * getUnsignedByte(b0);
	}

	private static int getUnsignedByte(byte b) {

		return b < 0? 256 + b : b;
	}

//	@Test
//	public void test() {
//
//		test(0);
//		test(0x1F);
//		test(0xFF);
//		test(0x1FFFFFFF);
//		test(0xFF000000);
//		test(0xFF00FF00);
//		test(0xFFFFFFFF);
//	}
//
//	private void test(int value) {
//
//		// compute hex string
//		String valueString = Padding.lpad(Integer.toHexString(value), "0", 8);
//
//		// get bytes and compute hex string
//		byte[] bytes = toBytes_BE(value);
//		String bytesString = "";
//		for (byte b: bytes) {
//			bytesString += Padding.lpad(Integer.toHexString(getUnsignedByte(b)), "0", 2);
//		}
//
//		Assert.assertEquals(valueString, bytesString);
//		Assert.assertEquals(value, toInt_BE(toBytes_BE(value)));
//		Assert.assertEquals(value, toInt_LE(toBytes_LE(value)));
//	}
}
