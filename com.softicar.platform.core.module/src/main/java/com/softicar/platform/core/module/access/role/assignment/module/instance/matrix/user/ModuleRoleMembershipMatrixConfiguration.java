package com.softicar.platform.core.module.access.role.assignment.module.instance.matrix.user;

// FIXME This commented code shall NOT be removed - it needs to be repaired.
class ModuleRoleMembershipMatrixConfiguration //extends AbstractEmfSettingMatrixConfiguration<EmfModuleKey, AGModuleInstanceGroup, EmfModuleRoleStateContainer> 
{
//
//	private final boolean editable;
//	private final ModuleInstanceCache moduleInstanceCache;
//
//	public ModuleRoleMembershipMatrixConfiguration(boolean editable) {
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
//	public IEmfSettingMatrixModelEntryInput<EmfModuleRoleStateContainer> createInput(Supplier<Optional<EmfModuleRoleStateContainer>> originalValueSupplier) {
//
//		return new ModuleRoleMembershipMatrixValueInput(originalValueSupplier);
//	}
//
//	@Override
//	public Supplier<EmfModuleRoleStateContainer> getDefaultValueSupplier() {
//
//		return EmfModuleRoleStateContainer::new;
//	}
//
//	@Override
//	public boolean isDefaultValue(EmfModuleRoleStateContainer value) {
//
//		return value.isAllInactive();
//	}
//
//	@Override
//	public EmfModuleRoleStateContainer deepCopyValue(EmfModuleRoleStateContainer value) {
//
//		return getDefaultValueSupplier().get().merge(value);
//	}
//
//	@Override
//	public EmfModuleRoleStateContainer mergeValues(EmfModuleRoleStateContainer inferior, EmfModuleRoleStateContainer superior) {
//
//		EmfModuleRoleStateContainer result = getDefaultValueSupplier().get();
//		TreeSet<IEmfModuleRole<?>> roles = new TreeSet<>();
//		roles.addAll(inferior.getRoles());
//		roles.addAll(superior.getRoles());
//
//		for (IEmfModuleRole<?> role: roles) {
//			if (inferior.isActive(role) != superior.isActive(role)) {
//				result.put(role, superior.isActive(role));
//			}
//		}
//		return result;
//	}
//
//	@Override
//	public boolean isEqualValues(EmfModuleRoleStateContainer first, EmfModuleRoleStateContainer second) {
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
