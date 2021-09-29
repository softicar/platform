package com.softicar.platform.emf.data.table.export.file.name;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.emf.data.table.export.util.TableExportLib;

public class TableExportDefaultFileNameSuffixCreator implements ITableExportFileNameSuffixCreator {

	@Override
	public TableExportFileNameWithOmittableSuffix createFileNameSuffix(String fileExtension, TableExportFileTimestampMode fileTimestampMode,
			boolean enableDeflateCompression) {

		StringBuilder suffix = new StringBuilder();

		if (fileTimestampMode == TableExportFileTimestampMode.CURRENT_TIME) {
			suffix.append("_" + DayTime.now().getTimeAsStringYYYYMMDD_HHMM());
		} else if (fileTimestampMode == TableExportFileTimestampMode.MOCKUP) {
			suffix.append("_<" + DomI18n.TIMESTAMP + ">");
		} else if (fileTimestampMode == TableExportFileTimestampMode.NONE) {
			// do nothing
		} else {
			throw new SofticarUnknownEnumConstantException(fileTimestampMode);
		}

		if (fileExtension != null) {
			fileExtension = fileExtension.trim();

			if (!fileExtension.isEmpty()) {
				suffix.append(".");
				suffix.append(fileExtension);
			}
		}

		String compressionSuffix = null;

		if (enableDeflateCompression) {
			compressionSuffix = "." + TableExportLib.DEFLATE_COMPRESSION_FILE_NAME_EXTENSION;
		}

		return new TableExportFileNameWithOmittableSuffix(suffix.toString(), compressionSuffix);
	}
}
