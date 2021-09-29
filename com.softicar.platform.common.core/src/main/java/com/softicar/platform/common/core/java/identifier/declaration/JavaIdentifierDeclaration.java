package com.softicar.platform.common.core.java.identifier.declaration;

import com.softicar.platform.common.core.java.access.level.AbstractJavaAccessLevelModifiable;
import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.identifier.key.JavaIdentifierKey;
import java.util.Comparator;

public class JavaIdentifierDeclaration extends AbstractJavaAccessLevelModifiable implements Comparable<JavaIdentifierDeclaration> {

	private final AnalyzedJavaClass owner;
	private final JavaIdentifierKey key;
	private final int access;

	public JavaIdentifierDeclaration(AnalyzedJavaClass owner, JavaIdentifierKey key, int access) {

		this.owner = owner;
		this.key = key;
		this.access = access;
	}

	public AnalyzedJavaClass getOwner() {

		return owner;
	}

	public JavaIdentifierKey getKey() {

		return key;
	}

	public String getName() {

		return key.getName();
	}

	public String getDescriptor() {

		return key.getDescriptor();
	}

	@Override
	public int getAccess() {

		return access;
	}

	@Override
	public int compareTo(JavaIdentifierDeclaration other) {

		return Comparator//
			.comparing((JavaIdentifierDeclaration declaration) -> declaration.getOwner().getClassName())
			.thenComparing(JavaIdentifierDeclaration::getKey)
			.thenComparing(JavaIdentifierDeclaration::getAccess)
			.compare(this, other);
	}

	@Override
	public String toString() {

		return String.format("%s.%s (opcodes: %s)", owner.getClassName(), key, access);
	}
}
