package com.softicar.platform.core.module.permission.assignment.matrix.user;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.permission.AbstractModulePermissionUiTest;
import com.softicar.platform.core.module.permission.assignment.AGModuleInstancePermissionAssignment;
import com.softicar.platform.core.module.test.module.standard.alpha.TestModuleAlphaPermissions;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.emf.matrix.EmfSettingMatrixMarker;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;

// FIXME reactive all tests
public class ModulePermissionOwnershipUserMatrixPageDivTest extends AbstractModulePermissionUiTest {

	private final Executor executor;
	private final Asserter asserter;
	private final AGUser user;
	private final AGModuleInstance moduleInstance;

	public ModulePermissionOwnershipUserMatrixPageDivTest() {

		getModuleRegistry().registerModule(new CoreModule());
		setupCurrentUser();
		this.executor = new Executor();
		this.asserter = new Asserter();
		this.user = insertTestUser();

		this.moduleInstance = insertModuleInstance(getTestModuleAlpha().getAnnotatedUuid());
		engine.setNodeSupplier(() -> new ModulePermissionOwnershipUserMatrixPageDiv());
	}

	@Test
	@Ignore
	public void testDisplay() {

		executor//
			.inputUser(user)
			.clickSelect();

		asserter//
			.assertCheckboxDisplayed(TestModuleAlphaPermissions.PERMISSION_ONE)
			.assertCheckboxDisplayed(TestModuleAlphaPermissions.PERMISSION_TWO);
	}

	@Test
	@Ignore
	public void testAddPermission() {

		executor//
			.inputUser(user)
			.clickSelect()
			.clickSinglePermissionCheckbox(TestModuleAlphaPermissions.PERMISSION_ONE)
			.clickSaveButton();

		AGModuleInstancePermissionAssignment record = getSingleOwnershipRecord();
		assertEquals(moduleInstance, record.getModuleInstance());
		assertEquals(TestModuleAlphaPermissions.PERMISSION_ONE.getAnnotatedUuid(), record.getPermissionUuid());
		assertEquals(user, record.getUser());
		assertTrue(record.isActive());
	}

	@Test
	@Ignore
	public void testRemovePermission() {

		insertOrUpdateOwnership(moduleInstance, TestModuleAlphaPermissions.PERMISSION_ONE.getAnnotatedUuid(), user, true);

		executor//
			.inputUser(user)
			.clickSelect()
			.clickSinglePermissionCheckbox(TestModuleAlphaPermissions.PERMISSION_ONE)
			.clickSaveButton();

		AGModuleInstancePermissionAssignment record = getSingleOwnershipRecord();
		assertEquals(moduleInstance, record.getModuleInstance());
		assertEquals(TestModuleAlphaPermissions.PERMISSION_ONE.getAnnotatedUuid(), record.getPermissionUuid());
		assertEquals(user, record.getUser());
		assertFalse(record.isActive());
	}

	private void setupCurrentUser() {

		AGUser accessManager = insertUser("Access", "Manager");
		insertAccessManagerPermission(accessManager);
		CurrentUser.set(accessManager);
	}

	private void insertAccessManagerPermission(AGUser user) {

		new AGModuleInstancePermissionAssignment()
			.setActive(true)
			.setModuleInstance(AGCoreModuleInstance.getInstance().pk())
			.setPermission(CorePermissions.ACCESS_MANAGEMENT.getAnnotatedUuid())
			.setUser(user)
			.save();
	}

	private AGModuleInstancePermissionAssignment getSingleOwnershipRecord() {

		List<AGModuleInstancePermissionAssignment> records = AGModuleInstancePermissionAssignment.TABLE.loadAll();
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

		public Executor clickSinglePermissionCheckbox(IEmfModulePermission<?> permission) {

			findNodes(DomCheckbox.class)//
				.withText(permission.getTitle())
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

		public Asserter assertCheckboxDisplayed(IEmfModulePermission<?> permission) {

			boolean nameFound = !findNodes(DomCheckbox.class)//
				.withText(permission.getTitle().toString())
				.toList()
				.isEmpty();
			assertTrue(//
				String.format("Failed to find checkbox for permission: %s", permission.getTitle()),
				nameFound);
			return this;
		}
	}
}
