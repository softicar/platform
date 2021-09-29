package com.softicar.platform.common.io.classfile;

import com.softicar.platform.common.io.classfile.constant.ClassFileConstantPool;
import java.util.ArrayList;
import java.util.List;

public class ClassFileMethod {

	private final ClassFileConstantPool pool;
	private final int accessFlags;
	private final int nameIndex;
	private final int descriptorIndex;
	private final List<ClassFileAttribute> attributes;

	public ClassFileMethod(ClassFileConstantPool pool, ClassFileReader reader) {

		this.pool = pool;
		this.accessFlags = reader.readInt16();
		this.nameIndex = reader.readInt16();
		this.descriptorIndex = reader.readInt16();

		int count = reader.readInt16();
		this.attributes = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			attributes.add(new ClassFileAttribute(pool, reader));
		}
	}

	public int getAccessFlags() {

		return accessFlags;
	}

	public int getNameIndex() {

		return nameIndex;
	}

	public int getDescriptorIndex() {

		return descriptorIndex;
	}

	public List<ClassFileAttribute> getAttributes() {

		return attributes;
	}

	public String getName() {

		return pool.getConstantUtf8(nameIndex).getText();
	}

	public String getDecriptor() {

		return pool.getConstantUtf8(descriptorIndex).getText();
	}

	@Override
	public String toString() {

		return getName() + " " + accessFlags + " " + getDecriptor();
	}
}
