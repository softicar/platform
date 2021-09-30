package com.softicar.platform.common.core.logging;

import com.softicar.platform.common.core.singleton.Singleton;
import java.util.Objects;

/**
 * This {@link Singleton} holds the current {@link ILogOutput} to be used by
 * {@link Log}.
 *
 * @author Oliver Richers
 */
public class CurrentLogOuput {

	private static final Singleton<ILogOutput> OUTPUT = new Singleton<>(() -> System.err::println);

	/**
	 * Returns the currently used {@link ILogOutput}.
	 *
	 * @return the current {@link ILogOutput} (never <i>null</i>)
	 */
	public static ILogOutput get() {

		return OUTPUT.get();
	}

	/**
	 * Sets the new {@link ILogOutput} to use.
	 *
	 * @param output
	 *            the new {@link ILogOutput} to use (never <i>null</i>)
	 */
	public static void set(ILogOutput output) {

		OUTPUT.set(Objects.requireNonNull(output));
	}

	/**
	 * Resets the output to default.
	 */
	public static void reset() {

		OUTPUT.reset();
	}
}
