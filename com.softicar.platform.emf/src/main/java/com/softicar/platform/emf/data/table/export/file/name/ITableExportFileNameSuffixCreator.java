package com.softicar.platform.emf.data.table.export.file.name;

public interface ITableExportFileNameSuffixCreator {

	TableExportFileNameWithOmittableSuffix createFileNameSuffix(String fileExtension, TableExportFileTimestampMode fileTimestampMode, boolean enableDeflateCompression);
}
