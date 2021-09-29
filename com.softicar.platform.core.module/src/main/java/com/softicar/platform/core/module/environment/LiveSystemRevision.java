package com.softicar.platform.core.module.environment;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.io.reader.BufferedReaderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Optional;
import java.util.Properties;

public class LiveSystemRevision {

	private static final String BUILD_PROPERTIES_FILEPATH = "/release.properties";
	private static final LiveSystemRevision CURRENT_REVISION = new LiveSystemRevision();
	private String name = null;

	/**
	 * Returns an object describing the current revision of the project.
	 *
	 * @return the project revision, never null
	 */
	public static LiveSystemRevision getCurrentRevision() {

		return CURRENT_REVISION;
	}

	/**
	 * Returns an Optional with the name of the current revision, or an empty
	 * Optional if the revision name could not be determined.
	 *
	 * @return an Optional with the name of the current revision, or an empty
	 *         Optional
	 */
	public Optional<String> getName() {

		return Optional.ofNullable(name);
	}

	private LiveSystemRevision() {

		try (InputStream inputStream = LiveSystemRevision.class.getResourceAsStream(BUILD_PROPERTIES_FILEPATH)) {
			if (inputStream != null) {
				this.name = parseRevision(inputStream);
			}
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private static String parseRevision(InputStream inputStream) {

		try (Reader reader = BufferedReaderFactory.readUtf8(inputStream)) {
			Properties properties = new Properties();
			properties.load(reader);
			return properties.getProperty("revision.id");
		} catch (Exception exception) {
			DevNull.swallow(exception);
			return null;
		}
	}
}
