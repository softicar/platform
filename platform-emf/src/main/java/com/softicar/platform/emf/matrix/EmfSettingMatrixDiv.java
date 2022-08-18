package com.softicar.platform.emf.matrix;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.EmfTestMarker;

/**
 * A matrix based input element for all kinds of settings.
 *
 * @author Alexander Schmidt
 */
public class EmfSettingMatrixDiv<R, C, V> extends DomDiv {

	private final EmfSettingMatrixController<R, C, V> controller;
	private final boolean editable;

	public EmfSettingMatrixDiv(IEmfSettingMatrixConfiguration<R, C, V> configuration, IEmfSettingMatrixPersistence<R, C, V> persistence) {

		EmfSettingMatrixViewOptionsDiv optionsDiv = new EmfSettingMatrixViewOptionsDiv(configuration, this::updateView);
		IEmfSettingMatrixModel<R, C, V> model = new EmfSettingMatrixModel<>(configuration);
		IEmfSettingMatrixView view = new EmfSettingMatrixView<>(configuration, model, optionsDiv);
		this.controller = new EmfSettingMatrixController<>(persistence, model, view);
		this.editable = configuration.isEditable();

		appendChild(optionsDiv);
		appendChildren(new SaveButton(), new ReloadButton());
		appendChild(view);
		appendChildren(new SaveButton(), new ReloadButton());

		load();
	}

	private void load() {

		controller.loadModel();
		updateView();
	}

	private void saveAndReload() {

		controller.saveModel();
		controller.loadModel();
		updateView();
	}

	private void updateView() {

		controller.updateView();
	}

	private class SaveButton extends DomButton {

		public SaveButton() {

			setIcon(EmfImages.ENTITY_SAVE.getResource());
			setLabel(EmfI18n.SAVE);
			setEnabled(editable);
			setClickCallback(() -> saveAndReload());
			addMarker(EmfTestMarker.SETTING_MATRIX_SAVE_BUTTON);
		}
	}

	private class ReloadButton extends DomButton {

		public ReloadButton() {

			setIcon(EmfImages.REFRESH.getResource());
			setLabel(EmfI18n.RELOAD);
			setEnabled(editable);
			setClickCallback(() -> load());
			addMarker(EmfTestMarker.SETTING_MATRIX_RELOAD_BUTTON);
		}
	}
}
