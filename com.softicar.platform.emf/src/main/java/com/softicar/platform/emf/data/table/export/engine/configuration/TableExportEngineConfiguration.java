package com.softicar.platform.emf.data.table.export.engine.configuration;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomI18n;
import java.util.Objects;

public class TableExportEngineConfiguration {

	private static final IDisplayString DEFAULT_ENGINE_DISPLAY_NAME = DomI18n.UNTITLED_EXPORT_ENGINE;
	private static final String DEFAULT_FILE_NAME_EXTENSION = "ext";

	private IDisplayString engineDisplayString;
	private String fileNameExtension;
	private boolean compressed;
	private boolean appendTimestamp;

	public TableExportEngineConfiguration() {

		this.engineDisplayString = DEFAULT_ENGINE_DISPLAY_NAME;
		this.fileNameExtension = DEFAULT_FILE_NAME_EXTENSION;
		this.compressed = false;
		this.appendTimestamp = true;
	}

	/**
	 * @return The {@link IDisplayString} under which the instantiated engine
	 *         should go.
	 */
	public IDisplayString getEngineDisplayString() {

		return engineDisplayString;
	}

	/**
	 * @return The file name extension of the files the instantiated engine
	 *         creates.
	 */
	public String getFileNameExtension() {

		return fileNameExtension;
	}

	/**
	 * @return Boolean, whether or not deflate compression is enabled by default
	 *         for files created by the instantiated engine.
	 */
	public boolean isCompressed() {

		return compressed;
	}

	/**
	 * @return Boolean, whether or not a timestamp is added by default to the
	 *         names of files created by the instantiated engine.
	 */
	public boolean isAppendTimestamp() {

		return appendTimestamp;
	}

	public TableExportEngineConfiguration setEngineDisplayString(IDisplayString engineDisplayString) {

		this.engineDisplayString = Objects.requireNonNull(engineDisplayString);
		return this;
	}

	public TableExportEngineConfiguration setFileNameExtension(String fileNameExtension) {

		this.fileNameExtension = Objects.requireNonNull(fileNameExtension);
		return this;
	}

	public TableExportEngineConfiguration setCompressed(boolean compressed) {

		this.compressed = compressed;
		return this;
	}

	public TableExportEngineConfiguration setAppendTimestamp(boolean appendTimestamp) {

		this.appendTimestamp = appendTimestamp;
		return this;
	}
}
