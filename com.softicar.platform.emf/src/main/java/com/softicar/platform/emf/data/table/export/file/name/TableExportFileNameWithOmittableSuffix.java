package com.softicar.platform.emf.data.table.export.file.name;

public class TableExportFileNameWithOmittableSuffix {

	private final String fileName;
	private final String omittableSuffix;

	public TableExportFileNameWithOmittableSuffix(String fileName, String omittableSuffix) {

		this.fileName = fileName;
		this.omittableSuffix = omittableSuffix;
	}

	public String getFileName(boolean omitCompressionSuffix) {

		String suffix = "";

		if (!omitCompressionSuffix) {
			if (this.omittableSuffix != null) {
				String trimmedSuffix = this.omittableSuffix.trim();

				if (!trimmedSuffix.isEmpty()) {
					suffix = trimmedSuffix;
				}
			}
		}

		return fileName + suffix;
	}

	public String getOmittableSuffix() {

		return omittableSuffix;
	}
}
