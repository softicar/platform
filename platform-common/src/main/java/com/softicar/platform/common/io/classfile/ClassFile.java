package com.softicar.platform.common.io.classfile;

import com.softicar.platform.common.io.classfile.constant.ClassFileConstantPool;
import com.softicar.platform.common.string.Imploder;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ClassFile {

	private int magic;
	private int minorVersion;
	private int majorVersion;
	private ClassFileConstantPool constantPool;
	private final int accessFlags;
	private final String thisClass;
	private final String superClass;
	private List<String> interfaces;
	private List<ClassFileField> fields;
	private List<ClassFileMethod> methods;
	private List<ClassFileAttribute> attributes;

	public ClassFile(InputStream inputStream) {

		ClassFileReader reader = new ClassFileReader(inputStream);
		readMagic(reader);
		readVersion(reader);
		readConstantPool(reader);
		this.accessFlags = reader.readInt16();
		this.thisClass = readClassName(reader);
		this.superClass = readClassName(reader);
		readInterfaces(reader);
		readFields(reader);
		readMethods(reader);
		readAttributes(reader);
	}

	public int getMagic() {

		return magic;
	}

	public int getMinorVersion() {

		return minorVersion;
	}

	public int getMajorVersion() {

		return majorVersion;
	}

	public int getAccessFlags() {

		return accessFlags;
	}

	public String getThisClass() {

		return thisClass;
	}

	public String getSuperClass() {

		return superClass;
	}

	public List<String> getInterfaces() {

		return interfaces;
	}

	public List<ClassFileField> getFields() {

		return fields;
	}

	public List<ClassFileMethod> getMethods() {

		return methods;
	}

	public List<ClassFileAttribute> getAttributes() {

		return attributes;
	}

	@Override
	public String toString() {

		return new StringBuilder()//
			.append("magic: " + Integer.toHexString(magic) + "\n")
			.append("version: " + majorVersion + "." + minorVersion + "\n")
			.append("constant pool: " + getConstantPool().size() + "\n")
			.append("access flags: " + accessFlags + "\n")
			.append("this class:\n\t" + thisClass + "\n")
			.append("super class:\n\t" + superClass + "\n")
			.append("interfaces:\n" + Imploder.implode(interfaces, "\n", "\t", "") + "\n")
			.append("fields:\n" + Imploder.implode(fields, "\n", "\t", "") + "\n")
			.append("methods:\n" + Imploder.implode(methods, "\n", "\t", "") + "\n")
			.append("attributes:\n" + Imploder.implode(attributes, "\n", "\t", "") + "\n")
			.toString();
	}

	// ------------------------------ read methods ------------------------------ //

	private void readMagic(ClassFileReader reader) {

		this.magic = reader.readInt32();
	}

	private void readVersion(ClassFileReader reader) {

		this.minorVersion = reader.readInt16();
		this.majorVersion = reader.readInt16();
	}

	private void readConstantPool(ClassFileReader reader) {

		constantPool = new ClassFileConstantPool(reader);
	}

	private void readInterfaces(ClassFileReader reader) {

		int count = reader.readInt16();
		this.interfaces = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			interfaces.add(readClassName(reader));
		}
	}

	private void readFields(ClassFileReader reader) {

		int count = reader.readInt16();
		this.fields = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			fields.add(new ClassFileField(getConstantPool(), reader));
		}
	}

	private void readMethods(ClassFileReader reader) {

		int count = reader.readInt16();
		this.methods = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			methods.add(new ClassFileMethod(getConstantPool(), reader));
		}
	}

	private void readAttributes(ClassFileReader reader) {

		int count = reader.readInt16();
		this.attributes = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			attributes.add(new ClassFileAttribute(getConstantPool(), reader));
		}
	}

	private String readClassName(ClassFileReader reader) {

		int classIndex = reader.readInt16();
		int nameIndex = getConstantPool().getConstantClass(classIndex).getNameIndex();
		return getConstantPool().getConstantUtf8(nameIndex).getText();
	}

	public ClassFileConstantPool getConstantPool() {

		return constantPool;
	}

}
