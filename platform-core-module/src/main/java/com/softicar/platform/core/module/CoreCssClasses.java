package com.softicar.platform.core.module;

import com.softicar.platform.dom.style.CssClass;
import com.softicar.platform.dom.style.ICssClass;

public interface CoreCssClasses {

	ICssClass FILE_DROP_AREA_DIV = new CssClass("FileDropAreaDiv", CoreCssFiles.FILE_DROP_AREA_STYLE);
	ICssClass FILE_DROP_AREA_DIV_HIDDEN = new CssClass("FileDropAreaDivHidden", CoreCssFiles.FILE_DROP_AREA_STYLE);
	ICssClass FILE_DROP_AREA_DIV_INPUT = new CssClass("FileDropAreaDivInput", CoreCssFiles.FILE_DROP_AREA_STYLE);
	ICssClass FILE_DROP_AREA_DIV_CLOSE_BUTTON = new CssClass("FileDropAreaDivCloseButton", CoreCssFiles.FILE_DROP_AREA_STYLE);

	ICssClass MAINTENANCE_WINDOWS_INFO_ELEMENT = new CssClass("MaintenanceWindowsInfoElement", CoreCssFiles.MAINTENANCE_WINDOW_STYLE);

	ICssClass PAGE_CONTENT_DIV = new CssClass("PageContentDiv", CoreCssFiles.PAGE_STYLE);
	ICssClass PAGE_HEADER_DIV = new CssClass("PageHeaderDiv", CoreCssFiles.PAGE_STYLE);
	ICssClass PAGE_HEADER_PATH_DIV = new CssClass("PageHeaderPathDiv", CoreCssFiles.PAGE_STYLE);
	ICssClass PAGE_HEADER_PAGE_NAME = new CssClass("PageHeaderPageName", CoreCssFiles.PAGE_STYLE);
	ICssClass PAGE_HEADER_MAINTENANCE_NOTIFICATION_AREA = new CssClass("PageHeaderMaintenanceNotificationArea", CoreCssFiles.PAGE_STYLE);
	ICssClass PAGE_HEADER_NAVIGATION_TOGGLE_BUTTON = new CssClass("PageHeaderNavigationToggleButton", CoreCssFiles.PAGE_STYLE);
	ICssClass PAGE_HEADER_SEPARATOR = new CssClass("PageHeaderSeparator", CoreCssFiles.PAGE_STYLE);
	ICssClass PAGE_HEADER_USER_POPOVER = new CssClass("PageHeaderUserPopover", CoreCssFiles.PAGE_STYLE);
	ICssClass PAGE_NAVIGATION_BADGE = new CssClass("PageNavigationBadge", CoreCssFiles.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_NAVIGATION_COLLAPSED = new CssClass("PageNavigationCollapsed", CoreCssFiles.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_NAVIGATION_DIV = new CssClass("PageNavigationDiv", CoreCssFiles.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_NAVIGATION_FOLDER_DIV = new CssClass("PageNavigationFolderDiv", CoreCssFiles.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_NAVIGATION_FOLDER_CHEVRON = new CssClass("PageNavigationFolderChevron", CoreCssFiles.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_NAVIGATION_FOLDER_CONTENT_DIV = new CssClass("PageNavigationFolderContentDiv", CoreCssFiles.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_NAVIGATION_FOLDER_TITLE_DIV = new CssClass("PageNavigationFolderTitleDiv", CoreCssFiles.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_NAVIGATION_LINK_DIV = new CssClass("PageNavigationLinkDiv", CoreCssFiles.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_NAVIGATION_LINK_TITLE = new CssClass("PageNavigationLinkTitle", CoreCssFiles.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_NAVIGATION_LOGO_DIV = new CssClass("PageNavigationLogoDiv", CoreCssFiles.PAGE_NAVIGATION_STYLE);
	ICssClass PAGE_SERVICE_LOGIN_DIV = new CssClass("PageServiceLoginDiv", CoreCssFiles.PAGE_STYLE);
	ICssClass PAGE_SERVICE_LOGIN_ERROR_DIV = new CssClass("PageServiceLoginErrorDiv", CoreCssFiles.PAGE_STYLE);
	ICssClass PAGE_SERVICE_LOGIN_MAINTENANCE_DIV = new CssClass("PageServiceLoginMaintenanceDiv", CoreCssFiles.PAGE_STYLE);

	ICssClass PAGE_SERVICE_PASSWORD_RESET_DIV = new CssClass("PageServicePasswordResetDiv", CoreCssFiles.PAGE_STYLE);
	ICssClass PAGE_SERVICE_PASSWORD_RESET_ERROR_DIV = new CssClass("PageServicePasswordResetErrorDiv", CoreCssFiles.PAGE_STYLE);

	ICssClass PROGRAM_EXECUTION_BOOLEAN_DISPLAY = new CssClass("ProgramExecutionBooleanDisplay", CoreCssFiles.PROGRAM_EXECUTION_STYLE);
	ICssClass PROGRAM_EXECUTION_OUTPUT_AREA = new CssClass("ProgramExecutionOutputArea", CoreCssFiles.PROGRAM_EXECUTION_STYLE);
	ICssClass PROGRAM_EXECUTION_STATUS_DISPLAY = new CssClass("ProgramExecutionStatusDisplay", CoreCssFiles.PROGRAM_EXECUTION_STYLE);

	ICssClass STORED_FILE_TEXT_DISPLAY = new CssClass("StoredFileTextDisplay", CoreCssFiles.STORED_FILE_STYLE);
	ICssClass STORED_FILE_UPLOAD_TABLE_DIV = new CssClass("StoredFileUploadTableDiv", CoreCssFiles.STORED_FILE_STYLE);

	ICssClass SYSTEM_EVENT_SEVERITY_DISPLAY = new CssClass("SystemEventSeverityDisplay", CoreCssFiles.SYSTEM_EVENT_STYLE);

	ICssClass TEST_SYSTEM = new CssClass("TestSystem", CoreCssFiles.PAGE_STYLE);

	ICssClass USER_PASSWORD_QUALITY_CRITERION_ROW = new CssClass("UserPasswordQualityCriterionRow", CoreCssFiles.USER_PASSWORD_QUALITY_STYLE);
	ICssClass USER_PASSWORD_QUALITY_CRITERION_VALUE = new CssClass("UserPasswordQualityCriterionValue", CoreCssFiles.USER_PASSWORD_QUALITY_STYLE);
	ICssClass USER_PASSWORD_QUALITY_CRITERION_FULFILLED = new CssClass("UserPasswordQualityCriterionFulfilled", CoreCssFiles.USER_PASSWORD_QUALITY_STYLE);
	ICssClass USER_PASSWORD_QUALITY_CRITERION_POLICY_FULFILLED =
			new CssClass("UserPasswordQualityCriterionPolicyFulfilled", CoreCssFiles.USER_PASSWORD_QUALITY_STYLE);
	ICssClass USER_PASSWORD_QUALITY_CRITERION_NOT_FULFILLED =
			new CssClass("UserPasswordQualityCriterionNotFulfilled", CoreCssFiles.USER_PASSWORD_QUALITY_STYLE);
}
