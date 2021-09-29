package com.softicar.platform.emf.data.table.export.file.name;

import com.softicar.platform.dom.DomI18n;

public class TableExportDefaultFileNameCreator implements ITableExportFileNameCreator {

	private final ITableExportFileNameSuffixCreator fileNameSuffixCreator;

	public TableExportDefaultFileNameCreator() {

		this.fileNameSuffixCreator = new TableExportDefaultFileNameSuffixCreator();
	}

	@Override
	public TableExportFileNameWithOmittableSuffix createFileName(String fileNamePrefix, String fileExtension, TableExportFileTimestampMode fileTimestampMode,
			boolean enableDeflateCompression) {

		StringBuilder exportFileNameBuilder = new StringBuilder();

		if (fileNamePrefix != null) {
			exportFileNameBuilder.append(fileNamePrefix);
		} else {
			exportFileNameBuilder.append(DomI18n.EXPORT);
		}

		TableExportFileNameWithOmittableSuffix suffix = fileNameSuffixCreator.createFileNameSuffix(fileExtension, fileTimestampMode, enableDeflateCompression);
		exportFileNameBuilder.append(suffix.getFileName(true));

		String exportFileName = exportFileNameBuilder.toString().replaceAll("\\s", "_");

		return new TableExportFileNameWithOmittableSuffix(exportFileName, suffix.getOmittableSuffix());
	}
}
