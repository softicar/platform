package com.softicar.platform.core.module;

import com.softicar.platform.dom.style.CssClass;
import com.softicar.platform.dom.style.ICssClass;

public interface CoreCssClasses {

	ICssClass MAINTENANCE_WINDOWS_INFO_ELEMENT = new CssClass("MaintenanceWindowsInfoElement", CoreCssFiles.MAINTENANCE_WINDOW_STYLE);

	ICssClass STORED_FILE_PREVIEW_IMAGE = new CssClass("StoredFilePreviewImage", CoreCssFiles.STORED_FILE_STYLE);
	ICssClass STORED_FILE_PREVIEW_IMAGE_DIV = new CssClass("StoredFilePreviewImageDiv", CoreCssFiles.STORED_FILE_STYLE);

	ICssClass STORED_FILE_TEXT_DISPLAY = new CssClass("StoredFileTextDisplay", CoreCssFiles.STORED_FILE_STYLE);
	ICssClass STORED_FILE_UPLOAD_DIV = new CssClass("StoredFileUploadDiv", CoreCssFiles.STORED_FILE_STYLE);
	ICssClass STORED_FILE_UPLOAD_INPUT = new CssClass("StoredFileUploadInput", CoreCssFiles.STORED_FILE_STYLE);
	ICssClass STORED_FILE_UPLOAD_TABLE_DIV = new CssClass("StoredFileUploadTableDiv", CoreCssFiles.STORED_FILE_STYLE);

	ICssClass SYSTEM_EVENT_SEVERITY_DISPLAY = new CssClass("SystemEventSeverityDisplay", CoreCssFiles.SYSTEM_EVENT_STYLE);

	ICssClass USER_PASSWORD_QUALITY_CRITERION_ROW = new CssClass("UserPasswordQualityCriterionRow", CoreCssFiles.USER_PASSWORD_QUALITY_STYLE);
	ICssClass USER_PASSWORD_QUALITY_CRITERION_VALUE = new CssClass("UserPasswordQualityCriterionValue", CoreCssFiles.USER_PASSWORD_QUALITY_STYLE);
	ICssClass USER_PASSWORD_QUALITY_CRITERION_FULFILLED = new CssClass("UserPasswordQualityCriterionFulfilled", CoreCssFiles.USER_PASSWORD_QUALITY_STYLE);
	ICssClass USER_PASSWORD_QUALITY_CRITERION_POLICY_FULFILLED =
			new CssClass("UserPasswordQualityCriterionPolicyFulfilled", CoreCssFiles.USER_PASSWORD_QUALITY_STYLE);
	ICssClass USER_PASSWORD_QUALITY_CRITERION_NOT_FULFILLED =
			new CssClass("UserPasswordQualityCriterionNotFulfilled", CoreCssFiles.USER_PASSWORD_QUALITY_STYLE);
}
