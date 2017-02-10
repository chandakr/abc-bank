package com.abc;

import java.util.Date;
import java.util.Map;

public class CheckingAccount extends BaseAccount{

	public CheckingAccount(Customer customer, int accountId) {
		
		super(customer, accountId);
		// TODO Auto-generated constructor stub
	}

	public AccountType getAccountType() {
		return AccountType.CHECKING;
	}

    public double interestEarned() {
    	double result=0;
    	Map<Date, Double> dateTotal = getDailyTransactionsSum();	
    	for (Map.Entry<Date, Double> entry : dateTotal.entrySet()) {
    		result+= InterestRateCalculator.calculateInterestAccruedDailyToDate(0.1, entry.getValue(), entry.getKey());
    	}
       return result;
    }

     
}
