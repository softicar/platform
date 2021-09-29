package com.softicar.platform.common.core.java.classes.analyzer;

import com.softicar.platform.common.core.java.access.level.AbstractJavaAccessLevelModifiable;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.identifier.declaration.JavaIdentifierDeclaration;
import com.softicar.platform.common.core.java.identifier.key.JavaIdentifierKey;
import com.softicar.platform.common.core.java.method.reference.IJavaMethodReferenceFilter;
import com.softicar.platform.common.core.java.method.reference.JavaLambdaMethodReferenceFilter;
import com.softicar.platform.common.core.java.method.reference.JavaMethodReference;
import java.lang.invoke.LambdaMetafactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureReader;

/**
 * Represents a class that was analyzed by {@link JavaClassAnalyzer}.
 *
 * @author Oliver Richers
 */
public class AnalyzedJavaClass extends AbstractJavaAccessLevelModifiable {

	private static final String MAIN_METHOD_NAME = "main";
	private static final String MAIN_METHOD_DESCRIPTOR = "([Ljava/lang/String;)V";
	private final List<AnalyzedJavaClassAnnotation> annotations;
	private final Set<JavaClassName> interfaces;
	private final Set<JavaClassName> referencedClasses;
	private final Set<JavaMethodReference> referencedMethods;
	private final Map<JavaIdentifierKey, JavaIdentifierDeclaration> declaredFields;
	private final Map<JavaIdentifierKey, JavaIdentifierDeclaration> declaredMethods;
	private JavaClassName className;
	private JavaClassName superClass;
	private int access;
	private boolean hasMainMethod;
	private boolean hasJunitTests;

	public AnalyzedJavaClass() {

		this.annotations = new ArrayList<>();
		this.interfaces = new TreeSet<>();
		this.referencedClasses = new TreeSet<>();
		this.referencedMethods = new TreeSet<>();
		this.declaredFields = new TreeMap<>();
		this.declaredMethods = new TreeMap<>();
		this.className = null;
		this.superClass = null;
		this.access = 0;
		this.hasMainMethod = false;
		this.hasJunitTests = false;
	}

	@Override
	public int getAccess() {

		return access;
	}

	// ------------------------------ getter ------------------------------ //

	public List<AnalyzedJavaClassAnnotation> getAnnotations() {

		return Collections.unmodifiableList(annotations);
	}

	public Set<JavaClassName> getReferencedClasses() {

		return Collections.unmodifiableSet(referencedClasses);
	}

	/**
	 * Returns <b>all</b> referenced methods.
	 * <p>
	 * This includes methods of the analyzed class as well as dynamically
	 * generated lambda methods.
	 *
	 * @return all referenced methods
	 */
	public Set<JavaMethodReference> getReferencedMethods() {

		return Collections.unmodifiableSet(referencedMethods);
	}

	/**
	 * Returns methods referenced by the class.
	 * <p>
	 * For example, the class {@link JavaLambdaMethodReferenceFilter} can be
	 * used to filter out generated lambda methods and {@link LambdaMetafactory}
	 * methods.
	 *
	 * @param filter
	 *            the filter to use (never null)
	 * @return the filtered referenced methods (never null)
	 */
	public Set<JavaMethodReference> getReferencedMethods(IJavaMethodReferenceFilter filter) {

		return referencedMethods//
			.stream()
			.filter(Objects.requireNonNull(filter))
			.collect(Collectors.toCollection(TreeSet::new));
	}

	public Collection<JavaIdentifierDeclaration> getDeclaredFields() {

		return Collections.unmodifiableCollection(declaredFields.values());
	}

	public Optional<JavaIdentifierDeclaration> findDeclaredField(JavaIdentifierKey fieldKey) {

		return Optional.ofNullable(declaredFields.get(fieldKey));
	}

	public Collection<JavaIdentifierDeclaration> getDeclaredMethods() {

		return Collections.unmodifiableCollection(declaredMethods.values());
	}

	public Optional<JavaIdentifierDeclaration> findDeclaredMethod(JavaIdentifierKey methodKey) {

		return Optional.ofNullable(declaredMethods.get(methodKey));
	}

	public JavaClassName getClassName() {

		return className;
	}

	public JavaClassName getSuperClass() {

		return superClass;
	}

	public Set<JavaClassName> getInterfaces() {

		return Collections.unmodifiableSet(interfaces);
	}

	public boolean hasMainMethod() {

		return hasMainMethod;
	}

