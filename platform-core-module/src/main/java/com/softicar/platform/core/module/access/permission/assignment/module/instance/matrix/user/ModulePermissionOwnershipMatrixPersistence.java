package com.softicar.platform.core.module.access.permission.assignment.module.instance.matrix.user;

import com.softicar.platform.common.container.map.map.IMapMap;
import com.softicar.platform.emf.matrix.IEmfSettingMatrixPersistence;

/**
 * FIXME Cleanup and refactoring: As soon as
 * {@link IEmfSettingMatrixPersistence} implementations are required for
 * non-user-centric permission ownership administration pages, wide parts of the
 * logic in here will be extracted and re-used.
 * <p>
 * TODO do not use {@link IMapMap} in here
 */
//FIXME This commented code shall NOT be removed - it needs to be repaired.
class ModulePermissionOwnershipMatrixPersistence {
//
//	private final AGUser user;
//
//	public ModulePermissionOwnershipMatrixPersistence(AGUser user) {
//
//		this.user = user;
//	}
//
//	@Override
//	public void load(IEmfSettingMatrixModelData<EmfModuleKey, AGModuleInstanceGroup, EmfModulePermissionStateContainer> modelData) {
//
//		IMapMap<EmfModuleKey, AGModuleInstanceGroup, EmfModulePermissionStateContainer> permissionMap = loadPermissionMap();
//		Map<EmfModuleKey, Collection<IEmfModulePermission<?>>> staticModulePermissionMap = loadStaticModulePermissionMap();
//		Set<EmfModuleKey> modules = loadAllModules();
//		Collection<AGModuleInstanceGroup> groups = AGModuleInstanceGroup.loadAllActive();
//
//		modelData.clearAll();
//
//		for (EmfModuleKey module: modules) {
//			for (AGModuleInstanceGroup group: groups) {
//				EmfModulePermissionStateContainer value = modelData.createDefaultValue();
//				staticModulePermissionMap.get(module).forEach(permission -> value.put(permission, false));
//				Optional.ofNullable(permissionMap.get(module, group)).ifPresent(value::merge);
//				modelData.setValue(module, group, value);
//			}
//		}
//	}
//
//	@Override
//	public void save(IEmfSettingMatrixModelData<EmfModuleKey, AGModuleInstanceGroup, EmfModulePermissionStateContainer> deltaModelData) {
//
//		ModulePermissionOwnershipRecordCreator creator = new ModulePermissionOwnershipRecordCreator().setUser(user);
//		Collection<AGModulePermissionOwnership> records = new ArrayList<>();
//		Set<ModulePermissionOwnershipEntry> entries = getPermissionEntries(deltaModelData);
//		for (ModulePermissionOwnershipEntry entry: entries) {
//			creator.createRecord(entry).ifPresent(records::add);
//		}
//		AGModulePermissionOwnership.TABLE.saveAll(records);
//	}
//
//	private Set<ModulePermissionOwnershipEntry> getPermissionEntries(
//			IEmfSettingMatrixModelData<EmfModuleKey, AGModuleInstanceGroup, EmfModulePermissionStateContainer> modelData) {
//
//		Set<ModulePermissionOwnershipEntry> entries = new TreeSet<>();
//		ModuleInstanceCache instanceCache = new ModuleInstanceCache();
//		for (AGModuleInstanceGroup group: modelData.getColumns()) {
//			for (EmfModuleKey module: modelData.getRows()) {
//				EmfModulePermissionStateContainer value = modelData.getValueOrDefault(module, group);
//				for (IEmfModulePermission<?> permission: value.getPermissions()) {
//					UUID permissionUuid = permission.getPermissionUuid();
//					UUID moduleUuid = module.getUuid();
//					if (instanceCache.isDeployed(moduleUuid, group)) {
//						entries
//							.add(
//								new ModulePermissionOwnershipEntry(//
//									instanceCache.getActive(moduleUuid, group).get(),
//									user,
//									permissionUuid,
//									value.isActive(permission)));
//					}
//				}
//			}
//		}
//		return entries;
//	}
//
//	private Map<ModuleKey, Collection<IEmfStaticPermission<?>>> loadStaticModulePermissionMap() {
//
//		Map<ModuleKey, Collection<IEmfStaticPermission<?>>> map = MapFactory.createTreeMap();
//		IEmfPermissionRegistry permissionRegistry = CurrentEmfPermissionRegistry.get();
//		permissionRegistry//
//			.getModules()
//			.forEach(module -> map.put(new ModuleKey(module), permissionRegistry.getStaticPermissions(module)));
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
//	private IMapMap<EmfModuleKey, AGModuleInstanceGroup, EmfModulePermissionStateContainer> loadPermissionMap() {
//
//		IMapMap<EmfModuleKey, AGModuleInstanceGroup, EmfModulePermissionStateContainer> map = MapMapFactory.create();
//		for (IRow row: IModulePermissionOwnershipQuery.FACTORY.createQuery().setUser(user).list()) {
//			Optional<IEmfModulePermission<?>> modulePermission = CurrentEmfPermissionRegistry.get().getModulePermission(row.getOwnership().getUuid());
//			if (modulePermission.isPresent()) {
//				EmfModuleKey module = new EmfModuleKey(row.getInstance().getModuleUuid().getUuid());
//				AGModuleInstanceGroup moduleInstanceGroup = row.getInstance().getModuleInstanceGroup();
//				EmfModulePermissionStateContainer value = map.get(module, moduleInstanceGroup);
//				if (value == null) {
//					value = new EmfModulePermissionStateContainer();
//					map.put(module, moduleInstanceGroup, value);
//				}
//				value.put(modulePermission.get(), true);
//			}
//		}
//		return map;
//	}
}
