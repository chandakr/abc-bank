package com.abc;

import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;

public class MaxiSavingsAccount extends BaseAccount{

	public MaxiSavingsAccount(Customer customer, int accountId) {
		super(customer, accountId);
		// TODO Auto-generated constructor stub
	}



	public AccountType getAccountType() {
		return AccountType.MAXI_SAVINGS;
	}

	
    public double interestEarned() {
    	double result=0;

    	Map<Date, Double> dateTotal = getDailyTransactionsSum();
 
		DateTime currentJodaDate = new DateTime();
		Date tenDaysPrior = currentJodaDate.minusDays(10).toDate();
		
    	double rate = 0.5;
    	
    	for (Date date : dateTotal.keySet()) {
    		if(date.compareTo(tenDaysPrior) > 0){
    			rate=0.1;
    			break;
    		}
    	}
    	
    	for (Map.Entry<Date, Double> entry : dateTotal.entrySet()) {
    		result+= InterestRateCalculator.calculateInterestAccruedDailyToDate(rate, entry.getValue(), entry.getKey());
    	}
  
    	return result;
    }


	
}
