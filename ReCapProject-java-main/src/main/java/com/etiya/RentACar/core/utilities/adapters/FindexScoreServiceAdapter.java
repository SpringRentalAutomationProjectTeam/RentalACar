package com.etiya.RentACar.core.utilities.adapters;

import org.springframework.stereotype.Service;

import com.etiya.RentACar.outServices.FindeksScoreService;

@Service
public class FindexScoreServiceAdapter implements CustomerFindexScoreService {

	FindeksScoreService findeksScoreService = new FindeksScoreService();
	
	@Override
	public Integer getIndivicualFindeksScore() {
		return findeksScoreService.getIndivicualFindeksScore();
	}

}
