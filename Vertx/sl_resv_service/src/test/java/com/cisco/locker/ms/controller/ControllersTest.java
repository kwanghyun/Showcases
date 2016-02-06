package com.cisco.locker.ms.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.cisco.locker.ms.model.Locker;
import com.cisco.locker.ms.model.LockerOrder;
import com.cisco.locker.ms.model.Reservation;
import com.cisco.locker.ms.util.Properties;
import com.cisco.locker.ms.web.Server;
import com.jayway.restassured.RestAssured;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

import static com.jayway.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(VertxUnitRunner.class)
public class ControllersTest {

	private Vertx vertx;

	private static final Logger logger = LoggerFactory.getLogger(ControllersTest.class);
	private static final String TEST_PKG_ID = "packageId1";
	private static final String TEST_SITE = "site1";
	private static final String TEST_BANK = "bank1";

	@BeforeClass
	public static void configureRestAssured() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = Integer.getInteger("http.port", 8080);
	}

	@AfterClass
	public static void unconfigureRestAssured() {
		RestAssured.reset();
	}

	@Before
	public void setUp(TestContext context) throws IOException {
		vertx = Vertx.vertx();
		DeploymentOptions options = new DeploymentOptions();

		// We pass the options as the second parameter of the deployVerticle
		// method.
		vertx.deployVerticle(Server.class.getName(), options, context.asyncAssertSuccess());

	}

	@After
	public void tearDown(TestContext context) {
		vertx.close(context.asyncAssertSuccess());
	}

	@Test
	public void testReservationRestAPIs() {

		int TEST_SIZE = 1;
		int TEST_CHG_SIZE = 2;
		String TEST_API_NAME = "/reservations/";

		JsonObject reservation = new JsonObject();
		reservation.put("site", TEST_SITE);
		reservation.put("bank", TEST_BANK);
		reservation.put("size", TEST_SIZE);
		reservation.put("packageId", TEST_PKG_ID);

		// DELETE : JUST IN CASE
		delete(Properties.API_ROOT + TEST_API_NAME + TEST_PKG_ID);

		// POST
		Reservation reserv = given().body(reservation.encode()).request().post(Properties.API_ROOT + TEST_API_NAME)
				.thenReturn().as(Reservation.class);

		assertThat(reserv.getSite()).isEqualToIgnoringCase(TEST_SITE);
		assertThat(reserv.getBank()).isEqualToIgnoringCase(TEST_BANK);
		assertThat(reserv.getSize() == TEST_SIZE);
		assertThat(reserv.getPackageId()).isEqualToIgnoringCase(TEST_PKG_ID);
		assertThat(reserv.getReservationDate()).isNotNull();
		assertThat(reserv.getExpiryDate()).isNotNull();

		get(Properties.API_ROOT + TEST_API_NAME + reserv.getPackageId()).then().assertThat().statusCode(200)
				.body("site", equalTo(TEST_SITE)).body("bank", equalTo(TEST_BANK))
				.body("packageId", equalTo(TEST_PKG_ID)).body("size", equalTo(TEST_SIZE));

		// PUT
		JsonObject modResv = new JsonObject();
		reservation.put("packageId", TEST_PKG_ID);
		modResv.put("size", TEST_CHG_SIZE);

		Reservation resultResrv = given().body(modResv.encode()).request()
				.put(Properties.API_ROOT + TEST_API_NAME + reserv.getPackageId()).thenReturn().as(Reservation.class);

		assertThat(resultResrv.getSize() == TEST_CHG_SIZE);

		// Delete
		delete(Properties.API_ROOT + TEST_API_NAME + TEST_PKG_ID).then().assertThat().statusCode(204);

		// GET : NOT FOUND
		get(Properties.API_ROOT + TEST_API_NAME + TEST_PKG_ID).then().assertThat().statusCode(404);
	}

	@Test
	public void testStagedRestAPIs() {

		int TEST_SIZE = 1;
		int TEST_CHG_SIZE = 2;
		String TEST_API_NAME = "/staged/";

		String TEST_EXPECTED_DATE = LocalDateTime.now().toString();
		String TEST_DEPOSIT_CODE = "D12345";
		String TEST_PICKUP_CODE = "P67890";

		int TEST_ORDER_TYPE = 2;

		JsonObject stagedOrder = new JsonObject();
		stagedOrder.put("site", TEST_SITE);
		stagedOrder.put("bank", TEST_BANK);
		stagedOrder.put("size", TEST_SIZE);
		stagedOrder.put("orderDate", TEST_EXPECTED_DATE);
		stagedOrder.put("packageId", TEST_PKG_ID);
		stagedOrder.put("expectedDepositDate", TEST_EXPECTED_DATE);
		stagedOrder.put("depositReleaseCode", TEST_DEPOSIT_CODE);
		stagedOrder.put("pickupReleaseCode", TEST_PICKUP_CODE);
		stagedOrder.put("orderType", TEST_ORDER_TYPE);

		// DELETE : JUST IN CASE
		delete(Properties.API_ROOT + TEST_API_NAME + TEST_PKG_ID);

		logger.info(TEST_API_NAME + " :: stagedOrder => " + stagedOrder.encodePrettily());

		// POST
		LockerOrder order = given().body(stagedOrder.encode()).request().post(Properties.API_ROOT + TEST_API_NAME)
				.thenReturn().as(LockerOrder.class);

		assertThat(order.getSite()).isEqualToIgnoringCase(TEST_SITE);
		assertThat(order.getBank()).isEqualToIgnoringCase(TEST_BANK);
		assertThat(order.getSize() == TEST_SIZE);
		assertThat(order.getOrderDate()).isEqualToIgnoringCase(TEST_EXPECTED_DATE);
		assertThat(order.getPackageId()).isEqualToIgnoringCase(TEST_PKG_ID);
		assertThat(order.getExpectedDepositDate()).isEqualToIgnoringCase(TEST_EXPECTED_DATE);
		assertThat(order.getDepositReleaseCode()).isEqualToIgnoringCase(TEST_DEPOSIT_CODE);
		assertThat(order.getPickupReleaseCode()).isEqualToIgnoringCase(TEST_PICKUP_CODE);
		assertThat(order.getOrderType() == TEST_ORDER_TYPE);

		get(Properties.API_ROOT + TEST_API_NAME + order.getPackageId()).then().assertThat().statusCode(200)
				.body("site", equalTo(TEST_SITE)).body("bank", equalTo(TEST_BANK))
				.body("packageId", equalTo(TEST_PKG_ID)).body("size", equalTo(TEST_SIZE))
				.body("expectedDepositDate", equalTo(TEST_EXPECTED_DATE))
				.body("depositReleaseCode", equalTo(TEST_DEPOSIT_CODE))
				.body("pickupReleaseCode", equalTo(TEST_PICKUP_CODE)).body("orderType", equalTo(TEST_ORDER_TYPE));

		logger.info(TEST_API_NAME + " :: POST :: DONE");

		// PUT
		JsonObject modifedOrder = new JsonObject();
		modifedOrder.put("packageId", TEST_PKG_ID);
		modifedOrder.put("size", TEST_CHG_SIZE);

		LockerOrder resultOrder = given().body(modifedOrder.encode()).request()
				.put(Properties.API_ROOT + TEST_API_NAME + order.getPackageId()).thenReturn().as(LockerOrder.class);

		assertThat(resultOrder.getSize() == TEST_CHG_SIZE);

		logger.info(TEST_API_NAME + " :: PUT :: DONE");

		// Delete
		delete(Properties.API_ROOT + TEST_API_NAME + TEST_PKG_ID).then().assertThat().statusCode(204);

		logger.info(TEST_API_NAME + " :: DELETE :: DONE");

		// GET : NOT FOUND
		get(Properties.API_ROOT + TEST_API_NAME + TEST_PKG_ID).then().assertThat().statusCode(404);
	}

	@Test
	public void testLockersRestAPIs() {

		int TEST_SIZE = 1;
		int TEST_CHG_SIZE = 2;
		String TEST_API_NAME = "/lockers/";

		int TEST_LOCKER_ID = 1;
		String TEST_EXPECTED_DATE = LocalDateTime.now().toString();
		String TEST_DEPOSIT_CODE = "D12345";
		String TEST_PICKUP_CODE = "P67890";

		int TEST_ORDER_TYPE = 2;

		JsonObject lockerObj = new JsonObject();
		lockerObj.put("lockerId", TEST_LOCKER_ID);
		lockerObj.put("site", TEST_SITE);
		lockerObj.put("bank", TEST_BANK);
		lockerObj.put("size", TEST_SIZE);
		lockerObj.put("orderDate", TEST_EXPECTED_DATE);
		lockerObj.put("packageId", TEST_PKG_ID);
		lockerObj.put("expectedDepositDate", TEST_EXPECTED_DATE);
		lockerObj.put("depositReleaseCode", TEST_DEPOSIT_CODE);
		lockerObj.put("pickupReleaseCode", TEST_PICKUP_CODE);
		lockerObj.put("orderType", TEST_ORDER_TYPE);

		// DELETE : JUST IN CASE
		delete(Properties.API_ROOT + TEST_API_NAME + TEST_PKG_ID);

		logger.info(TEST_API_NAME + " :: lockerObj => " + lockerObj.encodePrettily());

		// POST
		Locker locker = given().body(lockerObj.encode()).request().post(Properties.API_ROOT + TEST_API_NAME).thenReturn()
				.as(Locker.class);
		assertThat(locker.getLockerId() == TEST_LOCKER_ID);
		assertThat(locker.getSite()).isEqualToIgnoringCase(TEST_SITE);
		assertThat(locker.getBank()).isEqualToIgnoringCase(TEST_BANK);
		assertThat(locker.getSize() == TEST_SIZE);
		assertThat(locker.getOrderDate()).isEqualToIgnoringCase(TEST_EXPECTED_DATE);
		assertThat(locker.getPackageId()).isEqualToIgnoringCase(TEST_PKG_ID);
		assertThat(locker.getExpectedDepositDate()).isEqualToIgnoringCase(TEST_EXPECTED_DATE);
		assertThat(locker.getDepositReleaseCode()).isEqualToIgnoringCase(TEST_DEPOSIT_CODE);
		assertThat(locker.getPickupReleaseCode()).isEqualToIgnoringCase(TEST_PICKUP_CODE);
		assertThat(locker.getOrderType() == TEST_ORDER_TYPE);

		get(Properties.API_ROOT + TEST_API_NAME + locker.getPackageId()).then().assertThat().statusCode(200)
				.body("lockerId", equalTo(TEST_LOCKER_ID)).body("site", equalTo(TEST_SITE)).body("bank", equalTo(TEST_BANK))
				.body("packageId", equalTo(TEST_PKG_ID)).body("size", equalTo(TEST_SIZE))
				.body("expectedDepositDate", equalTo(TEST_EXPECTED_DATE))
				.body("depositReleaseCode", equalTo(TEST_DEPOSIT_CODE))
				.body("pickupReleaseCode", equalTo(TEST_PICKUP_CODE)).body("orderType", equalTo(TEST_ORDER_TYPE));

		logger.info(TEST_API_NAME + " :: POST :: DONE");

		// PUT
		JsonObject modifiedLocker = new JsonObject();
		modifiedLocker.put("packageId", TEST_PKG_ID);
		modifiedLocker.put("size", TEST_CHG_SIZE);

		Locker resultLocker = given().body(modifiedLocker.encode()).request()
				.put(Properties.API_ROOT + TEST_API_NAME + locker.getPackageId()).thenReturn().as(Locker.class);

		assertThat(resultLocker.getSize() == TEST_CHG_SIZE);

		logger.info(TEST_API_NAME + " :: PUT :: DONE");

		// Delete
		delete(Properties.API_ROOT + TEST_API_NAME + TEST_PKG_ID).then().assertThat().statusCode(204);

		logger.info(TEST_API_NAME + " :: DELETE :: DONE");

		// GET : NOT FOUND
		get(Properties.API_ROOT + TEST_API_NAME + TEST_PKG_ID).then().assertThat().statusCode(404);
	}

	// @Test
	public void test() {
		String TEST_API_NAME = "/reservations/";

		final int id = get(Properties.API_ROOT + TEST_API_NAME).then().assertThat().statusCode(200).extract().jsonPath()
				.getInt("find { it.name=='Bowmore 15 Years Laimrig' }.id");

		// Now get the individual resource and check the content
		get(Properties.API_ROOT + TEST_API_NAME + id).then().assertThat().statusCode(200)
				.body("name", equalTo("Bowmore 15 Years Laimrig")).body("origin", equalTo("Scotland, Islay"))
				.body("id", equalTo(id));
	}

}
