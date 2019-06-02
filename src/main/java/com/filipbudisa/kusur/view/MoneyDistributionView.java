package com.filipbudisa.kusur.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.filipbudisa.kusur.model.MoneyDistribution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoneyDistributionView {

	private String type;

	private List<MoneyDistributionPartView> parts;

	public MoneyDistributionView(MoneyDistribution distribution){
		this.type = distribution.getType().toString().toLowerCase();

		if(distribution.getType() != MoneyDistribution.MoneyDistributionType.EQUAL){
			parts = distribution.getParts()
					.parallelStream()
					.map(MoneyDistributionPartView::new)
					.collect(Collectors.toList());
		}
	}

	public String getType(){
		return type;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public List<MoneyDistributionPartView> getParts(){
		return parts;
	}
}
