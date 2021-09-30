package com.softicar.platform.dom.elements.testing.node.iterable;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.node.IDomNode;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface IDomNodeIterable<N extends IDomNode> extends Iterable<N> {

	IDomTestEngine getEngine();

	// ------------------------------ element access ------------------------------ //

	DomNodeTester first();

	DomNodeTester last();

	default Stream<N> stream() {

		return StreamSupport.stream(spliterator(), false);
	}

	default List<DomNodeTester> toList() {

		return stream().map(node -> new DomNodeTester(getEngine(), node)).collect(Collectors.toList());
	}

	default <T> List<T> toList(Function<N, T> factory) {

		return stream().map(factory).collect(Collectors.toList());
	}

	default int size() {

		return (int) stream().count();
	}

	// ------------------------------ map and filter ------------------------------ //

	<Target extends IDomNode> IDomNodeIterable<Target> map(Function<N, Target> function);

	IDomNodeIterable<N> filter(Predicate<N> predicate);

	// ------------------------------ to type ------------------------------ //

	<SubType extends IDomNode> IDomNodeIterable<SubType> toType(Class<SubType> type);

	// ------------------------------ with type ------------------------------ //

	default <Type extends IDomNode> IDomNodeIterable<Type> withType(Class<Type> type) {

		return withInstanceOf(type).toType(type);
	}

	// ------------------------------ with interface ------------------------------ //

	default IDomNodeIterable<N> withInstanceOf(Class<?> theClass) {

		return filter(node -> theClass.isInstance(node));
	}

	// ------------------------------ with marker ------------------------------ //

	default IDomNodeIterable<N> withMarker(IStaticObject marker) {

		return filter(node -> node.hasMarker(marker));
	}

	// ------------------------------ with text ------------------------------ //

	IDomNodeIterable<N> withText(String text);

	default IDomNodeIterable<N> withText(IDisplayString text) {

		return withText(text.toString());
	}

	// ------------------------------ starting with text ------------------------------ //

	IDomNodeIterable<N> startingWithText(String text);

	default IDomNodeIterable<N> startingWithText(IDisplayString text) {

		return startingWithText(text.toString());
	}

	// ------------------------------ starting with tool-tip ------------------------------ //

	IDomNodeIterable<N> startingWithTooltip(String text);

	default IDomNodeIterable<N> startingWithTooltip(IDisplayString text) {

		return startingWithTooltip(text.toString());
	}

	// ------------------------------ assert none ------------------------------ //

	default void assertNone() {

		assertNone(null);
	}

	void assertNone(String message);

	// ------------------------------ assert one ------------------------------ //

	default DomNodeTester assertOne() {

		return assertOne((String) null);
	}

	default DomNodeTester assertOne(String message) {

		return assertOne(message, node -> new DomNodeTester(getEngine(), node));
	}

	default <T> T assertOne(Function<N, T> factory) {

		return assertOne(null, factory);
	}

	<T> T assertOne(String message, Function<N, T> factory);

	// ------------------------------ assert some ------------------------------ //

	default List<DomNodeTester> assertSome() {

		return assertSome((String) null);
	}

	default List<DomNodeTester> assertSome(String message) {

		return assertSome(message, node -> new DomNodeTester(getEngine(), node));
	}

	default <T> List<T> assertSome(Function<N, T> factory) {

		return assertSome(null, factory);
	}

	<T> List<T> assertSome(String message, Function<N, T> factory);

	// ------------------------------ assert size ------------------------------ //

	default List<DomNodeTester> assertSize(int size) {

		return assertSize(null, size);
	}

	default <T> List<T> assertSize(int size, Function<N, T> factory) {

		return assertSize(null, size, factory);
	}

	default List<DomNodeTester> assertSize(String message, int size) {

		return assertSize(message, size, node -> new DomNodeTester(getEngine(), node));
	}

	<T> List<T> assertSize(String message, int size, Function<N, T> factory);
}
