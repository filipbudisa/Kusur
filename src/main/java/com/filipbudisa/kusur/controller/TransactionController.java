package com.filipbudisa.kusur.controller;

import com.filipbudisa.kusur.exception.DataException;
import com.filipbudisa.kusur.model.*;
import com.filipbudisa.kusur.repository.MoneyDistributionRepository;
import com.filipbudisa.kusur.repository.TransactionRepository;
import com.filipbudisa.kusur.repository.UserRepository;
import com.filipbudisa.kusur.view.IncomeView;
import com.filipbudisa.kusur.view.TransferView;
import org.assertj.core.util.Lists;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionRepository transactionRepo;

	@Autowired
	private MoneyDistributionRepository distributionRepo;

	@Autowired
	private UserRepository userRepo;

	@PostMapping("/transfer")
	@ResponseStatus(HttpStatus.CREATED)
	public TransferView createTransfer(@RequestBody String body) throws Exception {
		JSONObject data = new JSONObject(body);

		long fromId = data.getLong("from_user_id");
		long toId = data.getLong("to_user_id");

		if(fromId == toId){
			throw new DataException("User can't transfer money to himself");
		}

		User from = userRepo.findById(fromId).get();
		User to = userRepo.findById(toId).get();
		Double amount = data.getDouble("amount");

		from.moneySub(amount);
		to.moneyAdd(amount);

		return new TransferView(
				transactionRepo.save(new Transfer(
						from,
						to,
						amount
				)
		));
	}

	@PostMapping("/income")
	@ResponseStatus(HttpStatus.CREATED)
	public IncomeView createIncome(@RequestBody String body) throws Exception {
		JSONObject data = new JSONObject(body);

		Double amount = data.getDouble("amount");

		JSONArray $users = data.getJSONArray("users");
		JSONObject $distribution = data.getJSONObject("distribution");

		List<Long> userIds = new ArrayList<>();
		for(int i = 0; i < $users.length(); i++){
			userIds.add($users.getLong(i));
		}

		Iterable<User> users = userRepo.findAllById(userIds);

		MoneyDistribution.MoneyDistributionType distroType;
		try{
			distroType = MoneyDistribution.MoneyDistributionType.valueOf($distribution.getString("type").toUpperCase());
		}catch(IllegalArgumentException e){
			throw new DataException("Unknown distribution type");
		}

		MoneyDistribution distribution = new MoneyDistribution(distroType);

		if(distroType != MoneyDistribution.MoneyDistributionType.EQUAL){
			JSONObject $parts = $distribution.getJSONObject("parts");

			double sum = 0;

			for(User user : users){
				double partValue = $parts.getDouble(String.valueOf(user.getId()));
				MoneyDistributionPart part = new MoneyDistributionPart(user, partValue);
				sum += partValue;

				double moneyValue;

				if(distroType == MoneyDistribution.MoneyDistributionType.PERCENTAGE){
					moneyValue = amount * partValue / 100.0;
				}else{
					moneyValue = partValue;
				}

				user.moneyAdd(moneyValue);
				distribution.addPart(part);
			}

			if(distroType == MoneyDistribution.MoneyDistributionType.PERCENTAGE && sum != 100.0){
				throw new DataException("Percentage sum must be 100");
			}else if(distroType == MoneyDistribution.MoneyDistributionType.ABSOLUTE && sum != amount){
				throw new DataException("Parts sum must be equal to amount");
			}
		}else{
			double moneyValue = amount / userIds.size();
			for(User user : users){
				user.moneyAdd(moneyValue);
			}
		}

		distribution = distributionRepo.save(distribution);

		Income income = new Income(amount, Lists.newArrayList(users),distribution);
		income = transactionRepo.save(income);

		return new IncomeView(income);
	}
}
