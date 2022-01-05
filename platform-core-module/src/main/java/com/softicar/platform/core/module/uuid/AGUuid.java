package com.softicar.platform.core.module.uuid;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.uuid.IUuidAnnotated;
import com.softicar.platform.common.core.uuid.UuidBytes;
import com.softicar.platform.db.core.connection.DbConnectionOverrideScope;
import com.softicar.platform.db.core.connection.DbServerType;
import com.softicar.platform.db.core.database.DbCurrentDatabase;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.sql.statement.SqlSelectLock;
import com.softicar.platform.emf.authorization.role.CurrentEmfRoleRegistry;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRole;
import com.softicar.platform.emf.module.IUuid;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import com.softicar.platform.emf.source.code.reference.point.IEmfSourceCodeReferencePoint;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// TODO rename AGUuid into AGSourceCodeReferencePoint
public class AGUuid extends AGUuidGenerated implements IUuid, IEmfObject<AGUuid> {

	private static final Map<UUID, Integer> CACHE = new ConcurrentHashMap<>();

	@Override
	public IDisplayString toDisplayWithoutId() {

		return EmfSourceCodeReferencePoints//
			.getReferencePoint(getUuid())
			.map(IEmfSourceCodeReferencePoint::toDisplay)
			.orElse(getStaticRoleTitle(getUuid()));
	}

	// FIXME Should be replaced when roles can be created at runtime
	private static IDisplayString getStaticRoleTitle(UUID uuid) {

		return CurrentEmfRoleRegistry//
			.get()
			.getStaticRole(uuid)
			.map(IEmfStaticRole::getTitle)
			.orElse(DisplayString.EMPTY);
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
	 * Converts this {@link AGUuid} into the
	 * {@link IEmfSourceCodeReferencePoint} of the given {@link Class}.
	 *
	 * @param <T>
	 *            the type of {@link IEmfSourceCodeReferencePoint}
	 * @param referencePointClass
	 *            the {@link Class} of the {@link IEmfSourceCodeReferencePoint}
	 *            (never <i>null</i>)
	 * @return the converted {@link IEmfSourceCodeReferencePoint} (never
	 *         <i>null</i>)
	 * @throws RuntimeException
	 *             if the conversion failed
	 */
	public <T extends IEmfSourceCodeReferencePoint> T toReferencePoint(Class<T> referencePointClass) {

		return EmfSourceCodeReferencePoints.getReferencePointOrThrow(getUuid(), referencePointClass);
	}

	public static AGUuid getOrCreate(IEmfSourceCodeReferencePoint referencePoint) {

		return getOrCreate(referencePoint.getAnnotatedUuid());
	}

	public static AGUuid getOrCreate(Class<? extends IEmfSourceCodeReferencePoint> referencePointClass) {

		return getOrCreate(EmfSourceCodeReferencePoints.getUuidOrThrow(referencePointClass));
	}

	public static <T extends IDisplayable & IUuidAnnotated> UuidIndirectEntityCollection<T> createIndirectEntityCollection(Collection<T> elements) {

		return new UuidIndirectEntityCollection<>(elements);
	}

	/**
	 * TODO beautify this, see i50818
	 */
	private static AGUuid getFromCache(UUID uuid) {

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

	@Override
	public IDisplayString toDisplay() {

		return toDisplayWithoutId();
	}
}
