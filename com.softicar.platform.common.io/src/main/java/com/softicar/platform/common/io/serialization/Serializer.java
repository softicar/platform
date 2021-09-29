package com.softicar.platform.common.io.serialization;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.io.stream.OutputStreamWrapper;
import com.softicar.platform.common.string.unicode.Utf8Convering;
import java.io.OutputStream;
import java.lang.reflect.Field;

public class Serializer extends OutputStreamWrapper {

	public Serializer(OutputStream outputStream) {

		super(outputStream);
	}

	public void writeBoolean(boolean value) {

		byte byteValue = (byte) (value? 1 : 0);
		writeByte(byteValue);
	}

	public void writeByte(byte value) {

		write(value);
	}

	public void writeByteArray(byte[] bytes) {

		if (bytes != null) {
			writeInt(bytes.length);
			write(bytes);
		} else {
			writeInt(-1);
		}
	}

	public void writeShort(short value) {

		writeLong(value);
	}

	public void writeInt(int value) {

		writeLong(value);
	}

	public void writeLong(long value) {

		// write sign
		byte bits = 0;
		if (value < 0) {
			value = -value;
			bits |= 0b01000000;
		}

		// write lower 6 bits
		bits |= value & 0b00111111;
		value >>>= 6;
		bits |= value > 0? 0b10000000 : 0;
		write(bits);

		while (value > 0) {
			value = writeBits(value);
		}
	}

	private long writeBits(long value) {

		byte bits = 0;
		bits |= value & 0b01111111;
		value >>>= 7;
		bits |= value > 0? 0b10000000 : 0;
		write(bits);
		return value;
	}

	public void writeString(String value) {

		if (value != null) {
			writeByteArray(Utf8Convering.toUtf8(value));
		} else {
			writeInt(-1);
		}
	}

	public void writeEnum(Enum<?> value) {

		if (value != null) {
			writeInt(value.ordinal());
		} else {
			writeInt(-1);
		}
	}

	public void writeFields(Object object) {

		try {
			Class<? extends Object> objectClass = object.getClass();
			for (Field field: objectClass.getDeclaredFields()) {
				field.setAccessible(true);

				if (field.getType() == byte.class) {
					writeByte(field.getByte(object));
				} else if (field.getType() == short.class) {
					writeShort(field.getShort(object));
				} else if (field.getType() == int.class) {
					writeInt(field.getInt(object));
				} else if (field.getType() == long.class) {
					writeLong(field.getLong(object));
				} else if (field.getType() == byte[].class) {
					writeByteArray((byte[]) field.get(object));
				} else if (field.getType() == String.class) {
					writeString((String) field.get(object));
				} else if (field.getType().isEnum()) {
					writeEnum((Enum<?>) field.get(object));
				} else {
					throw new SofticarDeveloperException("Serialization of '%s' is not supported.", field.getType().getCanonicalName());
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException exception) {
			throw new SofticarException(exception);
		}
	}
}
