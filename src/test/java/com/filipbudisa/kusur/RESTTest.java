package com.filipbudisa.kusur;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { App.class }, webEnvironment
		= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RESTTest {

	@Test
	public void user() throws Exception {
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(new JSONObject().put("name","TEST").toString())
				.post("http://localhost:8080/user");

		assertEquals("TEST", response.path("name"));
	}

	@Test
	public void basic() throws Exception {
		int userA = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(new JSONObject().put("name", "A").toString())
				.post("http://localhost:8080/user")
				.path("id");

		int userB = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(new JSONObject().put("name", "B").toString())
				.post("http://localhost:8080/user")
				.path("id");

		int userC = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(new JSONObject().put("name", "C").toString())
				.post("http://localhost:8080/user")
				.path("id");

		JSONObject transfer = new JSONObject();

		transfer.put("type", "transfer")
				.put("from_user_id", userA)
				.put("to_user_id", userB)
				.put("amount", 100);
		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(transfer.toString())
				.post("http://localhost:8080/transaction");

		transfer.put("from_user_id", userB)
				.put("to_user_id", userC)
				.put("amount", 70);
		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(transfer.toString())
				.post("http://localhost:8080/transaction");

		transfer.put("from_user_id", userC)
				.put("to_user_id", userA)
				.put("amount", 120);
		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(transfer.toString())
				.post("http://localhost:8080/transaction");

		assertEquals(-20.0F, RestAssured.given()
				.contentType(ContentType.JSON)
				.get("http://localhost:8080/user/" + userA)
				.path("balance"), 0);

		assertEquals(-30.0F, RestAssured.given()
				.contentType(ContentType.JSON)
				.get("http://localhost:8080/user/" + userB)
				.path("balance"), 0);

		assertEquals(50.0F, RestAssured.given()
				.contentType(ContentType.JSON)
				.get("http://localhost:8080/user/" + userC)
				.path("balance"), 0);

		JSONObject taxi = new JSONObject()
				.put("type", "general")
				.put("amount", "63")
				.put("expense", new JSONObject()
					.put("distribution", "equal")
					.put("users", new JSONArray(new int[]{ userA, userB, userC })))
				.put("income", new JSONObject()
					.put("distribution", "equal")
					.put("users", new JSONArray(new int[]{ userB })));
		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(taxi.toString())
				.post("http://localhost:8080/transaction");

		assertEquals(-41.0F, RestAssured.given()
				.contentType(ContentType.JSON)
				.get("http://localhost:8080/user/" + userA)
				.path("balance"), 0);

		assertEquals(12.0F, RestAssured.given()
				.contentType(ContentType.JSON)
				.get("http://localhost:8080/user/" + userB)
				.path("balance"), 0);

		assertEquals(29.0F, RestAssured.given()
				.contentType(ContentType.JSON)
				.get("http://localhost:8080/user/" + userC)
				.path("balance"), 0);
	}

	@Test
	public void percentage() throws Exception {
		int userA = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(new JSONObject().put("name", "A").toString())
				.post("http://localhost:8080/user")
				.path("id");

		int userB = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(new JSONObject().put("name", "B").toString())
				.post("http://localhost:8080/user")
				.path("id");

		JSONObject transaction = new JSONObject()
				.put("type", "general")
				.put("amount", "63")
				.put("expense", new JSONObject()
						.put("distribution", "percentage")
						.put("users", new JSONArray(new JSONObject[]{
								new JSONObject().put("id", userA).put("value", 15),
								new JSONObject().put("id", userB).put("value", 85),
						})))
				.put("income", new JSONObject()
						.put("distribution", "equal")
						.put("users", new JSONArray(new int[]{ userA })));
		RestAssured.given()
				.contentType(ContentType.JSON)
				.body(transaction.toString())
				.post("http://localhost:8080/transaction");

		float balanceA = RestAssured.given()
				.contentType(ContentType.JSON)
				.get("http://localhost:8080/user/" + userA)
				.path("balance");

		float balanceB = RestAssured.given()
				.contentType(ContentType.JSON)
				.get("http://localhost:8080/user/" + userB)
				.path("balance");

		assertNotEquals(0.0F, balanceA, 0.001);
		assertNotEquals(0.0F, balanceB, 0.001);
		assertEquals(63.0F * 0.85F, balanceA, 0.001);
		assertEquals(-63.0F * 0.85F, balanceB, 0.001);
		assertEquals(0, balanceA + balanceB, 0.001);
	}
}
