package com.softicar.platform.dom.elements.popup.configuration;

class DomPopupDisplayMode implements IDomPopupDisplayMode {

	private boolean header;
	private boolean maximized;
	private boolean compact;
	private DomPopupModalMode modalMode;
	private DomPopupChildClosingMode defaultChildClosingMode;

	public DomPopupDisplayMode() {

		this.header = true;
		this.maximized = false;
		this.compact = false;
		this.modalMode = DomPopupModalMode.NON_MODAL;
		this.defaultChildClosingMode = DomPopupChildClosingMode.AUTOMATIC_NONE;
	}

	public DomPopupDisplayMode setHeader(boolean header) {

		this.header = header;
		return this;
	}

	@Override
	public boolean hasHeader() {

		return header;
	}

	public DomPopupDisplayMode setMaximized(boolean maximized) {

		this.maximized = maximized;
		return this;
	}

	@Override
	public boolean isMaximized() {

		return maximized;
	}

	public DomPopupDisplayMode setCompact(boolean compact) {

		this.compact = compact;
		return this;
	}

	@Override
	public boolean isCompact() {

		return compact;
	}

	public DomPopupDisplayMode setModalMode(DomPopupModalMode modalMode) {

		this.modalMode = modalMode;
		return this;
	}

	@Override
	public DomPopupModalMode getModalMode() {

		return modalMode;
	}

	public DomPopupDisplayMode setDefaultChildClosingMode(DomPopupChildClosingMode defaultChildClosingMode) {

		this.defaultChildClosingMode = defaultChildClosingMode;
		return this;
	}

	@Override
	public DomPopupChildClosingMode getDefaultChildClosingMode() {

		return defaultChildClosingMode;
	}
}
