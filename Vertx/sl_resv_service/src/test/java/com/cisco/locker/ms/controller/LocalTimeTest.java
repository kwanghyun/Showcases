package com.cisco.locker.ms.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.Test;

import io.vertx.core.json.JsonObject;

public class LocalTimeTest {

//	@Test
	public void testLocalTimeToString() {
		JsonObject jo = new JsonObject();
		LocalDateTime currDate = LocalDateTime.now();
		System.out.println("currDate :: " + currDate);
		jo.put("localTime", currDate.toString());
		assertThat(jo.getString("localTime")).isNotNull();
	}

//	@Test
	public void testStringToLocalTime() {
		JsonObject jo = new JsonObject();
		LocalDateTime currDate = LocalDateTime.now();

		jo.put("localTime", currDate.toString());

		LocalDateTime ldt = LocalDateTime.parse(jo.getString("localTime"));
		System.out.println("localTime :: " + ldt);
	}

//	@Test
	public void test(){
		JsonObject json = new JsonObject();
		json.put("value", "");
		String value = json.getString("value").isEmpty() ?  "EMPTY" : json.getString("value");
		System.out.println(value);
		assertThat(value).isEqualTo("Empty");
	}
}
