package com.softicar.platform.core.module.permission.assignment.matrix.user;

// FIXME This commented code shall NOT be removed - it needs to be repaired.
class ModulePermissionOwnershipMatrixConfiguration {
//
//	private final boolean editable;
//	private final ModuleInstanceCache moduleInstanceCache;
//
//	public ModulePermissionOwnershipMatrixConfiguration(boolean editable) {
//
//		this.editable = editable;
//		this.moduleInstanceCache = new ModuleInstanceCache();
//	}
//
//	@Override
//	public boolean isEditable() {
//
//		return editable;
//	}
//
//	@Override
//	public boolean isInputPossible(EmfModuleKey module, AGModuleInstanceGroup moduleInstanceGroup) {
//
//		return moduleInstanceCache.isDeployed(module.getUuid(), moduleInstanceGroup);
//	}
//
//	@Override
//	public IEmfSettingMatrixModelEntryInput<EmfModulePermissionStateContainer> createInput(Supplier<Optional<EmfModulePermissionStateContainer>> originalValueSupplier) {
//
//		return new ModulePermissionOwnershipMatrixValueInput(originalValueSupplier);
//	}
//
//	@Override
//	public Supplier<EmfModulePermissionStateContainer> getDefaultValueSupplier() {
//
//		return EmfModulePermissionStateContainer::new;
//	}
//
//	@Override
//	public boolean isDefaultValue(EmfModulePermissionStateContainer value) {
//
//		return value.isAllInactive();
//	}
//
//	@Override
//	public EmfModulePermissionStateContainer deepCopyValue(EmfModulePermissionStateContainer value) {
//
//		return getDefaultValueSupplier().get().merge(value);
//	}
//
//	@Override
//	public EmfModulePermissionStateContainer mergeValues(EmfModulePermissionStateContainer inferior, EmfModulePermissionStateContainer superior) {
//
//		EmfModulePermissionStateContainer result = getDefaultValueSupplier().get();
//		TreeSet<IEmfModulePermission<?>> permissions = new TreeSet<>();
//		permissions.addAll(inferior.getPermissions());
//		permissions.addAll(superior.getPermissions());
//
//		for (IEmfModulePermission<?> permission: permissions) {
//			if (inferior.isActive(permission) != superior.isActive(permission)) {
//				result.put(permission, superior.isActive(permission));
//			}
//		}
//		return result;
//	}
//
//	@Override
//	public boolean isEqualValues(EmfModulePermissionStateContainer first, EmfModulePermissionStateContainer second) {
//
//		return mergeValues(first, second).isEmpty();
//	}
//
//	@Override
//	public IDisplayString getRowTypeDisplayName() {
//
//		return CoreI18n.MODULE;
//	}
//
//	@Override
//	public IDisplayString getColumnTypeDisplayName() {
//
//		return AGModuleInstanceGroup.TABLE.getTitle();
//	}
//
//	@Override
//	public IDisplayString getRowDisplayName(EmfModuleKey row) {
//
//		return row.getDisplayName();
//	}
//
//	@Override
//	public IDisplayString getColumnDisplayName(AGModuleInstanceGroup column) {
//
//		return column.toDisplay();
//	}
}
