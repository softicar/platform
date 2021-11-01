package com.softicar.platform.emf.data.table.column.title;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.core.i18n.LanguageScope;
import com.softicar.platform.common.string.hash.Hash;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EmfDataTableColumnTitlesHashFactory {

	public String createHash(List<IDisplayString> columnTitles) {

		Objects.requireNonNull(columnTitles);
		try (LanguageScope scope = new LanguageScope(LanguageEnum.ENGLISH)) {
			List<String> translatedColumnTitles = columnTitles.stream().map(IDisplayString::toString).collect(Collectors.toList());
			return Hash.SHA1.getHashStringLC(String.join(";", translatedColumnTitles));
		}
	}
}
