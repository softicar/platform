package com.softicar.platform.emf.matrix;

import com.softicar.platform.db.core.transaction.DbTransaction;

/**
 * The controller of an {@link EmfSettingMatrixDiv}.
 *
 * @author Alexander Schmidt
 */
public class EmfSettingMatrixController<R, C, V> {

	private final IEmfSettingMatrixPersistence<R, C, V> persistence;
	private final IEmfSettingMatrixModel<R, C, V> model;
	private final IEmfSettingMatrixView view;

	public EmfSettingMatrixController(IEmfSettingMatrixPersistence<R, C, V> persistence, IEmfSettingMatrixModel<R, C, V> model, IEmfSettingMatrixView view) {

		this.persistence = persistence;
		this.model = model;
		this.view = view;
	}

	public EmfSettingMatrixController<R, C, V> updateView() {

		view.applyFromModel();
		return this;
	}

	public void loadModel() {

		model.getOriginalData().clearAll();
		try (DbTransaction transaction = new DbTransaction()) {
			persistence.load(model.getOriginalData());
			model.applyOriginalToCurrent();
			transaction.commit();
		}
	}

	public void saveModel() {

		view.applyToModel();

		try (DbTransaction transaction = new DbTransaction()) {
			persistence.save(model.calculateDeltaData());
			model.applyCurrentToOriginal();
			transaction.commit();
		}
	}
}
