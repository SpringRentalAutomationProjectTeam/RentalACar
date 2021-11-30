package com.etiya.RentACar.outServices;

import java.util.Random;

public class FindeksScoreService {

	
	public Integer getIndivicualFindeksScore() {
		Random random = new Random();
		return random.nextInt(1300)+600;
	}
	
}
