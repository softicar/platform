package com.softicar.platform.common.io.classfile;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClassFileReader {

	private final InputStream inputStream;

	public ClassFileReader(InputStream inputStream) {

		this.inputStream = new BufferedInputStream(inputStream);
	}

	public byte[] readBytes(int count) {

		try {
			byte[] bytes = new byte[count];
			int n = inputStream.read(bytes);
			if (n == count) {
				return bytes;
			} else {
				throw new RuntimeException("Unexpected end of class file.");
			}
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	public double readDouble() {

		return Double.longBitsToDouble(readLong());
	}

	public float readFloat() {

		return Float.intBitsToFloat(readInt32());
	}

	public int readInt32() {

		return (readInt8() << 24) + (readInt8() << 16) + (readInt8() << 8) + readInt8();
	}

	public int readInt16() {

		return (readInt8() << 8) + readInt8();
	}

	public int readInt8() {

		try {
			int b = inputStream.read();
			if (b >= 0) {
				return b;
			} else {
				throw new RuntimeException("Unexpected end of class file.");
			}
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	public long readLong() {

		return (long) readInt32() << 32 + readInt32();
	}
}
