package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.db.sql.example.SqlExampleArticle;
import com.softicar.platform.db.sql.example.SqlExampleDelivery;
import com.softicar.platform.db.sql.example.SqlExampleDeliveryItem;
import com.softicar.platform.db.sql.example.SqlExampleSupplier;
import com.softicar.platform.db.sql.statement.SqlSelectLock;
import org.junit.Test;

public class SqlSelectJoinTest extends AbstractTest {

	@Test
	public void testSelectLock() {

		String sql = Sql//
			.from(SqlExampleSupplier.TABLE)
			.select(SqlExampleSupplier.NAME)
			.where(SqlExampleSupplier.ID.isEqual(13))
			.setLock(SqlSelectLock.FOR_UPDATE)
			.getStatement()
			.getText();

		String expectedSql = new StringBuilder()//
			.append("SELECT t0.`name` ")
			.append("FROM `example`.`supplier` t0 ")
			.append("WHERE (t0.`id` = ?) ")
			.append("FOR UPDATE")
			.toString();

		assertEquals(expectedSql, sql);
	}

	@Test
	public void testFreeJoinOverId() {

		String sql = Sql//
			.from(SqlExampleSupplier.TABLE)
			.joinReverse(SqlExampleDelivery.SUPPLIER)
			.select(SqlExampleDelivery.NUMBER)
			.getStatement()
			.getText();

		String expectedSql = new StringBuilder()//
			.append("SELECT t1.`number` ")
			.append("FROM `example`.`supplier` t0 ")
			.append("JOIN `example`.`delivery` t1 ON (t1.`supplier` = t0.`id`)")
			.toString();

		assertEquals(expectedSql, sql);
	}

	@Test
	public void testFreeJoinAndLeftJoin() {

		String sql = Sql//
			.from(SqlExampleSupplier.TABLE)
			.join(SqlExampleArticle.TABLE)
			.on(SqlExampleSupplier.NAME.isEqual(SqlExampleArticle.NUMBER))
			.joinLeft(SqlExampleDelivery.TABLE)
			.on()
			.t2()
			.t1(SqlExampleDelivery.NUMBER.isEqual(SqlExampleArticle.NUMBER))
			.select(SqlExampleDelivery.NUMBER)
			.getStatement()
			.getText();

		String expectedSql = new StringBuilder()//
			.append("SELECT t2.`number` ")
			.append("FROM `example`.`supplier` t0 ")
			.append("JOIN `example`.`article` t1 ON (t0.`name` = t1.`number`) ")
			.append("LEFT JOIN `example`.`delivery` t2 ON (t2.`number` = t1.`number`)")
			.toString();

		assertEquals(expectedSql, sql);
	}

	@Test
	public void testJoinAndLeftJoinOnTable() {

		String sql = Sql//
			.from(SqlExampleDeliveryItem.TABLE)
			.joinOnTable0(SqlExampleDeliveryItem.DELIVERY)
			.joinLeftOnTable0(SqlExampleDeliveryItem.ARTICLE)
			.joinOnTable1(SqlExampleDelivery.SUPPLIER)
			.select(SqlExampleSupplier.NAME)
			.getStatement()
			.getText();

		String expectedSql = new StringBuilder()//
			.append("SELECT t3.`name` ")
			.append("FROM `example`.`deliveryItem` t0 ")
			.append("JOIN `example`.`delivery` t1 ON (t0.`delivery` = t1.`id`) ")
			.append("LEFT JOIN `example`.`article` t2 ON (t0.`article` = t2.`id`) ")
			.append("JOIN `example`.`supplier` t3 ON (t1.`supplier` = t3.`id`)")
			.toString();

		assertEquals(expectedSql, sql);
	}

	@Test
	public void testJoinReverseAndJoinLeftReverseOnTable() {

		String sql = Sql//
			.from(SqlExampleSupplier.TABLE)
			.joinReverseOnTable0(SqlExampleDelivery.SUPPLIER)
			.joinLeftReverseOnTable1(SqlExampleDeliveryItem.DELIVERY)
			.select(SqlExampleDeliveryItem.QUANTITY)
			.getStatement()
			.getText();

		String expectedSql = new StringBuilder()//
			.append("SELECT t2.`quantity` ")
			.append("FROM `example`.`supplier` t0 ")
			.append("JOIN `example`.`delivery` t1 ON (t1.`supplier` = t0.`id`) ")
			.append("LEFT JOIN `example`.`deliveryItem` t2 ON (t2.`delivery` = t1.`id`)")
			.toString();

		assertEquals(expectedSql, sql);
	}
}
