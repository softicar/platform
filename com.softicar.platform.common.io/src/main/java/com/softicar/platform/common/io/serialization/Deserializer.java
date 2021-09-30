package com.softicar.platform.common.io.serialization;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.core.utils.ReflectionUtils;
import com.softicar.platform.common.io.stream.InputStreamWrapper;
import com.softicar.platform.common.string.unicode.Utf8Convering;
import java.io.InputStream;
import java.lang.reflect.Field;

public class Deserializer extends InputStreamWrapper {

	public Deserializer(InputStream inputStream) {

		super(inputStream);
	}

	public boolean readBoolean() {

		return readByte() != 0;
	}

	public byte readByte() {

		return read();
	}

	public byte[] readByteArray() {

		int length = readInt();
		return length >= 0? read(length) : null;
	}

	public short readShort() {

		return (short) readLong();
	}

	public int readInt() {

		return (int) readLong();
	}

	public long readLong() {

		byte bits = readByte();
		boolean negative = (bits & 0b01000000) != 0;

		long value = bits & 0b00111111;
		long shift = 6;

		while ((bits & 0b10000000) != 0) {
			bits = readByte();
			value |= ((long) (bits & 0b01111111) << shift);
			shift += 7;
		}

		return negative? -value : value;
	}

	public String readString() {

		int length = readInt();

		if (length >= 0) {
			byte[] bytes = new byte[length];
			read(bytes);
			return Utf8Convering.fromUtf8(bytes);
		} else {
			return null;
		}
	}

	public Enum<?> readEnum(Class<Enum<?>> enumClass) {

		int ordinal = readInt();
		if (ordinal >= 0) {
			return enumClass.getEnumConstants()[ordinal];
		} else {
			return null;
		}
	}

	public void readFields(Object object) {

		try {
			Class<? extends Object> objectClass = object.getClass();
			for (Field field: objectClass.getDeclaredFields()) {
				field.setAccessible(true);

				if (field.getType() == byte.class) {
					field.setByte(object, readByte());
				} else if (field.getType() == short.class) {
					field.setShort(object, readShort());
				} else if (field.getType() == int.class) {
					field.setInt(object, readInt());
				} else if (field.getType() == long.class) {
					field.setLong(object, readLong());
				} else if (field.getType() == byte[].class) {
					field.set(object, readByteArray());
				} else if (field.getType() == String.class) {
					field.set(object, readString());
				} else if (field.getType().isEnum()) {
					Class<Enum<?>> enumClass = CastUtils.cast(field.getType());
					field.set(object, readEnum(enumClass));
				} else {
					throw new SofticarDeveloperException("Serialization of '%s' is not supported.", field.getType().getCanonicalName());
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException exception) {
			throw new SofticarException(exception);
		}
	}

	public <T> T readObject(Class<T> objectClass) {

		T object = ReflectionUtils.newInstance(objectClass);
		readFields(object);
		return object;
	}
}
