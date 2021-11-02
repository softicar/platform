package com.softicar.platform.dom.elements.testing.node.iterable;

import com.softicar.platform.common.container.filter.FilteringIterable;
import com.softicar.platform.common.container.iterable.CastingIterable;
import com.softicar.platform.common.container.iterable.MappingIterable;
import com.softicar.platform.common.container.iterable.concat.ConcatIterable;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.node.DomNodeAssertionError;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.node.IDomNode;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DomNodeIterable<N extends IDomNode> implements IDomNodeIterable<N> {

	private final IDomTestEngine engine;
	private final Iterable<N> iterable;

	public DomNodeIterable(IDomTestEngine engine, Iterable<N> iterable) {

		this.engine = engine;
		this.iterable = iterable;
	}

	public static DomNodeIterable<IDomNode> createWithRoot(IDomTestEngine engine, IDomNode root) {

		return new DomNodeIterable<>(engine, new ConcatIterable<>(Collections.singletonList(root), new DomNodeRecursiveIterable(root)));
	}

	public static DomNodeIterable<IDomNode> createWithoutRoot(IDomTestEngine engine, IDomNode root) {

		return new DomNodeIterable<>(engine, new DomNodeRecursiveIterable(root));
	}

	@Override
	public IDomTestEngine getEngine() {

		return engine;
	}

	// ------------------------------ element access ------------------------------ //

	@Override
	public Iterator<N> iterator() {

		return iterable.iterator();
	}

	@Override
	public DomNodeTester first() {

		if (iterator().hasNext()) {
			return new DomNodeTester(getEngine(), iterator().next());
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public DomNodeTester last() {

		Iterator<N> iterator = iterator();
		if (iterator.hasNext()) {
			N node = null;
			while (iterator.hasNext()) {
				node = iterator.next();
			}
			return new DomNodeTester(getEngine(), node);
		} else {
			throw new NoSuchElementException();
		}
	}

	// ------------------------------ map and filter ------------------------------ //

	@Override
	public <Target extends IDomNode> IDomNodeIterable<Target> map(Function<N, Target> function) {

		return new DomNodeIterable<>(engine, new MappingIterable<>(iterable, function));
	}

	@Override
	public IDomNodeIterable<N> filter(Predicate<N> predicate) {

		return new DomNodeIterable<>(engine, new FilteringIterable<>(this, predicate));
	}

	@Override
	public <Target extends IDomNode> IDomNodeIterable<Target> toType(Class<Target> type) {

		return new DomNodeIterable<>(engine, new CastingIterable<>(this, type));
	}

	@Override
	public IDomNodeIterable<N> withText(String text) {

		return filter(node -> new DomNodeTester(engine, node).getAllTextInDocument("").equalsIgnoreCase(text));
	}

	@Override
	public IDomNodeIterable<N> startingWithText(String text) {

		return filter(node -> new DomNodeTester(engine, node).getAllTextInDocument("").toLowerCase().startsWith(text.toLowerCase()));
	}

	@Override
	public IDomNodeIterable<N> startingWithTooltip(String tooltip) {

		return filter(node -> {
			return Optional//
				.ofNullable(node.getAttribute("title"))
				.map(attribute -> attribute.getValue())
				.map(title -> title.startsWith(tooltip))
				.orElse(false);
		});
	}

	// ------------------------------ assert ------------------------------ //

	@Override
	public void assertNone(String message) {

		if (iterator().hasNext()) {
			throw new DomNodeAssertionError(//
				message,
				"Expected to find no nodes but found %s nodes of the following classes:\n%s",
				size(),
				Imploder.implode(getNodeClassNames(), "\n"));
		}
	}

	@Override
	public <T> T assertOne(String message, Function<N, T> factory) {

		Iterator<N> iterator = iterator();
		if (iterator.hasNext()) {
			N node = iterator.next();
			if (iterator.hasNext()) {
				throw new DomNodeAssertionError(//
					message,
					"Expected only one node but found %s nodes of the following classes:\n%s",
					size(),
					Imploder.implode(getNodeClassNames(), "\n"));
			}
			return factory.apply(node);
		} else {
			throw new DomNodeAssertionError(message, "Expected one node but did not find any.");
		}
	}

	@Override
	public <T> List<T> assertSome(String message, Function<N, T> factory) {

		if (size() <= 0) {
			throw new DomNodeAssertionError(message, "Expected to find some nodes but found none.");
		} else {
			return toList(factory);
		}
	}

	@Override
	public <T> List<T> assertSize(String message, int size, Function<N, T> factory) {

		int actualSize = size();
		if (actualSize != size) {
			if (actualSize <= 0) {
				throw new DomNodeAssertionError(message, "Expected to find %s node(s) but found none.", size);
			} else {
				throw new DomNodeAssertionError(//
					message,
					"Expected to find %s node(s) but found %s nodes of the following classes:\n%s",
					size,
					actualSize,
					Imploder.implode(getNodeClassNames(), "\n"));
			}
		} else {
			return toList(factory);
		}
	}

	// ------------------------------ private ------------------------------ //

	private Collection<String> getNodeClassNames() {

		return stream()//
			.map(it -> it.getClass().getCanonicalName())
			.collect(Collectors.toCollection(TreeSet::new));
	}
}
