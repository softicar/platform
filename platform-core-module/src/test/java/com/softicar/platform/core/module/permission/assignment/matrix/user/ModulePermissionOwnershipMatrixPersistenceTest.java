package com.softicar.platform.core.module.permission.assignment.matrix.user;

import com.softicar.platform.core.module.permission.AbstractModulePermissionUiTest;

public class ModulePermissionOwnershipMatrixPersistenceTest extends AbstractModulePermissionUiTest {

//	private final AGUser userOne;
//	private final AGModuleInstance moduleInstanceAlphaOne;
//	private final AGModuleInstance moduleInstanceAlphaTwo;
//	private final AGModuleInstance moduleInstanceBetaOne;
//
//	private final ModulePermissionOwnershipMatrixConfiguration configuration;
//	private final ModulePermissionOwnershipMatrixPersistence persistence;
//	private final IEmfSettingMatrixModel<EmfModuleKey, AGModuleInstanceGroup, EmfModulePermissionStateContainer> model;
//	private final EmfSettingMatrixController<EmfModuleKey, AGModuleInstanceGroup, EmfModulePermissionStateContainer> controller;
//
//	public ModulePermissionOwnershipMatrixPersistenceTest() {
//
//		this.userOne = insertTestUser();
//		this.moduleInstanceAlphaOne = insertModuleInstance(getTestModuleAlpha().getUuid());
//		this.moduleInstanceAlphaTwo = insertModuleInstance(getTestModuleAlpha().getUuid());
//		this.moduleInstanceBetaOne = insertModuleInstance(getTestModuleBeta().getUuid());
//
//		this.configuration = new ModulePermissionOwnershipMatrixConfiguration(true);
//		this.persistence = new ModulePermissionOwnershipMatrixPersistence(userOne);
//		this.model = new EmfSettingMatrixModel<>(configuration);
//		this.controller = new EmfSettingMatrixController<>(persistence, model, Mockito.mock(IEmfSettingMatrixView.class));
//	}
//
//	@Test
//	public void testModelInitializedEmpty() {
//
//		assertTrue(model.getOriginalData().getRows().isEmpty());
//		assertTrue(model.getOriginalData().getColumns().isEmpty());
//		assertTrue(model.getCurrentData().getRows().isEmpty());
//		assertTrue(model.getCurrentData().getColumns().isEmpty());
//		assertOriginalModelNoActivePermissions(getTestModuleAlpha(), instanceGroupOne);
//		assertOriginalModelNoActivePermissions(getTestModuleBeta(), instanceGroupOne);
//		assertOriginalModelNoActivePermissions(getTestModuleAlpha(), instanceGroupTwo);
//	}
//
//	@Test
//	public void testLoadInitializesModelDimensions() {
//
//		loadModel();
//
//		assertEquals(2, model.getOriginalData().getRows().size());
//		assertEquals(2, model.getOriginalData().getColumns().size());
//		assertEquals(2, model.getCurrentData().getRows().size());
//		assertEquals(2, model.getCurrentData().getColumns().size());
//	}
//
//	@Test
//	public void testLoad() {
//
//		insertOrUpdateOwnershipip(moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_ONE.getPermissionUuid(), userOne, true);
//
//		loadModel();
//
//		assertOriginalModelActivePermissions(getTestModuleAlpha(), instanceGroupOne, TestModuleAlphaPermissions.PERMISSION_ONE);
//		assertOriginalModelNoActivePermissions(getTestModuleBeta(), instanceGroupOne);
//		assertOriginalModelNoActivePermissions(getTestModuleAlpha(), instanceGroupTwo);
//	}
//
//	@Test
//	public void testLoadAgain() {
//
//		insertOrUpdateOwnership(moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_ONE.getPermissionUuid(), userOne, true);
//		loadModel();
//		insertOrUpdateOwnership(moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_TWO.getPermissionUuid(), userOne, true);
//		loadModel();
//
//		assertOriginalModelActivePermissions(//
//			getTestModuleAlpha(),
//			instanceGroupOne,
//			TestModuleAlphaPermissions.PERMISSION_ONE,
//			TestModuleAlphaPermissions.PERMISSION_TWO);
//	}
//
//	@Test
//	public void testLoadWithInactivePermission() {
//
//		insertOrUpdateOwnership(moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_ONE.getPermissionUuid(), userOne, false);
//		insertOrUpdateOwnership(moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_TWO.getPermissionUuid(), userOne, true);
//		loadModel();
//
//		assertOriginalModelActivePermissions(//
//			getTestModuleAlpha(),
//			instanceGroupOne,
//			TestModuleAlphaPermissions.PERMISSION_TWO);
//	}
//
//	@Test
//	public void testLoadWithInstanceOfOtherInstanceGroup() {
//
//		insertOrUpdateOwnership(moduleInstanceAlphaTwo, TestModuleAlphaPermissions.PERMISSION_ONE.getPermissionUuid(), userOne, true);
//
//		loadModel();
//
//		assertOriginalModelNoActivePermissions(getTestModuleAlpha(), instanceGroupOne);
//		assertOriginalModelNoActivePermissions(getTestModuleBeta(), instanceGroupOne);
//		assertOriginalModelActivePermissions(getTestModuleAlpha(), instanceGroupTwo, TestModuleAlphaPermissions.PERMISSION_ONE);
//	}
//
//	/**
//	 * Makes sure that a permission of module alpha can be considered active for
//	 * module beta.
//	 */
//	@Test
//	public void testLoadWithInstanceOfOtherModule() {
//
//		insertOrUpdateOwnership(moduleInstanceBetaOne, TestModuleAlphaPermissions.PERMISSION_ONE.getPermissionUuid(), userOne, true);
//
//		loadModel();
//
//		assertOriginalModelNoActivePermissions(getTestModuleAlpha(), instanceGroupOne);
//		assertOriginalModelActivePermissions(getTestModuleBeta(), instanceGroupOne, TestModuleAlphaPermissions.PERMISSION_ONE);
//		assertOriginalModelNoActivePermissions(getTestModuleAlpha(), instanceGroupTwo);
//	}
//
//	@Test
//	public void testSaveUninitializedModel() {
//
//		saveModel();
//
//		assertTrue(AGModulePermissionOwnership.TABLE.loadAll().isEmpty());
//		assertTrue(model.getOriginalData().getRows().isEmpty());
//		assertTrue(model.getOriginalData().getColumns().isEmpty());
//		assertTrue(model.getCurrentData().getRows().isEmpty());
//		assertTrue(model.getCurrentData().getColumns().isEmpty());
//		assertOriginalModelNoActivePermissions(getTestModuleAlpha(), instanceGroupOne);
//		assertOriginalModelNoActivePermissions(getTestModuleBeta(), instanceGroupOne);
//		assertOriginalModelNoActivePermissions(getTestModuleAlpha(), instanceGroupTwo);
//	}
//
//	@Test
//	public void testSaveNewRecord() {
//
//		setCurrentModelValue(getTestModuleAlpha(), instanceGroupOne, TestModuleAlphaPermissions.PERMISSION_ONE, true);
//
//		saveModel();
//
//		AGModulePermissionOwnership record = loadOneOwnershipRecord();
//		assertPermissionRecord(record, moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_ONE, userOne, true);
//	}
//
//	@Test
//	public void testSaveDeactivatesRecords() {
//
//		insertOrUpdateOwnership(moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_ONE.getPermissionUuid(), userOne, true);
//
//		loadModel();
//
//		setCurrentModelValue(getTestModuleAlpha(), instanceGroupOne, TestModuleAlphaPermissions.PERMISSION_ONE, false);
//
//		saveModel();
//
//		AGModulePermissionOwnership record = loadOneOwnershipRecord();
//		assertPermissionRecord(record, moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_ONE, userOne, false);
//	}
//
//	@Test
//	public void testSaveReactivatesRecords() {
//
//		insertOrUpdateOwnership(moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_ONE.getPermissionUuid(), userOne, false);
//		setCurrentModelValue(getTestModuleAlpha(), instanceGroupOne, TestModuleAlphaPermissions.PERMISSION_ONE, true);
//
//		saveModel();
//
//		AGModulePermissionOwnership record = loadOneOwnershipRecord();
//		assertPermissionRecord(record, moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_ONE, userOne, true);
//	}
//
//	@Test
//	public void testSaveOmitsUnnecessaryDeactivatedRecords() {
//
//		setCurrentModelValue(getTestModuleAlpha(), instanceGroupOne, TestModuleAlphaPermissions.PERMISSION_ONE, false);
//
//		saveModel();
//
//		List<AGModulePermissionOwnership> records = AGModulePermissionOwnership.TABLE.loadAll();
//		assertTrue(records.isEmpty());
//	}
//
//	@Test
//	public void testSaveWithSameModuleAndPermissionButDifferentInstanceGroup() {
//
//		insertOrUpdateOwnership(moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_ONE.getPermissionUuid(), userOne, true);
//		insertOrUpdateOwnership(moduleInstanceAlphaTwo, TestModuleAlphaPermissions.PERMISSION_ONE.getPermissionUuid(), userOne, true);
//
//		loadModel();
//
//		setCurrentModelValue(getTestModuleAlpha(), instanceGroupTwo, TestModuleAlphaPermissions.PERMISSION_ONE, false);
//
//		saveModel();
//
//		assertOriginalModelActivePermissions(getTestModuleAlpha(), instanceGroupOne, TestModuleAlphaPermissions.PERMISSION_ONE);
//		assertOriginalModelNoActivePermissions(getTestModuleAlpha(), instanceGroupTwo);
//		List<AGModulePermissionOwnership> records = loadOwnershipRecords(2);
//		assertPermissionRecord(records.get(0), moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_ONE, userOne, true);
//		assertPermissionRecord(records.get(1), moduleInstanceAlphaTwo, TestModuleAlphaPermissions.PERMISSION_ONE, userOne, false);
//	}
//
//	@Test
//	public void testSaveWithSameModuleAndInstanceGroupButDifferentPermission() {
//
//		insertOrUpdateOwnership(moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_ONE.getPermissionUuid(), userOne, true);
//		insertOrUpdateOwnership(moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_TWO.getPermissionUuid(), userOne, false);
//
//		loadModel();
//
//		setCurrentModelValue(getTestModuleAlpha(), instanceGroupOne, TestModuleAlphaPermissions.PERMISSION_ONE, false);
//		insertOrUpdateOwnership(moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_TWO.getPermissionUuid(), userOne, true);
//
//		saveModel();
//
//		List<AGModulePermissionOwnership> records = loadOwnershipRecords(2);
//		assertPermissionRecord(records.get(0), moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_ONE, userOne, false);
//		assertPermissionRecord(records.get(1), moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_TWO, userOne, true);
//	}
//
//	@Test
//	public void testSaveWithSameModuleAndInstanceGroupAndPermission() {
//
//		loadModel();
//
//		// locally grant permission
//		setCurrentModelValue(getTestModuleAlpha(), instanceGroupOne, TestModuleAlphaPermissions.PERMISSION_ONE, true);
//
//		// simulate another user who revokes the permission
//		insertOrUpdateOwnership(moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_ONE.getPermissionUuid(), userOne, false);
//
//		// save local changes
//		saveModel();
//
//		// expect that foreign changes are overwritten
//		AGModulePermissionOwnership record = loadOneOwnershipRecord();
//		assertPermissionRecord(record, moduleInstanceAlphaOne, TestModuleAlphaPermissions.PERMISSION_ONE, userOne, true);
//	}
//
//	private void assertOriginalModelNoActivePermissions(IEmfModule<?> module, AGModuleInstanceGroup moduleInstanceGroup) {
//
//		assertOriginalModelActivePermissions(module, moduleInstanceGroup, new IEmfModulePermission<?>[0]);
//	}
//
//	private void assertOriginalModelActivePermissions(IEmfModule<?> module, AGModuleInstanceGroup moduleInstanceGroup, IEmfModulePermission<?>...expectedPermissions) {
//
//		Set<UUID> actualPermissionUuids = getOriginalModelActivePermissionUuids(module, moduleInstanceGroup);
//		Set<UUID> expectedPermissionUuids = Arrays.asList(expectedPermissions).stream().map(IEmfModulePermission::getPermissionUuid).collect(Collectors.toSet());
//
//		for (UUID expectedPermissionUuid: expectedPermissionUuids) {
//			assertTrue(//
//				String.format("Failed to find expected active permission %s.", expectedPermissionUuid),
//				actualPermissionUuids.contains(expectedPermissionUuid));
//		}
//
//		for (UUID actualPermissionUuid: actualPermissionUuids) {
//			assertTrue(//
//				String.format("Unexpectedly found surplus active permission %s.", actualPermissionUuid),
//				expectedPermissionUuids.contains(actualPermissionUuid));
//		}
//	}
//
//	private void assertPermissionRecord(AGModulePermissionOwnership record, AGModuleInstance moduleInstance, IEmfModulePermission<?> permission, AGUser user, boolean active) {
//
//		assertEquals(moduleInstance, record.getModuleInstance());
//		assertEquals(permission.getPermissionUuid(), record.getUuid());
//		assertEquals(user, record.getUser());
//		assertEquals(active, record.isActive());
//	}
//
//	private void loadModel() {
//
//		controller.loadModel();
//	}
//
//	private void saveModel() {
//
//		controller.saveModel();
//	}
//
//	private Set<UUID> getOriginalModelActivePermissionUuids(IEmfModule<?> module, AGModuleInstanceGroup moduleInstanceGroup) {
//
//		EmfModulePermissionStateContainer value = getOriginalModelValue(module.getUuid(), moduleInstanceGroup);
//		return value//
//			.getPermissions()
//			.stream()
//			.filter(permission -> value.isActive(permission))
//			.map(it -> it.getPermissionUuid())
//			.collect(Collectors.toSet());
//	}
//
//	private EmfModulePermissionStateContainer getOriginalModelValue(UUID moduleUuid, AGModuleInstanceGroup moduleInstanceGroup) {
//
//		return model
//			.getOriginalData()
//			.getValueOrDefault(//
//				new EmfModuleKey(moduleUuid),
//				moduleInstanceGroup);
//	}
//
//	private void setCurrentModelValue(IEmfModule<?> module, AGModuleInstanceGroup moduleInstanceGroup, IEmfModulePermission<?> permission, boolean active) {
//
//		model
//			.getCurrentData()
//			.setValue(//
//				new EmfModuleKey(module),
//				moduleInstanceGroup,
//				new EmfModulePermissionStateContainer().put(permission, active));
//	}
//
//	private AGModulePermissionOwnership loadOneOwnershipRecord() {
//
//		return loadOwnershipRecords(1).iterator().next();
//	}
//
//	private List<AGModulePermissionOwnership> loadOwnershipRecords(int count) {
//
//		List<AGModulePermissionOwnership> records = AGModulePermissionOwnership.TABLE.loadAll();
//		assertEquals(count, records.size());
//		return records;
//	}
}
