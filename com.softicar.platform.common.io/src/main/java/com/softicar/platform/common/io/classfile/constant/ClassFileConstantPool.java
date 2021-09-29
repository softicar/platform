package com.softicar.platform.common.io.classfile.constant;

import com.softicar.platform.common.io.classfile.ClassFileReader;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstant;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstantClass;
import com.softicar.platform.common.io.classfile.constants.ClassFileConstantUtf8;

public class ClassFileConstantPool {

	private final int size;
	private final ClassFileConstant[] constants;

	public ClassFileConstantPool(ClassFileReader reader) {

		this.size = reader.readInt16();
		this.constants = new ClassFileConstant[size];

		for (int i = 1; i < size; i++) {
			int typeId = reader.readInt8();
			ClassFileConstantType type = ClassFileConstantType.getById(typeId);
			constants[i] = type.parse(reader);

			if (type == ClassFileConstantType.DOUBLE || type == ClassFileConstantType.LONG) {
				// according to the standard, 8-byte constants take too indexes
				// from the standard: `In retrospect, making 8-byte constants take two constant pool entries was a poor
				// choice. `
				i++;
			}
		}
	}

	public int size() {

		return size;
	}

	public ClassFileConstant getConstant(int index) {

		return constants[index];
	}

	public ClassFileConstantClass getConstantClass(int index) {

		return (ClassFileConstantClass) constants[index];
	}

	public ClassFileConstantUtf8 getConstantUtf8(int index) {

		return (ClassFileConstantUtf8) constants[index];
	}
}
