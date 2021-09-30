package com.softicar.platform.core.module.access.role.assignment.module.instance.matrix.user;

import com.softicar.platform.core.module.access.role.AbstractModuleRoleUiTest;

public class ModuleRoleMembershipMatrixPersistenceTest extends AbstractModuleRoleUiTest {

//	private final AGUser userOne;
//	private final AGModuleInstance moduleInstanceAlphaOne;
//	private final AGModuleInstance moduleInstanceAlphaTwo;
//	private final AGModuleInstance moduleInstanceBetaOne;
//
//	private final ModuleRoleMembershipMatrixConfiguration configuration;
//	private final ModuleRoleMembershipMatrixPersistence persistence;
//	private final IEmfSettingMatrixModel<EmfModuleKey, AGModuleInstanceGroup, EmfModuleRoleStateContainer> model;
//	private final EmfSettingMatrixController<EmfModuleKey, AGModuleInstanceGroup, EmfModuleRoleStateContainer> controller;
//
//	public ModuleRoleMembershipMatrixPersistenceTest() {
//
//		this.userOne = insertTestUser();
//		this.moduleInstanceAlphaOne = insertModuleInstance(getTestModuleAlpha().getUuid());
//		this.moduleInstanceAlphaTwo = insertModuleInstance(getTestModuleAlpha().getUuid());
//		this.moduleInstanceBetaOne = insertModuleInstance(getTestModuleBeta().getUuid());
//
//		this.configuration = new ModuleRoleMembershipMatrixConfiguration(true);
//		this.persistence = new ModuleRoleMembershipMatrixPersistence(userOne);
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
//		assertOriginalModelNoActiveRoles(getTestModuleAlpha(), instanceGroupOne);
//		assertOriginalModelNoActiveRoles(getTestModuleBeta(), instanceGroupOne);
//		assertOriginalModelNoActiveRoles(getTestModuleAlpha(), instanceGroupTwo);
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
//		insertOrUpdateMembership(moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_ONE.getRoleUuid(), userOne, true);
//
//		loadModel();
//
//		assertOriginalModelActiveRoles(getTestModuleAlpha(), instanceGroupOne, TestStandardModuleAlphaRoles.ROLE_ONE);
//		assertOriginalModelNoActiveRoles(getTestModuleBeta(), instanceGroupOne);
//		assertOriginalModelNoActiveRoles(getTestModuleAlpha(), instanceGroupTwo);
//	}
//
//	@Test
//	public void testLoadAgain() {
//
//		insertOrUpdateMembership(moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_ONE.getRoleUuid(), userOne, true);
//		loadModel();
//		insertOrUpdateMembership(moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_TWO.getRoleUuid(), userOne, true);
//		loadModel();
//
//		assertOriginalModelActiveRoles(//
//			getTestModuleAlpha(),
//			instanceGroupOne,
//			TestStandardModuleAlphaRoles.ROLE_ONE,
//			TestStandardModuleAlphaRoles.ROLE_TWO);
//	}
//
//	@Test
//	public void testLoadWithInactivePermission() {
//
//		insertOrUpdateMembership(moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_ONE.getRoleUuid(), userOne, false);
//		insertOrUpdateMembership(moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_TWO.getRoleUuid(), userOne, true);
//		loadModel();
//
//		assertOriginalModelActiveRoles(//
//			getTestModuleAlpha(),
//			instanceGroupOne,
//			TestStandardModuleAlphaRoles.ROLE_TWO);
//	}
//
//	@Test
//	public void testLoadWithInstanceOfOtherInstanceGroup() {
//
//		insertOrUpdateMembership(moduleInstanceAlphaTwo, TestStandardModuleAlphaRoles.ROLE_ONE.getRoleUuid(), userOne, true);
//
//		loadModel();
//
//		assertOriginalModelNoActiveRoles(getTestModuleAlpha(), instanceGroupOne);
//		assertOriginalModelNoActiveRoles(getTestModuleBeta(), instanceGroupOne);
//		assertOriginalModelActiveRoles(getTestModuleAlpha(), instanceGroupTwo, TestStandardModuleAlphaRoles.ROLE_ONE);
//	}
//
//	/**
//	 * Makes sure that a role of module alpha can be considered active for
//	 * module beta.
//	 */
//	@Test
//	public void testLoadWithInstanceOfOtherModule() {
//
//		insertOrUpdateMembership(moduleInstanceBetaOne, TestStandardModuleAlphaRoles.ROLE_ONE.getRoleUuid(), userOne, true);
//
//		loadModel();
//
//		assertOriginalModelNoActiveRoles(getTestModuleAlpha(), instanceGroupOne);
//		assertOriginalModelActiveRoles(getTestModuleBeta(), instanceGroupOne, TestStandardModuleAlphaRoles.ROLE_ONE);
//		assertOriginalModelNoActiveRoles(getTestModuleAlpha(), instanceGroupTwo);
//	}
//
//	@Test
//	public void testSaveUninitializedModel() {
//
//		saveModel();
//
//		assertTrue(AGModuleRoleMembership.TABLE.loadAll().isEmpty());
//		assertTrue(model.getOriginalData().getRows().isEmpty());
//		assertTrue(model.getOriginalData().getColumns().isEmpty());
//		assertTrue(model.getCurrentData().getRows().isEmpty());
//		assertTrue(model.getCurrentData().getColumns().isEmpty());
//		assertOriginalModelNoActiveRoles(getTestModuleAlpha(), instanceGroupOne);
//		assertOriginalModelNoActiveRoles(getTestModuleBeta(), instanceGroupOne);
//		assertOriginalModelNoActiveRoles(getTestModuleAlpha(), instanceGroupTwo);
//	}
//
//	@Test
//	public void testSaveNewRecord() {
//
//		setCurrentModelValue(getTestModuleAlpha(), instanceGroupOne, TestStandardModuleAlphaRoles.ROLE_ONE, true);
//
//		saveModel();
//
//		AGModuleRoleMembership record = loadOneMembershipRecord();
//		assertPermissionRecord(record, moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_ONE, userOne, true);
//	}
//
//	@Test
//	public void testSaveDeactivatesRecords() {
//
//		insertOrUpdateMembership(moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_ONE.getRoleUuid(), userOne, true);
//
//		loadModel();
//
//		setCurrentModelValue(getTestModuleAlpha(), instanceGroupOne, TestStandardModuleAlphaRoles.ROLE_ONE, false);
//
//		saveModel();
//
//		AGModuleRoleMembership record = loadOneMembershipRecord();
//		assertPermissionRecord(record, moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_ONE, userOne, false);
//	}
//
//	@Test
//	public void testSaveReactivatesRecords() {
//
//		insertOrUpdateMembership(moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_ONE.getRoleUuid(), userOne, false);
//		setCurrentModelValue(getTestModuleAlpha(), instanceGroupOne, TestStandardModuleAlphaRoles.ROLE_ONE, true);
//
//		saveModel();
//
//		AGModuleRoleMembership record = loadOneMembershipRecord();
//		assertPermissionRecord(record, moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_ONE, userOne, true);
//	}
//
//	@Test
//	public void testSaveOmitsUnnecessaryDeactivatedRecords() {
//
//		setCurrentModelValue(getTestModuleAlpha(), instanceGroupOne, TestStandardModuleAlphaRoles.ROLE_ONE, false);
//
//		saveModel();
//
//		List<AGModuleRoleMembership> records = AGModuleRoleMembership.TABLE.loadAll();
//		assertTrue(records.isEmpty());
//	}
//
//	@Test
//	public void testSaveWithSameModuleAndRoleButDifferentInstanceGroup() {
//
//		insertOrUpdateMembership(moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_ONE.getRoleUuid(), userOne, true);
//		insertOrUpdateMembership(moduleInstanceAlphaTwo, TestStandardModuleAlphaRoles.ROLE_ONE.getRoleUuid(), userOne, true);
//
//		loadModel();
//
//		setCurrentModelValue(getTestModuleAlpha(), instanceGroupTwo, TestStandardModuleAlphaRoles.ROLE_ONE, false);
//
//		saveModel();
//
//		assertOriginalModelActiveRoles(getTestModuleAlpha(), instanceGroupOne, TestStandardModuleAlphaRoles.ROLE_ONE);
//		assertOriginalModelNoActiveRoles(getTestModuleAlpha(), instanceGroupTwo);
//		List<AGModuleRoleMembership> records = loadMembershipRecords(2);
//		assertPermissionRecord(records.get(0), moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_ONE, userOne, true);
//		assertPermissionRecord(records.get(1), moduleInstanceAlphaTwo, TestStandardModuleAlphaRoles.ROLE_ONE, userOne, false);
//	}
//
//	@Test
//	public void testSaveWithSameModuleAndInstanceGroupButDifferentRole() {
//
//		insertOrUpdateMembership(moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_ONE.getRoleUuid(), userOne, true);
//		insertOrUpdateMembership(moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_TWO.getRoleUuid(), userOne, false);
//
//		loadModel();
//
//		setCurrentModelValue(getTestModuleAlpha(), instanceGroupOne, TestStandardModuleAlphaRoles.ROLE_ONE, false);
//		insertOrUpdateMembership(moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_TWO.getRoleUuid(), userOne, true);
//
//		saveModel();
//
//		List<AGModuleRoleMembership> records = loadMembershipRecords(2);
//		assertPermissionRecord(records.get(0), moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_ONE, userOne, false);
//		assertPermissionRecord(records.get(1), moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_TWO, userOne, true);
//	}
//
//	@Test
//	public void testSaveWithSameModuleAndInstanceGroupAndRole() {
//
//		loadModel();
//
//		// locally grant permission
//		setCurrentModelValue(getTestModuleAlpha(), instanceGroupOne, TestStandardModuleAlphaRoles.ROLE_ONE, true);
//
//		// simulate another user who revokes the permission
//		insertOrUpdateMembership(moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_ONE.getRoleUuid(), userOne, false);
//
//		// save local changes
//		saveModel();
//
//		// expect that foreign changes are overwritten
//		AGModuleRoleMembership record = loadOneMembershipRecord();
//		assertPermissionRecord(record, moduleInstanceAlphaOne, TestStandardModuleAlphaRoles.ROLE_ONE, userOne, true);
//	}
//
//	private void assertOriginalModelNoActiveRoles(IEmfModule<?> module, AGModuleInstanceGroup moduleInstanceGroup) {
//
//		assertOriginalModelActiveRoles(module, moduleInstanceGroup, new IEmfModuleRole<?>[0]);
//	}
//
//	private void assertOriginalModelActiveRoles(IEmfModule<?> module, AGModuleInstanceGroup moduleInstanceGroup, IEmfModuleRole<?>...expectedRoles) {
//
//		Set<UUID> actualRoleUuids = getOriginalModelActiveRoleUuids(module, moduleInstanceGroup);
//		Set<UUID> expectedRoleUuids = Arrays.asList(expectedRoles).stream().map(IEmfModuleRole::getRoleUuid).collect(Collectors.toSet());
//
//		for (UUID expectedRoleUuid: expectedRoleUuids) {
//			assertTrue(//
//				String.format("Failed to find expected active role %s.", expectedRoleUuid),
//				actualRoleUuids.contains(expectedRoleUuid));
//		}
//
//		for (UUID actualRoleUuid: actualRoleUuids) {
//			assertTrue(//
//				String.format("Unexpectedly found surplus active role %s.", actualRoleUuid),
//				expectedRoleUuids.contains(actualRoleUuid));
//		}
//	}
//
//	private void assertPermissionRecord(AGModuleRoleMembership record, AGModuleInstance moduleInstance, IEmfModuleRole<?> role, AGUser user, boolean active) {
//
//		assertEquals(moduleInstance, record.getModuleInstance());
//		assertEquals(role.getRoleUuid(), record.getUuid());
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
//	private Set<UUID> getOriginalModelActiveRoleUuids(IEmfModule<?> module, AGModuleInstanceGroup moduleInstanceGroup) {
//
//		EmfModuleRoleStateContainer value = getOriginalModelValue(module.getUuid(), moduleInstanceGroup);
//		return value//
//			.getRoles()
//			.stream()
//			.filter(role -> value.isActive(role))
//			.map(it -> it.getRoleUuid())
//			.collect(Collectors.toSet());
//	}
//
//	private EmfModuleRoleStateContainer getOriginalModelValue(UUID moduleUuid, AGModuleInstanceGroup moduleInstanceGroup) {
//
//		return model
//			.getOriginalData()
//			.getValueOrDefault(//
//				new EmfModuleKey(moduleUuid),
//				moduleInstanceGroup);
//	}
//
//	private void setCurrentModelValue(IEmfModule<?> module, AGModuleInstanceGroup moduleInstanceGroup, IEmfModuleRole<?> role, boolean active) {
//
//		model
//			.getCurrentData()
//			.setValue(//
//				new EmfModuleKey(module),
//				moduleInstanceGroup,
//				new EmfModuleRoleStateContainer().put(role, active));
//	}
//
//	private AGModuleRoleMembership loadOneMembershipRecord() {
//
//		return loadMembershipRecords(1).iterator().next();
//	}
//
//	private List<AGModuleRoleMembership> loadMembershipRecords(int count) {
//
//		List<AGModuleRoleMembership> records = AGModuleRoleMembership.TABLE.loadAll();
//		assertEquals(count, records.size());
//		return records;
//	}
}
