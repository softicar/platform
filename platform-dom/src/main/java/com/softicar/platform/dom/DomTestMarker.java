package com.softicar.platform.dom;

import com.softicar.platform.common.core.interfaces.ITestMarker;

public enum DomTestMarker implements ITestMarker {

	DAY_INPUT,

	MODAL_DIALOG_CONTENT,
	MODAL_DIALOG_FRAME,
	MODAL_DIALOG_POPUP,
	MODAL_ALERT_CLOSE_BUTTON,
	MODAL_CONFIRM_CANCEL_BUTTON,
	MODAL_CONFIRM_OKAY_BUTTON,
	MODAL_PROMPT_INPUT_ELEMENT,

	PAGEABLE_TABLE,
	PAGEABLE_TABLE_NAVIGATION,
	PAGEABLE_TABLE_NAVIGATION_EXPORT_BUTTON,
	PAGEABLE_TABLE_NAVIGATION_PAGE_NEXT_BUTTON,
	PAGEABLE_TABLE_NAVIGATION_PAGE_NUMBER_BUTTON,
	PAGEABLE_TABLE_NAVIGATION_PAGE_PREV_BUTTON,

	POPUP_BACKDROP,
	POPUP_FRAME,
	POPUP_FRAME_CLOSE_BUTTON,
	POPUP_FRAME_HEADER,
	POPUP_COMPOSITOR_DIALOG_BUTTON_CANCEL,
	POPUP_COMPOSITOR_DIALOG_BUTTON_CLOSE_ALL,
	POPUP_COMPOSITOR_DIALOG_BUTTON_CLOSE_PARENT_ONLY,

	SIMPLE_VALUE_SELECT,

	TIME_INPUT_HOURS_INPUT,
	TIME_INPUT_MINUTES_INPUT,
	TIME_INPUT_SECONDS_INPUT,
}
