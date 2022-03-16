package com.softicar.platform.common.io.classfile;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.io.classfile.constant.ClassFileConstantPool;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstantUtf8;
import java.io.UnsupportedEncodingException;
import java.util.TreeSet;

public class ClassFileAttribute {

	private static final int BYTE = 'B';
	private static final int CHAR = 'C';
	private static final int DOUBLE = 'D';
	private static final int FLOAT = 'F';
	private static final int INT = 'I';
	private static final int LONG = 'J';
	private static final int SHORT = 'S';
	private static final int BOOLEAN = 'Z';
	private static final int STRING = 's';
	private static final int ENUM = 'e';
	private static final int CLASS = 'c';
	private static final int ANNOTATION = '@';
	private static final int ARRAY = '[';

	private final ClassFileConstantPool pool;
	private final int nameIndex;
	private final int length;
	private final byte[] bytes;

	public ClassFileAttribute(ClassFileConstantPool pool, ClassFileReader reader) {

		this.pool = pool;
		this.nameIndex = reader.readInt16();
		this.length = reader.readInt32();
		this.bytes = reader.readBytes(length);
	}

	public int getNameIndex() {

		return nameIndex;
	}

	public int getLength() {

		return length;
	}

	public byte[] getBytes() {

		return bytes;
	}

	public String getName() {

		return pool.getConstantUtf8(nameIndex).getText();
	}

	@Override
	public String toString() {

		return getName();
	}

	public TreeSet<String> parseAnnotationsFrom() {

		TreeSet<String> annotations = new TreeSet<>();
		byte[] classFileAttributeData = getBytes();
		int annunciator = 0;
		int numberOfAnnotations = readUnsignedShort(classFileAttributeData, annunciator);
		annunciator = skipTwoByte(annunciator);
		int searchedAnnotations = 0;

		while (searchedAnnotations < numberOfAnnotations) {
			annunciator = readAnnotation(classFileAttributeData, annunciator, pool, annotations);
			searchedAnnotations++;
		}
		return annotations;
	}

	private int readAnnotation(byte[] classFileAttributeData, int annunciator, ClassFileConstantPool pool, TreeSet<String> annotations) {

		int typeIndex = readUnsignedShort(classFileAttributeData, annunciator);
		annunciator = skipTwoByte(annunciator);
		ClassFileConstantUtf8 annotationConstant = pool.getConstantUtf8(typeIndex);
		String annotationName = annotationConstant.getText(); // e.g. "Lcom/example/Foo;"
		if (annotationName == null) {
			try {
				annotationName = new String(annotationConstant.getBytes(), "UTF8");
			} catch (UnsupportedEncodingException exception) {
				throw new SofticarDeveloperException(exception);
			}
		}

		int numberOfElementValuePairs = readUnsignedShort(classFileAttributeData, annunciator);
		annunciator = skipTwoByte(annunciator);
		for (int i = 0; i < numberOfElementValuePairs; ++i) {
			annunciator = skipTwoByte(annunciator); // skip element name
			annunciator = skipElementValuePairs(classFileAttributeData, annunciator, pool, annotations);
		}

		int annotationNameLength = annotationName.length();
		annotations.add(annotationName.substring(1, annotationNameLength - 1).replace("/", "."));
		return annunciator;
	}

	private int skipElementValuePairs(byte[] classFileAttributeData, int annunciator, ClassFileConstantPool pool, TreeSet<String> annotations) {

		final int tag = classFileAttributeData[annunciator] & 0xFF;
		annunciator++; // skip tag

		switch (tag) {
		case BYTE:
		case CHAR:
		case DOUBLE:
		case FLOAT:
		case INT:
		case LONG:
		case SHORT:
		case BOOLEAN:
		case STRING:
		case CLASS:
			return skipTwoByte(annunciator);
		case ENUM:
			return skipFourByte(annunciator);
		case ANNOTATION:
			return readAnnotation(classFileAttributeData, annunciator, pool, annotations);
		case ARRAY:
			int numberOfElements = readUnsignedShort(classFileAttributeData, annunciator);
			annunciator = skipTwoByte(annunciator);
			for (int i = 0; i < numberOfElements; ++i) {
				annunciator = skipElementValuePairs(classFileAttributeData, annunciator, pool, annotations);
			}
			return annunciator;
		default:
			throw new ClassFormatError("Not a valid annotation element type tag: 0x" + Integer.toHexString(tag));
		}
	}

	private int skipFourByte(int annunciator) {

		return annunciator = annunciator + 4;
	}

	private int skipTwoByte(int annunciator) {

		return annunciator = annunciator + 2;
	}

	private int readUnsignedShort(byte[] input, int annunciator) {

		return input[annunciator] << 8 & 0xFF00 | input[annunciator + 1] & 0xFF;
	}

}
