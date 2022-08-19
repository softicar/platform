package com.softicar.platform.core.module.uuid;

import com.softicar.platform.common.code.reference.point.ISourceCodeReferencePoint;
import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.uuid.IUuidAnnotated;
import com.softicar.platform.common.core.uuid.UuidBytes;
import com.softicar.platform.db.core.connection.DbConnectionOverrideScope;
import com.softicar.platform.db.core.connection.DbServerType;
import com.softicar.platform.db.core.database.DbCurrentDatabase;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.sql.statement.SqlSelectLock;
import com.softicar.platform.emf.module.IUuid;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.permission.CurrentEmfPermissionRegistry;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// TODO rename AGUuid into AGSourceCodeReferencePoint
public class AGUuid extends AGUuidGenerated implements IUuid, IEmfObject<AGUuid> {

	// TODO PLAT-1093 Because this cache is static, we need to ignore it during unit test execution.
	// TODO PLAT-1093 Reason: Static fields survive when a test finished, while the test database instance is lost and replaced by a new empty database.
	// TODO PLAT-1093 This should rather be a ThreadLocal / Singleton or so.
	private static final Map<UUID, Integer> CACHE = new ConcurrentHashMap<>();

	@Override
	public IDisplayString toDisplayWithoutId() {

		return SourceCodeReferencePoints//
			.getReferencePoint(getUuid())
			.map(ISourceCodeReferencePoint::toDisplay)
			.orElse(getStaticPermissionTitle(getUuid()));
	}

	@Override
	public UUID getUuid() {

		return UUID.fromString(getUuidString());
	}

	public static AGUuid getOrCreate(UUID uuid) {

		// get stub from global cache
		AGUuid uuidObject = getFromCache(uuid);
		if (uuidObject != null) {
			return uuidObject;
		}

		// get by UUID from table
		uuidObject = AGUuid.loadByUuidBytes(UuidBytes.asBytes(uuid));
		if (uuidObject != null) {
			CACHE.put(uuid, uuidObject.getId());
			return uuidObject;
		}

		// insert into table
		return insertUuid(uuid);
	}

	/**
	 * Converts this {@link AGUuid} into the {@link ISourceCodeReferencePoint}
	 * of the given {@link Class}.
	 *
	 * @param <T>
	 *            the type of {@link ISourceCodeReferencePoint}
	 * @param referencePointClass
	 *            the {@link Class} of the {@link ISourceCodeReferencePoint}
	 *            (never <i>null</i>)
	 * @return the converted {@link ISourceCodeReferencePoint} (never
	 *         <i>null</i>)
	 * @throws RuntimeException
	 *             if the conversion failed
	 */
	public <T extends ISourceCodeReferencePoint> T toReferencePoint(Class<T> referencePointClass) {

		return SourceCodeReferencePoints.getReferencePointOrThrow(getUuid(), referencePointClass);
	}

	public static AGUuid getOrCreate(ISourceCodeReferencePoint referencePoint) {

		return getOrCreate(referencePoint.getAnnotatedUuid());
	}

	public static AGUuid getOrCreate(Class<? extends ISourceCodeReferencePoint> referencePointClass) {

		return getOrCreate(SourceCodeReferencePoints.getUuidOrThrow(referencePointClass));
	}

	public static <T extends IDisplayable & IUuidAnnotated> UuidIndirectEntityCollection<T> createIndirectEntityCollection(Collection<T> elements) {

		return new UuidIndirectEntityCollection<>(elements);
	}

	private static AGUuid getFromCache(UUID uuid) {

		// TODO PLAT-1093 This is a hackish way to force-disable the cache during test execution. Should not be necessary.
		if (DbCurrentDatabase.get().getServerType() == DbServerType.H2_MEMORY) {
			return null;
		} else {
			Integer uuidId = CACHE.get(uuid);
			return uuidId != null? AGUuid.TABLE.getStub(uuidId) : null;
		}
	}

	private static AGUuid insertUuid(UUID uuid) {

		// we must use a separate connection for insertion here
		try (DbConnectionOverrideScope scope = new DbConnectionOverrideScope()) {
			try (DbTransaction transaction = new DbTransaction()) {
				byte[] uuidBytes = UuidBytes.asBytes(uuid);
				AGUuid uuidObject = AGUuid.TABLE//
					.createSelect(SqlSelectLock.FOR_UPDATE)
					.where(AGUuid.UUID_BYTES.isEqual(uuidBytes))
					.getFirst();
				if (uuidObject == null) {
					uuidObject = new AGUuid();
					uuidObject.setUuidString(uuid.toString());
					uuidObject.setUuidBytes(uuidBytes);
					uuidObject.save();
				}
				transaction.commit();
				return uuidObject;
			}
		}
	}

	// FIXME Should be replaced when permissions can be created at runtime
	private IDisplayString getStaticPermissionTitle(UUID uuid) {

		return CurrentEmfPermissionRegistry//
			.get()
			.getStaticPermission(uuid)
			.map(IEmfStaticPermission::getTitle)
			.orElse(IDisplayString.create(getUuidString()));
	}

	@Override
	public IDisplayString toDisplay() {

		return toDisplayWithoutId();
	}
}
