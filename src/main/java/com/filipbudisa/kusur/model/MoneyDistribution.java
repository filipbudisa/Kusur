package com.filipbudisa.kusur.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MoneyDistribution {

	public enum MoneyDistributionType {
		EQUAL, PERCENTAGE, ABSOLUTE
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Enumerated(value = EnumType.ORDINAL)
	private MoneyDistributionType type;

	@OneToMany(mappedBy = "distribution")
	private List<MoneyDistributionPart> parts;

	protected MoneyDistribution(){ }

	public MoneyDistribution(MoneyDistributionType type){
		this.type = type;
		parts = new ArrayList<>();
	}

	public MoneyDistributionType getType(){
		return type;
	}

	public List<MoneyDistributionPart> getParts(){
		return parts;
	}

	public void addPart(MoneyDistributionPart part){
		parts.add(part);
	}
}
