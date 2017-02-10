package com.abc;

import java.util.Date;
import java.util.Map;

public class SavingsAccount extends BaseAccount{

	public SavingsAccount(Customer customer, int accountId) {
		super(customer, accountId);
		// TODO Auto-generated constructor stub
	}


	
	public AccountType getAccountType() {
		return AccountType.SAVINGS;
	}

	
	
    public double interestEarned() {
    	double result=0;
    	Map<Date, Double> dateTotal = getDailyTransactionsSum();
    	
    	
    	double rate = 0.2;
    	double currentTotal = 0;

    	//The requirement is for FIRST 1000, the rate to be 0.1
    	//For now not exactly 1000, but a close aprox.
    	
    	for (Map.Entry<Date, Double> entry : dateTotal.entrySet()) {
    		currentTotal+=entry.getValue();
    		if(currentTotal<=1000){
    			rate=0.1;
    		}
    		
    		result+= InterestRateCalculator.calculateInterestAccruedDailyToDate(rate, entry.getValue(), entry.getKey());
    	}
    	
    	return result;
    }


	
}
