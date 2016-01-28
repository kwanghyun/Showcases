package com.cisco.locker.ms.util;

import java.time.LocalDateTime;

public class TimeUtils {

	public static LocalDateTime addDays(LocalDateTime currTime, int days) {
		return currTime.plusDays(days);
	}
	
	public static LocalDateTime toLocalDateTime(String input){
		if(input==null || input.equals(""))
			return null;
		
		return LocalDateTime.parse(input);
	}
}
