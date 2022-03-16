package com.softicar.platform.common.core.java.access.level;

import org.objectweb.asm.Opcodes;

public abstract class AbstractJavaAccessLevelModifiable {

	public boolean isAbstract() {

		return testAccess(Opcodes.ACC_ABSTRACT);
	}

	public boolean isAnnotation() {

		return testAccess(Opcodes.ACC_ANNOTATION);
	}

	public boolean isEnum() {

		return testAccess(Opcodes.ACC_ENUM);
	}

	public boolean isFinal() {

		return testAccess(Opcodes.ACC_FINAL);
	}

	public boolean isInterface() {

		return testAccess(Opcodes.ACC_INTERFACE);
	}

	public boolean isPublic() {

		return testAccess(Opcodes.ACC_PUBLIC);
	}

	public boolean isStatic() {

		return testAccess(Opcodes.ACC_STATIC);
	}

	public boolean isSynthetic() {

		return testAccess(Opcodes.ACC_SYNTHETIC);
	}

	public abstract int getAccess();

	private boolean testAccess(int mask) {

		return (getAccess() & mask) != 0;
	}
}
