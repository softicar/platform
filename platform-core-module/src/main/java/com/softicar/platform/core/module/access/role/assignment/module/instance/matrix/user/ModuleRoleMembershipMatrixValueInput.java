package com.softicar.platform.core.module.access.role.assignment.module.instance.matrix.user;

import com.softicar.platform.core.module.access.role.EmfModuleRoleStateContainer;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomColorEnum;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.styles.CssFontWeight;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.matrix.IEmfSettingMatrixModelEntryInput;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

class ModuleRoleMembershipMatrixValueInput extends DomDiv implements IEmfSettingMatrixModelEntryInput<EmfModuleRoleStateContainer> {

	private boolean enabled;
	private Optional<EmfModuleRoleStateContainer> currentValue;
	private final Supplier<Optional<EmfModuleRoleStateContainer>> originalValueSupplier;

	public ModuleRoleMembershipMatrixValueInput(Supplier<Optional<EmfModuleRoleStateContainer>> originalValueSupplier) {

		this.originalValueSupplier = originalValueSupplier;
		this.enabled = true;
		this.currentValue = Optional.empty();
	}

	@Override
	public Optional<EmfModuleRoleStateContainer> getValue() {

		return currentValue;
	}

	@Override
	public void setValue(EmfModuleRoleStateContainer value) {

		this.currentValue = Optional.ofNullable(value);
		refresh();
	}

	@Override
	public void setInputEnabled(boolean enabled) {

		this.enabled = enabled;
		refresh();
	}

	private void refresh() {

		removeChildren();
		if (currentValue.isPresent()) {
			Collection<IEmfModuleRole<?>> roles = currentValue.get().getRoles();
			if (!roles.isEmpty()) {
				for (IEmfModuleRole<?> role: roles) {
					boolean checked = currentValue.get().isActive(role);
					DomDiv container = appendChild(new DomDiv());
					StyleUpdater styleUpdater = new StyleUpdater(role, container);
					container
						.appendChild(
							new DomCheckbox()//
								.setChecked(checked)
								.setLabel(role.getTitle())
								.setEnabled(enabled)
								.setChangeCallback(new ChangeHandler(role, styleUpdater)::handle));
					styleUpdater.update(checked);
				}
			} else {
				appendText(EmfI18n.NO_ROLES.encloseInParentheses());
			}
		}
	}

	private class StyleUpdater {

		private final IEmfModuleRole<?> role;
		private final IDomElement container;

		public StyleUpdater(IEmfModuleRole<?> role, IDomElement container) {

			this.role = role;
			this.container = container;
		}

		public void update(boolean checked) {

			setHighlighting(checked);
		}

		private void setHighlighting(boolean checked) {

			if (differsFromOriginalValue(checked)) {
				container.setColor(DomColorEnum.RED);
				container.setStyle(CssFontWeight.BOLD);
			} else {
				container.unsetStyle(CssStyle.COLOR);
				container.unsetStyle(CssStyle.FONT_WEIGHT);
			}
		}

		private boolean differsFromOriginalValue(boolean checked) {

			return originalValueSupplier//
				.get()
				.map(value -> value.isActive(role) != checked)
				.orElse(false);
		}
	}

	private class ChangeHandler {

		private final IEmfModuleRole<?> role;
		private final StyleUpdater styleUpdater;

		public ChangeHandler(IEmfModuleRole<?> role, StyleUpdater styleUpdater) {

			this.role = role;
			this.styleUpdater = styleUpdater;
		}

		public void handle(boolean checked) {

			setValue(checked);
			styleUpdater.update(checked);
		}

		private void setValue(boolean checked) {

			currentValue.get().put(role, checked);
		}
	}
}
