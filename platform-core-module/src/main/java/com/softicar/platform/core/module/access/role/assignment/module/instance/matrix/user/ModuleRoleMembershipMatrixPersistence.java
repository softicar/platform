package com.softicar.platform.core.module.access.role.assignment.module.instance.matrix.user;

import com.softicar.platform.common.container.map.map.IMapMap;
import com.softicar.platform.emf.matrix.IEmfSettingMatrixPersistence;

/**
 * FIXME Cleanup and refactoring: As soon as
 * {@link IEmfSettingMatrixPersistence} implementations are required for
 * non-user-centric role membership administration pages, wide parts of the
 * logic in here will be extracted and re-used.
 * <p>
 * TODO do not use {@link IMapMap} in here
 */
//FIXME This commented code shall NOT be removed - it needs to be repaired.
class ModuleRoleMembershipMatrixPersistence// implements IEmfSettingMatrixPersistence<EmfModuleKey, AGModuleInstanceGroup, EmfModuleRoleStateContainer>
{
//
//	private final AGUser user;
//
//	public ModuleRoleMembershipMatrixPersistence(AGUser user) {
//
//		this.user = user;
//	}
//
//	@Override
//	public void load(IEmfSettingMatrixModelData<EmfModuleKey, AGModuleInstanceGroup, EmfModuleRoleStateContainer> modelData) {
//
//		IMapMap<EmfModuleKey, AGModuleInstanceGroup, EmfModuleRoleStateContainer> permissionMap = loadPermissionMap();
//		Map<EmfModuleKey, Collection<IEmfModuleRole<?>>> staticModuleRoleMap = loadStaticModuleRoleMap();
//		Set<EmfModuleKey> modules = loadAllModules();
//		Collection<AGModuleInstanceGroup> groups = AGModuleInstanceGroup.loadAllActive();
//
//		modelData.clearAll();
//
//		for (EmfModuleKey module: modules) {
//			for (AGModuleInstanceGroup group: groups) {
//				EmfModuleRoleStateContainer value = modelData.createDefaultValue();
//				staticModuleRoleMap.get(module).forEach(role -> value.put(role, false));
//				Optional.ofNullable(permissionMap.get(module, group)).ifPresent(value::merge);
//				modelData.setValue(module, group, value);
//			}
//		}
//	}
//
//	@Override
//	public void save(IEmfSettingMatrixModelData<EmfModuleKey, AGModuleInstanceGroup, EmfModuleRoleStateContainer> deltaModelData) {
//
//		ModuleRoleMembershipRecordCreator creator = new ModuleRoleMembershipRecordCreator().setUser(user);
//		Collection<AGModuleRoleMembership> records = new ArrayList<>();
//		Set<ModuleRoleMembershipEntry> entries = getPermissionEntries(deltaModelData);
//		for (ModuleRoleMembershipEntry entry: entries) {
//			creator.createRecord(entry).ifPresent(records::add);
//		}
//		AGModuleRoleMembership.TABLE.saveAll(records);
//	}
//
//	private Set<ModuleRoleMembershipEntry> getPermissionEntries(
//			IEmfSettingMatrixModelData<EmfModuleKey, AGModuleInstanceGroup, EmfModuleRoleStateContainer> modelData) {
//
//		Set<ModuleRoleMembershipEntry> entries = new TreeSet<>();
//		ModuleInstanceCache instanceCache = new ModuleInstanceCache();
//		for (AGModuleInstanceGroup group: modelData.getColumns()) {
//			for (EmfModuleKey module: modelData.getRows()) {
//				EmfModuleRoleStateContainer value = modelData.getValueOrDefault(module, group);
//				for (IEmfModuleRole<?> role: value.getRoles()) {
//					UUID roleUuid = role.getRoleUuid();
//					UUID moduleUuid = module.getUuid();
//					if (instanceCache.isDeployed(moduleUuid, group)) {
//						entries
//							.add(
//								new ModuleRoleMembershipEntry(//
//									instanceCache.getActive(moduleUuid, group).get(),
//									user,
//									roleUuid,
//									value.isActive(role)));
//					}
//				}
//			}
//		}
//		return entries;
//	}
//
//	private Map<ModuleKey, Collection<IEmfStaticRole<?>>> loadStaticModuleRoleMap() {
//
//		Map<ModuleKey, Collection<IEmfStaticRole<?>>> map = MapFactory.createTreeMap();
//		IEmfRoleRegistry roleRegistry = CurrentEmfRoleRegistry.get();
//		roleRegistry//
//			.getModules()
//			.forEach(module -> map.put(new ModuleKey(module), roleRegistry.getStaticRoles(module)));
//		return map;
//	}
//
//	private Set<ModuleKey> loadAllModules() {
//
//		return CurrentEmfModuleRegistry//
//			.get()
//			.getAllModules()
//			.stream()
//			.map(ModuleKey::new)
//			.collect(Collectors.toSet());
//	}
//
//	private IMapMap<EmfModuleKey, AGModuleInstanceGroup, EmfModuleRoleStateContainer> loadPermissionMap() {
//
//		IMapMap<EmfModuleKey, AGModuleInstanceGroup, EmfModuleRoleStateContainer> map = MapMapFactory.create();
//		for (IRow row: IModuleRoleMembershipQuery.FACTORY.createQuery().setUser(user).list()) {
//			Optional<IEmfModuleRole<?>> moduleRole = CurrentEmfRoleRegistry.get().getModuleRole(row.getMembership().getUuid());
//			if (moduleRole.isPresent()) {
//				EmfModuleKey module = new EmfModuleKey(row.getInstance().getModuleUuid().getUuid());
//				AGModuleInstanceGroup moduleInstanceGroup = row.getInstance().getModuleInstanceGroup();
//				EmfModuleRoleStateContainer value = map.get(module, moduleInstanceGroup);
//				if (value == null) {
//					value = new EmfModuleRoleStateContainer();
//					map.put(module, moduleInstanceGroup, value);
//				}
//				value.put(moduleRole.get(), true);
//			}
//		}
//		return map;
//	}
}
