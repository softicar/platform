package com.softicar.platform.emf.matrix;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.DomFieldset;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.event.IDomEnterKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.styles.CssDisplay;
import com.softicar.platform.emf.EmfI18n;

/**
 * The standard implementation of an option UI element for an
 * {@link IEmfSettingMatrixView}.
 *
 * @author Alexander Schmidt
 */
public class EmfSettingMatrixViewOptionsDiv extends DomDiv implements IEmfSettingMatrixViewOptions {

	private final INullaryVoidFunction applier;
	private final DomTextInput rowFilterInput;
	private final DomTextInput columnFilterInput;
	private final DomCheckbox hideRowsWithDefaultValuesCheckbox;
	private final DomCheckbox hideColumnsWithDefaultValuesCheckbox;
	private final DomCheckbox flipRowsAndColumnsCheckbox;

	public EmfSettingMatrixViewOptionsDiv(IEmfSettingMatrixConfiguration<?, ?, ?> configuration, INullaryVoidFunction applier) {

		this.applier = applier;
		this.rowFilterInput = new TextFilterInput();
		this.rowFilterInput.addMarker(EmfSettingMatrixMarker.ROW_FILTER_INPUT);
		this.columnFilterInput = new TextFilterInput();
		this.columnFilterInput.addMarker(EmfSettingMatrixMarker.COLUMN_FILTER_INPUT);
		this.hideRowsWithDefaultValuesCheckbox = new DomCheckbox(false);
		this.hideColumnsWithDefaultValuesCheckbox = new DomCheckbox(false);
		this.flipRowsAndColumnsCheckbox = new DomCheckbox(false);
		this.flipRowsAndColumnsCheckbox.addMarker(EmfSettingMatrixMarker.FLIP_CHECKBOX);

		DomFieldset fieldset = appendChild(new OptionsFieldset());
		fieldset//
			.appendChild(new DomLabelGrid())
			.add(configuration.getRowTypeDisplayName().concat(" - ").concat(EmfI18n.FILTER), rowFilterInput)
			.add(configuration.getRowTypeDisplayName().concat(" - ").concat(EmfI18n.HIDE_DEFAULT_VALUES), hideRowsWithDefaultValuesCheckbox)
			.add(configuration.getColumnTypeDisplayName().concat(" - ").concat(EmfI18n.FILTER), columnFilterInput)
			.add(configuration.getColumnTypeDisplayName().concat(" - ").concat(EmfI18n.HIDE_DEFAULT_VALUES), hideColumnsWithDefaultValuesCheckbox)
			.add(EmfI18n.FLIP_ROWS_AND_COLUMNS, flipRowsAndColumnsCheckbox);
		fieldset
			.appendChild(
				new DomButton()//
					.setIcon(DomElementsImages.DIALOG_OKAY.getResource())
					.setLabel(EmfI18n.APPLY)
					.setClickCallback(this::applyOptions)
					.addMarker(EmfSettingMatrixMarker.APPLY_BUTTON));
	}

	@Override
	public String getRowFilterString() {

		return rowFilterInput.getValueTextTrimmed();
	}

	@Override
	public String getColumnFilterString() {

		return columnFilterInput.getValueTextTrimmed();
	}

	@Override
	public boolean isHideRowsWithDefaultValues() {

		return hideRowsWithDefaultValuesCheckbox.isChecked();
	}

	@Override
	public boolean isHideColumnsWithDefaultValues() {

		return hideColumnsWithDefaultValuesCheckbox.isChecked();
	}

	@Override
	public boolean isFlipRowsAndColumns() {

		return flipRowsAndColumnsCheckbox.isChecked();
	}

	private void applyOptions() {

		applier.apply();
	}

	private class OptionsFieldset extends DomFieldset {

		public OptionsFieldset() {

			super(EmfI18n.OPTIONS);
			setStyle(CssDisplay.INLINE);
		}
	}

	private class TextFilterInput extends DomTextInput implements IDomEnterKeyEventHandler {

		@Override
		public void handleEnterKey(IDomEvent event) {

			applyOptions();
		}
	}
}
