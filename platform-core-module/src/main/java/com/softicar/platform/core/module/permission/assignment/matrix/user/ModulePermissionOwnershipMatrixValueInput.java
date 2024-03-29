package com.softicar.platform.core.module.permission.assignment.matrix.user;

import com.softicar.platform.core.module.permission.ModulePermissionStateContainer;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomColorEnum;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.styles.CssFontWeight;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.field.bool.EmfBooleanInput;
import com.softicar.platform.emf.matrix.IEmfSettingMatrixModelEntryInput;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

class ModulePermissionOwnershipMatrixValueInput extends DomDiv implements IEmfSettingMatrixModelEntryInput<ModulePermissionStateContainer> {

	private boolean disabled;
	private Optional<ModulePermissionStateContainer> currentValue;
	private final Supplier<Optional<ModulePermissionStateContainer>> originalValueSupplier;

	public ModulePermissionOwnershipMatrixValueInput(Supplier<Optional<ModulePermissionStateContainer>> originalValueSupplier) {

		this.originalValueSupplier = originalValueSupplier;
		this.disabled = false;
		this.currentValue = Optional.empty();
	}

	@Override
	public Optional<ModulePermissionStateContainer> getValue() {

		return currentValue;
	}

	@Override
	public void setValue(ModulePermissionStateContainer value) {

		this.currentValue = Optional.ofNullable(value);
		refresh();
	}

	@Override
	public void setInputDisabled(boolean disabled) {

		this.disabled = disabled;
		refresh();
	}

	private void refresh() {

		removeChildren();
		if (currentValue.isPresent()) {
			Collection<IEmfModulePermission<?>> permissions = currentValue.get().getPermissions();
			if (!permissions.isEmpty()) {
				for (IEmfModulePermission<?> permission: permissions) {
					boolean checked = currentValue.get().isActive(permission);
					DomDiv container = appendChild(new DomDiv());
					StyleUpdater styleUpdater = new StyleUpdater(permission, container);
					DomCheckbox input = container
						.appendChild(
							new EmfBooleanInput(checked)//
								.setLabel(permission.getTitle()));
					input.setDisabled(disabled);
					input.addChangeCallback(() -> new ChangeHandler(permission, styleUpdater).handle(input.isChecked()));
					styleUpdater.update(checked);
				}
			} else {
				appendText(EmfI18n.NO_PERMISSIONS.encloseInParentheses());
			}
		}
	}

	private class StyleUpdater {

		private final IEmfModulePermission<?> permission;
		private final IDomElement container;

		public StyleUpdater(IEmfModulePermission<?> permission, IDomElement container) {

			this.permission = permission;
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
				.map(value -> value.isActive(permission) != checked)
				.orElse(false);
		}
	}

	private class ChangeHandler {

		private final IEmfModulePermission<?> permission;
		private final StyleUpdater styleUpdater;

		public ChangeHandler(IEmfModulePermission<?> permission, StyleUpdater styleUpdater) {

			this.permission = permission;
			this.styleUpdater = styleUpdater;
		}

		public void handle(boolean checked) {

			setValue(checked);
			styleUpdater.update(checked);
		}

		private void setValue(boolean checked) {

			currentValue.get().put(permission, checked);
		}
	}
}
