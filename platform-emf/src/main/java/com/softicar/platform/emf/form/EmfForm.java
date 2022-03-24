package com.softicar.platform.emf.form;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.Consumers;
import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusEvent;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusListener;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.authorizer.EmfAccessPermissionException;
import com.softicar.platform.emf.form.error.EmfFormAccessDeniedDiv;
import com.softicar.platform.emf.form.indicator.EmfFormIndicatorRow;
import com.softicar.platform.emf.form.tab.EmfFormTabBar;
import com.softicar.platform.emf.form.tab.factory.IEmfFormTabConfiguration;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.IEmfValidator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

public class EmfForm<R extends IEmfTableRow<R, ?>> extends DomDiv implements IEmfForm<R>, IDomRefreshBusListener {

	private final IEmfFormFrame<R> frame;
	private final R tableRow;
	private final IEmfFormTabConfiguration<R> tabConfiguration;
	private final Collection<IEmfValidator<R>> additionalValidators;
	private EmfFormTabBar<R> tabBar;
	private EmfFormIndicatorRow<R> indicatorRow;
	private EmfFormBody<R> body;
	private boolean closed;
	private Consumer<R> callbackAfterCreation;
	private Consumer<EmfFormMode> modeChangeCallback;
	private boolean directEditingEnabled;
	private boolean initialized;

	public EmfForm(IEmfFormFrame<R> frame, R tableRow) {

		this.frame = Objects.requireNonNull(frame);
		this.tableRow = Objects.requireNonNull(tableRow);
		this.tabConfiguration = tableRow.table().getEmfTableConfiguration().getFormTabConfiguration();
		this.additionalValidators = new ArrayList<>();
		this.tabBar = null;
		this.indicatorRow = null;
		this.body = null;
		this.closed = false;
		this.callbackAfterCreation = Consumers.noOperation();
		this.modeChangeCallback = Consumers.noOperation();
		this.directEditingEnabled = false;
		this.initialized = false;
		addCssClass(EmfCssClasses.EMF_FORM);
	}

	@Override
	public IEmfFormFrame<R> getFrame() {

		return frame;
	}

	@Override
	public R getTableRow() {

		return tableRow;
	}

	@Override
	public Collection<IEmfValidator<R>> getAdditionalValidators() {

		return additionalValidators;
	}

	@Override
	public void refresh(IDomRefreshBusEvent event) {

		if (event.isChanged(tableRow)) {
			peekAndRefresh();
		}
	}

	@Override
	public void invalidateCachedData(IDomRefreshBusEvent event) {

		if (event.isChanged(tableRow)) {
			tableRow//
				.getAttributeOwners()
				.forEach(it -> it.invalidate());
		}
	}

	@Override
	public EmfForm<R> peekAndRefresh() {

		if (!closed && ensureEntityIsVisible()) {
			if (initialized) {
				appendTabBarIfNecessary();
				refreshIndicatorRowAndBodyAndFrame();
			} else {
				initialize();
			}
		}
		return this;
	}

	public void closeFrame() {

		frame.closeFrame();
		this.closed = true;
	}

	public void focusFrame() {

		frame.focusFrame();
	}

	// ------------------------------ behavioral configuration ------------------------------ //

	@Override
	public void setCallbackAfterCreation(Consumer<R> callbackAfterCreation) {

		this.callbackAfterCreation = Objects.requireNonNull(callbackAfterCreation);
	}

	protected Consumer<R> getCallbackAfterCreation() {

		return callbackAfterCreation;
	}

	@Override
	public EmfForm<R> addAdditionalValidator(IEmfValidator<R> validator) {

		additionalValidators.add(Objects.requireNonNull(validator));
		return this;
	}

	@Override
	public void setDirectEditing(boolean enabled) {

		this.directEditingEnabled = enabled;
	}

	protected boolean isDirectEditingEnabled() {

		return directEditingEnabled;
	}

	public void setModeChangeCallback(Consumer<EmfFormMode> modeChangeCallback) {

		this.modeChangeCallback = Objects.requireNonNull(modeChangeCallback);
	}

	@Override
	public void handleModeChange(EmfFormMode mode) {

		modeChangeCallback.accept(mode);
	}

	// ------------------------------ refresh and initialize ------------------------------ //

	private void initialize() {

		boolean showTabBar = isShowTabBar();

		this.indicatorRow = new EmfFormIndicatorRow<>(tableRow);
		this.body = new EmfFormBody<>(this);
		this.tabBar = showTabBar? new EmfFormTabBar<>(this, tabConfiguration, body) : null;
		appendChild(indicatorRow);
		appendChild(showTabBar? tabBar : body);
		refreshFormTitle();

		this.initialized = true;
	}

	private void refreshIndicatorRowAndBodyAndFrame() {

		indicatorRow.refresh();
		body.refresh();
		refreshFormTitle();
	}

	private void refreshFormTitle() {

		frame.setTitle(tableRow.table().getTitle(), getEntityAsDisplayString());
	}

	private IDisplayString getEntityAsDisplayString() {

		return tableRow.impermanent()? IDisplayString.EMPTY : tableRow.toDisplay();
	}

	// ------------------------------ tab bar ------------------------------ //

	private void appendTabBarIfNecessary() {

		if (tabBar == null && isShowTabBar()) {
			this.body.disappend();
			this.tabBar = appendChild(new EmfFormTabBar<>(this, tabConfiguration, body));
		}
	}

	private boolean isShowTabBar() {

		return !tableRow.impermanent() && tabConfiguration.hasVisibleTabs(tableRow);
	}

	// ------------------------------ entity visibility ------------------------------ //

	private boolean ensureEntityIsVisible() {

		if (!tableRow.impermanent()) {
			try {
				tableRow.table().getAuthorizer().assertIsVisible(tableRow, CurrentBasicUser.get());
			} catch (EmfAccessPermissionException exception) {
				removeChildrenAndAppendExceptionDisplay(exception);
				return false;
			}
		}
		return true;
	}

	private void removeChildrenAndAppendExceptionDisplay(EmfAccessPermissionException exception) {

		removeChildren();
		appendChild(new EmfFormAccessDeniedDiv<>(this, exception));
		frame.setTitle(tableRow.table().getTitle(), EmfI18n.ERROR);
	}
}
