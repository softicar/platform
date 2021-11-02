package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.common.string.trim.MultiLineStringTrimmer;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Objects;

public class EmfStringAttribute<R extends IEmfTableRow<R, ?>> extends EmfFieldAttribute<R, String> {

	private boolean autoTrimming;
	private boolean multiline;
	private boolean passwordMode;
	private int maximumLength;

	public EmfStringAttribute(IDbField<R, String> field) {

		super(field);

		this.autoTrimming = true;
		this.multiline = false;
		this.passwordMode = false;
		this.maximumLength = 0;

		addValidator(new EmfStringAttributeValidator<>(this));
		setDisplayFactory(this::createDisplay);
		setInputFactory(this::createInput);
	}

	/**
	 * Toggles the auto-trimming mode.
	 * <p>
	 * If auto-trimming is enabled, setting or getting a value of this attribute
	 * will automatically trim the text. Auto-trimming is enabled by default.
	 *
	 * @param autoTrimming
	 *            <i>true</i> to enable auto-trimming, <i>false</i> to disable
	 *            auto-trimming
	 * @return this attribute
	 */
	public EmfStringAttribute<R> setAutoTrimming(boolean autoTrimming) {

		this.autoTrimming = autoTrimming;
		return this;
	}

	/**
	 * Returns whether auto-trimming is enabled or not.
	 *
	 * @return <i>true</i> if auto-trimming is enabled, <i>false</i> if
	 *         auto-trimming is disabled
	 */
	public boolean isAutoTrimming() {

		return autoTrimming;
	}

	/**
	 * Toggles the multi-line mode.
	 * <p>
	 * If multi-line mode is enabled, this attribute will use a multi-line input
	 * and display. Multi-line mode is disabled by default.
	 *
	 * @param multiline
	 *            <i>true</i> to enable multi-line mode, <i>false</i> to disable
	 *            multi-line mode
	 * @return this attribute
	 */
	public EmfStringAttribute<R> setMultiline(boolean multiline) {

		this.multiline = multiline;
		return this;
	}

	/**
	 * Returns whether multi-line mode is enabled or not.
	 *
	 * @return <i>true</i> if multi-line mode is enabled, <i>false</i> if
	 *         multi-line mode is disabled
	 */
	public boolean isMultiline() {

		return multiline;
	}

	public EmfStringAttribute<R> setPasswordMode(boolean passwordMode) {

		this.passwordMode = passwordMode;
		return this;
	}

	public boolean isPasswordMode() {

		return passwordMode;
	}

	/**
	 * Defines the maximum text length for this attribute in unicode characters.
	 * <p>
	 * By default, the maximum length is zero, effectively disabling the length
	 * check, that is, the text length is unlimited.
	 *
	 * @param maximumLength
	 *            the maximum text length in characters; 0 for unlimited
	 * @return this attribute
	 */
	public EmfStringAttribute<R> setMaximumLength(int maximumLength) {

		this.maximumLength = maximumLength;
		return this;
	}

	/**
	 * Returns the maximum text length for this attribute in unicode characters.
	 *
	 * @return the maximum text length in characters; 0 for unlimited
	 */
	public int getMaximumLength() {

		return maximumLength;
	}

	// ------------------------------ overwrite ------------------------------ //

	@Override
	public void setValue(R tableRow, String value) {

		super.setValue(tableRow, getNormalizedValue(value));
	}

	@Override
	public String getValue(R tableRow) {

		return getNormalizedValue(super.getValue(tableRow));
	}

	@Override
	protected boolean isUserInputMandatory(R tableRow) {

		// FIXME this method should not be necessary (i80947)
		return super.isUserInputMandatory(tableRow) && !Objects.equals(field.getDefault(), "");
	}

	// ------------------------------ private ------------------------------ //

	private IDomElement createDisplay(String value) {

		if (passwordMode) {
			return new EmfPasswordDisplay(value);
		} else if (multiline) {
			return new EmfMultilineStringDisplay(value);
		} else {
			return new EmfStringDisplay(value);
		}
	}

	private IEmfInput<String> createInput() {

		if (passwordMode) {
			return new EmfPasswordInput();
		} else if (multiline) {
			return new EmfMultilineStringInput();
		} else {
			return new EmfStringInput();
		}
	}

	private String getNormalizedValue(String value) {

		if (value == null) {
			return null;
		} else if (value.isEmpty() && field.isNullable()) {
			return null;
		} else if (autoTrimming) {
			if (multiline) {
				return new MultiLineStringTrimmer(value).trim();
			} else {
				return value.trim();
			}
		} else {
			return value;
		}
	}
}
