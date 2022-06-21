package com.softicar.platform.core.module.permission;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.access.module.AbstractModuleTest;
import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.core.module.permission.assignment.AGModuleInstancePermissionAssignment;
import com.softicar.platform.core.module.role.AGRole;
import com.softicar.platform.core.module.role.permission.AGRolePermission;
import com.softicar.platform.core.module.role.user.AGRoleUser;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.Test;

public class UserModulePermissionCacheTest extends AbstractModuleTest {

	private static final UUID A = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
	private static final UUID B = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
	private static final UUID C = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");

	private final AGUser alice;
	private final UserModulePermissionCache cacheAlice;
	private final AGUser bob;
	private final UserModulePermissionCache cacheBob;
	private final IModuleInstance<?> moduleInstance;

	public UserModulePermissionCacheTest() {

		this.alice = insertUser("alice");
		this.cacheAlice = new UserModulePermissionCache(alice);
		this.bob = insertUser("bob");
		this.cacheBob = new UserModulePermissionCache(bob);
		this.moduleInstance = AGCoreModuleInstance.getInstance();
	}

	@Test
	public void testWithModulePermissionAssignments() {

		// assign permissions: Alice=A+C Bob=B+C
		insertPermissionAssignment(alice, A, moduleInstance);
		insertPermissionAssignment(alice, C, moduleInstance);
		insertPermissionAssignment(bob, B, moduleInstance);
		insertPermissionAssignment(bob, C, moduleInstance);

		// assert permissions
		assertAlicesPermissions(A, C);
		assertBobsPermissions(B, C);

		// deactivate Alice's permission A
		deactivateModulePermission(alice, A, moduleInstance);
		assertAlicesPermissions(C);
		assertBobsPermissions(B, C);
	}

	@Test
	public void testWithRolePermissions() {

		// dedicated role for Alice with permission A
		var aliceOnly = insertRole("Alice Only");
		insertRoleUser(aliceOnly, alice);
		insertRolePermission(aliceOnly, A, moduleInstance);

		// dedicated role for Bob with permission B
		var bobOnly = insertRole("Bob Only");
		insertRoleUser(bobOnly, bob);
		insertRolePermission(bobOnly, B, moduleInstance);

		// common role for Alice and Bob with permission C
		var common = insertRole("Common");
		insertRoleUser(common, alice);
		insertRoleUser(common, bob);
		insertRolePermission(common, C, moduleInstance);

		// assert permissions
		assertAlicesPermissions(A, C);
		assertBobsPermissions(B, C);

		// deactivate Alice's dedicated role
		deactivateRole(aliceOnly);
		assertAlicesPermissions(C);
		assertBobsPermissions(B, C);

		// deactivate Bob's membership in his dedicated role
		deactivateRoleUser(bobOnly, bob);
		assertAlicesPermissions(C);
		assertBobsPermissions(C);

		// deactivate permission C in common role
		deactivateRolePermission(common, C, moduleInstance);
		assertAlicesPermissions();
		assertBobsPermissions();
	}

	// ------------------------------ utilities ------------------------------ //

	private void deactivateRole(AGRole role) {

		role.setActive(false).save();
	}

	private void deactivateRoleUser(AGRole role, AGUser user) {

		AGRoleUser.TABLE//
			.createSelect()
			.where(AGRoleUser.ROLE.isEqual(role))
			.where(AGRoleUser.USER.isEqual(user))
			.getOne()
			.setActive(false)
			.save();
	}

	private void deactivateRolePermission(AGRole role, UUID permission, IModuleInstance<?> moduleInstance) {

		AGRolePermission.TABLE//
			.createSelect()
			.where(AGRolePermission.ROLE.isEqual(role))
			.where(AGRolePermission.MODULE_INSTANCE.isEqual(moduleInstance.pk()))
			.where(AGRolePermission.PERMISSION_UUID.isEqual(AGUuid.getOrCreate(permission)))
			.getOne()
			.setActive(false)
			.save();
	}

	private void deactivateModulePermission(AGUser user, UUID permission, IModuleInstance<?> moduleInstance) {

		AGModuleInstancePermissionAssignment.TABLE//
			.createSelect()
			.where(AGModuleInstancePermissionAssignment.USER.isEqual(user))
			.where(AGModuleInstancePermissionAssignment.MODULE_INSTANCE.isEqual(moduleInstance.pk()))
			.where(AGModuleInstancePermissionAssignment.PERMISSION.isEqual(AGUuid.getOrCreate(permission)))
			.getOne()
			.setActive(false)
			.save();
	}

	private void assertAlicesPermissions(UUID...expectedPermissions) {

		assertPermissions(cacheAlice, Set.of(expectedPermissions));
	}

	private void assertBobsPermissions(UUID...expectedPermissions) {

		assertPermissions(cacheBob, Set.of(expectedPermissions));
	}

	private void assertPermissions(UserModulePermissionCache cache, Set<UUID> expectedPermissions) {

		for (UUID uuid: List.of(A, B, C)) {
			var actuallyHasPermission = cache.hasModulePermission(moduleInstance.pk(), uuid);
			if (expectedPermissions.contains(uuid)) {
				assertTrue("missing permission " + uuid, actuallyHasPermission);
			} else {
				assertFalse("unexpected permission " + uuid, actuallyHasPermission);
			}
		}
	}
}
