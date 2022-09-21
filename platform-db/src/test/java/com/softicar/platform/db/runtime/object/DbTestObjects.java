package com.softicar.platform.db.runtime.object;

public class DbTestObjects {

	/**
	 * Directly updates the string field of a {@link DbTestObjects} in the
	 * database, circumventing the framework.
	 *
	 * @param id
	 *            the object ID
	 * @param value
	 *            the new string value
	 */
	public static void updateStringField(int id, String value) {

		DbTestObject.TABLE//
			.createUpdate()
			.set(DbTestObject.STRING_FIELD, value)
			.where(DbTestObject.ID_FIELD.isEqual(id))
			.execute();
	}

	/**
	 * Directly inserts a new object into the database, circumventing the
	 * framework.
	 * <p>
	 * The database table for {@link DbTestObject} will be created if necessary.
	 *
	 * @param value
	 *            the string value
	 * @return the id of the inserted object
	 */
	public static int insertObject(String value) {

		return DbTestObject.TABLE//
			.createInsert()
			.set(DbTestObject.STRING_FIELD, value)
			.execute();
	}
}
