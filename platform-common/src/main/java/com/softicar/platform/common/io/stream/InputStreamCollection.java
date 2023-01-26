package com.softicar.platform.common.io.stream;

import com.softicar.platform.common.core.exception.ExceptionsCollector;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

/**
 * Manages the safe opening and closing of a {@link Collection} of
 * {@link InputStream} objects.
 * <p>
 * This class implements {@link AutoCloseable} and guarantees the proper closing
 * of all successfully opened {@link InputStream} instances, as long as the
 * {@link #close} method is called, which should be done by a try-with-resource
 * block.
 *
 * @author Oliver Richers
 */
public class InputStreamCollection implements AutoCloseable {

	private final Collection<InputStream> streams;

	public InputStreamCollection() {

		this.streams = new ArrayList<>();
	}

	/**
	 * Uses the given {@link Supplier} to obtain the {@link InputStream} and
	 * adds it to the internal collection of all open {@link InputStream}
	 * instances.
	 *
	 * @param supplier
	 *            the {@link Supplier} for the {@link InputStream} (never
	 *            <i>null</i>)
	 * @return the opened {@link InputStream} (never <i>null</i>)
	 */
	public InputStream add(Supplier<InputStream> supplier) {

		var stream = supplier.get();
		streams.add(stream);
		return stream;
	}

	/**
	 * Calls {@link #add(Supplier)} for all given {@link Supplier} instances.
	 *
	 * @param suppliers
	 *            a {@link Collection} of {@link Supplier} instances (never
	 *            <i>null</i>)
	 */
	public void addAll(Collection<? extends Supplier<InputStream>> suppliers) {

		suppliers.forEach(this::add);
	}

	/**
	 * Returns all opened {@link InputStream} instances.
	 *
	 * @return all {@link InputStream} instances (never <i>null</i>)
	 */
	public Collection<InputStream> getAllInputStreams() {

		return Collections.unmodifiableCollection(streams);
	}

	/**
	 * Closes all successfully obtained {@link InputStream} instances.
	 */
	@Override
	public void close() {

		ExceptionsCollector collector = new ExceptionsCollector();
		streams.forEach(stream -> {
			try {
				stream.close();
			} catch (Exception exception) {
				collector.add(exception);
			}
		});
		collector.throwExceptionIfNotEmpty();
	}
}
