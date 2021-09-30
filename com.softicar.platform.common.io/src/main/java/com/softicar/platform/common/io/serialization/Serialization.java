package com.softicar.platform.common.io.serialization;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.StreamUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * This class provides static utility method for serialization and
 * deserialization of objects.
 * 
 * @author Oliver Richers
 */
public class Serialization {

	/**
	 * Serializes the given object into the specified {@link OutputStream}.
	 * <p>
	 * Note that the specified {@link OutputStream} will not be closed. This
	 * allows the output of consecutive objects.
	 * 
	 * @param object
	 *            the object to serialize
	 * @param outputStream
	 *            the output
	 */
	public static void serialize(Object object, OutputStream outputStream) {

		try {
			ObjectOutputStream output = new ObjectOutputStream(outputStream);
			output.writeObject(object);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	/**
	 * Serializes the given object into a byte array.
	 * 
	 * @param object
	 *            the object to serialize
	 * @return the binary serialization data
	 */
	public static byte[] serialize(Object object) {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		serialize(object, outputStream);
		StreamUtils.close(outputStream);

		return outputStream.toByteArray();
	}

	/**
	 * Deserializes an object from the given byte array.
	 * 
	 * @param data
	 *            the serialization data
	 * @return the deserialized object
	 */
	public static Object deserialize(byte[] data) {

		ByteArrayInputStream inputStream = new ByteArrayInputStream(data);

		try {
			ObjectInputStream input = new ObjectInputStream(inputStream);
			Object deserializedState = input.readObject();
			return deserializedState;
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		} catch (ClassNotFoundException exception) {
			throw new RuntimeException(exception);
		}
	}
}