	public boolean hasJunitTests() {

		return hasJunitTests;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		builder.append("Analyzed class:\n");
		builder.append("\tname: " + className + "\n");
		builder.append("\thas main method: " + hasMainMethod + "\n");
		builder.append("\thas junit tests: " + hasJunitTests + "\n");
		builder.append("Referenced classes:\n");
		referencedClasses.forEach(it -> builder.append("\t" + it + "\n"));
		builder.append("Referenced methods:\n");
		referencedMethods.forEach(it -> builder.append("\t" + it + "\n"));
		return builder.toString();
	}

	// ------------------------------ auxiliary methods ------------------------------ //

	public boolean is(Class<?> testClass) {

		return is(new JavaClassName(testClass));
	}

	public boolean is(JavaClassName testClassName) {

		return className.equals(testClassName);
	}

	public Class<?> loadClass() throws ClassNotFoundException {

		return Class.forName(className.getName());
	}

	public boolean hasAnnotation(Class<?> annotationClass) {

		return hasAnnotation(new JavaClassName(annotationClass));
	}

	public boolean hasAnnotation(JavaClassName className) {

		return annotations.stream().anyMatch(annotation -> annotation.is(className));
	}

	public boolean implementsInterface(Class<?> interfaceClass) {

		return implementsInterface(new JavaClassName(interfaceClass));
	}

	public boolean implementsInterface(JavaClassName className) {

		return interfaces.contains(className);
	}

	// ------------------------------ mutating interface ------------------------------ //

	void addAnnotation(String annotationDescriptor) {

		this.annotations.add(new AnalyzedJavaClassAnnotation(annotationDescriptor));
	}

	void addReferencedClass(JavaClassName referencedClass) {

		this.referencedClasses.add(referencedClass);
	}

	void addReferencedClass(String referencedClass) {

		addReferencedClass(new JavaClassName(referencedClass));
	}

	void addReferencedClasses(String[] referencedClasses) {

		addReferencedClasses(Arrays.asList(referencedClasses));
	}

	void addReferencedClasses(Collection<String> referencedClasses) {

		referencedClasses.forEach(this::addReferencedClass);
	}

	void addReferencedMethod(String owner, String name, String descriptor) {

		this.referencedMethods.add(new JavaMethodReference(new JavaClassName(owner), new JavaIdentifierKey(name, descriptor)));
	}

	void addDeclaredField(JavaIdentifierDeclaration fieldDeclaration) {

		this.declaredFields.put(fieldDeclaration.getKey(), fieldDeclaration);
	}

	void addDeclaredMethod(JavaIdentifierDeclaration methodDeclaration) {

		this.declaredMethods.put(methodDeclaration.getKey(), methodDeclaration);

		if (methodDeclaration.getName().equals(MAIN_METHOD_NAME) && methodDeclaration.getDescriptor().equals(MAIN_METHOD_DESCRIPTOR)) {
			setHasMainMethod(true);
		}
	}

	void addReferencesFromHandle(Handle handle) {

		addReferencedClass(handle.getOwner());
		addReferencesFromSignature(handle.getDesc());

		switch (handle.getTag()) {
		case Opcodes.H_INVOKEINTERFACE:
		case Opcodes.H_INVOKESPECIAL:
		case Opcodes.H_INVOKESTATIC:
		case Opcodes.H_INVOKEVIRTUAL:
		case Opcodes.H_NEWINVOKESPECIAL:
			addReferencedMethod(handle.getOwner(), handle.getName(), handle.getDesc());
		}
	}

	void addReferencesFromSignature(String signature) {

		if (signature != null) {
			new SignatureReader(signature).accept(new InternalSignatureAnalyzer(this));
		}
	}

	void addReferencesFromType(Type type) {

		if (type.getSort() == Type.OBJECT) {
			addReferencesFromSignature(type.getDescriptor());
		}
	}

	void setClassName(String className) {

		this.className = new JavaClassName(className);
	}

	void setAccess(int access) {

		this.access = access;
	}

	void setSuperClass(JavaClassName superClass) {

		this.superClass = superClass;
		addReferencedClass(superClass);
	}

	void addInterface(JavaClassName interfaceClass) {

		this.interfaces.add(interfaceClass);
		addReferencedClass(interfaceClass);
	}

	void setHasMainMethod(boolean hasMainMethod) {

		this.hasMainMethod = hasMainMethod;
	}

	void setHasJunitTests(boolean hasJunitTests) {

		this.hasJunitTests = hasJunitTests;
	}
}
