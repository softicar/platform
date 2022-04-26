package com.softicar.platform.core.module.access.role.assignment.module.instance.matrix.user;

import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.access.role.AbstractModuleRoleUiTest;
import com.softicar.platform.core.module.access.role.assignment.module.instance.AGModuleInstanceRoleAssignment;
import com.softicar.platform.core.module.access.role.assignment.module.system.AGSystemModuleRoleAssignment;
import com.softicar.platform.core.module.test.module.standard.alpha.TestStandardModuleAlphaRoles;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.emf.matrix.EmfSettingMatrixMarker;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;

// FIXME reactive all tests
public class ModuleRoleMembershipUserMatrixPageDivTest extends AbstractModuleRoleUiTest {

	private final Executor executor;
	private final Asserter asserter;
	private final AGUser user;
	private final AGModuleInstance moduleInstance;

	public ModuleRoleMembershipUserMatrixPageDivTest() {

		getModuleRegistry().registerModule(new CoreModule());
		setupCurrentUser();
		this.executor = new Executor();
		this.asserter = new Asserter();
		this.user = insertTestUser();

		this.moduleInstance = insertModuleInstance(getTestModuleAlpha().getAnnotatedUuid());
		engine.setNodeSupplier(() -> new ModuleRoleMembershipUserMatrixPageDiv());
	}

	@Test
	@Ignore
	public void testDisplay() {

		executor//
			.inputUser(user)
			.clickSelect();

		asserter//
			.assertCheckboxDisplayed(TestStandardModuleAlphaRoles.ROLE_ONE)
			.assertCheckboxDisplayed(TestStandardModuleAlphaRoles.ROLE_TWO);
	}

	@Test
	@Ignore
	public void testAddPermission() {

		executor//
			.inputUser(user)
			.clickSelect()
			.clickSingleRoleCheckbox(TestStandardModuleAlphaRoles.ROLE_ONE)
			.clickSaveButton();

		AGModuleInstanceRoleAssignment record = getSingleMembershipRecord();
		assertEquals(moduleInstance, record.getModuleInstance());
		assertEquals(TestStandardModuleAlphaRoles.ROLE_ONE.getAnnotatedUuid(), record.getRoleUuid());
		assertEquals(user, record.getUser());
		assertTrue(record.isActive());
	}

	@Test
	@Ignore
	public void testRemovePermission() {

		insertOrUpdateMembership(moduleInstance, TestStandardModuleAlphaRoles.ROLE_ONE.getAnnotatedUuid(), user, true);

		executor//
			.inputUser(user)
			.clickSelect()
			.clickSingleRoleCheckbox(TestStandardModuleAlphaRoles.ROLE_ONE)
			.clickSaveButton();

		AGModuleInstanceRoleAssignment record = getSingleMembershipRecord();
		assertEquals(moduleInstance, record.getModuleInstance());
		assertEquals(TestStandardModuleAlphaRoles.ROLE_ONE.getAnnotatedUuid(), record.getRoleUuid());
		assertEquals(user, record.getUser());
		assertFalse(record.isActive());
	}

	private void setupCurrentUser() {

		AGUser accessManager = insertUser("Access", "Manager");
		insertAccessManagerPermission(accessManager);
		CurrentUser.set(accessManager);
	}

	private void insertAccessManagerPermission(AGUser user) {

		new AGSystemModuleRoleAssignment()
			.setActive(true)
			.setModule(AGUuid.getOrCreate(CoreModule.class))
			.setRole(CoreRoles.ACCESS_MANAGER.getAnnotatedUuid())
			.setUser(user)
			.save();
	}

	private AGModuleInstanceRoleAssignment getSingleMembershipRecord() {

		List<AGModuleInstanceRoleAssignment> records = AGModuleInstanceRoleAssignment.TABLE.loadAll();
		assertEquals(1, records.size());
		return records.iterator().next();
	}

	private class Executor {

		public Executor clickSelect() {

			findNodes(EmfSettingMatrixMarker.ENTITY_SELECT_BUTTON)//
				.first()
				.click();
			return this;
		}

		public Executor inputUser(AGUser user) {

			findNode(EmfSettingMatrixMarker.ENTITY_INPUT)//
				.findNode(IDomTextualInput.class)
				.setInputValue(user.toDisplay().toString());
			return this;
		}

		public Executor clickSingleRoleCheckbox(IEmfModuleRole<?> role) {

			findNodes(DomCheckbox.class)//
				.withText(role.getTitle())
				.first()
				.click();
			return this;
		}

		public Executor clickSaveButton() {

			findNodes(EmfSettingMatrixMarker.SAVE_BUTTON)//
				.first()
				.click();
			return this;
		}
	}

	private class Asserter {

		public Asserter assertCheckboxDisplayed(IEmfModuleRole<?> role) {

			boolean roleNameFound = !findNodes(DomCheckbox.class)//
				.withText(role.getTitle().toString())
				.toList()
				.isEmpty();
			assertTrue(//
				String.format("Failed to find checkbox for role: %s", role.getTitle()),
				roleNameFound);
			return this;
		}
	}
}
