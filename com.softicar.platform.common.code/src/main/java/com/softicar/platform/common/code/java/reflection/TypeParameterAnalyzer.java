package com.softicar.platform.common.code.java.reflection;

import com.softicar.platform.common.string.Padding;
import com.softicar.platform.common.string.Substring;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Analyzes the generic type parameters of a given {@link Type} or
 * {@link Field}.
 *
 * @author Alexander Schmidt
 */
public class TypeParameterAnalyzer {

	private final Type type;

	/**
	 * Constructs a {@link TypeParameterAnalyzer}.
	 *
	 * @param type
	 *            the {@link Type} to analyze (never <i>null</i>)
	 */
	public TypeParameterAnalyzer(Type type) {

		this.type = Objects.requireNonNull(type);
	}

	/**
	 * Constructs a {@link TypeParameterAnalyzer} for the declared generic
	 * {@link Type} of the given {@link Field}.
	 *
	 * @param field
	 *            the {@link Field} to analyze (never <i>null</i>)
	 */
	public TypeParameterAnalyzer(Field field) {

		this(field.getGenericType());
	}

	/**
	 * Determines the generic type parameter of the given {@link Type} or
	 * {@link Field}, as a {@link String} of canonical class names.
	 * <p>
	 * Examples:
	 * <ul>
	 * <li>For type "{@code Long}" (or any other non-generic type), this method
	 * returns an empty {@link String}.</li>
	 * <li>For type "{@code Optional<String>}", this method returns
	 * "{@code java.lang.String}".</li>
	 * <li>For type "{@code Optional<Supplier<String>>}", this method returns
	 * "{@code java.util.function.Supplier<java.lang.String>}".</li>
	 * </ul>
	 *
	 * @return the generic type parameter, as a {@link String} (never
	 *         <i>null</i>)
	 */
	public String getTypeParameterName() {

		String genericTypeName = type.getTypeName();
		return Substring.between(genericTypeName, "<", ">");
	}

	/**
	 * Determines whether the generic type parameter of the given {@link Type}
	 * or {@link Field} corresponds to the given {@link Class}.
	 * <p>
	 * Examples:
	 * <ul>
	 * <li>For type "{@code Long}" and given class {@link Long}, this method
	 * returns <i>false</i>.</li>
	 * <li>For type "{@code Optional<Long>}" and given class {@link Long}, this
	 * method returns <i>true</i>.</li>
	 * </ul>
	 *
	 * @param expectedTypeParameter
	 *            the expected type parameter {@link Class} (never <i>null</i>)
	 * @return <i>true</i> if the given {@link Class} is used as a type
	 *         parameter; <i>false</i> otherwise
	 */
	public boolean hasExpectedTypeParameter(Class<?> expectedTypeParameter) {

		return hasExpectedTypeParameter(expectedTypeParameter, new Class<?>[0]);
	}

	/**
	 * Determines whether the generic type parameter of the given {@link Type}
	 * or {@link Field} corresponds to the given {@link Class}, and further
	 * nested classes.
	 * <p>
	 * Examples:
	 * <ul>
	 * <li>For type "{@code Optional<Long>}" and given class {@link Long}, this
	 * method returns <i>true</i>.</li>
	 * <li>For type "{@code Optional<Supplier<Long>>}" and given class
	 * {@link Long}, this method returns <i>false</i>.</li>
	 * <li>For type "{@code Optional<Supplier<Long>>}" and given classes
	 * {@link Supplier} and {@link Long}, this method returns <i>true</i>.</li>
	 * </ul>
	 *
	 * @param expectedTypeParameter
	 *            the expected type parameter {@link Class} (never <i>null</i>)
	 * @param expextedNestedTypeParameters
	 *            further nested type parameter {@link Class}es
	 * @return <i>true</i> if the given {@link Class}es are used as a type
	 *         parameters; <i>false</i> otherwise
	 */
	public boolean hasExpectedTypeParameter(Class<?> expectedTypeParameter, Class<?>...expextedNestedTypeParameters) {

		return getTypeParameterName().equals(createExpectedTypeName(expectedTypeParameter, expextedNestedTypeParameters));
	}

	private String createExpectedTypeName(Class<?> expectedTypeParameter, Class<?>[] expextedNestedTypeParameters) {

		StringBuilder typeName = new StringBuilder();
		typeName.append(expectedTypeParameter.getCanonicalName());
		for (Class<?> type: expextedNestedTypeParameters) {
			typeName.append("<");
			typeName.append(type.getCanonicalName());
		}
		typeName.append(Padding.generate('>', expextedNestedTypeParameters.length));
		return typeName.toString();
	}
}
