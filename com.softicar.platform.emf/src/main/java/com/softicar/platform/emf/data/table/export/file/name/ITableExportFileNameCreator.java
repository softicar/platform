package com.softicar.platform.emf.data.table.export.file.name;

public interface ITableExportFileNameCreator {

	TableExportFileNameWithOmittableSuffix createFileName(String fileNamePrefix, String fileExtension, TableExportFileTimestampMode fileTimestampMode,
			boolean enableDeflateCompression);
}
