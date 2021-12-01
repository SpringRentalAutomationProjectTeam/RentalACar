package com.etiya.RentACar.outServices;

import java.util.Random;

public class FindeksScoreService {

	
	public Integer getIndividualFindeksScore() {
		Random random = new Random();
		return random.nextInt(1300)+600;
	}
	public Integer getCorporateFindeksScore() {
		Random random = new Random();
		return random.nextInt(1300)+600;
		
	}
	
}
