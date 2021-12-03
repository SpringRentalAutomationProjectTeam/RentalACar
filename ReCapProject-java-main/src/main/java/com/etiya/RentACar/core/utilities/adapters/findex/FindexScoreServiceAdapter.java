package com.etiya.RentACar.core.utilities.adapters.findex;

import org.springframework.stereotype.Service;

import com.etiya.RentACar.outServices.FindeksScoreService;

@Service
public class FindexScoreServiceAdapter implements CustomerFindexScoreService {

	FindeksScoreService findeksScoreService = new FindeksScoreService();
	
	@Override
	public Integer getIndividualFindeksScore() {
		return findeksScoreService.getIndividualFindeksScore();
	}

	@Override
	public Integer getCorporateFindeksScore() {
		return findeksScoreService.getCorporateFindeksScore();
	}

}
